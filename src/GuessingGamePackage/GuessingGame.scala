package GuessingGamePackage

import scala.util.Random

object GuessingGame {
  def main(args: Array[String]) {
    var random=new Random()
    val targetedNumber = random.nextInt(100) + 1

    
    var attempts=0;
    var guess = -1
    
    while(guess != targetedNumber && attempts <10){
      println("enter the guessing number")
      var guess=scala.io.StdIn.readInt()
      if(guess < targetedNumber){
        println(" Your guess is too low. Try again")
      }
      else if(guess > targetedNumber){
        println("Your guess is too high. Try again.")
      }
      else{
        println("Congratulations, you guessed the number!")
      }
      attempts +=1
      if(attempts == 10){
        println("You ran out of attempts. The correct number was "+targetedNumber)
      }
    }
    
  }
  
}