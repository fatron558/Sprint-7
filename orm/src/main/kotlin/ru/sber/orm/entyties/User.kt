package entyties

import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var firstName: String,
    var lastName: String,
    var address: String,
    var phone: String,
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    var note: Note? = null,
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinTable(name = "user_car", joinColumns = [JoinColumn(name = "user_id")], inverseJoinColumns = [JoinColumn(name = "car_id")])
    var cars: MutableSet<Car> = HashSet()
) {
    override fun toString(): String {
        return "User(id=$id, firstName='$firstName', lastName='$lastName', address='$address', phone='$phone', cars=$cars)"
    }
}