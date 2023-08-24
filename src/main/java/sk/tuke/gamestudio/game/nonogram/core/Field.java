package sk.tuke.gamestudio.game.nonogram.core;

public abstract class Field {
    private final int Row;
    private final int Col;

    public Field(int Row, int Col) {
        this.Row = Row;
        this.Col = Col;
    }

    public int getRow() {
        return Row;
    }

    public int getCol() {
        return Col;
    }

    public abstract Tile getTile(int row, int column);
}


