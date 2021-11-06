package ru.sber.sping.data

import ru.sber.sping.data.entyties.Car
import ru.sber.sping.data.entyties.Note
import ru.sber.sping.data.entyties.User
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.sber.sping.data.dao.AddressBookRepository

@SpringBootApplication
class SpringDataApplication(
    private val dao: AddressBookRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val note1 = Note(
            user = User(
                firstName = "Alex",
                lastName = "Fedorov",
                address = "Russia",
                phone = "12431325",
                cars = mutableSetOf(
                    Car(
                        model = "VAZ",
                        series = "2106"
                    ),
                    Car(
                        model = "VAZ",
                        series = "2109"
                    )
                )
            )
        )
        val note2 = Note(
            user = User(
                firstName = "Nike",
                lastName = "Borzov",
                address = "Russia",
                phone = "96756456",
                cars = mutableSetOf(
                    Car(
                        model = "audi",
                        series = "Q7"
                    )
                )
            )
        )
        dao.saveAll(listOf(note1, note2))

        var allNotes = dao.findAll()
        println("все записи: $allNotes")

        var found = dao.findById(note1.id).get()
        println("Найдена запись: $found \n")

        found.user.firstName = "Edit"

        dao.save(found)

        dao.deleteById(note2.id)

        allNotes = dao.findAll()
        println("все записи: $allNotes")
    }
}

fun main(args: Array<String>) {
    runApplication<SpringDataApplication>(*args)
}
