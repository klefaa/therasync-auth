package project.therasync.data.repository

import project.therasync.data.model.User
import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?

    fun findByFirstNameContainingIgnoreCase(name: String): List<User>
}
