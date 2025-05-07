package project.therasync.config

import project.therasync.data.repository.UserRepository
import project.therasync.service.JwtService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.net.URLEncoder

@Component
class OAuth2LoginSuccessHandler(
    private val jwtService: JwtService,
    private val userRepository: UserRepository
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val email = authentication.name
        val user = userRepository.findByEmail(email)

        if (user != null) {
            val token = jwtService.generateToken(user)
            val encodedToken = URLEncoder.encode(token, "UTF-8")
            response.sendRedirect("http://localhost:8080/auth/token")
        } else {
            response.sendRedirect("http://localhost:8080")
        }
    }
}