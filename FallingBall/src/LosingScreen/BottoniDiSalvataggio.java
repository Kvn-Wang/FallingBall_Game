package LosingScreen;

import Client.FallingBall_Client;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.YES_NO_OPTION;

public class BottoniDiSalvataggio 
{
    String nomeUtente;
    
    ArrayList <Button> bottoni = new ArrayList();
    ArrayList <InformazioniSalvataggio> info = new ArrayList();
    
    Image immagineBottone = new Image("Add.png", 20, 20, true, true);
    ImageView viewImmage = new ImageView(immagineBottone);
    
    //5 = nessun bottone selezionato
    int bottoneSelezionato = 5;
    
    TextArea nome;
    double tempo;

    FileReader fr;
    BufferedReader br;
    
    public BottoniDiSalvataggio(TextArea nome, double tempo) 
    {
        try 
        {
            FileReader streamCredenziali = new FileReader("Credenziali.txt");
            BufferedReader bufferCredenziali = new BufferedReader(streamCredenziali);
            
            nomeUtente = bufferCredenziali.readLine();
            
            streamCredenziali.close();
            bufferCredenziali.close();
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(BottoniDiSalvataggio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BottoniDiSalvataggio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try 
        {
            fr = new FileReader("Saves.txt");
            br = new BufferedReader(fr);
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(BottoniDiSalvataggio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        for(int cont = 0; cont < 3; cont++)
        {
            try 
            {
                String suppNome = br.readLine();
                double suppTempo = Double.parseDouble(br.readLine());
                
                info.add(new InformazioniSalvataggio(suppNome, suppTempo));
            }
            catch (IOException ex)
            {
                Logger.getLogger(BottoniDiSalvataggio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        for(int cont = 0; cont < 3; cont++)
        {
            bottoni.add(new Button("Salvataggio "+ cont +"        Utente: "+ info.get(cont).nomeUtente +"\n                              Tempo: "+ info.get(cont).tempoOttenuto));
        }
        
        this.nome = nome;
        this.tempo = tempo;
        
        bottoni.get(0).setStyle("-fx-background-color: #5cd65c;");
        bottoni.get(1).setStyle("-fx-background-color: #5cd65c;");
        bottoni.get(2).setStyle("-fx-background-color: #5cd65c;");
        
        bottoni.get(0).setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                bottoneSelezionato = 0;
                refresh(0);
            }
        });
        
        bottoni.get(1).setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                bottoneSelezionato = 1;
                refresh(1);
            }
        });
        
        bottoni.get(2).setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                bottoneSelezionato = 2;
                refresh(2);
            }
        });
    }
    
    public void refresh(int numero)
    {
        for(int cont = 0; cont < bottoni.size(); cont++)
        {
            if(cont == numero)
            {
                bottoni.get(cont).setStyle("-fx-background-color: #006bb3;");
                bottoni.get(cont).setGraphic(viewImmage);
            }
            else
            {
                bottoni.get(cont).setStyle("-fx-background-color: #5cd65c;");
                bottoni.get(cont).setGraphic(null);
            }
        }
    }
    
    public boolean salvataggio()
    {
        //nessun bottone è stato selezionato o nessun nome
        if(bottoneSelezionato == 5 || nome.getText().equalsIgnoreCase("inserire il Nome..."))
        {
            return false;
        }
        
        info.get(bottoneSelezionato).nomeUtente = nome.getText();
        info.get(bottoneSelezionato).tempoOttenuto = tempo;
        
        int a = JOptionPane.showConfirmDialog(null, "Sei sicuro di salvare con il nome: "+ nome.getText() +"\n e con un tempo di: "+ tempo +"s?", "Domanda", YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if(a == 0)
        {
            try
            {
                FallingBall_Client client = new FallingBall_Client();
                salvataggioFile();
                refreshSalvataggi();
                
                client.esportazioneSalvataggiProgressi(nomeUtente);
            } 
            catch (IOException ex) 
            {
                JOptionPane.showConfirmDialog(null, "Impossibile contattare il server, si prega di accenderlo prima di salvare", "Messaggio di errore", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
            }
        }
        
        return true;
    }
    
    public void salvataggioFile()
    {
        FileWriter fw = null;
        try 
        {
            fw = new FileWriter("Saves.txt");
            PrintWriter bw = new PrintWriter(fw);
            
            for(int cont = 0; cont < bottoni.size(); cont++)
            {
                bw.println(info.get(cont).nomeUtente);
                bw.println(info.get(cont).tempoOttenuto);
            }
            
            fw.close();
            bw.close();
        } 
        catch (IOException e) 
        {
            System.out.println("errore salvataggio file");
        }
    }

    private void refreshSalvataggi() 
    {
        for(int cont = 0; cont < bottoni.size(); cont++)
        {
            bottoni.get(cont).setText("Salvataggio "+ cont +"        Utente: "+ info.get(cont).nomeUtente +"\n                              Tempo: "+ info.get(cont).tempoOttenuto);
        }
    }
}