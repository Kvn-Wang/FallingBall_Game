package LosingScreen;

import fallingball.LaserBeam;
import fallingball.TimerGioco;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;

public class LoseScreen extends Thread
{
    fallingball.TimerGioco timer;
    
    boolean perso = false;
    
    ColorAdjust blackout = new ColorAdjust(0, 0, -0.5, 0);
    
    ImageView imageView;
    
    Pane root;
    
    Scene scene;
    
    Stage menuDiGioco;
    Stage giocoPrincipale;
    
    LaserBeam laser;
    
    MediaPlayer playMusic;
    
    public boolean chiusura = false;
    
    public LoseScreen(TimerGioco timer, ImageView imageView, Pane root, Scene scene, Stage menuDiGioco, Stage giocoPrincipale, LaserBeam laser, MediaPlayer playMusic) 
    {
        this.playMusic = playMusic;
        this.laser = laser;
        this.giocoPrincipale = giocoPrincipale;
        this.menuDiGioco = menuDiGioco;
        this.timer = timer;
        
        setDaemon(true);
        
        this.imageView = imageView;
        this.root = root;
        this.scene = scene;
        
        scene.getStylesheets().add(LoseScreen.class.getResource("StyleSheet.css").toExternalForm());
    }  
    
    public int messaggioFinePartita()
    {
        //oscura l'immagine di background
        imageView.setEffect(blackout);
        
        int a = JOptionPane.showConfirmDialog(null, "Hai perso!!!\nHai resistito: " + timer.secondi + " Secondi \nDesidera Salvare la partita?", "Messagio Fine Partita", YES_NO_OPTION, INFORMATION_MESSAGE);
        
        return a;
    }
    
    public synchronized void hoPerso()
    {
        //entra solo una volta
        if(!perso)
        {
            perso = true;
            notifyAll();
        }
    }
    
    public void quit()
    {
        aspetta();
        
        int supp = messaggioFinePartita();
        
        //Option: SI
        if(supp == 0)
        {
            Pane finestra = new Pane();
            
            Image image = new Image("Cornice.png", 400, 500, false, true);
            ImageView imageView = new ImageView(image);
            
            double corniceSuperiore = 63;
            double corniceDestro = 42;
            
            Rectangle rettangolo = new Rectangle(image.getWidth() - (corniceDestro * 2), image.getHeight() - (corniceSuperiore * 2), Color.web("hsla(90, 100%, 20%, 0.7)"));
            rettangolo.relocate(corniceDestro, corniceSuperiore);
            
            finestra.relocate((scene.getWidth() / 2 ) - (image.getWidth() / 2), (scene.getHeight() / 2 ) - (image.getHeight() / 2));
                    
            finestra.getChildren().add(imageView);
            finestra.getChildren().add(rettangolo);
            
            Label tempoOttenuto = new Label("Tempo resistito: "+ timer.secondi +"s");
            
            Label testo = new Label("Inserire il nome con cui registrarsi: ");
            TextArea testoNome = new TextArea("inserire il Nome...");
            testoNome.setPrefHeight(2);  
            testoNome.setPrefWidth(250);    
            
            BottoniDiSalvataggio bottoniSalvataggio = new BottoniDiSalvataggio(testoNome ,timer.secondi);
            VBox contenitoreSalvataggi = new VBox(10, bottoniSalvataggio.bottoni.get(0), bottoniSalvataggio.bottoni.get(1), bottoniSalvataggio.bottoni.get(2));
            
            Button bottoneConferma = new Button("Conferma");
            Button bottoneEsci = new Button("esci");
            HBox contenitoreAzioni = new HBox(30, bottoneEsci, bottoneConferma);
            
            final double posizioneX = corniceDestro + 20;
            final double posizioneY = corniceSuperiore + 10;
            
            Label messaggioErrore = new Label("Si prega si selezionare lo slot di salvataggio \nprima di confermare o di inserire \nun nome valido");
            messaggioErrore.setVisible(false);
            
            VBox contenitoreGenerale = new VBox(13, tempoOttenuto, testo, testoNome, contenitoreSalvataggi, contenitoreAzioni, messaggioErrore);
            contenitoreGenerale.relocate(posizioneX, posizioneY);
            
            finestra.getChildren().add(contenitoreGenerale);
            
            Platform.runLater(new Runnable() 
            {
                @Override public void run() 
                {
                    root.getChildren().add(finestra);
                }
            });
            
            bottoneEsci.setOnAction(new EventHandler<ActionEvent>() 
            {
                @Override
                public void handle(ActionEvent event) 
                {
                    int a = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler uscire?", "Domanda", YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if(a == 0)
                    {
                        laser.scriviStopLaser();
                        chiusura = true;
                        
                        //aspetta che il thread laser si fermi
                        laser.aspettaLaser();
                        
                        playMusic.stop();
                        giocoPrincipale.close();
                        menuDiGioco.show();
                    }
                }
            });
            
            bottoneConferma.setOnAction(new EventHandler<ActionEvent>() 
            {
                @Override
                public void handle(ActionEvent event) 
                {
                    boolean success = bottoniSalvataggio.salvataggio();
                    
                    if(success == false)
                    {
                        messaggioErrore.setVisible(true);
                    }
                }
            });
        }
        //è stata selezionata l'opzione:"No"
        else if(supp == 1)
        {
            Platform.runLater(new Runnable() 
            {
                @Override public void run() 
                {
                    laser.scriviStopLaser();
                    chiusura = true;
                        
                    //aspetta che il thread laser si fermi
                    laser.aspettaLaser();

                    playMusic.stop();
                    giocoPrincipale.close();
                    menuDiGioco.show();
                }
            });
        }
    }
    
    public synchronized void aspetta()
    {
        while(perso == false)
        {
            try 
            {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(LoseScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void run()
    {
        quit();
    }
}