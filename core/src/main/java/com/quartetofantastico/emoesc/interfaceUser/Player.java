package com.quartetofantastico.emoesc.interfaceUser;

import com.quartetofantastico.emoesc.logicAndMechanic.Constants;

public class Player {
    Constants CONST= new Constants();
    private String name, player;
    private int score, id;
    public Player(String _name, int _id) {
        this.player=_name;
        this.name+=", the ";
        this.id=_id;
        switch(_id){
            case 1 -> this.name+="Singer";
            case 2 -> this.name+="Mage";
            case 3 -> this.name+="Artist";
            case 4 -> this.name+="Cook";
            case 5 -> this.name+="Guard";
            case 6 -> this.name+="Astronaut";
            case 7 -> this.name+="Detective";
            case 8 -> this.name+="Pilote";
            case 9 -> this.name+="Ninja";
            case 10 -> this.name+="Scientist";
            case 11 -> this.name+="Mechanic";
            case 12 -> this.name+="Firefighter";
            case 13 -> this.name+="Farmer";
            case 14 -> this.name+="Teacher";
            case 15 -> this.name+="Teachnologist";
            case 16 -> this.name+="Judge";
            case 17 -> this.name+="Student";
            default -> this.name+="Unknown";
        }
    }

    public String getName() {
        return name;
    }

    public String getPlayer(){
        return player;
    }
    public int getScore() {
        return score;
    }

    public void increaseScore(int points) {
        score += points;
        if(score>CONST.MAX_SCORE) score=CONST.MAX_SCORE;
    }
}
//Essa classe é responsável por tratar dos dados do jogador;
