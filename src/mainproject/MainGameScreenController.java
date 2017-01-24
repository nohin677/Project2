/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainproject;

import changeLineInFile.ChangeLineInFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class MainGameScreenController implements Initializable {

    @FXML
    private Button confirmButton;
    private ChangeLineInFile newChanges;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            createDialogueOptions();
        } catch (IOException ex) {

        }
    }

    public void createDialogueOptions() throws IOException {
        newChanges = new ChangeLineInFile();
        modifyNarratorDialogue1();
    }

    public void modifyNarratorDialogue1() throws IOException {
        String file = "src/files/naratorDialuge1.txt";
        String word1 = GameScreenController.characterClass;
        String word2 = GameScreenController.characterName;
        String word3 = GameScreenController.characterBodyType;
        newChanges.changeALineInATextFile(file, word1+"'s Squire", 4);
        newChanges.changeALineInATextFile(file, word2, 6);
        newChanges.changeALineInATextFile(file, word3, 8);
    }

    public void confirmButtonAction(ActionEvent event) throws Exception {
        String FxName = "storyScreen.fxml";
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        MakingGame newGame = new MakingGame();
        newGame.start(stage, FxName);
    }

}
