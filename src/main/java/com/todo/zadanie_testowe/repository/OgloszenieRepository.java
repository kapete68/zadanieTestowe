package com.todo.zadanie_testowe.repository;

import com.todo.zadanie_testowe.model.Ogloszenie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OgloszenieRepository extends JpaRepository<Ogloszenie, Long> {
}
