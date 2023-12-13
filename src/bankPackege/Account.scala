package bankPackege

class Account {
  trait Account {
    val accountNumber: Int
    val name: String
    var balance: Double
    val pin: Int

    def toString: String
  }

  object Account {
    def apply(accountNumber: Int, name: String, balance: Double, pin: Int): Account = {
      new AccountImpl(accountNumber, name, balance, pin)
    }
  }

  private class AccountImpl(accountNumber: Int, name: String, balance: Double, pin: Int) extends Account {
    override val accountNumber: Int = accountNumber
    override val name: String = name
    override var balance: Double = balance
    override val pin: Int = pin

    override def toString: String = {
      s"Account Number: $accountNumber\n" +
        s"Name: $name\n" +
        s"Balance: $balance"
    }
  }

}