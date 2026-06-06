package com.quartetofantastico.emoesc.logicAndMechanic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DesafioManager {

    private List<Desafio> desafios = new ArrayList<>();
    private Random random = new Random();

    public DesafioManager() {
        carregarDesafios();
    }

    private void carregarDesafios() {
        desafios.add(new Desafio(
            "O que é, o que é?\nQuanto mais se tira, maior fica.",
            "buraco"
        ));
        desafios.add(new Desafio(
            "O que é, o que é?\nTem dentes, mas não morde.",
            "pente"
        ));
        desafios.add(new Desafio(
            "O que é, o que é?\nCorre, mas não tem pernas.",
            "água"
        ));
        desafios.add(new Desafio(
            "O que é, o que é?\nEnche uma sala, mas não ocupa espaço.",
            "luz"
        ));
        desafios.add(new Desafio(
            "O que é, o que é?\nQuanto mais seca, mais molhada fica.",
            "toalha"
        ));
    }

    public Desafio sortear() {
        if (desafios.isEmpty()) return null;
        return desafios.get(random.nextInt(desafios.size()));
    }
}
