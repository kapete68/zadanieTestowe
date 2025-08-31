package com.todo.zadanie_testowe.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OgloszenieTest {
    @Test
    void tworzniePoprawnejKlasy() {
        Ogloszenie ogloszenie = new Ogloszenie("Testowe ogloszenie");

        ogloszenie.onCreate();

        assertNotNull(ogloszenie.getDataDodania(), "Data dodania jest pusta!");
        assertEquals(0L, ogloszenie.getIloscWyswietlen(), "Ilosc wyswietlen powinna byc 0!");
        LocalDateTime dataTeraz = LocalDateTime.now();
        assertTrue(dataTeraz.isAfter(ogloszenie.getDataDodania()), "Data dodania nie moze byc w przyszlosci");
        assertEquals("Testowe ogloszenie", ogloszenie.getTresc());
    }

    @Test
    void inkrementacjaIlosciWyswietlen() {
        Ogloszenie ogloszenie = new Ogloszenie("Testowe ogloszenie");
        ogloszenie.onCreate();

        assertEquals(0L, ogloszenie.getIloscWyswietlen(), "Ilosc wyswietlen powinna byc 0!");
        assertNotNull(ogloszenie.getDataDodania(), "Data dodania jest pusta!");

        Long poprzedniaIloscWyswietlen = ogloszenie.getIloscWyswietlen();
        ogloszenie.zwiekszIloscWyswietlen();

        assertEquals(poprzedniaIloscWyswietlen + 1, ogloszenie.getIloscWyswietlen(), "Ilosc wyswietlen powinna sie zmienic po inkrementacji o 1");
    }

    @Test
    void stworzenieKlasyBezWywolaniaOnCreate() {
        Ogloszenie ogloszenie = new Ogloszenie("Testowe ogloszenie");

        assertNull(ogloszenie.getDataDodania(), "Data dodania powinna byc pusta!");
    }
}