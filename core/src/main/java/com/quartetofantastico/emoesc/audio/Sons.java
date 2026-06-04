package com.quartetofantastico.emoesc.audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
public class Sons {

    // Sons gerais do jogo
    public static Sound START, PORTA, ERRO, VIDA_PERDIDA, GAMEOVER, TIMER_BEEP, TIMER;

    // 4 tipos de animais
    public static Sound  ANIMAL_RESTRITO, HOSTIL;

    // Sons por entidade quando pega emoji
    public static Sound NINJA,  POLICIAL, ESTUDANTE;

    //Sons de transporte no fim da frase
    public static Sound NINJA_TRANS, MEDICO_TRANS, POLICIAL_TRANS, BOMBEIRO_TRANS, ESTUDANTE_Trans;

    public static Music DIA, TARDE, NOITE;

    public static void carregar(){
        START = Gdx.audio.newSound(Gdx.files.internal("sound/start.wav"));
        PORTA = Gdx.audio.newSound(Gdx.files.internal("sound/door.wav"));
        ERRO = Gdx.audio.newSound(Gdx.files.internal("sound/error.wav"));
        VIDA_PERDIDA= Gdx.audio.newSound(Gdx.files.internal("sound/losing.wav"));
        GAMEOVER = Gdx.audio.newSound(Gdx.files.internal("sound/gameover.wav"));
        TIMER_BEEP = Gdx.audio.newSound(Gdx.files.internal("sound/countdown.wav"));
        TIMER = Gdx.audio.newSound(Gdx.files.internal("sound/timer.wav"));

        //Animais
        ANIMAL_RESTRITO = Gdx.audio.newSound(Gdx.files.internal("sound/checkpoint.wav"));
        HOSTIL = Gdx.audio.newSound(Gdx.files.internal("sound/wolves.wav"));

        //Emoji por entidade
        NINJA = Gdx.audio.newSound(Gdx.files.internal("sound/bell.wav"));
        POLICIAL = Gdx.audio.newSound(Gdx.files.internal("sound/police.wav"));
        ESTUDANTE = Gdx.audio.newSound(Gdx.files.internal("sound/bell.wav"));

        //Transporte por entidade
        NINJA_TRANS = Gdx.audio.newSound(Gdx.files.internal("sound/horse.wav"));
        POLICIAL_TRANS = Gdx.audio.newSound(Gdx.files.internal("sound/police.wav"));
        ESTUDANTE_Trans = Gdx.audio.newSound(Gdx.files.internal("sound/bell.wav"));

        NINJA_TRANS = Gdx.audio.newSound(Gdx.files.internal("sound/horse.wav"));
        POLICIAL_TRANS = Gdx.audio.newSound(Gdx.files.internal("sound/police.wav"));
        ESTUDANTE_Trans = Gdx.audio.newSound(Gdx.files.internal("sound/victory.wav"));

        DIA = Gdx.audio.newMusic(Gdx.files.internal("sound/futuristic.wav"));
        TARDE = Gdx.audio.newMusic(Gdx.files.internal("sound/rain.wav"));
        NOITE = Gdx.audio.newMusic(Gdx.files.internal("sound/HOSPITAL.wav"));

        DIA.setLooping(true); TARDE.setLooping(true); NOITE.setLooping(true);

        }
        public static void dispose(){
        START.dispose(); PORTA.dispose(); ERRO.dispose(); VIDA_PERDIDA.dispose();
        GAMEOVER.dispose(); TIMER_BEEP.dispose(); TIMER.dispose();
        ANIMAL_RESTRITO.dispose(); HOSTIL.dispose();
        NINJA_TRANS.dispose(); POLICIAL_TRANS.dispose(); ESTUDANTE_Trans.dispose();
        DIA.dispose(); TARDE.dispose(); NOITE.dispose();
        }
    }

