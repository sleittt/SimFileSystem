import java.io.File
import kotlin.math.max

//Текстовый файл состоит из цифр от 1 до 9, знаков операций «+», «–» и «*» (сложение, вычитание и умножение)
//и заглавных латинских букв A, B, C, D.
//Назовём правильной суммой строку, содержащую последовательность из одного или более десятичных чисел,
//в которой между соседними числами стоит ровно один знак «+» или «-» и нет других знаков.
//Примеры правильных сумм: «23», «115+6», «1980+12−123−51+3».
//Назовём результатом правильной суммы число, которое получится при выполнении записанных в соответствующей
//строке сложений. Например, результат правильной суммы «2+3» — число 5, а результат правильной суммы
// «1+2−8+3»— число −2.
//Найдите в данной строке расположенную непосредственно после буквы D правильную сумму с наибольшим результатом.
//В ответе запишите результат найденной суммы. Гарантируется, что ответ не превышает 2·10**9.
fun main() {
    val text = File("24.txt").readText()
    val regex = Regex("D[1-9]+(?:[+\\-][1-9]+)*")
    val matches = regex.findAll(text)

    var maxi = 0
    for (match in matches) {
        val expression = match.value.substring(1) // Убираем 'D' в начале
        val result = evaluateExpression(expression)
        maxi = max(result, maxi)
    }

    println(maxi)
}

fun evaluateExpression(expr: String): Int {
    // Разбиваем выражение на числа и операторы
    val parts = expr.split(Regex("(?=[+-])|(?<=[+-])"))

    var result = 0
    var currentOperator = "+"

    for (part in parts) {
        when {
            part == "+" -> currentOperator = "+"
            part == "-" -> currentOperator = "-"
            part.matches(Regex("\\d+")) -> {
                val number = part.toInt()
                result = when (currentOperator) {
                    "+" -> result + number
                    "-" -> result - number
                    else -> result
                }
            }
        }
    }

    return result
}