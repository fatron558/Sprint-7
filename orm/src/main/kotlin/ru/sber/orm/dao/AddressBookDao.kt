package dao

import entyties.Note
import org.hibernate.SessionFactory

class AddressBookDAO(
    private val sessionFactory: SessionFactory,
) {
    fun save(note: Note) {
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.save(note)
            session.transaction.commit()
        }
    }

    fun find(id: Long): Note? {
        val result: Note?
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result = session.get(Note::class.java, id)
            session.transaction.commit()
        }
        return result
    }

    fun findAll(): List<Note> {
        val result: List<Note>
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result = session.createQuery("from Note").list() as List<Note>
            session.transaction.commit()
        }
        return result
    }

    fun update(note: Note) {
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.merge(note)
            session.transaction.commit()
        }
    }

    fun delete(id: Long) {
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            val note = session.get(Note::class.java, id)
            session.delete(note)
            session.transaction.commit()
        }
    }
}