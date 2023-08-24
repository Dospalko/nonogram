package sk.tuke.gamestudio.game.nonogram.core;

public class Hint extends Tile{

    private int value = 0;

    public Hint(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
