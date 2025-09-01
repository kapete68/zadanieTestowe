package com.todo.zadanie_testowe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Ogloszenie {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String tresc;

    private LocalDateTime dataDodania;

    @Column(columnDefinition = "bigint default 0")
    private Long iloscWyswietlen;

    @PrePersist
    protected void onCreate() {
        dataDodania = LocalDateTime.now();
    }

    public Ogloszenie() {
        // wymagany przez JPA
    }

    public Ogloszenie(String tresc) {
        this.tresc = tresc;
        this.iloscWyswietlen = 0L;
    }
}
