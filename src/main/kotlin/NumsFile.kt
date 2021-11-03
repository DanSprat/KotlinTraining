import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class NumsFile(private val path:String) {
    private var arrNumbs:ArrayList<Double> = ArrayList()
    var minimum: Double? = null
    get(){
        if (field == null){
            var min:Double = arrNumbs[0];
            for (i in 1 until arrNumbs.size){
                if (arrNumbs[i] < min){
                    min = arrNumbs[i]
                }
            }
            minimum = min
        }
        return field
    }

    var maximum: Double? = null
    get() {
        if (field == null){
            var max:Double = arrNumbs[0];
            for (i in 1 until arrNumbs.size){
                if (arrNumbs[i] > max){
                    max = arrNumbs[i]
                }
            }
            maximum = max
        }
        return field
    }

    var summ: Double? = null
    get(){
        if (field == null){
            var currentSumm:Double = 0.0
            arrNumbs.forEach{currentSumm+=it}
            summ = currentSumm;
        }
       return field
    }


    fun getByCondition(predicate: (Double) -> Boolean): MutableList<Double>? {
        var conditionArray:ArrayList<Double> = ArrayList()
        arrNumbs.forEach { if (predicate(it)) conditionArray.add(it) }
       return Collections.unmodifiableList(conditionArray)

    }
    init {
        val reader = File(path).bufferedReader()
        reader.lines().forEach { arrNumbs.add(it.toDouble()) }

    }




}