package com.quartetofantastico.emoesc.audio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomizadorEmojis {
    public static List<Integer> sortearPosicoes(int totalPosicoes){
        List<Integer> posicoes = new ArrayList<>();
        for (int i = 0; i < totalPosicoes; i++) posicoes.add(i);
        Collections.shuffle(posicoes);
        return posicoes.subList(0, 5);
    }
}
