package sk.tuke.gamestudio.game.nonogram.core;

public abstract class Tile {
    private TileState state;


    public TileState getState(){
        return state;
    }

    void setState(TileState state){
        this.state = state;
    }

}
