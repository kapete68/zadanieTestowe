package com.todo.zadanie_testowe.strategy;

import com.todo.zadanie_testowe.model.Ogloszenie;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZmienIloscOJedenWyswietlenTest {

    @Test
    void zwieksz_powinnoDodacJedenDoIlosciWyswietlen() {
        // given
        Ogloszenie ogloszenie = new Ogloszenie("Testowa tresc");
        ogloszenie.setIloscWyswietlen(5L); // początkowa liczba wyświetleń

        ZmienIloscOJedenWyswietlen strategia = new ZmienIloscOJedenWyswietlen();

        // when
        strategia.zwieksz(ogloszenie);

        // then
        assertEquals(6, ogloszenie.getIloscWyswietlen(), "Liczba wyświetleń powinna wzrosnąć o 1");
    }
}