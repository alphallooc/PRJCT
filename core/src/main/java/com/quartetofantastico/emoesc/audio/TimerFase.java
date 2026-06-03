package com.quartetofantastico.emoesc.audio;

public class TimerFase {
    private float tempoRestate;
    private boolean rodando;
    private int fase;

    public TimerFase(){
        this.fase = fase;
        if (fase == 1) tempoRestate = 20 * 60;
        if (fase == 2) tempoRestate = 15 * 60;
        if (fase == 3) tempoRestate = 10 * 60;
    }
    public void iniciar(){
        rodando = true;
    }
    public void atualizar(float delta){
        if (rodando && tempoRestate > 0){
            tempoRestate -= delta;
        }
    }
    public float getTempoRestate(){
        return tempoRestate;
    }
    public boolean acabou(){
        return tempoRestate <=0;
    }
}
