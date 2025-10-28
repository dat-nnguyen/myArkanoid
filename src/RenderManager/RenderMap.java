package RenderManager;

import entities.Brick;
import entities.BrickType;
import utils.Constants;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class RenderMap {

    private final String startPath = "src/RenderManager/MapList/";
    private final String endPath = ".txt";

    public RenderMap() {
        System.out.println("\uD83D\uDDFA Map Renderer Init Success !");
    }

    public void render(int level, ArrayList<Brick> brickList) {
        brickList.clear(); // Remove all past level list of bricks.
        StringBuilder path = new StringBuilder(startPath);
        path.append(level);
        path.append(endPath);
        try (BufferedReader readFile = new BufferedReader(new FileReader(String.valueOf(path)))) {
            String line;
            int j = 0;
            while ((line = readFile.readLine()) != null) {
                if (line.isEmpty()) continue;
                int size = line.length();
                for (int i = 0; i < size; ++i) {
                    int num = line.charAt(i) - '0';
                    if (num == 0) continue;
                    int x = Constants.MARGIN_WIDTH + i * Constants.BRICK_WIDTH + (i + 1) * Constants.BRICK_SPACING;
                    int y = Constants.MARGIN_HEIGHT + j * Constants.BRICK_HEIGHT + (j + 1) * Constants.BRICK_SPACING;
                    BrickType type = BrickType.NORMAL;
                    switch (num) {
                        case 1:
                            // good
                            break;
                        case 2:
                            type = BrickType.MEDIUM;
                            break;
                        case 3:
                            type = BrickType.HARD;
                            break;
                        case 4:
                            type = BrickType.IMPOSSIBLE;
                            break;
                        default:
                            System.err.println("Invalid number for BrickType !");

                    }
                    brickList.add(new Brick(x, y, Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT, type));
                }
                ++j;
            }
            System.out.println("\uD83C\uDFAE Current level: " + level);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}