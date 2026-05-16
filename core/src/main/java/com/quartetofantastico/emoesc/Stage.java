package com.quartetofantastico.emoesc;

import com.badlogic.gdx.ApplicationAdapter;

public class Stage {
    private String name;
    private int level;
    private int sublevel;
    public class Maize extends Stage{

        public Maize(String _name, int _level, int _sublevel) {
            super(_name, _level, _sublevel);
        }
    }
    public class KeyWord extends Stage{

        public KeyWord(String _name, int _level, int _sublevel) {
            super(_name, _level, _sublevel);
        }
    }
    public Stage(String _name, int _level, int _sublevel){

    }
}
//Essa classe é responsável pelos níveis, subníveis e outros dados que o compõe
