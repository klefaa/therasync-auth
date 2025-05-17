package project.therasync.service

import org.springframework.stereotype.Service
import project.therasync.config.ApiException.Companion.NOT_FOUND_EXCEPTION
import project.therasync.controller.ClientResponse
import project.therasync.data.dto.UpdateUserRequest
import project.therasync.data.model.Role
import project.therasync.data.model.User
import project.therasync.data.repository.UserRepository
import java.time.LocalDateTime

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun getUser(id: Long): User =
        userRepository
            .findById(id)
            .orElseThrow { NOT_FOUND_EXCEPTION }

    fun getUsers(ids: List<Long>): List<User> = userRepository.findAllById(ids)

    fun updateUser(
        id: Long,
        request: UpdateUserRequest,
    ): User {
        val user =
            getUser(id).copy(
                firstName = request.firstName,
                lastName = request.lastName,
                updatedAt = LocalDateTime.now(),
            )
        if (user.role == Role.DEFAULT) {
            user.role = request.role
        }
        return userRepository.save(user)
    }

    fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }

    fun findByName(name: String) =
        userRepository.findByFirstNameContainingIgnoreCase(name).map {
            ClientResponse(
                email = it.email,
                firstName = it.firstName,
                lastName = it.lastName,
                clientId = it.id,
            )
        }
}
