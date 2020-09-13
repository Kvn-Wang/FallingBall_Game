package fallingball;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Rettangoli 
{
    ArrayList <Rectangle> rettangoli = new ArrayList();
    ArrayList<Boolean> dangerous = new ArrayList();
    
    //numero di giri prima della creazione del rettangolo
    int maxCycle = 120;
    int cycle = maxCycle;
    
    Scene scene;
    Pane finestra;
    
    //velocita per pixel di ogni rettangolo ad ogni giro
    float velocitàRettangolo = 1;
    
    final int lunghezzaRettangolo = 190;
    final int altezzaRettangolo = 10;
    
    //se è appena iniziato il gioco
    boolean startUp = true;
    int heightStartUP = 300;
    final int costante = 140;
    
    public Rettangoli(Scene scene, Pane finestra) 
    {
        this.scene = scene;
        this.finestra = finestra;
    }
    
    public void creazioneRettangolo()
    {  
        if(startUp == true)
        {
            rettangoli.add(new Rectangle(lunghezzaRettangolo, altezzaRettangolo, Color.web("#003366")));
            dangerous.add(new Boolean(false));
            
            rettangoli.get(rettangoli.size()-1).relocate((scene.getWidth()/2 - lunghezzaRettangolo/2), heightStartUP);
            finestra.getChildren().add(rettangoli.get(rettangoli.size()-1));
            
            while(heightStartUP < (scene.getHeight() - 50))
            {
                int numero = generaNumero();
                heightStartUP += costante;
                
                rettangoli.add(new Rectangle(lunghezzaRettangolo, altezzaRettangolo, Color.web("#003366")));
                dangerous.add(new Boolean(false));
                
                rettangoli.get(rettangoli.size()-1).relocate(numero, heightStartUP);
                finestra.getChildren().add(rettangoli.get(rettangoli.size()-1));
            }
            startUp = false;
            cycle = 0;
        }
        
        else
        {
            cycle = 0;
            
            boolean generazioneDoppiaRettangolo = generaDoppioRettangolo();
            boolean pericoloRettangolo = generaPericoloRettangolo();
            
            Color colore = Color.web("#003366");
            
            if(pericoloRettangolo == true)
            {
                colore = Color.web("#990000");
            }
            
            rettangoli.add(new Rectangle(lunghezzaRettangolo, altezzaRettangolo, colore));
            dangerous.add(new Boolean(pericoloRettangolo));
            
            int numero = generaNumero();
            
            //costante che serve in caso di doppia generazione rettangolo
            int rangeNonAmmissibile = numero;
            
            rettangoli.get(rettangoli.size()-1).relocate(numero, scene.getHeight());

            finestra.getChildren().add(rettangoli.get(rettangoli.size()-1));
            
            //crea un'altro rettangolo oltre a un'altro è stato creato precedentemente
            if(generazioneDoppiaRettangolo == true)
            {
                rettangoli.add(new Rectangle(lunghezzaRettangolo, altezzaRettangolo, colore));
                dangerous.add(new Boolean(pericoloRettangolo));
                
                //condizione che fa in modo che i 2 rettangoli non appaiano attaccati
                while((rangeNonAmmissibile - lunghezzaRettangolo - 30) < numero && numero < (rangeNonAmmissibile + lunghezzaRettangolo + 30))
                {
                    numero = generaNumero();
                }
                
                rettangoli.get(rettangoli.size()-1).relocate(numero, scene.getHeight());
                
                finestra.getChildren().add(rettangoli.get(rettangoli.size()-1));
            }
        }
    }
    
    public void eliminazioneRettangolo()
    {
        rettangoli.remove(rettangoli.get(0));
        dangerous.remove(dangerous.get(0));
    }
    
    public void movimentoRettangolo()
    {
        for(int cont = 0; cont < rettangoli.size(); cont++)
        {
            rettangoli.get(cont).setLayoutY(rettangoli.get(cont).getLayoutY() - velocitàRettangolo);
        }
    }
    
    Random random = new Random();
    
    public int generaNumero()
    {
        final int min = lunghezzaRettangolo / 4;
        final int max = (int) (scene.getWidth() - (lunghezzaRettangolo / 4));

        int numeroRandom = 0;

        while(numeroRandom < min)
        {
           numeroRandom = random.nextInt(max);
        }
        
        return numeroRandom;
    }
    
    public boolean generaPericoloRettangolo()
    {
        boolean condizione = false;
        
        //ho la probabilità di generare un rettangolo "pericoloso" : probMax / probMin
        final int probMin = 1;
        final int probMax = 5;
        
        int numero = random.nextInt(probMax);
        
        if(numero < probMin)
        {
            condizione = true;
        }
        
        return condizione;
    }
    
    public boolean generaDoppioRettangolo()
    {
        boolean condizione = false;
        
        //ho la probabilità di generare un rettangolo "doppio" : probMax / probMin
        final int probMin = 2;
        final int probMax = 5;
        
        int numero = random.nextInt(probMax);
        
        if(numero < probMin)
        {
            condizione = true;
        }
        
        return condizione;
    }
    
    public boolean condizioneDiPericolo(boolean pericolo)
    {
        if(pericolo == true)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}