fun main(args: Array<String>) {
    val fileNums:NumsFile = NumsFile("C:\\Users\\popov\\Desktop\\numbers.txt")

    var max: Double? = fileNums.maximum
    var min: Double? = fileNums.minimum
    var summ:Double? = fileNums.summ

    var arr = fileNums.getByCondition { it > 5.0 }
    println("Maximum double in file is $max")
    println("Minimum double in file is $min")
    println("Summ of doubles in file is $summ")

    arr?.forEach{ print("$it ")}
}