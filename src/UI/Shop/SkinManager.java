package UI.Shop;

import UI.SceneManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.List;

public class SkinManager {

    private static SkinManager instance;
    private static final String SKINS_FILE = "skins.txt";

    //Paddle + ball skins list
    private final List<SkinData> paddleSkins = new ArrayList<>();
    private final List<SkinData> ballSkins = new ArrayList<>();

    //Current skin
    private int selectedPaddleId = 1;
    private int selectedBallId = 1;

    private final String currentPlayerName = SceneManager.getInstance().getCurrentPlayerName();

    private SkinManager() {
        initSkins();
        loadSkins();
    }

    public static SkinManager getInstance() {
        if (instance == null) {
            instance = new SkinManager();
        }
        return instance;
    }

    /**
     * Initialize.
     *
     */
    private void initSkins() {
        //Paddle skins
        paddleSkins.add(new SkinData(1, "/images/shopImages/paddleSkins/defaultPaddle.png", 0, true));
        paddleSkins.add(new SkinData(2, "/images/shopImages/paddleSkins/redPaddle.png", 500, false));
        paddleSkins.add(new SkinData(3, "/images/shopImages/paddleSkins/bluePaddle.png", 1500, false));
        paddleSkins.add(new SkinData(4, "/images/shopImages/paddleSkins/purplePaddle.png", 2000, false));
        paddleSkins.add(new SkinData(5, "/images/shopImages/paddleSkins/greenPaddle.png", 2500, false));
        paddleSkins.add(new SkinData(6, "/images/shopImages/paddleSkins/pinkPaddle.png", 3000, false));
        paddleSkins.add(new SkinData(7, "/images/shopImages/paddleSkins/orangePaddle.png", 3500, false));

        //Ball skins
        ballSkins.add(new SkinData(1, "/images/shopImages/ballSkins/defaultBall.png", 0, true));
        ballSkins.add(new SkinData(2, "/images/shopImages/ballSkins/ballSkin.png", 500, false));
        ballSkins.add(new SkinData(3, "/images/shopImages/ballSkins/ballSkin2.png", 1500, false));
        ballSkins.add(new SkinData(4, "/images/shopImages/ballSkins/ballSkin3.png", 2000, false));
        ballSkins.add(new SkinData(5, "/images/shopImages/ballSkins/ballSkin4.png", 2500, false));
        ballSkins.add(new SkinData(6, "/images/shopImages/ballSkins/ballSkin5.png", 3000, false));
        ballSkins.add(new SkinData(7, "/images/shopImages/ballSkins/ballSkin6.png", 3500, false));
        ballSkins.add(new SkinData(8, "/images/shopImages/ballSkins/ballSkin7.png", 4000, false));
        ballSkins.add(new SkinData(9, "/images/shopImages/ballSkins/ballSkin8.png", 4500, false));
    }

    // ---Getters---
    public List<SkinData> getPaddleSkins() {
        return paddleSkins;
    }

    public List<SkinData> getBallSkins() {
        return ballSkins;
    }

    public SkinData getSelectedPaddle() {
        return getSkinById(paddleSkins, selectedPaddleId);
    }

    public SkinData getSelectedBall() {
        return getSkinById(ballSkins, selectedBallId);
    }

    private SkinData getSkinById(List<SkinData> skins, int id) {
        for (SkinData skin : skins) {
            if (skin.getId() == id) {
                return skin;
            }
        }
        return null;
    }

    // ---- BUY SKIN ----
    public boolean buyPaddle(int id) {
        SkinData skin = getSkinById(paddleSkins, id);
        if (skin != null && !skin.isOwned()) {
            skin.setOwned(true);
            return true;
        }
        return false;
    }

    public boolean buyBall(int id) {
        SkinData skin = getSkinById(ballSkins, id);
        if (skin != null && !skin.isOwned()) {
            skin.setOwned(true);
            return true;
        }
        return false;
    }

    // ---- EQUIP SKIN ----
    public void selectPaddle(int id) {
        SkinData skin = getSkinById(paddleSkins, id);
        if (skin != null && skin.isOwned()) {
            selectedPaddleId = id;
        }
    }

    public void selectBall(int id) {
        SkinData skin = getSkinById(ballSkins, id);
        if (skin != null && skin.isOwned()) {
            selectedBallId = id;
        }
    }

    /**
     * Load list of skins.
     *
     */
    private void loadSkins() {
        File file = new File(SKINS_FILE);
        if (!file.exists()) {
            System.out.println("Cannot load skin file !");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(SKINS_FILE))) {
            String line1, line2, line3, line4, playerName;
            while ((line1 = reader.readLine()) != null) {
                line2 = reader.readLine();
                line3 = reader.readLine();
                line4 = reader.readLine();
                playerName = reader.readLine();
                if (playerName != null && playerName.equals(currentPlayerName)) {
                    if (line1.startsWith("selectedPaddle:")) {
                        selectedPaddleId = Integer.parseInt(line1.substring("selectedPaddle:".length()));
                    }
                    if (line2.startsWith("selectedBall:")) {
                        selectedBallId = Integer.parseInt(line2.substring("selectedBall:".length()));
                    }
                    if (line3.startsWith("ownedPaddles:")) {
                        setOwnedSkinsFromLine(line3, paddleSkins);
                    }
                    if (line4.startsWith("ownedBalls:")) {
                        setOwnedSkinsFromLine(line4, ballSkins);
                    }
                    System.out.println("Đã load skins cho: " + currentPlayerName);
                    return;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Save list of skins.
     *
     */
    public void saveSkins() {
        List<String> lines = new ArrayList<>();
        boolean found = false;
        File file = new File(SKINS_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(SKINS_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        for (int i = 0; i < lines.size(); i += 5) {
            if (i + 4 < lines.size()) {
                String playerNameLine = lines.get(i + 4);
                if (currentPlayerName.equals(playerNameLine)) {
                    lines.set(i, "selectedPaddle:" + selectedPaddleId);
                    lines.set(i + 1, "selectedBall:" + selectedBallId);
                    lines.set(i + 2, "ownedPaddles:" + getOwnedIds(paddleSkins));
                    lines.set(i + 3, "ownedBalls:" + getOwnedIds(ballSkins));
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            lines.add("selectedPaddle:" + selectedPaddleId);
            lines.add("selectedBall:" + selectedBallId);
            lines.add("ownedPaddles:" + getOwnedIds(paddleSkins));
            lines.add("ownedBalls:" + getOwnedIds(ballSkins));
            lines.add(currentPlayerName);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SKINS_FILE))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Method that setOwnedSkins from each line of file
     *
     * @param line line need to write
     * @param skins current skin
     */
    private void setOwnedSkinsFromLine(String line, List<SkinData> skins) {
        String[] parts = line.split(":", 2);
        if (parts.length > 1) {
            String[] ids = parts[1].split(",");
            for (SkinData skin : skins) {
                skin.setOwned(false);
            }
            for (String idStr : ids) {
                try {
                    int id = Integer.parseInt(idStr.trim());
                    SkinData skin = getSkinById(skins, id);
                    if (skin != null) {
                        skin.setOwned(true);
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    /**
     * Get skin by id
     *
     * @param skins skin that owned
     * @return info of skin
     */
    private String getOwnedIds(List<SkinData> skins) {
        StringBuilder ids = new StringBuilder();
        for (SkinData skin : skins) {
            if (skin.isOwned()) {
                if (!ids.isEmpty()) {
                    ids.append(",");
                }
                ids.append(skin.getId());
            }
        }
        return ids.toString();
    }
}