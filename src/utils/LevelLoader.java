// File: utils/LevelLoader.java
package utils;

import entities.Brick;
import entities.BrickType;
import java.util.ArrayList;

public class LevelLoader {

    /**
     * Creates a list of bricks for a specific level.
     * @param levelNumber The level to load.
     * @return An ArrayList of Brick objects.
     */
    public static ArrayList<Brick> loadLevel(int levelNumber) {
        ArrayList<Brick> bricks = new ArrayList<>();
        int screenPadding = 80;
        int topPadding = 50;
        int brickWidth = 80;
        int brickHeight = 30;
        int brickSpacing = 5;

        if (levelNumber == 1) {
            BrickType[] types = {
                    BrickType.IMPOSSIBLE,
                    BrickType.NORMAL
            };

            int numBricksPerRow = 10;

            for (int i = 0; i < types.length; i++) {
                BrickType currentType = types[i];
                for (int j = 0; j < numBricksPerRow; j++) {
                    double x = screenPadding + j * (brickWidth + brickSpacing);
                    double y = topPadding + i * (brickHeight + brickSpacing);
                    bricks.add(new Brick(x, y, brickWidth, brickHeight, currentType));
                }
            }
        }

        return bricks;
    }
}