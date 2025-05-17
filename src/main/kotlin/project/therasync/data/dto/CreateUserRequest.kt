package project.therasync.data.dto

import project.therasync.data.model.Role

data class UpdateUserRequest(
    val firstName: String,
    val lastName: String,
    val role: Role,
)

data class GetUsersRequest(
    val ids: List<Long>,
)
