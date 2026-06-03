package com.quartetofantastico.emoesc.audio;

public class Vidas {
    private int vidas = 7;
    private int coracoes = 3;

    public void perderVida(){
        if (vidas > 0){
            vidas--;

            ControladorAudio.hostilAtaca();
            if (vidas == 0) ControladorAudio.gameOver();
        }
    }
    public void ganharVida(){
        if (vidas < 7) {
            vidas++;
            ControladorAudio.animalAmigavel();
        }
    }
    public int getVidas(){
        return vidas;
    }
    public int getCoracoes(){
        return coracoes;
    }
}
