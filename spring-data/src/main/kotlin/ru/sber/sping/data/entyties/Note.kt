package ru.sber.sping.data.entyties

import javax.persistence.*

@Entity
@Table(name = "notes")
class Note(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id")
    var user: User,
) {
    override fun toString(): String {
        return "Note(id=$id, user=$user)"
    }
}