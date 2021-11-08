package tasks.second

import java.io.File

class HTMLText(private var path:String) {
    private var size:Int
    private var text:StringBuilder = StringBuilder()
    private var absolutePosition:Int
    private var position:Int
    private var operationStack:ArrayDeque<Executable> = ArrayDeque()

    init {
        File(path).bufferedReader().lines().forEach { text.append(it) }
        absolutePosition = 0
        position = 0
        size = text.length
    }

    inner class Move(val pos:Int): Executable{
        override fun execute() {
          var currentPos:Int = position
          var currentAbsPos:Int = absolutePosition
          var strBuilder:StringBuilder = StringBuilder()
          var isBracket:Boolean = false
          while (true){
              if (isBracket){
                  strBuilder.append(text[currentAbsPos])
              }
          }

        }

        override fun undo() {

        }

    }


}