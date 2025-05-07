package project.therasync.service

import project.therasync.data.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*


@Service
class JwtService {
    private val secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode("dyqwdSuc7WALbIZptYDUHdYVOxhYfmNC4EQpI7zKDPI="))

    fun generateToken(user: User): String {
        val now = Instant.now()
        val expiration = now.plusSeconds(3600*24)

        return Jwts.builder()
            .subject(user.email)
            .claim("role", user.role)
            .claim("clientId", user.id.toString())
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiration))
            .signWith(secretKey)
            .compact()
    }

    fun validateToken(token: String): Claims? {
        return try {
            Jwts.parser().verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (e: Exception) {
            null
        }
    }
}