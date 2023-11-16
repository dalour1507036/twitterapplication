package com.example.twitterapplication.exceptionhandler

import com.example.twitterapplication.exceptionhandler.exceptions.UserAlreadyExist
import com.example.twitterapplication.exceptionhandler.exceptions.UserNotFound
import com.example.twitterapplication.exceptionhandler.exceptions.UsernamePasswordMismatch
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {
    @ExceptionHandler(UserNotFound::class)
    fun handleUserNotFoundException(ex: UserNotFound): ResponseEntity<FormattedApiError> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ex.message?.let {
                FormattedApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    it
                )
            })
    }

    @ExceptionHandler(UserAlreadyExist::class)
    fun handleUserAlreadyExistException(ex: UserAlreadyExist): ResponseEntity<FormattedApiError> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ex.message?.let {
                FormattedApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    it
                )
            })
    }

    @ExceptionHandler(UsernamePasswordMismatch::class)
    fun handleUsernamePasswordMismatchException(ex: UsernamePasswordMismatch): ResponseEntity<FormattedApiError> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ex.message?.let {
                FormattedApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    it
                )
            })
    }
}