package com.todo.zadanie_testowe.controller;

import com.todo.zadanie_testowe.dto.ApiResponse;
import com.todo.zadanie_testowe.dto.OgloszenieRequest;
import com.todo.zadanie_testowe.model.Ogloszenie;
import com.todo.zadanie_testowe.service.OgloszenieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ogloszenie")
public class OgloszenieController {
    private final OgloszenieService ogloszenieService;

    public OgloszenieController(OgloszenieService ogloszenieService) {
        this.ogloszenieService = ogloszenieService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ogloszenie> pobierzOgloszeniePoId(@PathVariable Long id) {
        return ResponseEntity.ok().body(ogloszenieService.znajdzOrazZwiekszIloscWyswietlenPoId(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> stworzOgloszenie(@Valid @RequestBody OgloszenieRequest request) {
        ApiResponse response = ogloszenieService.stworzNoweOgloszenie(request.getTresc());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> zmianaOgloszenia(@PathVariable Long id, @Valid @RequestBody OgloszenieRequest ogloszenieRequest) {
        ApiResponse response = ogloszenieService.zmianaTresciOgloszeniaPoId(id, ogloszenieRequest.getTresc());
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> usunOgloszenie(@PathVariable Long id) {
        ogloszenieService.usunOgloszeniePoId(id);
        return ResponseEntity.noContent().build();
    }
}
