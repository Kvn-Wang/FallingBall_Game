package particleSystem;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ParticleSystem extends Thread
{
    ArrayList <Particella> system = new ArrayList<>();

    Scene scene;
    Pane root;

    public ParticleSystem(Scene scene, Pane root) 
    {
        this.scene = scene;
        this.root = root;
        setDaemon(true);
    }
    
    Random random = new Random();
    int min = 5;
    int max = 30;

    int delay = random.nextInt(max - min) + min;
    int cont = 0;
            
    @Override
    public void run()
    {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ParticleSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Timeline tempoParticella = new Timeline(new KeyFrame(Duration.millis(25), new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                for(int cont = 0; cont < system.size(); cont++)
                {
                    system.get(cont).update();

                    if(system.get(cont).lifeTime <= 0.0)
                    {
                        system.get(cont).figura.setVisible(false);
                        system.remove(cont);
                    }
                }
                cont++;
                
                if(cont >= delay)
                {
                    delay = random.nextInt(max - min) + min;
                    cont = 0;
                    
                    aggiungiParticella();
                }
            }
        }));
        tempoParticella.setCycleCount(Timeline.INDEFINITE);
        tempoParticella.play();
    }
    
    public void aggiungiParticella()
    {
        system.add(new Particella(scene, root));
    }
}