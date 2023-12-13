package ToDoListPackage
import scala.collection.mutable.ListBuffer

object ToDoList {
  var toDoList = new ListBuffer[String]()

  def main(args: Array[String]): Unit = {
    var option = 0

    do {
      println("----------------------------------------------")
      println("To-Do List Manager")
      println("----------------------------------------------")
      println("1. Add a task")
      println("2. Remove a task")
      println("3. Mark a task as completed")
      println("4. Show all tasks")
      println("5. Show completed tasks")
      println("6. Show incomplete tasks")
      println("7. Exit")
      println("----------------------------------------------")
      print("Enter your choice: ")
      option = scala.io.StdIn.readInt()

      option match {
        case 1 => addTask()
        case 2 => removeTask()
        case 3 => markTaskAsCompleted()
        case 4 => showTasks()
        case 5 => showCompletedTasks()
        case 6 => showIncompleteTasks()
        case 7 => println("Goodbye!")
        case _ => println("Invalid option. Please enter a valid option (1-7).")
      }
    } while (option != 7)
  }

  def addTask(): Unit = {
    print("Enter a task to add: ")
    val task = scala.io.StdIn.readLine()
    toDoList += task
    println(task + " added to the list.")
  }

  def removeTask(): Unit = {
    if (toDoList.isEmpty) {
      println("The to-do list is empty.")
      return
    }

    print("Enter the task number to remove: ")
    val taskNumber = scala.io.StdIn.readInt()

    if (taskNumber < 1 || taskNumber > toDoList.length) {
      println("Invalid task number. Please enter a valid task number (1-" + toDoList.length + ").")
      return
    }

    val taskToRemove = toDoList(taskNumber - 1)
    toDoList -= taskToRemove
    println(taskToRemove + " removed from the list.")
  }

  def markTaskAsCompleted(): Unit = {
    if (toDoList.isEmpty) {
      println("The to-do list is empty.")
      return
    }

    print("Enter the task number to mark as completed: ")
    val taskNumber = scala.io.StdIn.readInt()

    if (taskNumber < 1 || taskNumber > toDoList.length) {
      println("Invalid task number. Please enter a valid task number (1-" + toDoList.length + ").")
      return
    }

    val taskToComplete = toDoList(taskNumber - 1)
    toDoList = toDoList.updated(taskNumber - 1, "[COMPLETED] " + taskToComplete)
    println(taskToComplete + " marked as completed.")
  }

  def showTasks(): Unit = {
    if (toDoList.isEmpty) {
      println("The to-do list is empty.")
      return
    }

    println("To-Do List:")
    toDoList.foreach(println)
  }

  def showCompletedTasks(): Unit = {
    val completedTasks = toDoList.filter(_.startsWith("[COMPLETED]"))
    if (completedTasks.isEmpty) {
      println("No completed tasks.")
      return
    }

    println("Completed Tasks:")
    completedTasks.foreach(println)
  }

  def showIncompleteTasks(): Unit = {
    val incompleteTasks = toDoList.filter(!_.startsWith("[COMPLETED]"))
    if (incompleteTasks.isEmpty) {
      println("No incomplete tasks.")
      return
    }

    println("Incomplete Tasks:")
    incompleteTasks.foreach(println)
  }
}

  














