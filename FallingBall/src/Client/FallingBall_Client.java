package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FallingBall_Client 
{
    final int numeroPorta = 50000;
    
    Socket myServer=new Socket(InetAddress.getLocalHost(), numeroPorta);
    Scanner sc=new Scanner(System.in);

    OutputStreamWriter osw=new OutputStreamWriter(myServer.getOutputStream());
    BufferedWriter bw=new BufferedWriter(osw);
    PrintWriter pw=new PrintWriter(bw, true);

    InputStreamReader isr=new InputStreamReader(myServer.getInputStream());
    BufferedReader br=new BufferedReader(isr);
        
    String tipoDiServizio;

    public FallingBall_Client() throws IOException 
    {}
    
    public boolean compra(int numeroColore, String nomeUtente)
    {
        boolean conferma = false;
        
        tipoDiServizio = "compra"; 
        pw.println(tipoDiServizio);
        
        try
        {
            pw.println(numeroColore);
            pw.println(nomeUtente);
            
            String supp = br.readLine();
            
            //se ho ricevuto il permesso di comprare....
            if(supp.equalsIgnoreCase("true"))
            {
                conferma = true;
            }
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(FallingBall_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return conferma;
    }
    
    public boolean registrazione(String nomeUtente, String password)
    {
        tipoDiServizio = "registrazione";
        pw.println(tipoDiServizio);
        
        pw.println(nomeUtente);
        pw.println(password);
        
        try 
        {
            int conferma = br.read();
            
            //risposta negativa
            if(conferma == 102)
            {
                return false;
            }
            //risposta positiva
            if(conferma == 116)
            {
                return true;
            }
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(FallingBall_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean login(String nomeUtente, String password)
    {
        tipoDiServizio = "login";
        pw.println(tipoDiServizio);
        
        pw.println(nomeUtente);
        pw.println(password);
        
        try 
        {
            String conferma = br.readLine();
            //risposta negativa
            if(conferma.equalsIgnoreCase("false"))
            {
                return false;
            }
            //risposta positiva
            if(conferma.equalsIgnoreCase("true"))
            {
                FileWriter streamCredenziali = new FileWriter("Credenziali.txt");
                PrintWriter bufferCredenziali = new PrintWriter(streamCredenziali);
                
                //ricezione nome utente 
                String credenziale = br.readLine();
                bufferCredenziali.println(credenziale);
                
                streamCredenziali.close();
                bufferCredenziali.close();
                
                FileWriter streamShop = new FileWriter("Shop.txt");
                PrintWriter bufferShop = new PrintWriter(streamShop);
                
                //ricezione colori sbloccati
                for(int cont = 0; cont < 8; cont++)
                {
                    String supp = br.readLine();
                    bufferShop.println(supp);
                }
                
                streamShop.close();
                bufferShop.close();
                
                FileWriter streamSalvataggi = new FileWriter("Saves.txt");
                PrintWriter bufferSalvataggi = new PrintWriter(streamSalvataggi);
                
                //ricezione salvataggi
                for(int cont = 0; cont < 6; cont++)
                {
                    String supp = br.readLine();
                    bufferSalvataggi.println(supp);
                }
                
                streamSalvataggi.close();
                bufferSalvataggi.close();
                return true;
            }
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(FallingBall_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public void esportazioneSalvataggiColori(String nomeUtente)
    {
        try 
        {
            tipoDiServizio = "salvataggioUtente";
            pw.println(tipoDiServizio);
            
            pw.println(nomeUtente);
            
            pw.println("colore");
            
            FileReader frEsportazione = new FileReader("Shop.txt");
            BufferedReader brEsportazione = new BufferedReader(frEsportazione);
            
            for(int cont = 0; cont < 8; cont++)
            {
                String supp = brEsportazione.readLine();
                pw.println(supp);
            }
            
            frEsportazione.close();
            brEsportazione.close();
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(FallingBall_Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FallingBall_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void esportazioneSalvataggiProgressi(String nomeUtente)
    {
        try 
        {
            tipoDiServizio = "salvataggioUtente";
            pw.println(tipoDiServizio);
            
            pw.println(nomeUtente);
            
            pw.println("salvataggioProgresso");
            
            FileReader frEsportazione = new FileReader("Saves.txt");
            BufferedReader brEsportazione = new BufferedReader(frEsportazione);
            
            for(int cont = 0; cont < 6; cont++)
            {
                String supp = brEsportazione.readLine();
                pw.println(supp);
            }
            
            frEsportazione.close();
            brEsportazione.close();
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(FallingBall_Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FallingBall_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
