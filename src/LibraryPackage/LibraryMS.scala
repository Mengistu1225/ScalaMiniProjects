package LibraryPackage

import java.time.LocalDate
import scala.collection.mutable.ListBuffer

case class LibraryBook(id: Int, title: String, author: String, publicationDate: LocalDate)
class Library {
  private val books: ListBuffer[LibraryBook] = ListBuffer()

  def addBook(book: LibraryBook): Unit = {
    books += book
    println(s"Added book: ${book.title} by ${book.author}")
  }

  def removeBook(id: Int): Unit = {
    val bookIndex = books.indexWhere(_.id == id)
    if (bookIndex >= 0) {
      val removedBook = books.remove(bookIndex)
      println(s"Removed book: ${removedBook.title} by ${removedBook.author}")
    } else {
      println("Book not found.")
    }
  }

  def displayAllBooks(): Unit = {
    if (books.isEmpty) {
      println("No books found.")
    } else {
      println("All Books:")
      books.foreach(book => println(s"${book.id}: ${book.title} by ${book.author}"))
    }
  }

}

object LibraryMS {
  def main(args: Array[String]): Unit = {
    val library = new Library()

    val book1 = LibraryBook(1, "Book 1", "Author 1", LocalDate.of(2022, 1, 1))
    val book2 = LibraryBook(2, "Book 2", "Author 2", LocalDate.of(2022, 2, 1))
    val book3 = LibraryBook(3, "Book 3", "Author 3", LocalDate.of(2022, 3, 1))
    val book4 = LibraryBook(4, "Book 4", "Author 4", LocalDate.of(2022, 4, 1))

    library.addBook(book1)
    library.addBook(book2)
    library.addBook(book3)

    library.displayAllBooks()

    library.removeBook(2)

    library.addBook(book4)

    library.displayAllBooks()
  }

}