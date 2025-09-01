package com.todo.zadanie_testowe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.zadanie_testowe.dto.ApiResponse;
import com.todo.zadanie_testowe.dto.OgloszenieRequest;
import com.todo.zadanie_testowe.model.Ogloszenie;
import com.todo.zadanie_testowe.repository.OgloszenieRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OgloszenieControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OgloszenieRepository ogloszenieRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void pobierzOgloszeniePoId() throws Exception {
        Ogloszenie nowe = new Ogloszenie("Testowe ogloszenie");
        Ogloszenie zapisaneOgloszenie = ogloszenieRepository.save(nowe);

        String json = mockMvc.perform(get("/api/v1/ogloszenie/{id}", zapisaneOgloszenie.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Ogloszenie ogloszenie = objectMapper.readValue(json, Ogloszenie.class);
        assertEquals(zapisaneOgloszenie.getTresc(), ogloszenie.getTresc());
        assertEquals(zapisaneOgloszenie.getDataDodania(), ogloszenie.getDataDodania());
        assertEquals(1L, ogloszenie.getIloscWyswietlen());
    }

    @Test
    void pobierzOgloszeniePoId_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/ogloszenie/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void stworzOgloszenie() throws Exception {
        OgloszenieRequest ogloszenieRequest = new OgloszenieRequest("Testowa tresc");

        String json = mockMvc.perform(post("/api/v1/ogloszenie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ogloszenieRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ApiResponse response = objectMapper.readValue(json, ApiResponse.class);
        assertEquals("Poprawnie utworzono ogloszenie", response.message());
    }

    @Test
    void stworzOgloszenie_ShouldReturnBadRequest() throws Exception {
        OgloszenieRequest ogloszenieRequest = new OgloszenieRequest("");

        mockMvc.perform(post("/api/v1/ogloszenie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ogloszenieRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void zmianaOgloszenia() throws Exception {
        Ogloszenie nowe = new Ogloszenie("Testowe ogloszenie");
        Ogloszenie zapisaneOgloszenie = ogloszenieRepository.save(nowe);
        OgloszenieRequest ogloszenieRequest = new OgloszenieRequest("Nowa tresc");

        String json = mockMvc.perform(patch("/api/v1/ogloszenie/{id}", zapisaneOgloszenie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ogloszenieRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ApiResponse response = objectMapper.readValue(json, ApiResponse.class);
        assertEquals("Poprawnie zaktualizowano ogloszenie", response.message());

        Ogloszenie nadpisaneOgloszenie = ogloszenieRepository.findById(zapisaneOgloszenie.getId()).orElseThrow(() -> new RuntimeException("Nie znaleziono ogloszenia ktore zostalo zapisane wczesniej!"));
        assertNotEquals("Testowe ogloszenie", nadpisaneOgloszenie.getTresc());
    }

    @Test
    void usunOgloszenie() throws Exception {
        Ogloszenie nowe = new Ogloszenie("Testowe ogloszenie");
        Ogloszenie zapisaneOgloszenie = ogloszenieRepository.save(nowe);

        mockMvc.perform(delete("/api/v1/ogloszenie/{id}", zapisaneOgloszenie.getId()))
                .andExpect(status().isNoContent());

        Optional<Ogloszenie> ogloszenieOptional = ogloszenieRepository.findById(zapisaneOgloszenie.getId());
        assertTrue(ogloszenieOptional.isEmpty(), "Ogloszenie powinno nie istniec!");
    }

    @Test
    void usunOgloszenieKtoregoNieMa_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/ogloszenie/{id}", 1L))
                .andExpect(status().isNotFound());
    }
}