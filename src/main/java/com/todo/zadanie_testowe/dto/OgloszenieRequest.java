package com.todo.zadanie_testowe.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
// Specjalnie nie użyłem @JsonProperty – to mały projekt, więc przyjąłem domyślne mapowanie nazw pól z JSON na pola klasy.
public class OgloszenieRequest {
    @NotBlank(message = "Treść ogłoszenia nie może być pusta")
    private String tresc;

    public OgloszenieRequest (String tresc) {
        this.tresc = tresc;
    }
}
