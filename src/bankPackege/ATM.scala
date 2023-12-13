package bankPackege

class ATM {
  trait ATM {
    def validateAccount(accountNumber: Int, pin: Int): Option[Account]
    def withdrawMoney(account: Account, amount: Double): Option[Double]
    def depositMoney(account: Account, amount: Double): Unit
    def checkBalance(account: Account): Option[Double]
    def transferMoney(fromAccount: Account, toAccountNumber: Int, amount: Double): Option[Account]
    def changePin(account: Account): Unit
    def showMiniStatement(account: Account): Unit
  }

  object ATM {
    def apply(accounts: Map[Int, Account]): ATM = {
      new ATMImpl(accounts)
    }
  }

  private class ATMImpl(private val accounts: Map[Int, Account]) extends ATM {
    override def validateAccount(accountNumber: Int, pin: Int): Option[Account] = {
      accounts.get(accountNumber) match {
        case Some(account) if account.pin == pin => Some(account)
        case _                                   => None
      }
    }

    override def withdrawMoney(account: Account, amount: Double): Option[Double] = {
      if (account.balance >= amount) {
        account.balance -= amount
        Some(amount)
      } else {
        None
      }
    }

    override def depositMoney(account: Account, amount: Double): Unit = {
      account.balance += amount
    }

    override def checkBalance(account: Account): Option[Double] = {
      Some(account.balance)
    }

    override def transferMoney(fromAccount: Account, toAccountNumber: Int, amount: Double): Option[Account] = {
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

    override def changePin(account: Account): Unit = {
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

    override def showMiniStatement(account: Account): Unit = {
      println("Mini-Statement:")
      println("----------------")

      // Retrieve the last 10 transactions from the account's history
      val recentTransactions = account.transactionHistory.take(10)

      if (recentTransactions.isEmpty) {
        println("No transactions found.")
      } else {
        for (transaction <- recentTransactions) {
          println(transaction.toString)
        }
      }
    }
  }

}