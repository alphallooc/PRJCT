package com.quartetofantastico.emoesc.audio;

import com.badlogic.gdx.audio.Music;

public class ControladorAudio {
    private static Music musicaAtual;
    private static String entidadeAtual = "policial";

    public static void iniciar(){
        Sons.carregar();
        Sons.START.play();
    }
    public static void escolherEntidade(String entidade){
        entidadeAtual = entidade.toLowerCase();
        trocarMusica(1);
    }
    public static void trocarMusica(int fase){
        if (musicaAtual != null) musicaAtual.stop();
        if (fase == 1) musicaAtual = Sons.DIA;
        if (fase == 2) musicaAtual = Sons.TARDE;
        if (fase == 1) musicaAtual = Sons.NOITE;
        musicaAtual.play();
    }
    public static void tocarSomemoji(){
        if (entidadeAtual.equals("ninja")) Sons.NINJA.play();
        if (entidadeAtual.equals("policial")) Sons.POLICIAL.play();
        if (entidadeAtual.equals("estudante")) Sons.ESTUDANTE.play();
    }

    public static void emojiErrado(){
        Sons.ERRO.play();
        Sons.HOSTIL.play();
    }
    public static void abrirPorta(){
        Sons.PORTA.play();
    }
    public static void tocarTransporte(){
        if (entidadeAtual.equals("ninja")) Sons.NINJA_TRANS.play();
        if (entidadeAtual.equals("policial")) Sons.POLICIAL_TRANS.play();
        if (entidadeAtual.equals("estudante")) Sons.ESTUDANTE_Trans.play();
    }
    public static void timerBeep(){
        Sons.TIMER_BEEP.play();
    }
    public static void animalRestrito(){
        Sons.ANIMAL_RESTRITO.play();
    }
    public static void hostilAtaca(){
        Sons.HOSTIL.play();
        Sons.VIDA_PERDIDA.play();
    }
    public static void gameOver(){
        if (musicaAtual != null) musicaAtual.stop();
        Sons.GAMEOVER.play();
    }
    public static void para(){
        if (musicaAtual != null) musicaAtual.stop();
        Sons.dispose();
    }
}
