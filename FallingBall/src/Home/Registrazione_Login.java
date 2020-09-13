package Home;

import Client.FallingBall_Client;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;

public class Registrazione_Login 
{
    Pane scene;
    
    boolean logged;
    
    String nomeUtente;
    Text testoLogin = new Text("Benvenuto "+ nomeUtente +"!!!");
    Button esci = new Button("Esci");
    
    Button registrati = new Button("Registrati");
    Button login = new Button("Login");

    HBox contenitoreBottoni = new HBox(20);
    
    public Registrazione_Login(Pane scene) 
    {
        this.scene = scene;
        testoLogin.setId("textLogin");
                
        final int bottoneX = 100;
        final int bottoneY = 25;
        
        registrati.setPrefSize(bottoneX, bottoneY);
        login.setPrefSize(bottoneX, bottoneY);
        esci.setPrefSize(bottoneX, bottoneY);
        
        contenitoreBottoni.relocate(scene.getWidth() - (80 + bottoneX * 2), 10);
        contenitoreBottoni.setAlignment(Pos.CENTER);
        refresh();
        
        scene.getChildren().add(contenitoreBottoni);
        
        registrati.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                try 
                {
                    FallingBall_Client client = new FallingBall_Client();
                    
                    String nome = JOptionPane.showInputDialog("Inserisci il nome utente con la quale registrarsi");
                    if(nome != null)
                    {
                        String password = JOptionPane.showInputDialog("Inserisci la password con la quale registrarsi");
                        if(password != null)
                        {
                            JOptionPane.showConfirmDialog(null, "è sicuro di registrarsi con:\nNome: "+ nome +"\nPassword: "+ password, "Messaggio di errore", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                            boolean confermaRegistrazione = client.registrazione(nome, password);

                            if(confermaRegistrazione == false)
                            {
                                JOptionPane.showConfirmDialog(null, "errore durante la registrazione, oppure il username inserito è già stato preso", "Messaggio di errore", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                            }
                            if(confermaRegistrazione == true)
                            {
                                JOptionPane.showConfirmDialog(null, "La registrazione è andata a buon fine", "Messaggio di errore", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                } 
                catch (IOException ex) 
                {
                    JOptionPane.showConfirmDialog(null, "Impossibile contattare il server", "Messaggio di errore", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        login.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                try 
                {
                    FallingBall_Client client = new FallingBall_Client();
                    
                    String nome = JOptionPane.showInputDialog("Inserisci il nome utente");
                    if(nome != null)
                    {
                        String password = JOptionPane.showInputDialog("Inserisci la password");
                        if(password != null)
                        {
                            boolean esistenzaAccount = client.login(nome, password);
                            if(esistenzaAccount == false)
                            {
                                JOptionPane.showConfirmDialog(null, "Credenziali errate!!!", "Messaggio di errore", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }
                } 
                catch (IOException ex) 
                {
                    JOptionPane.showConfirmDialog(null, "Impossibile contattare il server", "Messaggio di errore", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                }
                
                refresh();
            }
        });
        
        esci.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                esci();
            }
        });
    }
    
    public void refresh()
    {
        try 
        {
            FileReader letturaCrendeziali = new FileReader("Credenziali.txt");
            BufferedReader bufferCrendeziali = new BufferedReader(letturaCrendeziali);
            
            nomeUtente = bufferCrendeziali.readLine();
            
            if(nomeUtente.equalsIgnoreCase("null"))
            {
                contenitoreBottoni.getChildren().clear();
                contenitoreBottoni.getChildren().addAll(registrati, login);
                
                logged = false;
            }
            else
            {
                contenitoreBottoni.getChildren().clear();
                
                testoLogin.setText("Benvenuto "+ nomeUtente +"!!!");
                contenitoreBottoni.getChildren().addAll(testoLogin, esci);
                
                logged = true;
            }
            
            letturaCrendeziali.close();
            bufferCrendeziali.close();
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(Registrazione_Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Registrazione_Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void esci()
    {
        try 
        {
            FileWriter streamCredenziali = new FileWriter("Credenziali.txt");
            PrintWriter bufferCredenziali = new PrintWriter(streamCredenziali);

            bufferCredenziali.println("null");

            streamCredenziali.close();
            bufferCredenziali.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Registrazione_Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        refresh();
    }
    
}