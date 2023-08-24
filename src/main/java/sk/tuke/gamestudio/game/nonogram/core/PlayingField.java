package sk.tuke.gamestudio.game.nonogram.core;

public class PlayingField extends Field {
    private final Tile[][] playingField;
    private FieldState state = FieldState.PLAYING;

    public PlayingField(int Row, int Col) {
        super(Row, Col);
        playingField = new Tile[Row][Col];
        generatePlayingField();
    }

    public void generatePlayingField() {
        for (int i = 0; i < this.getRow(); i++) {
            for (int j = 0; j < this.getCol(); j++) {
                playingField[i][j] = new Marked();
                playingField[i][j].setState(TileState.WHITE);
            }
        }
    }

    public void colorTile(int row, int column, TileState color) {
        playingField[row][column].setState(color);
    }

    public void markTile(int row, int column) {
        if (playingField[row][column].getState() != TileState.MARK) {
            playingField[row][column].setState(TileState.MARK);
        }
    }

    public Tile getTile(int row, int column) {
        return playingField[row][column];
    }

    public FieldState getState() {
        return state;
    }

    public void setState(FieldState state) {
        this.state = state;
    }


}