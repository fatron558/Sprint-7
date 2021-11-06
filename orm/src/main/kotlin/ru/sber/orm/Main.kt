import dao.AddressBookDAO
import entyties.*
import org.hibernate.cfg.Configuration

fun main() {
    val sessionFactory = Configuration().configure()
        .addAnnotatedClass(Note::class.java)
        .addAnnotatedClass(User::class.java)
        .addAnnotatedClass(Car::class.java)
        .buildSessionFactory()

    sessionFactory.use { sessionFactory ->
        val dao = AddressBookDAO(sessionFactory)

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

        dao.save(note1)

        dao.save(note2)

        var allNotes = dao.findAll()
        println("все записи: $allNotes")

        var found = dao.find(note1.id)
        println("Найдена запись: $found \n")

        found!!.user.firstName = "Edit"

        dao.update(found)

        dao.delete(note2.id)

        allNotes = dao.findAll()
        println("все записи: $allNotes")

    }
}