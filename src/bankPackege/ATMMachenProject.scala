package bankPackege
import scala.collection.mutable.Map


object ATMMachenProject {

  def main(args: Array[String]): Unit = {
    // Create ATM instance and provide the accounts map
    val atm = new ATM(Map(
      1234 -> Account(123456, "Mengistu", 123, 1234),
      2345 -> Account(234567, "Araya", 234, 2345),
      3456 -> Account(345678, "Kalayu", 345, 3456)
    ))

    // Welcome message and account number input
    println("Welcome to the ATM!")
    println("Enter your account number:")
    val accountNumber = scala.io.StdIn.readInt()

    // PIN input and account validation
    println("Enter your PIN:")
    val pin = scala.io.StdIn.readInt()

    val accountOption = atm.validateAccount(accountNumber, pin)

    // Handle account validation result
    accountOption match {
      case Some(account) =>
        println("\nWelcome, " + account.name + "!")

        // Display menu and handle user selection
        println("Please select an option:")
        println("1. Withdraw money")
        println("2. Deposit money")
        println("3. Check balance")
        println("4. Transfer money")
        println("5. Change PIN")
        println("6. Show mini-statement")
        println("7. Exit")

       val selection = scala.io.StdIn.readInt()
        selection match {
          case 1 ⇒
            println("enter the ammount to withdrowal ")
            val withdrowalAmount = scala.io.StdIn.readDouble()
            val withdrowalAmountOption = atm.withdrowMoney(account, withdrowalAmount)
            withdrowalAmountOption match {
              case Some(amount) ⇒ println("you have withdrow $" + amount)
              case None         ⇒ println(" Insuficient funds to withdrow $" + withdrowalAmount)
            }
          case 2 ⇒
            println(" enter the ammount to deposit")
            val depositAmount = scala.io.StdIn.readDouble()
            atm.depositMoney(account, depositAmount)
            println(" you have deposite  $" + depositAmount)
          case 3 ⇒
            println(" your current balance is $" + atm.checkBalance(account))
          case 4 =>
            println("Enter the recipient's account number:")
            val toAccountNumber = scala.io.StdIn.readInt()

            println("Enter the amount to transfer:")
            val transferAmount = scala.io.StdIn.readDouble()

            val updatedAccountOption = atm.transferMoney(account, toAccountNumber, transferAmount)

            updatedAccountOption match {
              case Some(updatedAccount) => println("Transfer successful. Your balance is now $" + updatedAccount.balance)
              case None => println("Transfer failed")
            }
          case _ ⇒
            println(" Invalid Selection : please try again")
        }
      case None ⇒
        println(" invalid Account Number :please try again !")

    }

  }

}