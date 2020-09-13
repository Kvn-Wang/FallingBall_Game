package fallingball_server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class FallingBall_Server 
{
    final static int numeroPorta = 50000;

    public static void main(String[] args) throws IOException 
    {
        String nomeFileSalvataggio = "Saves.txt";

        String tipoDiServizio;
        String nomeUtenteFile;
        
        //creo un oggetto server in ascolto sulla porta indicata
        ServerSocket myServer = new ServerSocket(numeroPorta);
        System.out.println("Server in ascolto");
                
        while(true)
        {
            try 
            {
                Socket myClient = myServer.accept();
                System.out.println("\nAccettato client");

                //oggetti per l'output sul stream
                OutputStreamWriter osw=new OutputStreamWriter(myClient.getOutputStream());
                BufferedWriter bw = new BufferedWriter(osw);
                PrintWriter pw = new PrintWriter(bw, true);

                //lettura input stream
                InputStreamReader isr=new InputStreamReader(myClient.getInputStream());
                BufferedReader br=new BufferedReader(isr);
                
                //ricevi il tipo di servizio richiesto dal client
                tipoDiServizio = br.readLine();
                
                System.out.println("tipo di servizio: "+ tipoDiServizio);
                
                switch(tipoDiServizio)
                {
                    case "compra":
                        
                        int numeroColoreRichiesto = br.read();
                        String nomeUtenteCompra = br.readLine();
                        
                        System.out.println("Arrivata la richiesta di comprare il colore numero: "+ numeroColoreRichiesto +" dall'utente: "+ nomeUtenteCompra);
                        pw.println(true);
                        break;
                    
                    case "registrazione":
                        System.out.println("preparo gli elementi necessari a registrarsi");
                        
                        String nomeUtente = br.readLine();
                        String password = br.readLine();
                        
                        //variabili di controllo
                        FileReader streamReaderRegistrazione = new FileReader(nomeFileSalvataggio);
                        BufferedReader bufferReaderRegistrazione = new BufferedReader(streamReaderRegistrazione);
                        
                        String nomeUtenteFileRegistrazione = "";
                        boolean usernameIdentico = false;
                        
                        //ciclo di controllo
                        while(nomeUtenteFileRegistrazione != null)
                        {
                            nomeUtenteFileRegistrazione = bufferReaderRegistrazione.readLine();
                            
                            if(nomeUtente.equalsIgnoreCase(nomeUtenteFileRegistrazione))
                            {
                                usernameIdentico = true;
                                break;
                            }
                        }
                        
                        //username identico
                        if(usernameIdentico == true)
                        {
                            //invio risposta al client
                            pw.println(false);
                        }
                        
                        //in caso contrario, scrivi i nuovi dati su file
                        else if(usernameIdentico == false)
                        {
                            //invio risposta al client
                            pw.println(true);
                            
                            //inizializzazione valori default
                            boolean [] coloriSbloccati = new boolean[8];

                            for(int cont = 0; cont < 8; cont++)
                            {
                                if(cont == 0)
                                {
                                    //il primo colore, quello di default è sempre sbloccato
                                    coloriSbloccati[0] = true;
                                }
                                else
                                {
                                    coloriSbloccati[cont] = false;
                                }
                            }

                            String nomeSalvataggio = "Nessun Salvataggio";
                            double tempoSalvataggio = 0.00;

                            System.out.println("aggiunta dei dati su file");

                            //scrittura su file
                            FileWriter fw1 = null;
                            try 
                            {
                                fw1 = new FileWriter(nomeFileSalvataggio, true);
                                PrintWriter bw1 = new PrintWriter(fw1);

                                bw1.println(nomeUtente);
                                bw1.println(password);

                                for(int cont = 0; cont < 8; cont++)
                                {
                                    bw1.println(coloriSbloccati[cont]);
                                }
                                for(int cont = 0; cont < 3; cont++)
                                {
                                    bw1.println(nomeSalvataggio);
                                    bw1.println(tempoSalvataggio);
                                }
                                //chiusura stream scrittura
                                fw1.close();
                                bw1.close();
                            } 
                            catch (IOException e) 
                            {
                                System.out.println("errore salvataggio file");
                            }
                        }
                        break;
                    
                    case "login":
                        System.out.println("Confronto i dati immessi dall'utente");
                        
                        //oggetti di lettura
                        FileReader streamReader = new FileReader(nomeFileSalvataggio);
                        BufferedReader bufferReader = new BufferedReader(streamReader);
                        
                        String nomeUtenteLogin = br.readLine();
                        String passwordLogin = br.readLine();
                        
                        nomeUtenteFile = "";
                        String passwordFile = "";
                        
                        boolean utenteTrovato = false;
                        
                        while(nomeUtenteFile != null)
                        {
                            nomeUtenteFile = bufferReader.readLine();
                            
                            if(nomeUtenteLogin.equalsIgnoreCase(nomeUtenteFile))
                            {
                                passwordFile = bufferReader.readLine();
                                
                                if(passwordLogin.equals(passwordFile))
                                {
                                    utenteTrovato = true;
                                    break;
                                }
                            }
                        }
                        
                        if(utenteTrovato == true)
                        {
                            System.out.println("dato trovato sul file, invio dati al client");
                            pw.println(true);
                            
                            //invio credenziali al client
                            pw.println(nomeUtenteFile);
                            
                            //invio colori sbloccati
                            for(int cont = 0; cont < 8; cont++)
                            {
                                String supp = bufferReader.readLine();
                                pw.println(supp);
                                System.out.println(supp);
                            }
                            
                            //invio salvataggi dell'utente in questione
                            for(int cont = 0; cont < 6; cont++)
                            {
                                String supp = bufferReader.readLine();
                                pw.println(supp);
                                System.out.println(supp);
                            }
                        }
                        else
                        {
                            System.out.println("dato non trovato sul file, invio risposta al client");
                            pw.println(false);
                        }
                        
                        streamReader.close();
                        bufferReader.close();
                        break;
                    
                    case "salvataggioUtente":
                        String nomeUtenteCercaColore = br.readLine();
                        System.out.println("Nome utente: "+ nomeUtenteCercaColore);
                        
                        String tipoDiSalvataggio = br.readLine();
                        System.out.println("Tipo di salvataggio: "+ tipoDiSalvataggio);
                        
                        nomeUtenteFile = "";
                            
                        ArrayList <String> contenitoreDatiFile = new ArrayList<>();
                        
                        //variabili di controllo
                        FileReader streamReaderColore = new FileReader(nomeFileSalvataggio);
                        BufferedReader bufferReaderColore = new BufferedReader(streamReaderColore);
                        
                        while(nomeUtenteFile != null)
                        {
                            nomeUtenteFile = bufferReaderColore.readLine();
                            
                            if(nomeUtenteFile == null)
                            {
                                break;
                            }
                            else
                            {
                                contenitoreDatiFile.add(new String(nomeUtenteFile));
                            }
                        }
                        for(int cont = 0; cont < contenitoreDatiFile.size(); cont++)
                        {
                            if(contenitoreDatiFile.get(cont).equalsIgnoreCase(nomeUtenteCercaColore))
                            {
                                if(tipoDiSalvataggio.equalsIgnoreCase("colore"))
                                {
                                    //vai avanti di due posti per skippare user e psw
                                    cont+=2;
                                    
                                    for(int cont1 = cont; cont1 < cont+8; cont1++)
                                    {
                                        contenitoreDatiFile.set(cont1, br.readLine());
                                    }
                                }
                                else if(tipoDiSalvataggio.equalsIgnoreCase("salvataggioProgresso"))
                                {
                                    //vai avanti di n posti per skippare user e psw e i salvataggiColori
                                    cont+=10;
                                    
                                    for(int cont1 = cont; cont1 < cont+6; cont1++)
                                    {
                                        contenitoreDatiFile.set(cont1, br.readLine());
                                    }
                                }
                                
                                //sovrascrittura intero file
                                try 
                                {
                                    FileWriter fw1 = new FileWriter(nomeFileSalvataggio);
                                    PrintWriter bw1 = new PrintWriter(fw1);
                                    
                                    for(int cont2 = 0; cont2 < contenitoreDatiFile.size(); cont2++)
                                    {
                                        bw1.println(contenitoreDatiFile.get(cont2));
                                    }
                                    
                                    //chiusura stream scrittura
                                    fw1.close();
                                    bw1.close();
                                } 
                                catch (IOException e) 
                                {
                                    System.out.println("errore salvataggio file");
                                }
                                
                                break;
                            }
                        }
                        break;
                }
                
                System.out.println("Richiesta Processata con successo");
            } 
            catch (IOException ex)
            {
                System.out.println("Errore nel processare la richiesta");
            }
        }
    }
}