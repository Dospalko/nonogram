package sk.tuke.gamestudio.game.nonogram.test.filedTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.game.nonogram.core.HintField;
import sk.tuke.gamestudio.game.nonogram.core.TileState;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class HintFieldTest {

    private HintField hintField;

    @BeforeEach
    void setUp() {
        try {
            hintField = new HintField(5, 5, 1, true);
        } catch (FileNotFoundException e) {
            fail("Failed to load field from file: " + e.getMessage());
        }
    }



    @Test
    void testCorrectField() throws FileNotFoundException {
        HintField field = new HintField(3, 3, 1, true);
        field.correctField();
        assertNotNull(field.getTile(0, 2));
        assertEquals(TileState.GREEN, field.getTile(0, 2).getState());
        assertNotNull(field.getTile(1, 2));
        assertEquals(TileState.GREEN, field.getTile(1, 2).getState());
        assertNotNull(field.getTile(2, 2));
        assertEquals(TileState.GREEN, field.getTile(2, 2).getState());
    }
}
