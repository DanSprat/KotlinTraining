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

    companion object {
        var tags: ArrayList<String> = ArrayList(Arrays.asList("<b>","</b>","<i>","</i>","<u>","</u>"))
    }
    init {
        File(path).bufferedReader().lines().forEach { text.append(it) }
        absolutePosition = 3
        position = 0
        size = text.length
    }

    fun MoveTo(step:Int){
        var move:Move = Move(step)
        move.execute()
        operationStack.add(move)
    }

    fun undo(){
        var undoEx = operationStack.removeFirst()
        undoEx.undo()
    }


    private inner class Move(val pos:Int): Executable{
        var absolutePosPrevious:Int = absolutePosition

        override fun execute() {
          absolutePosPrevious = absolutePosition
          var currentPos:Int = 0
          var currentAbsPos:Int = absolutePosition
          var strBuilder:StringBuilder = StringBuilder()
          var isBracket:Boolean = false

          while (currentPos < pos){
              absolutePosition++
              if (isBracket){
                  var arr:ArrayList<String> = ArrayList()
                  strBuilder.append(text[absolutePosition])
                  if (strBuilder.length == 4 || text[absolutePosition]=='>') {
                      if (strBuilder.toString() !in tags){
                          currentPos += strBuilder.length
                          strBuilder = StringBuilder()
                      }
                      isBracket = false
                  }
              } else {
                  if (text[absolutePosition] == '<') {
                      strBuilder.append('<')
                      isBracket = true
                  } else {
                      currentPos++
                  }

              }
          }
            position+=pos
        }

        override fun undo() {
            position -= pos
            absolutePosition = absolutePosPrevious
        }

    }


}