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



    private inner class Cursor(var pos: Int,var absPos:Int)

    private inner class Move(val pos:Int): Executable {
        var absolutePosPrevious:Int = absolutePosition
        var posPrevious:Int = pos

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
            cursor.pos+=pos
        }

        override fun undo() {
            cursor.pos = posPrevious
            cursor.absPos = absolutePosPrevious
        }

    }




}