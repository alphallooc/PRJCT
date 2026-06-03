package com.quartetofantastico.emoesc.audio;

import com.badlogic.gdx.audio.Music;

class ControladorAudio {
    private static Music musicaAtual;
    private static String entidadeAtual = "Medico";

    public static void iniciar() {
        Sons.carregar();
        Sons.START.play(); // som ao iniciar jogo
    }

    public static void escolherEntidade(String entidade) {
        entidadeAtual = entidade.toLowerCase();
        Sons.START.play();
        trocarMusica(1); // começa a Fase1 = Dia
    }

    //Regra: FASE1=DIA, FASE2=TARDE, FASE3=NOITE
    public static void trocarMusica(int fase) {
        if (musicaAtual != null) musicaAtual.stop();

        if (entidadeAtual.equals("medico")) {
            if (fase == 1) musicaAtual = Sons.MEDICO_FASE1;
            if (fase == 2) musicaAtual = Sons.MEDICO_FASE2;
            if (fase == 3) musicaAtual = Sons.MEDICO_FASE3;
        }
        if (entidadeAtual.equals("policial")) {
            if (fase == 1) musicaAtual = Sons.POLICIAL_FASE1;
            if (fase == 2) musicaAtual = Sons.POLICIAL_FASE2;
            if (fase == 3) musicaAtual = Sons.POLICIAL_FASE3;
        }
        if (entidadeAtual.equals("ninja")) {
            if (fase == 1) musicaAtual = Sons.NINJA_FASE1;
            if (fase == 2) musicaAtual = Sons.NINJA_FASE2;
            if (fase == 3) musicaAtual = Sons.NINJA_FASE3;
        }
        if (entidadeAtual.equals("bombeiro")) {
            if (fase == 1) musicaAtual = Sons.BOMBEIRO_FASE1;
            if (fase == 2) musicaAtual = Sons.BOMBEIRO_FASE2;
            if (fase == 3) musicaAtual = Sons.BOMBEIRO_FASE3;
        }
        if (entidadeAtual.equals("estudante")) {
            if (fase == 1) musicaAtual = Sons.ESTUDANTE_FASE1;
            if (fase == 2) musicaAtual = Sons.ESTUDANTE_FASE2;
            if (fase == 3) musicaAtual = Sons.ESTUDANTE_FASE3;
        }
        if (musicaAtual != null) musicaAtual.play();
    }

    //Regra: cada entidade tem som unico ao pegar emoji
    public static void tocarSomEmoji() {
        if (entidadeAtual.equals("ninja")) Sons.NINJA.play();
        if (entidadeAtual.equals("medico")) Sons.MEDICO.play();
        if (entidadeAtual.equals("policial")) Sons.POLICIAL.play();
        if (entidadeAtual.equals("bombeiro")) Sons.BOMBEIRO.play();
        if (entidadeAtual.equals("estudante")) Sons.ESTUDANTE.play();
    }

    //Regra: emoji errado = som erro + hostil spawna
    public static void emojiErro() {
        Sons.ERRO.play();
        Sons.HOSTIL.play();
    }

    //Regra: porta abre no fim da fase
    public static void abrirPorta() {
        Sons.PORTA.play();
    }

    public static void tocarTranporte() {
        if (entidadeAtual.equals("ninja")) Sons.NINJA_TRANS.play();
        if (entidadeAtual.equals("medico")) Sons.MEDICO_TRANS.play();
        if (entidadeAtual.equals("policial")) Sons.POLICIAL_TRANS.play();
        if (entidadeAtual.equals("bombeiro")) Sons.BOMBEIRO_TRANS.play();
        if (entidadeAtual.equals("estudante")) Sons.Estudante_Trans.play();
    }

    // Regras: beep nos ultimos 10s
    public static void timerBeep() {
        Sons.TIMER_BEEP.play();
    }

    //Regra 4 tipos de animais
    public static void animalAmigavel() {
        Sons.ANIMAL_AMIGAVEL.play();
    }

    public static void animalNeutro() {
        Sons.ANIMAL_NEUTRO.play();
    }

    public static void animalRestrito() {
        Sons.ANIMAL_RESTRITO.play();
    }

    public static void hostilAtaca() {
        Sons.HOSTIL.play();
        Sons.VIDA_PERDEU.play();
    }

    public static void gameOver() {
        if (musicaAtual != null) musicaAtual.stop();
        Sons.GAMEOVER.play();
    }

    public static void parar() {
        if (musicaAtual != null) musicaAtual.stop();
        Sons.dispose();
    }


}
