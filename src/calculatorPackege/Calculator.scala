package calculatorPackege

object Calculator {
  def main(args:Array[String]):Unit={
   val num1 = readDouble("Enter first number: ")
    val operator = readLine("Enter operator (+, -, *, /): ")
    val num2 = readDouble("Enter second number: ")

    val result = operator match {
      case "+" => num1 + num2
      case "-" => num1 - num2
      case "*" => num1 * num2
      case "/" => num1 / num2
      case _ => throw new IllegalArgumentException("Invalid operator")
    }

    println("Result: " + result)
  }
  
  def readDouble(prompt: String): Double = {
    print(prompt)
    val input = scala.io.StdIn.readLine()
    try {
      input.toDouble
    } catch {
      case e: NumberFormatException =>
        println("Invalid number format. Please enter a valid number.")
        readDouble(prompt)
    }
  }
}