package UI.Shop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import UI.SceneManager;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Credits managers : Singleton
 *
 */
public class CreditsManager {

    // Just only one credits manager.
    private static CreditsManager instance;
    private final IntegerProperty credits = new SimpleIntegerProperty(0);
    private static final String CREDITS_FILE = "credits.txt";
    private final String currentPlayerName = SceneManager.getInstance().getCurrentPlayerName();

  private CreditsManager() {
    loadCredits();
  }

  public static CreditsManager getInstance(){
    if(instance == null){
      instance = new CreditsManager();
    }
    return instance;
  }

    /**
     * Load credits.
     *
     */
    private void loadCredits() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDITS_FILE))) {
            String creditsLine;
            String nameLine;
            while ((creditsLine = reader.readLine()) != null) {
                nameLine = reader.readLine();
                if (nameLine != null && nameLine.equals(currentPlayerName)) {
                    setCredits(Integer.parseInt(creditsLine));
                    return;
                }
            }
            setCredits(0);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            setCredits(0);
        }
    }

    /**
     * Save credits.
     *
     */
    public void saveCredits() {
        List<String> lines = new ArrayList<String>();
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDITS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        for (int i = 0; i < lines.size(); i += 2) {
            if (i + 1 < lines.size()) {
                String nameLine = lines.get(i + 1);
                if (currentPlayerName.equals(nameLine)) {
                    lines.set(i, String.valueOf(getCredits()));
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            lines.add(String.valueOf(getCredits()));
            lines.add(currentPlayerName);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CREDITS_FILE))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Add credits
     *
     * @param amount amount of credits
     */
    public void addCredits(int amount){
        int newCredits = getCredits() + amount;
        credits.set(newCredits);
        saveCredits();
    }

    /**
     * Spend credits
     *
     * @param amount amount of credits
     * @return yes or no ? Can you buy it ??
     */
  public boolean spendCredits(int amount){ //Only spend if enough credits
    if(getCredits() >= amount){
      int newCredits = getCredits() - amount;
      credits.set(newCredits);
      saveCredits();
      return true;
    }
    return false;
  }


  // Getter and Setter methods.
  public int getCredits(){
    return credits.get();
  }

  public void setCredits(int amount){
    credits.set(amount);
  }

  public IntegerProperty getCreditsProperty(){
    return credits;
  }

}
