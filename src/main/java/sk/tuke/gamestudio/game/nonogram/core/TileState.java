package sk.tuke.gamestudio.game.nonogram.core;

public enum TileState {
    RED("\u001B[31m"),
    YELLOW("\u001B[33m"),
    GREEN("\u001B[32m"),
    PURPLE("\u001B[35m"),
    WHITE("\u001B[47m"),
    GRAY("\u001B[37m"),
    BLACK("\u001B[30m"),
    ORANGE("\u001B[38;5;208m"),
    BLUE("\u001B[34m"),
    MARK("+"),
    EMPTY("\u001B[0m");
    private final String text;
    TileState(String s) {
        this.text = s;
    }
    public String getText(){
        return text;
    }


}
