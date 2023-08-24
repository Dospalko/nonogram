package sk.tuke.gamestudio.game.nonogram.test.filedTest;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.game.nonogram.core.Field;
import sk.tuke.gamestudio.game.nonogram.core.Marked;
import sk.tuke.gamestudio.game.nonogram.core.Tile;
import sk.tuke.gamestudio.game.nonogram.core.TileState;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FieldTest {

    private static class TestField extends Field {
        private final Tile[][] tiles;

        public TestField(int rows, int cols) {
            super(rows, cols);
            tiles = new Tile[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    tiles[i][j] = new Marked();
                }
            }
        }

        @Override
        public Tile getTile(int row, int column) {
            return tiles[row][column];
        }
    }

    @Test
    void testGetRow() {
        Field field = new TestField(10, 20);
        assertEquals(10, field.getRow());
    }

    @Test
    void testGetCol() {
        Field field = new TestField(10, 20);
        assertEquals(20, field.getCol());
    }


}

