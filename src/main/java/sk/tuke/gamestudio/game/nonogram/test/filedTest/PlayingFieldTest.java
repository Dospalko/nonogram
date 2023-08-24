package sk.tuke.gamestudio.game.nonogram.test.filedTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.game.nonogram.core.FieldState;
import sk.tuke.gamestudio.game.nonogram.core.PlayingField;
import sk.tuke.gamestudio.game.nonogram.core.Tile;
import sk.tuke.gamestudio.game.nonogram.core.TileState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PlayingFieldTest {
    private PlayingField playingField;

    @BeforeEach
    void setUp() {
        playingField = new PlayingField(10, 10);
    }

    @Test
    void testGeneratePlayingField() {
        playingField.generatePlayingField();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertEquals(TileState.WHITE, playingField.getTile(i, j).getState());
            }
        }
    }

    @Test
    void testColorTile() {
        playingField.colorTile(0, 0, TileState.BLACK);
        assertEquals(TileState.BLACK, playingField.getTile(0, 0).getState());
    }

    @Test
    void testMarkTile() {
        playingField.markTile(0, 0);
        assertEquals(TileState.MARK, playingField.getTile(0, 0).getState());
    }

    @Test
    void testGetTile() {
        Tile tile = playingField.getTile(0, 0);
        assertNotNull(tile);
    }

    @Test
    void testGetState() {
        FieldState state = playingField.getState();
        assertEquals(FieldState.PLAYING, state);
    }

    @Test
    void testSetState() {
        playingField.setState(FieldState.SOLVED);
        assertEquals(FieldState.SOLVED, playingField.getState());
    }
}
