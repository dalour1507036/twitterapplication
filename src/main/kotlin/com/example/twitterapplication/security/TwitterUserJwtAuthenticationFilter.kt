package com.example.twitterapplication.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class TwitterUserJwtAuthenticationFilter(
    private val jwtDecoder: JwtDecoder,
    private val decodedJwtToTwitterUserPrincipal: DecodedJwtToTwitterUserPrincipal
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        extractTokenFromRequest(request)
            .map(jwtDecoder::decode)
            .map(decodedJwtToTwitterUserPrincipal::convert)
            .map(::TwitterUserPrincipalAuthenticationToken)
            .ifPresent { authentication -> SecurityContextHolder.getContext().authentication = authentication }

        filterChain.doFilter(request,response)
    }

    private fun extractTokenFromRequest(request: HttpServletRequest): Optional<String> {
        val token = request.getHeader("Authorization")
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return Optional.of(token.substring(7))
        }
        return Optional.empty()
    }
}