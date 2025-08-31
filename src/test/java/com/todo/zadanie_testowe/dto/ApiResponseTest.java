package com.todo.zadanie_testowe.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {
    @Test
    void testApiResponseCreation() {
        ApiResponse response = new ApiResponse("Poprawnie utworzono ogloszenie", 42L);

        assertEquals("Poprawnie utworzono ogloszenie", response.message());
        assertEquals(42L, response.id());
    }
}