package sk.tuke.gamestudio.game.nonogram.core;

import java.util.Random;

public class RandomNumberFieldGeneration {
    private int number;

    public RandomNumberFieldGeneration(){
        number = generation();
    }

    public int generation() {
        Random rand = new Random();
        number = rand.nextInt(5) + 1;
        return number;
    }
}
