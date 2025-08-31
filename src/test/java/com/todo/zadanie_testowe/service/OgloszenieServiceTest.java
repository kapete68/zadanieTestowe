package com.todo.zadanie_testowe.service;

import com.todo.zadanie_testowe.dto.ApiResponse;
import com.todo.zadanie_testowe.dto.OgloszenieRequest;
import com.todo.zadanie_testowe.model.Ogloszenie;
import com.todo.zadanie_testowe.repository.OgloszenieRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OgloszenieServiceTest {

    @Autowired
    private OgloszenieRepository ogloszenieRepository;

    @Autowired
    private OgloszenieService ogloszenieService;

    private Ogloszenie testOgloszenie;

    @BeforeEach
    void setUp() {
        testOgloszenie = new Ogloszenie("Testowe ogloszenie");
        testOgloszenie = ogloszenieRepository.save(testOgloszenie);
    }

    @Test
    void znajdzOrazZwiekszIloscWyswietlenPoId() {
        Ogloszenie ogloszenie = new Ogloszenie("Testowe oglsozenie");
        Ogloszenie zapisaneOgloszenie = ogloszenieRepository.save(ogloszenie);

        Ogloszenie znalezioneOgloszenie = ogloszenieService.znajdzOrazZwiekszIloscWyswietlenPoId(zapisaneOgloszenie.getId());

        assertEquals(zapisaneOgloszenie.getTresc(), znalezioneOgloszenie.getTresc(), "Tresc ogloszenia powinna byc taka sama!");
        assertEquals(1L, znalezioneOgloszenie.getIloscWyswietlen(), "Ilosc wyswietlen po odczytaniu powinna wieksza o jeden!");
        assertEquals(zapisaneOgloszenie.getDataDodania(), znalezioneOgloszenie.getDataDodania(), "Data dodania oglsozenia powinna byc taka sama!");
    }

    @Test
    void stworzNoweOgloszenie() throws AssertionError {
        ApiResponse response = ogloszenieService.stworzNoweOgloszenie("Testowa tresc");

        assertEquals("Poprawnie utworzono ogloszenie", response.message());

        List<Ogloszenie> wszystkie = ogloszenieRepository.findAll();

        assertFalse(wszystkie.isEmpty(), "Powinno byc przynajmniej jedno ogloszenie w bazie");

        Ogloszenie ogloszenie = ogloszenieRepository.findAll()
                .stream()
                .filter(o -> "Testowa tresc".equals(o.getTresc()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Nie znaleziono ogloszenia w bazie"));

        assertEquals(0L, ogloszenie.getIloscWyswietlen());
        assertNotNull(ogloszenie.getDataDodania(), "Data dodania nie powinna byc null");
    }

    @Test
    void zmianaTresciOgloszeniaPoId() throws RuntimeException {
        ApiResponse response = ogloszenieService.zmianaTresciOgloszeniaPoId(testOgloszenie.getId(), "Nowa tresc");
        assertEquals("Poprawnie zaktualizowano ogloszenie", response.message());

        Ogloszenie ogloszenieNadpisane = ogloszenieRepository.findById(testOgloszenie.getId())
                .orElseThrow(() -> new RuntimeException("Nie znaleziono zapisanego ogloszenia!"));
        assertEquals("Nowa tresc", ogloszenieNadpisane.getTresc());
    }

    @Test
    void usunOgloszeniePoId() {
        List<Ogloszenie> przedUsunieciemWszystkie = ogloszenieRepository.findAll();

        ogloszenieService.usunOgloszeniePoId(testOgloszenie.getId());

        List<Ogloszenie> poUsunieciuWszystkie = ogloszenieRepository.findAll();
        assertNotEquals(przedUsunieciemWszystkie.size(), poUsunieciuWszystkie.size(), "Powinna byc zmiana ilosci rekordow w bazie danych");
    }
}