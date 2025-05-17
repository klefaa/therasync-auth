package project.therasync.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import project.therasync.data.dto.GetUsersRequest
import project.therasync.data.dto.UpdateUserRequest
import project.therasync.data.model.User
import project.therasync.service.UserService

@RestController
@RequestMapping("/api/users")
class UserProfileController(
    private val userService: UserService,
) {
    @GetMapping("/{id}")
    fun getUser(
        @PathVariable id: Long,
    ): User = userService.getUser(id)

    @PostMapping
    fun getUsers(
        @RequestBody request: GetUsersRequest,
    ): List<User> = userService.getUsers(request.ids)

    @PutMapping
    fun updateUser(
        @RequestHeader("X-Client-Id") clientId: Long,
        @RequestBody request: UpdateUserRequest,
    ): User = userService.updateUser(clientId, request)

    @DeleteMapping
    fun deleteUser(
        @RequestHeader("X-Client-Id") clientId: Long,
    ): ResponseEntity<Void> {
        userService.deleteUser(clientId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/search")
    fun searchClients(
        @RequestParam name: String,
    ): List<ClientResponse> = userService.findByName(name)
}

data class ClientResponse(
    val clientId: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
)
