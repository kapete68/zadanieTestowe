package com.todo.zadanie_testowe.service;

import com.todo.zadanie_testowe.dto.ApiResponse;
import com.todo.zadanie_testowe.exception.NotFoundException;
import com.todo.zadanie_testowe.model.Ogloszenie;
import com.todo.zadanie_testowe.repository.OgloszenieRepository;
import com.todo.zadanie_testowe.strategy.ZmienIloscWyswietlen;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OgloszenieService {
    private final OgloszenieRepository ogloszenieRepository;
    private final ZmienIloscWyswietlen zmienIloscWyswietlen;

    public OgloszenieService(OgloszenieRepository ogloszenieRepository, @Qualifier("zwiekszJeden") ZmienIloscWyswietlen zmienIloscWyswietlen) {
        this.ogloszenieRepository = ogloszenieRepository;
        this.zmienIloscWyswietlen = zmienIloscWyswietlen;
    }


    public Ogloszenie znajdzOrazZwiekszIloscWyswietlenPoId(Long id) {
        Ogloszenie ogloszenie = ogloszenieRepository.findById(id).orElseThrow(() -> new NotFoundException("Nie znaleziono ogloszenia"));

        zmienIloscWyswietlen.zwieksz(ogloszenie);
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
