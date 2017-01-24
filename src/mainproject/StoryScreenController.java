/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainproject;

import Dialogues.Dialogue;
import File.MakeMusicFile;
import File.MakeTextFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.animation.Animation.Status.STOPPED;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class StoryScreenController implements Initializable {

    @FXML
    private TextArea mainTextArea;
    @FXML//test
    private ComboBox<?> characterComboBox;
    @FXML
    private Button inventoryButton;
    @FXML
    private TreeView<?> equipmentTreeView;
    @FXML
    private ListView<String> commandsListView;
    @FXML
    private Button saveButton;
    @FXML
    private MediaView mv;

    private MediaPlayer mp;
    private MakeMusicFile newMusic;
    private Media me;
    private MakeTextFile newTextFile;
    private int time;
    private Timeline timeline;
    private boolean running, condition = true;
    private Thread thread;
    private int chapter = 0;
    private int nextQuestion;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        running = true;
        thread = new Thread(new game());
        thread.start();
        Dialogue dialogues = new Dialogue();
        playMusic();
        
        //startGame();
        //startDialuge();

    }

    private void scene1(int chapters) {
        switch (chapter) {
            case 0:

                mainTextArea.setEditable(false);
                if (condition) {
                    mainTextArea.appendText("Act 1, Scene 1 \n");
                    condition = false;
                }
                chapter = 1;

                break;
            case 1:
                if (!condition) {
                    //System.out.println("no");
                    dialuge1();
                    condition = true;
                }
                chapter = 2;
                break;
            case 2:
                if (condition && timeline.getStatus().equals(STOPPED)) {
                    squireDialuge(nextQuestion);
                    condition = false;
                    chapter = 3;
                }

                break;
            case 3:
                if (!condition) {
                    //squireDialuge(nextQuestion);
                }

        }
    }

    public void startDialuge() {
        newTextFile = new MakeTextFile("src/files/naratorDialuge1.txt");
        newTextFile.createNewFile();
        try {
            newTextFile.createInputStream();
        } catch (FileNotFoundException ex) {

        }
    }

    public void dialuge1() {

        int cycle = 9;
        setTimer(cycle);

    }

    public void squireDialuge(int i) {
        String[] squireDialuge = {"Hey " + GameScreenController.characterName + " aren't you exited for the war? \n", "", "Yea it's going to be fun"};
        mainTextArea.appendText(squireDialuge[i]);
        String[][] avaibleCommands = {{"Sayyes", "Sayno", "ignore"}, {}};
        String[][] textComments = {{"yes", "no", ""}, {}};
        avaliableCommandsDialuge(avaibleCommands[i], i, textComments[i]);
    }

    public void avaliableCommandsDialuge(String[] commands, int i, String[] textCommands) {
        commandsListView.getItems().addAll(commands);
        
       commandsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        // Your action here
        
       newValue = textCommands[commandsListView.getItems().indexOf(newValue)];
       mainTextArea.appendText(newValue);
        commandsListView.getItems().removeAll();
    }
});
        
        ObservableList<String> commands2;
        commands2 = commandsListView.getSelectionModel().getSelectedItems();
        
        /*if(!commands2.get(0).equals(null)){
        System.out.println(commands2.get(0));
        }*/

    }

    public void setTimer(int cycle) {

        time = 0;
        timeline = new Timeline(new KeyFrame(
                Duration.millis(100),
                e -> displayWords()));
        timeline.setCycleCount(cycle);
        timeline.play();
        //squireDialuge();

    }

    public void startGame() {
        int chapters = 0;
        startDialuge();
        while (running) {
            act1(chapters);
        }
        stop();
    }

    public void act1(int chapters) {

        scene1(chapters);
    }

    public void stop() {
        if (!running) {
            return;
        }
        running = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(StoryScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void displayWords() {
        //time++;

        try {
            mainTextArea.appendText(newTextFile.OutPutReadLine() + "\n");

        } catch (IOException ex) {
            System.err.print("Error Found");
        }
    }

    public void playMusic() {

        String path = new File("src/media/song2.mp3").getAbsolutePath();
        me = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(me);
        mv.setMediaPlayer(mp);
        mp.setAutoPlay(true);

        /*newMusic = new MakeMusicFile("C:/Users/USER/Documents/NetBeansProjects/MainProject/src/media/song2.mp3",mp);
         try {
         newMusic.createNewFile();
         newMusic.createMusicPlayer();
         } catch (FileNotFoundException ex) {
         }
         mv.setMediaPlayer(newMusic.returnMediaPlayer());
         (newMusic.returnMediaPlayer()).play();*/
    }

    class game implements Runnable {

        @Override
        public void run() {
            startGame();
        }
        /*
         public void startGame(){
         while(true){
         act1();
         }
         }
         public void act1(){
         int chapters = 0;
         scene1(chapters);
         }*/
    }

}
