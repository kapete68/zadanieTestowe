package com.todo.zadanie_testowe.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OgloszenieRequestTest {

    @Test
    void tworzeniePoprawnejKlasyOgloszenieRequest() {
        OgloszenieRequest ogloszenieRequest = new OgloszenieRequest("Testowa tresc");

        assertEquals("Testowa tresc", ogloszenieRequest.getTresc());
    }
}