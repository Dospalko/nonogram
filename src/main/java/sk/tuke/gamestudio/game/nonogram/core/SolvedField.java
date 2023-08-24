package sk.tuke.gamestudio.game.nonogram.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SolvedField extends Field {
    private final int fieldNumber;
    private final Tile[][] solvedField;


    public SolvedField(int Row, int Col, int fieldNumber) throws FileNotFoundException {
        super(Row, Col);
        this.fieldNumber = fieldNumber;
        solvedField = new Tile[Row][Col];
        String fileName = "field" + fieldNumber + ".txt";
        File file = new File("C:\\Users\\domin\\IdeaProjects\\gamestudio-6454\\src\\main\\java\\sk\\tuke\\gamestudio\\game\\nonogram\\maps\\fields\\" + fileName);
        Scanner input = new Scanner(file);
        for (int i = 0; i < Row; i++) {
            for (int j = 0; j < Col; j++) {
                solvedField[i][j] = new Marked();
                String word = input.next();
                if (solvedField[i][j] != null) {
                    TileState state = TileState.valueOf(word.toUpperCase());
                    solvedField[i][j].setState(state);
                }
            }
        }
    }


    public Tile getTile(int row, int column) {
        return solvedField[row][column];
    }

}
