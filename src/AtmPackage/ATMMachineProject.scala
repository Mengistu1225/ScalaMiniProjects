package AtmPackage
import scala.collection.mutable.Map
import java.io.FileWriter
import java.io.BufferedWriter
import java.io.IOException

class Account(var accountNumber: Int, var name: String, var balance: Double) {
  var pin = 1234 // Changed `val` to `var` to allow changing the PIN
  override def toString: String = {
    s"Account Number: $accountNumber\n" +
      s"Name: $name\n" +
      s"Balance: $balance"
  }

  // Initialize an empty transaction history for the account
  var transactionHistory: List[Transaction] = List()
  def addToTransactionHistory(transaction: Transaction): Unit = {
    transactionHistory = transaction :: transactionHistory
    writeTransactionToLog(transaction)
  }
  private def writeTransactionToLog(transaction: Transaction): Unit = {
    try {
      val fileWriter = new FileWriter(LOG_FILE, true)
      val bufferedWriter = new BufferedWriter(fileWriter)

      bufferedWriter.write(transaction.toString)
      bufferedWriter.newLine()

      bufferedWriter.close()
    } catch {
      case e: IOException => println(s"Error writing to log file: ${e.getMessage}")
    }
  }
}

class ATM {
  private val LOG_FILE = "atm_log.txt"
  private val accounts: Map[Int, Account] = Map(
    1234 -> new Account(123456, "Mengistu", 123),
    2345 -> new Account(234567, "Araya", 234),
    3456 -> new Account(345678, "Kalayu", 345))

  def validateAccount(accountNumber: Int, pin: Int): Option[Account] = {
    accounts.get(accountNumber) match {
      case Some(account) => if (account.pin == pin) Some(account) else None
      case None          => None
    }
  }

  def withdrawMoney(account: Account, amount: Double): Option[Double] = {
    if (account.balance >= amount) {
      account.balance -= amount
      account.addToTransactionHistory(Transaction(getCurrentDate(), "Withdrawal", amount))
      Some(amount)
    } else {
      None
    }
  }

  def depositMoney(account: Account, amount: Double): Unit = {
    account.balance += amount
    account.addToTransactionHistory(Transaction(getCurrentDate(), "Deposit", amount))
  }
  private def getCurrentDate(): String = {
    val dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    dateFormat.format(new java.util.Date())
  }

  def checkBalance(account: Account): Option[Double] = {
    Some(account.balance)
  }

  def transferMoney(fromAccount: Account, toAccountNumber: Int, amount: Double): Option[Account] = {
    if (fromAccount.balance >= amount) {
      val toAccount = accounts.get(toAccountNumber)

      toAccount match {
        case Some(account) =>
          fromAccount.balance -= amount
          account.balance += amount
          Some(account)
        case None =>
          println("Invalid transfer destination account number")
          None
      }
    } else {
      println("Insufficient funds to transfer $" + amount)
      None
    }
  }

  def changePin(account: Account): Unit = {
    println("Enter your current PIN:")
    val currentPin = scala.io.StdIn.readInt()

    if (currentPin != account.pin) {
      println("Incorrect current PIN. Please try again.")
    } else {
      println("Enter your new PIN:")
      val newPin = scala.io.StdIn.readInt()
      account.pin = newPin
      println("PIN changed successfully.")
    }
  }

  def showMiniStatement(account: Account): Unit = {
    println("Mini-Statement:")
    println("----------------")

    val recentTransactions = account.transactionHistory.take(10)

    if (recentTransactions.isEmpty) {
      println("No transactions found.")
    } else {
      for (transaction <- recentTransactions) {
        println(transaction.toString)
      }
    }
  }
  def viewTransactionHistory(account: Account): Unit = {
    println("Transaction History:")
    println("-------------------")

    val transactions = account.transactionHistory

    if (transactions.isEmpty) {
      println("No transactions found.")
    } else {
      for (transaction <- transactions) {
        println(transaction.toString)
      }
    }
  }
  def viewLogFile(): Unit = {
    try {
      val bufferedSource = Source.fromFile(LOG_FILE)
      bufferedSource.getLines.foreach(println)
      bufferedSource.close()
    } catch {
      case e: Exception =>
        println(s"Error occurred while viewing the log file: ${e.getMessage}")
    }
  }
}

case class Transaction(date: String, transactionType: String, amount: Double)

object ATMMachineProject {
  def main(args: Array[String]): Unit = {
    val atm = new ATM()
    println("Welcome to ATM, your respected customer.")
    println("Enter your account number:")
    val accountNumber = scala.io.StdIn.readInt()
    println("Enter your PIN number:")
    val pin = scala.io.StdIn.readInt()

    val accountOption = atm.validateAccount(accountNumber, pin)
    accountOption match {
      case Some(account) =>
        println("Please select an option:")
        println("1. Withdraw money")
        println("2. Deposit money")
        println("3. Check balance")
        println("4. Transfer money")
        println("5. Change PIN")
        println("6. Show mini-statement")
        println("7. view transaction history")
        println("8. view log file")

        val selection = scala.io.StdIn.readInt()
        selection match {
          case 1 =>
            println("Enter the amount to withdraw:")
            val withdrawalAmount = scala.io.StdIn.readDouble()
            val withdrawalAmountOption = atm.withdrawMoney(account, withdrawalAmount)
            withdrawalAmountOption match {
              case Some(amount) => println("You have withdrawn $" + amount)
              case None         => println("Insufficient funds to withdraw $" + withdrawalAmount)
            }
          case 2 =>
            println("Enter the amount to deposit:")
            val depositAmount = scala.io.StdIn.readDouble()
            atm.depositMoney(account, depositAmount)
            println("You have deposited $" + depositAmount)
          case 3 =>
            println("Your current balance is $" + atm.checkBalance(account).getOrElse(0.0))
          case 4 =>
            println("Enter the recipient's account number:")
            val toAccountNumber = scala.io.StdIn.readInt()

            println("Enter the amount to transfer:")
            val transferAmount = scala.io.StdIn.readDouble()

            val updatedAccountOption = atm.transferMoney(account, toAccountNumber, transferAmount)

            updatedAccountOption match {
              case Some(updatedAccount) =>
                println("Transfer successful. Your balance is now $" + updatedAccount.balance)
              case None =>
                println("Transfer failed")
            }
          case 5 => atm.changePin(account)
          case 6 => atm.showMiniStatement(account)
          case 7 => atm.viewTransactionHistory(account) // New option to view transaction history
          case 8 => atm.viewLogFile() // New option to view the log file
          case _ => println("Invalid Selection: please try again")
        }
      case None => println("Invalid Account Number: please try again!")
    }
  }
}
