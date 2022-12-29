/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author michel
 */
public class SpaceInvaders extends Application {
        
    // CONFIGURATIONS VALUES
    private int WIDTH = 1600;
    private int HEIGHT = 900;
    
    private boolean GAME_BEGIN = false;
    
    private final Image BACKGROUND_IMAGE_GAME = new Image("images/background_space_invaders.png", WIDTH, HEIGHT, false, false);
    private final Image BACKGROUND_IMAGE_MENU = new Image("images/main-background.png", WIDTH, HEIGHT, false, false);
    
    private final String TITLE = "Space Invaders";
    
    private GraphicsContext gc;
    private GameManager Game;
    private ArrayList<String> input;

    @Override
    public void start(Stage stage) throws Exception {
        Sound sound = new Sound();
        sound.selectSound(Sound.sounds.SOUNDTRACK);
        sound.loop();
        sound.play();
        initUI(stage);
        
    }

    private void initUI(Stage stage){
        // GRAPHICS CONFIGURATIONS
        stage.setTitle(TITLE);
        stage.setFullScreen(false);
        stage.setMaximized(false);
        stage.setResizable(false);

        // HANDLE WITH GAME SCENE
        Group game_group = new Group();
        Scene game_scene = new Scene(game_group);

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        game_group.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();
        gc.drawImage(BACKGROUND_IMAGE_MENU, 0, 0);

        stage.setScene(game_scene);
        
        // DETCAÇÃO DA ENTRADA DO USUÁRIO
        detectKeyboard(game_scene);
        
        // GAME ADMINISTRATION
        Game = new GameManager(gc);
        Game.Start();
        
        AnimationTimer GameTimer = new GameTimer();
        GameTimer.start();
        
        // SHOW STAGE
        stage.show();
    }
    
    private class GameTimer extends AnimationTimer {
        
        @Override
        public void handle(long currentNanoTime) {
            if (GAME_BEGIN == false){
                if (input.contains("SPACE")) {
                    GAME_BEGIN = true;
                }
            } else {
                doHandle(currentNanoTime);
            }
        }
        
        public void doHandle(long currentNanoTime){
            gc.clearRect(0, 0, WIDTH, HEIGHT);
            gc.drawImage(BACKGROUND_IMAGE_GAME, 0, 0);
            
            Game.Update(currentNanoTime, input);
            if (Game.getGameOver()) {
                Game.Finish();
                this.stop();
            }
            
        }
        
    }
    
    private void detectKeyboard(Scene game_scene){
        // KEYBOARD DETECTION
        input = new ArrayList<String>();

        game_scene.setOnKeyPressed((KeyEvent e) -> {
            String code = e.getCode().toString();
            // only add once... prevent duplicates
            if (!input.contains(code)) {
                input.add(code);
            }
        });

        game_scene.setOnKeyReleased((KeyEvent e) -> {
            String code = e.getCode().toString();
            input.remove(code);
        });
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
        
    }
    
}
