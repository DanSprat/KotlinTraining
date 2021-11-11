package tasks.second

import java.io.File
import java.util.*
import kotlin.collections.ArrayDeque

class HTMLText(private var path:String) {
    private var size:Int
    private var text:StringBuilder = StringBuilder()
    private var absolutePosition:Int
    private var position:Int
    private var operationStack:ArrayDeque<Executable> = ArrayDeque()
    private var cursor:Cursor

    companion object {
        var tags: ArrayList<String> = ArrayList(listOf("<b>","</b>","<i>","</i>","<u>","</u>"))
    }
    init {
        File(path).bufferedReader().lines().forEach { text.append(it) }
        absolutePosition = 3
        position = 0
        size = text.length
        cursor = Cursor(0,3)
    }

    fun moveTo(step:Int){
        var move:Move = Move(step)
        move.execute()
        operationStack.add(move)
    }

    fun undo(){
        var undoEx = operationStack.removeFirst()
        undoEx.undo()
    }

    fun remove(){
        Remove().also {
            it.execute()
            operationStack.addFirst(it)
        }
    }

    fun insert (char:Char) {
        Insert(char).also {
            it.execute()
            operationStack.addFirst(it)
        }
    }

    private inner class Cursor(var pos: Int,var absPos:Int)

    private inner class Move(val pos:Int): Executable {
        var absolutePosPrevious:Int = absolutePosition
        var posPrevious:Int = position

        override fun execute() {
          absolutePosPrevious = absolutePosition
          var diff = pos - cursor.pos
          var step:Int = if (diff > 0) 1 else -1

          var strBuilder:StringBuilder = StringBuilder()
          var isBracket:Boolean = false

          while (cursor.pos != pos){
              cursor.absPos+=step
              if (isBracket){
                  var arr:ArrayList<String> = ArrayList()
                  strBuilder.append(text[cursor.absPos])
                  if (strBuilder.length == 4 || text[cursor.absPos]=='>') {
                      if (strBuilder.toString() !in tags){
                          cursor.pos += strBuilder.length
                          strBuilder = StringBuilder()
                      }
                      isBracket = false
                  }
              } else {
                  if (text[cursor.absPos] == '<') {
                      strBuilder.append('<')
                      isBracket = true
                  } else {
                      cursor.pos+=step
                  }

              }
          }
        }

        override fun undo() {
            cursor.pos = posPrevious
            cursor.absPos = absolutePosPrevious
        }

    }

    private inner class Remove: Executable {

        var char:Char = text[cursor.absPos]
        var move:Move = Move(cursor.pos - 1)

        override fun execute() {
            text.deleteCharAt(cursor.absPos)
            move.execute()
        }

        override fun undo() {
            move.undo()
            text.setCharAt(cursor.absPos,char)
        }

    }

    private inner class Insert(val char: Char): Executable {
        var move:Move = Move(cursor.pos + 1)
        override fun execute() {
            text.setCharAt(cursor.absPos,char)
            move.execute()
        }

        override fun undo() {
            move.undo()
            text.deleteCharAt(cursor.absPos)
        }

    }



}