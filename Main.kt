package search

import java.io.File

fun main(args: Array<String>) {

    var fileName = ""
    val persons = mutableListOf<List<String>>()
    for (i in args.indices) { if (args[i] == "--data" && i != args.lastIndex) fileName = args[i + 1] }
    File(fileName).forEachLine { persons.add(it.split(" ")) }

    val invIndex = HashMap<String, List<Int>>()
    for (i in persons) {
        for (j in i) {
            val res = mutableListOf<Int>()
            for (k in persons.indices) {
                for (l in persons[k].indices) {
                    if (j == persons[k][l]) res.add(k)
                }
            }
            invIndex[j.lowercase()] = res
        }
    }

    while (true) {
        printMenu()
        val choice = readln().toInt()
        when (choice) {
            1 -> findPersonStrategy(invIndex, persons)
            2 -> printAllPerson(persons)
            0 -> { println("\nBye!"); return }
            else -> println("\nIncorrect option! Try again.")
        }
    }
}

fun printMenu() {
    println("\n=== Menu ===")
    println("1. Find a person")
    println("2. Print all people")
    println("0. Exit")
}

fun printAllPerson(_persons: List<List<String>>) {
    println("\n=== List of people ===")
    for (i in _persons) println(i.joinToString(" "))
}

fun findPersonStrategy(_invIndex: HashMap<String, List<Int>>, _persons: List<List<String>>) {
    println("\nSelect a matching strategy: ALL, ANY, NONE")
    val select = readln().uppercase()
    println("\nEnter a name or email to search all suitable people.")
    val dataSearch = readln().lowercase().split(" ")

    val res = mutableListOf<List<Int>>()
    for (i in dataSearch) {
        if (_invIndex[i] != null) {
            res.add(_invIndex[i]!!)
        }
    }

    if (res.isNotEmpty()) {
        var result = setOf<Int>()
        when (select) {
            "ALL" -> {
                for (i in res) {
                    result = if (result.isEmpty()) i.toSet() else result intersect i.toSet()
                }
            }
            "ANY" -> {
                for (i in res) {
                    result = if (result.isEmpty()) i.toSet() else result + i.toSet()
                }
            }
            "NONE" -> {
                for (i in res) {
                    result = if (result.isEmpty()) i.toSet() else result + i.toSet()
                }
                result = _persons.indices.toSet() subtract result
            }

        }

        if (result.isNotEmpty()) {
            println("${result.size} person found:")
            for (i in result) {
                println(_persons[i].joinToString(" "))
            }
        } else println("No matching people found.")

    } else println ("No matching people found.")




}



