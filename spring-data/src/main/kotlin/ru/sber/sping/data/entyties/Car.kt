package ru.sber.sping.data.entyties

import javax.persistence.*

@Entity
@Table(name = "cars")
class Car(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var model: String,
    var series: String,
    @ManyToMany(mappedBy = "cars")
    var user: MutableSet<User> = HashSet(),
) {
    override fun toString(): String {
        return "Car(id=$id, model='$model', series='$series')"
    }
}