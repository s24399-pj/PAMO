package org.example

import kotlin.math.PI
import kotlin.random.Random

/**
 * Exercise - Collections - 1
 *
 * You have a list of "green" numbers and a list of "red" numbers. Complete the code to print how many numbers there are in total.
 */
fun exercise1() {
    val greenNumbers = listOf(1, 4, 23)
    val redNumbers = listOf(17, 2)
    val totalNumbers = greenNumbers.size + redNumbers.size
    println("Total numbers: $totalNumbers")
}

/**
 * Exercise - Collections - 2
 *
 * You have a set of protocols supported by your server. A user requests to use a particular protocol.
 * Complete the program to check whether the requested protocol is supported or not (isSupported must be a Boolean value).
 */
fun exercise2() {
    val SUPPORTED = setOf("HTTP", "HTTPS", "FTP")
    val requested = "smtp"
    val isSupported = requested.uppercase() in SUPPORTED
    println("Support for $requested: $isSupported")
}

/**
 * Exercise - Loops - 1
 *
 * You have a program that counts pizza slices until there's a whole pizza with 8 slices. Refactor this program in two ways:
 * - Use a while loop.
 * - Use a do-while loop.
 */
fun exercise3() {
    var pizzaSlices = 0
    while (pizzaSlices < 8) {
        pizzaSlices++
        println("There's only $pizzaSlices slice/s of pizza :(")
    }
    println("There are $pizzaSlices slices of pizza. Hooray! We have a whole pizza! :D")
}

/**
 * Exercise - Loops - 2
 *
 * Write a program that simulates the Fizz buzz game. Your task is to print numbers from 1 to 100 incrementally,
 * replacing any number divisible by three with the word "fizz", and any number divisible by five with the word "buzz".
 * Any number divisible by both 3 and 5 must be replaced with the word "fizzbuzz".
 *
 * Hint 1: Use a for loop to count numbers and a when expression to decide what to print at each step.
 * Hint 2: Use the modulo operator (%) to return the remainder of a number being divided.
 * Use the equality operator (==) to check if the remainder equals zero.
 */
fun exercise4() {
    for (i in 1..100) {
        when {
            i % 3 == 0 && i % 5 == 0 -> println("fizzbuzz")
            i % 3 == 0 -> println("fizz")
            i % 5 == 0 -> println("buzz")
            else -> println(i)
        }
    }
}

/**
 * Exercise - Loops - 3
 *
 * You have a list of words. Use for and if to print only the words that start with the letter l.
 *
 * Hint: Use the .startsWith() function for String type.
 */
fun exercise5() {
    val words = listOf("dinosaur", "limousine", "magazine", "language")
    for (word in words) {
        if (word.startsWith("l")) {
            println(word)
        }
    }
}

/**
 * Exercise - Functions - 1
 *
 * Write a function called circleArea that takes the radius of a circle in integer format as a parameter
 * and outputs the area of that circle.
 *
 * @param radius The radius of the circle as an integer
 * @return The area of the circle as a Double
 */
fun circleArea(radius: Int): Double {
    return PI * radius * radius
}

fun exercise6() {
    println(circleArea(2))
}

/**
 * Exercise - Functions - 2
 *
 * Rewrite the circleArea function from the previous exercise as a single-expression function.
 *
 * @param radius The radius of the circle as an integer
 * @return The area of the circle as a Double
 */
fun circleArea2(radius: Int): Double = PI * radius * radius

fun exercise7() {
    println(circleArea2(2))
}

/**
 * Exercise - Functions - 3
 *
 * You have a function that translates a time interval given in hours, minutes, and seconds into seconds.
 * In most cases, you need to pass only one or two function parameters while the rest are equal to 0.
 * Improve the function and the code that calls it by using default parameter values and named arguments
 * so that the code is easier to read.
 *
 * @param hours The number of hours in the interval (default 0)
 * @param minutes The number of minutes in the interval (default 0)
 * @param seconds The number of seconds in the interval (default 0)
 * @return The total number of seconds in the interval
 */
fun intervalInSeconds(hours: Int = 0, minutes: Int = 0, seconds: Int = 0): Int =
    ((hours * 60) + minutes) * 60 + seconds

fun exercise8() {
    println(intervalInSeconds(1, 20, 15))
    println(intervalInSeconds(minutes = 1, seconds = 25))
    println(intervalInSeconds(hours = 2))
    println(intervalInSeconds(minutes = 10))
    println(intervalInSeconds(hours = 1, seconds = 1))
}

/**
 * Exercise - Classes - 1
 *
 * Define a data class Employee with two properties: one for a name, and another for a salary.
 * Make sure that the property for salary is mutable, otherwise you won't get a salary boost at the end of the year!
 * The main function demonstrates how you can use this data class.
 */
data class Employee(val name: String, var salary: Int)

fun exercise9() {
    val emp = Employee("Mary", 20)
    println(emp)
    emp.salary += 10
    println(emp)
}

/**
 * Exercise - Classes - 2
 *
 * Declare the additional data classes that are needed for this code to compile.
 */

data class Person(val name: Name, val address: Address, val ownsAPet: Boolean = true)
data class Name(val firstName: String, val lastName: String)
data class City(val name: String, val country: String)
data class Address(val street: String, val city: City)


fun exercise10() {
    val person = Person(
        Name("John", "Smith"),
        Address("123 Fake Street", City("Springfield", "US")),
        ownsAPet = false
    )
    println(person)
}

/**
 * Exercise - Classes - 3
 *
 * To test your code, you need a generator that can create random employees. Define a RandomEmployeeGenerator class
 * with a fixed list of potential names (inside the class body). Configure the class with a minimum and maximum salary
 * (inside the class header). In the class body, define the generateEmployee() function. Once again, the main function
 * demonstrates how you can use this class.
 *
 * Hint 1: Lists have an extension function called .random() that returns a random item within a list.
 * Hint 2: Random.nextInt(from = ..., until = ...) gives you a random Int number within specified limits.
 */
class RandomEmployeeGenerator(var minSalary: Int, var maxSalary: Int) {
    private val names = listOf("John", "Mary", "Bob", "Alice", "Tom", "Kate")

    fun generateEmployee(): Employee {
        val randomName = names.random()
        val randomSalary = Random.nextInt(from = minSalary, until = maxSalary + 1)
        return Employee(randomName, randomSalary)
    }
}

fun exercise11() {
    val empGen = RandomEmployeeGenerator(10, 30)
    println(empGen.generateEmployee())
    println(empGen.generateEmployee())
    println(empGen.generateEmployee())
    empGen.minSalary = 50
    empGen.maxSalary = 100
    println(empGen.generateEmployee())
}

/**
 * Exercise - Null safety - 1
 *
 * You have the employeeById function that gives you access to a database of employees of a company.
 * Unfortunately, this function returns a value of the Employee? type, so the result can be null.
 * Your goal is to write a function that returns the salary of an employee when their id is provided,
 * or 0 if the employee is missing from the database.
 *
 * @param id The employee ID to look up
 * @return The employee's salary, or 0 if the employee does not exist
 */
fun employeeById(id: Int) = when (id) {
    1 -> Employee("Mary", 20)
    2 -> null
    3 -> Employee("John", 21)
    4 -> Employee("Ann", 23)
    else -> null
}

fun salaryById(id: Int): Int = employeeById(id)?.salary ?: 0

fun main() {
    println("=== Exercise 1 - Collections - 1 ===")
    exercise1()

    println("\n=== Exercise 2 - Collections - 2 ===")
    exercise2()

    println("\n=== Exercise 3 - Loops - 1 ===")
    exercise3()

    println("\n=== Exercise 4 - Loops - 2 ===")
    exercise4()

    println("\n=== Exercise 5 - Loops - 3 ===")
    exercise5()

    println("\n=== Exercise 6 - Functions - 1 ===")
    exercise6()

    println("\n=== Exercise 7 - Functions - 2 ===")
    exercise7()

    println("\n=== Exercise 8 - Functions - 3 ===")
    exercise8()

    println("\n=== Exercise 9 - Classes - 1 ===")
    exercise9()

    println("\n=== Exercise 10 - Classes - 2 ===")
    exercise10()

    println("\n=== Exercise 11 - Classes - 3 ===")
    exercise11()

    println("\n=== Exercise 12 - Null safety - 1 ===")
    println("Sum of salaries for employees with IDs 1-5: ${(1..5).sumOf { id -> salaryById(id) }}")
    println("Individual salaries:")
    for (id in 1..5) {
        println("Employee $id: ${salaryById(id)}")
    }
}