package fallingball;

import LosingScreen.LoseScreen;
import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FallingBall extends Application
{
    Circle palla;
    
    Stage menuDiGioco;
    
    Media music;
    MediaPlayer playMusic;
    
    Timeline tempo;
    
    public FallingBall(Circle palla,Stage menuDiGioco) 
    {
        this.menuDiGioco = menuDiGioco;
        this.palla = palla;
    }
    
    @Override
    public void start(Stage primaryStage)
    {
        Pane root = new Pane();
        
        Pane finestra = new Pane();
        root.getChildren().add(finestra);
        
        Scene scene = new Scene(root, 550, 700);
        
        Pallina cerchio = new Pallina(scene, palla);
        Rettangoli rettangolo = new Rettangoli(scene, finestra);
        
        LaserBeam laser = new LaserBeam(scene, finestra, cerchio);
        laser.start();

        TimerGioco timer = new TimerGioco();

        Image image = new Image("BackGroundImage.png", scene.getWidth() + 10, scene.getHeight() + 10, false, true);
        ImageView imageView = new ImageView(image);
        finestra.getChildren().add(imageView);
        
        try {
            music = new Media(new File("./src/MainMusic.mp3").toURI().toURL().toExternalForm());
            playMusic = new MediaPlayer(music);
            playMusic.play();
        }
        catch (MalformedURLException ex) 
        {
            Logger.getLogger(FallingBall.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        LosingScreen.LoseScreen loseScreen = new LoseScreen(timer, imageView, root, scene, menuDiGioco, primaryStage, laser, playMusic);
        loseScreen.start();

        primaryStage.setTitle("Falling Ball");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);

        //keyPressed event
        scene.setOnKeyPressed((e -> 
        {
            if (e.getCode() == KeyCode.A) 
            {
                if(cerchio.vxX > -cerchio.maxVxX)
                {
                    cerchio.giraSinistra();
                }
            }

            if (e.getCode() == KeyCode.D) {
                if(cerchio.vxX < cerchio.maxVxX)
                {
                    cerchio.giraDestra();
                }
            }
        }));
        
        finestra.getChildren().add(cerchio.palla);
        finestra.getChildren().add(timer.testo);
        
        tempo = new Timeline(new KeyFrame(Duration.millis(25), new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                boolean pallaSopraRettangolo = false;
                int suppCont = 0;

                cerchio.rilocalizzaX();
                timer.aggiornamento();
                
                //ripeti la condizione per ogni rettangolo esistente
                for(int cont = 0; cont < rettangolo.rettangoli.size(); cont++)
                {
                    //condizione velocita cerchio - rettangolo
                    //se il cerchio va contro il rettangolo
                    //Y superioreRettangolo < Y baseCerchio < Y inferiore Rettangolo
                    if((rettangolo.rettangoli.get(cont).getLayoutY() < (cerchio.palla.getLayoutY() + cerchio.palla.getRadius() + cerchio.vxY)) && ((cerchio.palla.getLayoutY() + cerchio.palla.getRadius() + cerchio.vxY) < rettangolo.rettangoli.get(cont).getLayoutY() + rettangolo.rettangoli.get(cont).getHeight()))
                    {
                        //se è nel width del rettangolo
                        if(rettangolo.rettangoli.get(cont).getLayoutX() < cerchio.palla.getLayoutX() && cerchio.palla.getLayoutX() < rettangolo.rettangoli.get(cont).getLayoutX() + rettangolo.rettangoli.get(cont).getWidth())
                        {
                            //se il rettangolo è pericoloso = fine partita
                            if(rettangolo.dangerous.get(cont) == true)
                            {
                                cerchio.pallaColpita();
                            }
                            else
                            {
                                //azzera la velocita della palla e trasporta la palla al di sopra del rettangolo
                                cerchio.vxY = 0;
                                pallaSopraRettangolo = true;
                                suppCont = cont;
                            }
                        }
                    }
                }
                
                if(pallaSopraRettangolo == true)
                {
                    cerchio.palla.setLayoutY(rettangolo.rettangoli.get(suppCont).getLayoutY() - cerchio.palla.getRadius());
                }
                else
                {
                    cerchio.accelerazioneY();
                    cerchio.rilocalizzaY();
                }
                
                //se si è raggiunto il n.di cicli necessari, crea un rettangolo in una poszione X random
                if(rettangolo.cycle == rettangolo.maxCycle)
                {
                    rettangolo.creazioneRettangolo();
                }
                rettangolo.cycle++;
                
                //muovi tutti i rettangolo verso l'alto
                rettangolo.movimentoRettangolo();
                
                //se la palla è finita fuori mappa
                if(((cerchio.palla.getLayoutY() - cerchio.palla.getRadius()) > scene.getHeight()) || (cerchio.palla.getLayoutX() < (0 - cerchio.palla.getRadius() - 50)) || (cerchio.palla.getLayoutX() > (scene.getWidth() + 50)) || (cerchio.palla.getLayoutY() < (0 - cerchio.palla.getRadius())) )
                {
                    cerchio.pallaColpita();
                } 
                
                //condizione di uscita dal gioco
                if(cerchio.leggiPallaColpita())
                {
                    try 
                    {
                        timer.stopTimer();
                        loseScreen.hoPerso();
                    } 
                    catch (Exception ex) 
                    {
                        Logger.getLogger(FallingBall.class.getName()).log(Level.SEVERE, null, ex);
                    }   
                }
                
                //se il rettangolo è fuori mappa, eliminalo
                if((rettangolo.rettangoli.get(0).getLayoutY() + rettangolo.altezzaRettangolo) < 0)
                {
                    rettangolo.eliminazioneRettangolo();
                }
                
                if(loseScreen.chiusura == true)
                {
                    tempo.stop();
                }
            }
        }));
        
        tempo.setCycleCount(Timeline.INDEFINITE);
        tempo.play();
    }
}