/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author michel
 */
public class SpaceInvaders extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        int WIDTH = 1000;
        int HEIGHT = 1000;
        String TITLE = "Space Invaders";
        
        try {
            stage.setTitle(TITLE);
            stage.setFullScreen(true);
            stage.setResizable(false);

            Group root = new Group();
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("main.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);

            Canvas canvas = new Canvas(WIDTH, HEIGHT);
            root.getChildren().add(canvas);

            GraphicsContext gc = canvas.getGraphicsContext2D();

            GameManager Game = new GameManager(gc, canvas);

            Game.Start();

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
