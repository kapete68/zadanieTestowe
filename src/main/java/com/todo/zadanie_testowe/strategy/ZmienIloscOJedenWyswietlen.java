package com.todo.zadanie_testowe.strategy;

import com.todo.zadanie_testowe.model.Ogloszenie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("zwiekszJeden")
public class ZmienIloscOJedenWyswietlen implements ZmienIloscWyswietlen {
    @Override
    public void zwieksz(Ogloszenie ogloszenie) {
        ogloszenie.setIloscWyswietlen(ogloszenie.getIloscWyswietlen() + 1);
    }
}
