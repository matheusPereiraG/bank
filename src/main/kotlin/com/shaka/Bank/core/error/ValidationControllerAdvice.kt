package com.shaka.Bank.core.error

import com.shaka.Bank.core.dto.ApiErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ValidationControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ApiErrorResponse> {
        val errors = ex.allErrors.map { it.defaultMessage ?: "Invalid input" }
        return ResponseEntity.badRequest().body(
            ApiErrorResponse(
                errorMsg = "Validation Error",
                details = errors
            )
        )
    }

}