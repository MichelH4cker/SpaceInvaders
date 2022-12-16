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
     
    @Override
    public void start(Stage stage) throws Exception {
        
        // CONFIGURATIONS VALUES
        int WIDTH = 1600;
        int HEIGHT = 900;
        final Image BACKGROUND_IMAGE = new Image("images/background-image.jpg", WIDTH, HEIGHT, false, false);

        String TITLE = "Space Invaders";
        
        try {
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
            
            GraphicsContext gc = canvas.getGraphicsContext2D();
            GraphicsContext gcBackground = canvas.getGraphicsContext2D();
            
            stage.setScene(game_scene);
            
            // KEYBOARD DETECTION
            ArrayList<String> input = new ArrayList<String>();
            
            game_scene.setOnKeyPressed((KeyEvent e) -> {
                String code = e.getCode().toString();
                // only add once... prevent duplicates
                if ( !input.contains(code) ) input.add( code );
            });
            
            game_scene.setOnKeyReleased((KeyEvent e) -> {
                String code = e.getCode().toString();
                input.remove(code);
            });
            
            // GAME ADMINISTRATION
            GameManager Game = new GameManager(gc);
            
            Game.Start();
            
            // MAIN GAME LOOP 
            new AnimationTimer() {
                
                long prevNanoTime = 0;
                
                @Override
                public void handle(long currentNanoTime) {
                    gc.clearRect(0, 0, WIDTH, HEIGHT);
                    gc.drawImage(BACKGROUND_IMAGE, 0, 0);
                    
                    Game.Update(currentNanoTime, input);
                    if (Game.getGameOver()) {
                        Game.Finish();
                    }
                    
                }
            }.start();
            
            
            // SHOW STAGE
            stage.show();
            
        } catch (Exception e) {
            System.err.println("ERRO!");
            System.err.println("Cause: " + e.getCause());
            System.err.println("Message: " + e.getMessage());
            System.err.println("Localized Message: " + e.getLocalizedMessage());
            System.err.println("Stack Trace: " + e.getStackTrace());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
        
    }
    
}
