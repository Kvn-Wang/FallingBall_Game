package particleSystem;

import java.util.Random;

class PVector 
{
    double x;
    double y;

    public PVector() {
    }
    
    public PVector(double x, double y) 
    {
        this.x = x;
        this.y = y;
    }

    public PVector add(PVector other) 
    {
        return new PVector(x + other.x, y + other.y);
    }
    
    public PVector random() 
    {
        Random generatore = new Random();
        
        double max = 0.2;
        double min = -0.2;
                
        double randomValue = min + (max - min) * generatore.nextDouble();
        double randomValue2 = min + (max - min) * generatore.nextDouble();
        
        return new PVector(randomValue, randomValue2);
    }
    
    public PVector limit(PVector vettore)
    {
        int max = 2;
        
        if(vettore.x > max)
        {
            vettore.x = max;
        }
        
        if(vettore.y > max)
        {
            vettore.y = max;
        }
        
        return new PVector(vettore.x, vettore.y);
    }
}