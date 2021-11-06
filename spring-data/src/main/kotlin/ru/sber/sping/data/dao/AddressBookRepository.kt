package ru.sber.sping.data.dao

import ru.sber.sping.data.entyties.Note
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressBookRepository : CrudRepository<Note, Long>