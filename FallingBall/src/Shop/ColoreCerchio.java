package Shop;

import Client.FallingBall_Client;
import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ColoreCerchio
{
    Pane finestra;
    String nomeUtente;
    
    final int dimensioneCerchio = 20;
    
    final int dimensioneXRettangolo = 70;
    final int dimensioneYRettangolo = 10;
    
    final int spazioCerchioBottone = 10;
    final int spazioCoppie = 40;
    final int spazioRighe = 50;
    
    DisponibilitaColore coloreComprato;
    
    Circle palla;

    ArrayList <Circle> sceltaCerchi = new ArrayList<>();
    ArrayList <Button> bottoneDiVendita = new ArrayList<>();
    
    ArrayList <VBox> contenitoreVerticale = new ArrayList<>();
    
    //ogni riga avrà 4 coppie di cerchi e bottoni
    HBox contenitoreLinea1 = new HBox(spazioCoppie);
    HBox contenitoreLinea2 = new HBox(spazioCoppie);
    
    public VBox contenitoreGenerale = new VBox(spazioRighe);
    
    int bottoneSelezionato = 0;

    public ColoreCerchio(Circle palla, Pane finestra, String nomeUtente) 
    {
        this.palla = palla;
        this.finestra = finestra;
        this.nomeUtente = nomeUtente;
        coloreComprato = new DisponibilitaColore(nomeUtente);
        
        //primo disponibile: bianco 
        sceltaCerchi.add(new Circle(dimensioneCerchio, Color.web("hsl(0, 0%, 100%)")));
        sceltaCerchi.get(0).setStroke(Color.web("#323232"));
        sceltaCerchi.get(0).setStrokeWidth(1);
        
        aggiungiBottone(0);
        bottoneDiVendita.get(0).setPrefSize(dimensioneXRettangolo, dimensioneYRettangolo);
        
        contenitoreVerticale.add(new VBox(spazioCerchioBottone, sceltaCerchi.get(0), bottoneDiVendita.get(0)));
        contenitoreVerticale.get(0).setAlignment(Pos.CENTER);
                
        //aggiungo le restanti 7 coppie
        int supp = 0;
        for(int cont = 1; cont < 8; cont++)
        {
            sceltaCerchi.add(new Circle(dimensioneCerchio, Color.web("hsl("+ supp +", 100%, 50%)")));
            sceltaCerchi.get(cont).setStroke(Color.web("#323232"));
            sceltaCerchi.get(cont).setStrokeWidth(1);
            supp += 50;
            
            aggiungiBottone(cont);
            bottoneDiVendita.get(cont).setPrefSize(dimensioneXRettangolo, dimensioneYRettangolo);
            
            contenitoreVerticale.add(new VBox(spazioCerchioBottone, sceltaCerchi.get(cont), bottoneDiVendita.get(cont)));
            contenitoreVerticale.get(cont).setAlignment(Pos.CENTER);
        }
        
        for(int cont1 = 0; cont1 < 8; cont1++)
        {
            //sfrutto il for settare il disponibile di default dei vari pulsanti
            bottoneDiVendita.get(cont1).setStyle("-fx-background-color: #007CC3;");
                    
            //prime 4 coppie
            if(cont1 < 4)
            {
                contenitoreLinea1.getChildren().add(contenitoreVerticale.get(cont1));
            }
            //ultime 4 coppie
            else if(cont1 < 8)
            {
                contenitoreLinea2.getChildren().add(contenitoreVerticale.get(cont1));
            }
        }
        contenitoreGenerale.getChildren().addAll(contenitoreLinea1, contenitoreLinea2);
        
        //attivo i vari bottoni
        attivaBottoni();
        
        refresh();
        
        //il disponibile iniziale della palla è di default bianca
        palla = sceltaCerchi.get(0);
    }
    
    public void attivaBottoni()
    {
        bottoneDiVendita.get(0).setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                if(coloreComprato.disponibile[0] == true)
                {
                    bottoneSelezionato = 0;
                }
                if(coloreComprato.disponibile[0] == false)
                {
                    compra(0);
                }
                refresh();
            }
        });
        
        bottoneDiVendita.get(1).setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                if(coloreComprato.disponibile[1] == true)
                {
                    bottoneSelezionato = 1;
                }
                if(coloreComprato.disponibile[1] == false)
                {
                    compra(1);
                }
                refresh();
            }
        });
        
        bottoneDiVendita.get(2).setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                if(coloreComprato.disponibile[2] == true)
                {
                    bottoneSelezionato = 2;
                }
                if(coloreComprato.disponibile[2] == false)
                {
                    compra(2);
                }
                refresh();
            }
        });
        
        bottoneDiVendita.get(3).setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                if(coloreComprato.disponibile[3] == true)
                {
                    bottoneSelezionato = 3;
                }
                if(coloreComprato.disponibile[3] == false)
                {
                    compra(3);
                }
                refresh();
            }
        });
        
        bottoneDiVendita.get(4).setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                if(coloreComprato.disponibile[4] == true)
                {
                    bottoneSelezionato = 4;
                }
                if(coloreComprato.disponibile[4] == false)
                {
                    compra(4);
                }
                refresh();
            }
        });
        
        bottoneDiVendita.get(5).setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                if(coloreComprato.disponibile[5] == true)
                {
                    bottoneSelezionato = 5;
                }
                if(coloreComprato.disponibile[5] == false)
                {
                    compra(5);
                }
                refresh();
            }
        });
        
        bottoneDiVendita.get(6).setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                if(coloreComprato.disponibile[6] == true)
                {
                    bottoneSelezionato = 6;
                }
                if(coloreComprato.disponibile[6] == false)
                {
                    compra(6);
                }
                refresh();
            }
        });
        
        bottoneDiVendita.get(7).setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                if(coloreComprato.disponibile[7] == true)
                {
                    bottoneSelezionato = 7;
                }
                if(coloreComprato.disponibile[7] == false)
                {
                    compra(7);
                }
                refresh();
            }
        });
    }
    
    public void refresh()
    {
        for(int cont = 0; cont < bottoneDiVendita.size(); cont++)
        {
            if(cont == bottoneSelezionato)
            {
                if(coloreComprato.disponibile[bottoneSelezionato] == true)
                {
                    bottoneDiVendita.get(bottoneSelezionato).setText("Selected");
                    bottoneDiVendita.get(cont).setStyle("-fx-background-color: #AF2624;");
                    
                    palla.setFill(sceltaCerchi.get(bottoneSelezionato).getFill());
                }
            }
            else
            {
                if(coloreComprato.disponibile[cont] == true)
                {
                    bottoneDiVendita.get(cont).setText("Pick me");
                }
                else if(coloreComprato.disponibile[cont] == false)
                {
                    bottoneDiVendita.get(cont).setText("Buy");
                }
                bottoneDiVendita.get(cont).setStyle("-fx-background-color: #007CC3;");
            }
        }
    }
    
    public void aggiungiBottone(int numeroBottone)
    {
        if(coloreComprato.disponibile[numeroBottone] == true)
        {
            bottoneDiVendita.add(new Button("Select"));
        }
        else if(coloreComprato.disponibile[numeroBottone] == false)
        {
            bottoneDiVendita.add(new Button("Buy"));
        }
    }
    
    public void compra(int numeroColore)
    {
        Pane finestraLoading = new Pane();
        schermataLoading(finestraLoading, numeroColore);
    }
    
    public void schermataLoading(Pane finestraLoading, int numeroPallina)
    {
        final int costante = 100;
        final int costanteX = 70;
        final int costanteY = 50;
        final int costanteSpazio = 20;
        
        Rectangle rettangolo = new Rectangle(finestra.getWidth() - (costante * 2), finestra.getHeight()- (costante * 2), Color.web("#959595", 0.9));
        rettangolo.setStroke(Color.BLACK);
        rettangolo.setStrokeWidth(1.0);
        
        finestraLoading.relocate(costante, costante);
        finestraLoading.getChildren().add(rettangolo);
        
        Circle pallaEsempio = (new Circle(dimensioneCerchio, sceltaCerchi.get(numeroPallina).getFill()));
        pallaEsempio.setStroke(Color.web("#323232"));
        pallaEsempio.setStrokeWidth(1);
        
        Text testo = new Text("Sei sicuro di voler comprare questo colore di pallina?");
        Button no = new Button("No");
        Button si = new Button("Si");
        
        HBox contenitoreRisposta = new HBox(costanteSpazio, no, si);
        VBox contenitoreGenerale = new VBox(costanteSpazio, pallaEsempio, testo, contenitoreRisposta);
        
        contenitoreRisposta.setAlignment(Pos.CENTER);
        contenitoreGenerale.setAlignment(Pos.CENTER);
        contenitoreGenerale.relocate(costanteX, costanteY);
        
        finestraLoading.getChildren().add(contenitoreGenerale);
        
        finestra.getChildren().add(finestraLoading);
        
        no.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                finestra.getChildren().remove(finestraLoading);
            }
        });
        
        si.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                finestraLoading.getChildren().remove(contenitoreGenerale);
                
                final int dimensioneIcon = 100;
                Image image = new Image("Loading.gif", dimensioneIcon * 1.5, dimensioneIcon, false, true);
                ImageView imageView = new ImageView(image);
                
                Text testo = new Text("Comunicando con il server...");
                testo.setFont(Font.font(20));
                
                VBox contenitoreGenerale1 = new VBox(costanteSpazio, imageView, testo);
                contenitoreGenerale1.relocate(costanteX + 20, costanteY);
                contenitoreGenerale1.setAlignment(Pos.CENTER);
                
                finestraLoading.getChildren().add(contenitoreGenerale1);
                
                Timeline tempo = new Timeline(new KeyFrame(Duration.millis(2000), new EventHandler<ActionEvent>() 
                {
                    @Override
                    public void handle(ActionEvent event) 
                    {         
                    }
                }));
                tempo.setCycleCount(1);
                tempo.play();

                tempo.setOnFinished(event1 -> 
                {
                    try 
                    {
                        //se il server non è acceso, si procede per il codice del catch
                        FallingBall_Client client = new FallingBall_Client();
                        
                        Image image2 = new Image("Loading2.gif", dimensioneIcon, dimensioneIcon, true, true);
                        ImageView imageView2 = new ImageView(image2);
                        
                        Text testo2 = new Text("Comunicando con il server...");
                        testo.setFont(Font.font(20));

                        VBox contenitoreGenerale2 = new VBox(costanteSpazio, imageView2, testo2);
                        contenitoreGenerale2.relocate(costanteX + 50, costanteY);
                        contenitoreGenerale2.setAlignment(Pos.CENTER);
                        
                        finestraLoading.getChildren().remove(contenitoreGenerale1);
                        finestraLoading.getChildren().add(contenitoreGenerale2);
                        
                        Timeline tempo2 = new Timeline(new KeyFrame(Duration.millis(2000), new EventHandler<ActionEvent>() 
                        {
                            @Override
                            public void handle(ActionEvent event) 
                            {         
                            }
                        }));
                        tempo2.setCycleCount(1);
                        tempo2.play();

                        tempo2.setOnFinished(event2 -> 
                        {
                            boolean conferma = client.compra(numeroPallina, nomeUtente);

                            if(conferma == true)
                            {
                                coloreComprato.disponibile[numeroPallina] = true;

                                coloreComprato.scriviDatiFile();
                                refresh();
                            }
                            
                            Image image3 = new Image("Loading3.gif", dimensioneIcon, dimensioneIcon, true, true);
                            ImageView imageView3 = new ImageView(image3);

                            Text testo3 = new Text("Sblocco colore effettuato!!!");
                            testo.setFont(Font.font(20));
                            
                            Button bottoneConferma = new Button("Ok");
                            
                            bottoneConferma.setOnAction(new EventHandler<ActionEvent>() 
                            {
                                @Override
                                public void handle(ActionEvent event) 
                                {
                                    finestra.getChildren().remove(finestraLoading);
                                    
                                    //carica i dati sul server
                                    coloreComprato.esportaDati();
                                }
                            });

                            VBox contenitoreGenerale3 = new VBox(costanteSpazio, imageView3, testo3, bottoneConferma);
                            contenitoreGenerale3.relocate(costanteX + 50, costanteY);
                            contenitoreGenerale3.setAlignment(Pos.CENTER);

                            finestraLoading.getChildren().remove(contenitoreGenerale2);
                            finestraLoading.getChildren().add(contenitoreGenerale3);
                        });
                    } 
                    catch (IOException ex) 
                    {
                        Image image4 = new Image("Sad.png", dimensioneIcon, dimensioneIcon, true, true);
                        ImageView imageView4 = new ImageView(image4);

                        Text testo4 = new Text("Imbossibile Contattare il server");
                        testo.setFont(Font.font(20));

                        Button bottoneConferma = new Button("Ok");

                        bottoneConferma.setOnAction(new EventHandler<ActionEvent>() 
                        {
                            @Override
                            public void handle(ActionEvent event) 
                            {
                                finestra.getChildren().remove(finestraLoading);
                            }
                        });

                        VBox contenitoreGenerale4 = new VBox(costanteSpazio, imageView4, testo4, bottoneConferma);
                        contenitoreGenerale4.relocate(costanteX + 50, costanteY);
                        contenitoreGenerale4.setAlignment(Pos.CENTER);
                        
                        finestraLoading.getChildren().remove(contenitoreGenerale1);
                        finestraLoading.getChildren().add(contenitoreGenerale4);
                    }
                });
            }
        });
    }
}