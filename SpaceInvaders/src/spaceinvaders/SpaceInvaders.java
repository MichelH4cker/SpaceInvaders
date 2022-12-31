/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * essa classe é responsável por iniciar todas as outras classes, além de possuir
 * o método <code>main</code>e o método <code>start</code>, ambos importantes para o início do programa
 * @author michel (nusp: 12609690)
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
        //sound.loop();
        //sound.play();
        initUI(stage);
        
    }

    /**
     * incializa parte gráfica do jogo
     * @param stage 
     */
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
    
    /**
     * classe que cuida da animação principal do jogo, controlando o seu tempo
     */
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
    
    /**
     * método responsável por capturar qual tecla foi digitada pelo usuário, de 
     * maneira a produzir uma resposta no futuro, tal como 'nada', 'atirar' e 
     * 'mover' a Spaceship
     * @see <code>Spaceship</code>
     * @param game_scene scene principal do jogo
     */
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
