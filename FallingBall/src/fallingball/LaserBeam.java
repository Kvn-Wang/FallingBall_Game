package fallingball;

import java.io.File;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class LaserBeam extends Thread
{
    Rectangle laserAvviso;
    
    Scene scene;
    Pane finestra;
    
    double dimensioneImmagine = 50;  
    Image immagine = new Image("SegnoDiPericolo.png", dimensioneImmagine, dimensioneImmagine, true, true);
    ImageView vista = new ImageView(immagine);
        
    double altezza;

    double supp = 0;
    
    //se per il thread è la prima volta che esegue le istruzioni
    boolean firstTime = true;
    
    String file = "./src/laserSound.mp3";
    Media music;
    MediaPlayer playMusic;
    
    Pallina cerchio;
    
    boolean stopLaser = false;
    boolean laserStopped = false;
    
    public LaserBeam(Scene scene, Pane finestra, Pallina cerchio) 
    {
        this.cerchio = cerchio;
        this.scene = scene;
        this.finestra = finestra;
        setDaemon(true);
        
        laserAvviso = new Rectangle(scene.getWidth(), 3);
        
        try
        {
            music = new Media(new File(file).toURI().toString());
            playMusic = new MediaPlayer(music);
        }
        catch(Exception e)
        {
            System.out.println(e.getCause());
        }
    }
    
    @Override
    public void run() 
    {
        try {
            Thread.sleep(5000);
            while(leggiStopLaser() == false)
            {
                creaRaggioLaser();
                
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LaserBeam.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            fermaLaser();
        } catch (InterruptedException ex) {
            Logger.getLogger(LaserBeam.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public synchronized boolean leggiStopLaser()
    {
        return stopLaser;
    }
    
    public synchronized void scriviStopLaser()
    {
        stopLaser = true;
    }
    
    public synchronized void fermaLaser()
    {
        laserStopped = true;
        notifyAll();
    }
    
    public synchronized void aspettaLaser()
    {
        while(laserStopped == false)
        {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(LaserBeam.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void creaRaggioLaser() 
    {
        //genera un num. casuale che corrisponde all'altezza del laser
        altezza = generatore();
        
        //setup dei vari companenti del laser
        //setup segnale pericolo
        vista.relocate((scene.getWidth() - dimensioneImmagine), (altezza - (dimensioneImmagine /2)));
        
        if(firstTime == true)
        {
            Platform.runLater(new Runnable() {
                @Override public void run() {
                    finestra.getChildren().add(vista);
                    vista.setVisible(false);
                }
            });
        }
        
        //setup pre-laser
        laserAvviso.relocate(0, (altezza - (laserAvviso.getHeight() / 2)));
        
        if(firstTime == true)
        {
            Platform.runLater(new Runnable() {
                @Override public void run() {
                   finestra.getChildren().add(laserAvviso);
                   laserAvviso.setVisible(false);
                   firstTime = false;
                }
            });
        }
        
        //start
        spawnAvviso(altezza);
    }
    
    public void spawnAvviso(double altezza) 
    {
        supp = 0;
        
        Timeline tempo = new Timeline(new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) 
            {
                if((supp % 2) == 0)
                {
                    vista.setVisible(true);
                }
                
                if((supp % 2) != 0)
                {
                    vista.setVisible(false);
                }
                
                supp++;
            }
        }));
        tempo.setCycleCount(6);
        tempo.play();
        
        tempo.setOnFinished(event -> {
            spawnLaserDiAvviso(altezza);
        });
    }
    
    public void spawnLaserDiAvviso(double altezza) 
    {
        laserAvviso.setVisible(true);
        supp = 0;
        double costante = 0.01;

        laserAvviso.setFill(Color.web("hsla(0, 100%, 60%," + Double.toString(supp + costante) +")"));
        
        Timeline tempo2 = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {         
                supp += costante;
                
                laserAvviso.setFill(Color.web("hsla(0, 100%, 60%," + Double.toString(supp + costante) +")"));
            }
        }));
        tempo2.setCycleCount(50);
        tempo2.play();
        
        tempo2.setOnFinished(event -> {
            laserAvviso.setVisible(false);
            spawnLaser(altezza);
        });
    }

    final int dimensioneHeightRettangolo = 20;
    Rectangle [] rettangoliSuperiori = new Rectangle[dimensioneHeightRettangolo];
    Rectangle [] rettangoliInferiori = new Rectangle[dimensioneHeightRettangolo];
    int index;
    
    public void spawnLaser(double altezza) 
    {
        supp = 0;
        double costante = 1 / (double) dimensioneHeightRettangolo;
        
        index = 0;
        
        avviaMusica();
        
        Timeline tempo3 = new Timeline(new KeyFrame(Duration.millis(70), new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                rettangoliInferiori[index] = new Rectangle(scene.getWidth(), 1, Color.web("hsla(120, 80%, 40%," + Double.toString(supp + costante) +")"));
                rettangoliSuperiori[index] = new Rectangle(scene.getWidth(), 1, Color.web("hsla(120, 80%, 40%," + Double.toString(supp + costante) +")"));
                supp += costante;
                
                rettangoliInferiori[index].relocate(0, altezza + (1 * index));
                rettangoliSuperiori[index].relocate(0, altezza - (1 * index));
                
                //se la palla è nel range del laser, chiudi la partita
                if((altezza - (1 * index)) < cerchio.palla.getLayoutY() && cerchio.palla.getLayoutY() < (altezza + (1 * index)))
                {
                    cerchio.pallaColpita();
                }
                
                finestra.getChildren().add(rettangoliInferiori[index]);
                finestra.getChildren().add(rettangoliSuperiori[index]);
                        
                index++;
            }
        }));
        
        tempo3.setCycleCount(dimensioneHeightRettangolo);
        tempo3.play();
        
        tempo3.setOnFinished(event -> 
        {
            Timeline pausa = new Timeline(new KeyFrame(Duration.millis(500)));
            pausa.play();
            pausa.setCycleCount(1);
            
            eliminaLaser();
        });
               
    }
    
    public void eliminaLaser()
    {
        index = dimensioneHeightRettangolo - 1;
        
        Timeline tempo4 = new Timeline(new KeyFrame(Duration.millis(30), new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                rettangoliInferiori[index].setVisible(false);
                rettangoliSuperiori[index].setVisible(false);
                
                //se la palla è nel range del laser, chiudi la partita
                if((altezza - (1 * index)) < cerchio.palla.getLayoutY() && cerchio.palla.getLayoutY() < (altezza + (1 * index)))
                {
                    cerchio.pallaColpita();
                }
                
                index--;
            }
        }));
            
        tempo4.setCycleCount(dimensioneHeightRettangolo);
        tempo4.play(); 
    }
    
    public int generatore()
    {
        Random a = new Random();
        
        int numero = a.nextInt((int) scene.getHeight());
        
        return numero;
    }
    
    public void avviaMusica()
    {
        playMusic.seek(Duration.ZERO);
        playMusic.play();
    }
}