package com.example.twitterapplication.config


import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import java.util.concurrent.TimeUnit
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(1)
class TimingFilter : OncePerRequestFilter() {
    var formattedStartTime: String? = null
    var formattedEndTime: String? = null
    var executionTime: Long = 0

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val startTime = System.currentTimeMillis()
        formattedStartTime = timeFormatter(startTime)
        MDC.put("hitTime", formattedStartTime)

        MDC.put("logId", UUID.randomUUID().toString())
        MDC.put("apiUrl", request.requestURL.toString())
        MDC.put("apiMethod", request.method)

        filterChain.doFilter(request, response)

        MDC.put("status", response.status.toString())

        val endTime = System.currentTimeMillis()
        formattedEndTime = timeFormatter(endTime)
        MDC.put("endTime", formattedEndTime)

        executionTime = endTime - startTime
        MDC.put("executionTime", executionTime.toString())

        TimingFilter.logger.info("STAT LOG")
        MDC.clear()
    }

    private fun timeFormatter(time: Long): String {
        val hoursForTime = TimeUnit.MILLISECONDS.toHours(time).toInt() % 12
        val minutesForTime = TimeUnit.MILLISECONDS.toMinutes(time).toInt() % 60
        val secondsForTime = TimeUnit.MILLISECONDS.toSeconds(time).toInt() % 60
        return String.format(
            "%02d:%02d:%02d",
            hoursForTime + 6,
            minutesForTime,
            secondsForTime
        )
    }

    companion object {
        var logger: Logger = LoggerFactory.getLogger(TimingFilter::class.java)
    }
}