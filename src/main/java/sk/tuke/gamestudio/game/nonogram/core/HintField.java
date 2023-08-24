package sk.tuke.gamestudio.game.nonogram.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HintField extends Field {
    private final Tile[][] hintField;
    private String fieldPath;

    public HintField(int Row, int Col, int fieldNumber, boolean orientation) throws FileNotFoundException {
        super(Row, Col);
        fieldPath = String.format("C:\\Users\\domin\\IdeaProjects\\gamestudio-6454\\src\\main\\java\\sk\\tuke\\gamestudio\\game\\nonogram\\maps\\hints\\%sHint%s.txt", orientation ? "v" : "h", fieldNumber);
        hintField = new Tile[Row][Col];
        loadFieldFromFile();
    }

    public void loadFieldFromFile() throws FileNotFoundException {
        File file = new File(fieldPath);
        Scanner input = new Scanner(file);

        for (int i = 0; i < this.getRow(); i++) {
            int j = 0;
            String line = input.nextLine();
            Scanner lineScan = new Scanner(line);
            while (lineScan.hasNext()) {
                int value = Integer.parseInt(lineScan.next());
                if (value > 0) {
                    hintField[i][j] = new Hint(value);
                    String word = lineScan.next();
                    switch (word) {
                        case "BLACK":
                            hintField[i][j].setState(TileState.BLACK);
                            break;
                        case "BLUE":
                            hintField[i][j].setState(TileState.BLUE);
                            break;
                        case "RED":
                            hintField[i][j].setState(TileState.RED);
                            break;
                        case "YELLOW":
                            hintField[i][j].setState(TileState.YELLOW);
                            break;
                        case "GREEN":
                            hintField[i][j].setState(TileState.GREEN);
                            break;
                        case "PURPLE":
                            hintField[i][j].setState(TileState.PURPLE);
                            break;
                        case "GRAY":
                            hintField[i][j].setState(TileState.GRAY);
                            break;
                        case "ORANGE":
                            hintField[i][j].setState(TileState.ORANGE);
                            break;
                        default:
                            break;
                    }
                }
                j++;
            }
        }
        correctField();
    }

    public void correctField() {
        while (check()) {
            for (int column = getCol() - 1; column > 0; column--) {
                for (int row = 0; row < getRow(); row++) {
                    if (hintField[row][column] == null) {
                        hintField[row][column] = hintField[row][column - 1];
                        hintField[row][column - 1] = null;
                    }
                }
            }
        }
    }

    public boolean check() {
        for (int row = 0; row < getRow(); row++) {
            if (hintField[row][getCol() - 1] == null) {
                return true;
            }
        }
        return false;
    }
    public Tile getTile(int row, int column) {
        return hintField[row][column];
    }
}
