package project.therasync.service

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import project.therasync.data.model.Role.DEFAULT
import project.therasync.data.model.User
import project.therasync.data.repository.UserRepository

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository,
) : DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)
        println(oAuth2User.attributes)
        val email = oAuth2User.attributes["default_email"] as String
        val firstName = oAuth2User.attributes["first_name"] as String
        val lastName = oAuth2User.attributes["last_name"] as String
        val avatarId = oAuth2User.attributes["default_avatar_id"] as? String
        val avatarUrl = avatarId?.let { "https://avatars.yandex.net/get-yapic/$it/islands-200" }

        val user =
            userRepository.findByEmail(email) ?: userRepository.save(
                User(
                    email = email,
                    role = DEFAULT,
                    firstName = firstName,
                    lastName = lastName,
                    image = avatarUrl,
                ),
            )

        val authorities = listOf(SimpleGrantedAuthority("ROLE_${user.role}"))
        return DefaultOAuth2User(authorities, oAuth2User.attributes, "default_email")
    }
}
