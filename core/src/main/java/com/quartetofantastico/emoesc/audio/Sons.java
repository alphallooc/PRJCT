package com.quartetofantastico.emoesc.audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
public class Sons {

    // Sons gerais do jogo
    public static Sound START, PORTA, ERRO, VIDA_PERDEU, GAMEOVER, TIMER_BEEP;

    // 4 tipos de animais
    public static Sound ANIMAL_AMIGAVEL, ANIMAL_NEUTRO, ANIMAL_RESTRITO, HOSTIL;

    // Sons por entidade quando pega emoji
    public static Sound NINJA, MEDICO, POLICIAL, BOMBEIRO, ESTUDANTE;

    //Sons de transporte no fim da frase
    public static Sound NINJA_TRANS, MEDICO_TRANS, POLICIAL_TRANS, BOMBEIRO_TRANS, Estudante_Trans;

    //Musicas por entidades e por fase
    public static Music MEDICO_FASE1, MEDICO_FASE2, MEDICO_FASE3;
    public static Music POLICIAL_FASE1, POLICIAL_FASE2, POLICIAL_FASE3;
    public static Music BOMBEIRO_FASE1, BOMBEIRO_FASE2, BOMBEIRO_FASE3;
    public static Music ESTUDANTE_FASE1, ESTUDANTE_FASE2, ESTUDANTE_FASE3;
    public static Music NINJA_FASE1, NINJA_FASE2, NINJA_FASE3;

    public static void carregar(){
        START = Gdx.audio.newSound(Gdx.files.internal("sound/start.wav"));
        PORTA = Gdx.audio.newSound(Gdx.files.internal("sound/door.wav"));
        ERRO = Gdx.audio.newSound(Gdx.files.internal("sound/error.wav"));
        VIDA_PERDEU= Gdx.audio.newSound(Gdx.files.internal("sound/losing.wav"));
        GAMEOVER = Gdx.audio.newSound(Gdx.files.internal("sound/gameover.wav"));
        TIMER_BEEP = Gdx.audio.newSound(Gdx.files.internal("sound/countdown.wav"));

        //Animais
        ANIMAL_AMIGAVEL =   Gdx.audio.newSound(Gdx.files.internal("sound/bonus.wav"));
        ANIMAL_NEUTRO = Gdx.audio.newSound(Gdx.files.internal("sound/laser.wav"));
        ANIMAL_RESTRITO = Gdx.audio.newSound(Gdx.files.internal("sound/checkpoint.wav"));
        HOSTIL = Gdx.audio.newSound(Gdx.files.internal("sound/wolves.wav"));

        //Emoji por entidade
        NINJA = Gdx.audio.newSound(Gdx.files.internal("sound/bell.wav"));
        MEDICO = Gdx.audio.newSound(Gdx.files.internal("sound/ambulance.wav"));
        POLICIAL = Gdx.audio.newSound(Gdx.files.internal("sound/police.wav"));
        BOMBEIRO = Gdx.audio.newSound(Gdx.files.internal("sound/bell.wav"));
        ESTUDANTE = Gdx.audio.newSound(Gdx.files.internal("sound/bell.wav"));

        //Transporte por entidade
        NINJA_TRANS = Gdx.audio.newSound(Gdx.files.internal("sound/horse.wav"));
        MEDICO_TRANS = Gdx.audio.newSound(Gdx.files.internal("sound/police.wav"));
        POLICIAL_TRANS = Gdx.audio.newSound(Gdx.files.internal("sound/police.wav"));
        BOMBEIRO_TRANS = Gdx.audio.newSound(Gdx.files.internal("sound/bell.wav"));
        Estudante_Trans = Gdx.audio.newSound(Gdx.files.internal("sound/bell.wav"));

        //Musicas fase1= Dia, Fase2=Tarde, Fase3=Noite
        MEDICO_FASE1 = Gdx.audio.newMusic(Gdx.files.internal("sound/futuristic.wav"));
        MEDICO_FASE2 = Gdx.audio.newMusic(Gdx.files.internal("sound/rain.wav"));
        MEDICO_FASE3 = Gdx.audio.newMusic(Gdx.files.internal("sound/hospital_noite.wav"));

        POLICIAL_FASE1 = Gdx.audio.newMusic(Gdx.files.internal("sound/futuristic.wav"));
        POLICIAL_FASE2 = Gdx.audio.newMusic(Gdx.files.internal("sound/futuristic.wav"));
        POLICIAL_FASE3 = Gdx.audio.newMusic(Gdx.files.internal("sound/futuristic.wav"));

        NINJA_FASE1 = Gdx.audio.newMusic(Gdx.files.internal("sound/futuristic.wav"));
        NINJA_FASE2 = Gdx.audio.newMusic(Gdx.files.internal("sound/futuristic.wav"));
        NINJA_FASE3 = Gdx.audio.newMusic(Gdx.files.internal("sound/futuristic.wav"));

        BOMBEIRO_FASE1 = Gdx.audio.newMusic(Gdx.files.internal("sound/futuristic"));
        BOMBEIRO_FASE2 = Gdx.audio.newMusic(Gdx.files.internal("sound/futuristic"));
        BOMBEIRO_FASE3 = Gdx.audio.newMusic(Gdx.files.internal("sound/futuristic"));

        ESTUDANTE_FASE1 = Gdx.audio.newMusic(Gdx.files.internal("sout/futuristic.wav"));
        ESTUDANTE_FASE2 = Gdx.audio.newMusic(Gdx.files.internal("sout/futuristic.wav"));
        ESTUDANTE_FASE3 = Gdx.audio.newMusic(Gdx.files.internal("sout/futuristic.wav"));

        // lopp nas musicas
        for (Music m : new Music[]{MEDICO_FASE1, MEDICO_FASE2, MEDICO_FASE3, POLICIAL_FASE1, POLICIAL_FASE2,POLICIAL_FASE3, NINJA_FASE1, NINJA_FASE2, NINJA_FASE3, BOMBEIRO_FASE1, BOMBEIRO_FASE2, BOMBEIRO_FASE3, ESTUDANTE_FASE1, ESTUDANTE_FASE2, ESTUDANTE_FASE3}){
            m.setLooping(true);
        }
    }
    public static void dispose(){
        START.dispose(); PORTA.dispose(); ERRO.dispose(); VIDA_PERDEU.dispose();
        GAMEOVER.dispose(); TIMER_BEEP.dispose();
        ANIMAL_AMIGAVEL.dispose(); ANIMAL_NEUTRO.dispose(); ANIMAL_RESTRITO.dispose(); HOSTIL.dispose();
        NINJA.dispose(); MEDICO.dispose(); POLICIAL.dispose(); BOMBEIRO.dispose(); ESTUDANTE.dispose();
        MEDICO_FASE1.dispose(); MEDICO_FASE2.dispose(); MEDICO_FASE3.dispose();
        POLICIAL_FASE1.dispose(); POLICIAL_FASE2.dispose(); POLICIAL_FASE3.dispose();
        NINJA_FASE1.dispose(); NINJA_FASE2.dispose(); NINJA_FASE3.dispose();
        BOMBEIRO_FASE1.dispose(); BOMBEIRO_FASE2.dispose(); BOMBEIRO_FASE3.dispose();
        ESTUDANTE_FASE1.dispose(); ESTUDANTE_FASE2.dispose(); ESTUDANTE_FASE3.dispose();
    }
}

