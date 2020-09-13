package fallingball;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class TimerGioco 
{
    final double startMillis = System.currentTimeMillis();
    
    Text testo = new Text("Secondi:");
            
    public double secondi;
            
    boolean stop = false;
    
    public TimerGioco() 
    {
        testo.relocate(330, 15);
        
        String family = "Brush Script MT";
        
        testo.setFont(Font.font(family, FontWeight.BOLD ,FontPosture.ITALIC, 30));
    }

    public void aggiornamento()
    {
        if(stop == false)
        {
            long currentMillis = System.currentTimeMillis();
        
            double time = Math.round(((currentMillis - startMillis) / 1000) * 100.0) / 100.0;

            secondi = time;

            testo.setText("Secondi: " + secondi + "s");
        }
    }
    
    public void stopTimer()
    {
        stop = true;
    }
}