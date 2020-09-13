package fallingball;

import javafx.scene.Scene;
import javafx.scene.shape.Circle;

public class Pallina 
{
    final double radiusCerchio = 20;
    
    Circle palla;
    
    float maxVxX = 5;
    float maxVxY = (float) 3;
    
    float accelerazioneVxX = (float) 0.5;
    float accelerazioneVxY = (float) 0.1;
    
    float vxX = 0;
    float vxY = maxVxY;
    
    boolean pallaColpita = false;

    public Pallina(Scene scene, Circle palla) 
    {
        this.palla = palla;
        
        palla.relocate(scene.getWidth() /2 , 0);
    }
    
    public void giraDestra()
    {
        vxX += accelerazioneVxX;
    }
    
    public void giraSinistra()
    {
        vxX -= accelerazioneVxX;
    }
    
    public void rilocalizzaX()
    {
        palla.setLayoutX(palla.getLayoutX() + vxX);
    }
    
    public void rilocalizzaY()
    {
        palla.setLayoutY(palla.getLayoutY() + vxY);
    }
    
    public void accelerazioneY()
    {
        vxY = maxVxY;
    }
    
    public synchronized void pallaColpita()
    {
        pallaColpita = true;
    }
    
    public synchronized boolean leggiPallaColpita()
    {
        return pallaColpita;
    }
}