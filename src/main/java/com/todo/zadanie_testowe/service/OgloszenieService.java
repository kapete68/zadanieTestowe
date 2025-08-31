package com.todo.zadanie_testowe.service;

import com.todo.zadanie_testowe.dto.ApiResponse;
import com.todo.zadanie_testowe.exception.NotFoundException;
import com.todo.zadanie_testowe.model.Ogloszenie;
import com.todo.zadanie_testowe.repository.OgloszenieRepository;
import org.springframework.stereotype.Service;

@Service
public class OgloszenieService {
    private final OgloszenieRepository ogloszenieRepository;

    public OgloszenieService(OgloszenieRepository ogloszenieRepository) {
        this.ogloszenieRepository = ogloszenieRepository;
    }

    public Ogloszenie znajdzOrazZwiekszIloscWyswietlenPoId(Long id) {
        Ogloszenie ogloszenie = ogloszenieRepository.findById(id).orElseThrow(() -> new NotFoundException("Nie znaleziono ogloszenia"));

        // Według mnie to jest w porządku pod względem SOLID, ponieważ logika biznesowa jasno mówi,
        // że podczas pobierania ogłoszenia zwiększamy również licznik wyświetleń.
        // Stworzenie osobnej funkcji wyłącznie do wywołania zwiekszIloscWyswietlen() w tym kontekście
        // wydawałoby się sztuczne i nie wnosiłoby realnej wartości.
        ogloszenie.zwiekszIloscWyswietlen();
        ogloszenieRepository.save(ogloszenie);

        return ogloszenie;
    }

    public ApiResponse stworzNoweOgloszenie(String tresc) {
        Ogloszenie ogloszenie = new Ogloszenie(tresc);

        Ogloszenie zapisaneOgloszenie = ogloszenieRepository.save(ogloszenie);
        return new ApiResponse("Poprawnie utworzono ogloszenie", zapisaneOgloszenie.getId());
    }

    public ApiResponse zmianaTresciOgloszeniaPoId(Long id, String nowaTresc) {
        Ogloszenie ogloszenie = ogloszenieRepository.findById(id).orElseThrow(() -> new NotFoundException("Nie znaleziono ogloszenia"));
        ogloszenie.setTresc(nowaTresc);

        Ogloszenie zapisaneOgloszenie = ogloszenieRepository.save(ogloszenie);

        return new ApiResponse("Poprawnie zaktualizowano ogloszenie", zapisaneOgloszenie.getId());
    }

    public void usunOgloszeniePoId(Long id) {
        Ogloszenie ogloszenie = ogloszenieRepository.findById(id).orElseThrow(() -> new NotFoundException("Nie znaleziono ogloszenia"));

        ogloszenieRepository.delete(ogloszenie);
    }
}
