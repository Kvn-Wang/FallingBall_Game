package particleSystem;

import java.util.Random;
import javafx.scene.Scene;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Particella 
{
    Scene scene;
    
    Random random = new Random();
    double min = 0;
                
    double randomValue = (min + (800 - min) * random.nextDouble());
    double randomValue2 = (min + (550 - min) * random.nextDouble());
    
    PVector particella = new PVector(randomValue, randomValue2); 
    PVector velocita = new PVector(0, 0);
    PVector accelerazione = new PVector();
    
    double lifeTime = 200;
    
    String coloreCasuale = Integer.toString(random.nextInt(360));
    Color colore = Color.web("hsla(" +coloreCasuale+ ", 100%, 50%, 1.0)");
    
    Ellipse figura = new Ellipse(particella.x, particella.y, 1, 1);
    DropShadow shadow = new DropShadow(BlurType.GAUSSIAN, colore, 45, 0.990, 0, 0);
    
    public Particella(Scene scene, Pane root) 
    {
        this.scene = scene;
        
        root.getChildren().add(figura);
        
        figura.setFill(Color.web("hsla(0, 0%, 100%,1.0)"));
        figura.setEffect(shadow);
    }
    
    public void update()
    {
        accelerazione = accelerazione.random();
        
        velocita = velocita.add(accelerazione);
        velocita = velocita.limit(velocita);
        
        particella = particella.add(velocita);
        
        figura.relocate(particella.x, particella.y);
        
        lifeTime--;
    }
}