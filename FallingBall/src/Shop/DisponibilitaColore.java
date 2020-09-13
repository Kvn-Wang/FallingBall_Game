package Shop;

import Client.FallingBall_Client;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DisponibilitaColore 
{
    String nomeUtente;
    
    final int numeroColori = 8;
    
    boolean [] disponibile = new boolean[numeroColori];

    FileReader fr;
    BufferedReader br;
    
    public DisponibilitaColore(String nomeUtente) 
    {
        this.nomeUtente = nomeUtente;
        
        for(int cont = 0; cont < numeroColori; cont++)
        {
            disponibile[cont] = new Boolean(false);
        }
        
        try 
        {
            fr = new FileReader("Shop.txt");
            br = new BufferedReader(fr);
            caricaDatiFile();
        } catch (FileNotFoundException ex) {} catch (IOException ex) {}
    }
    
    public void caricaDatiFile()
    {
        try 
        {
            for(int cont = 0; cont < numeroColori; cont++)
            {
                String supp;

                supp = br.readLine();
                if(supp.equalsIgnoreCase("True"))
                {
                    disponibile[cont] = true;
                }
                if(supp.equalsIgnoreCase("False"))
                {
                    disponibile[cont] = false;
                }
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(DisponibilitaColore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void scriviDatiFile()
    {
        FileWriter fw = null;
        try 
        {
            fw = new FileWriter("Shop.txt");
            PrintWriter bw = new PrintWriter(fw);
            
            for(int cont = 0; cont < disponibile.length; cont++)
            {
                bw.println(disponibile[cont]);
            }
            
            fw.close();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(DisponibilitaColore.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void esportaDati()
    {
        try 
        {
            FallingBall_Client client = new FallingBall_Client();
            client.esportazioneSalvataggiColori(nomeUtente);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(DisponibilitaColore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}