package Home;

import Shop.ColoreCerchio;
import fallingball.FallingBall;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import particleSystem.ParticleSystem;

public class Menu extends Application 
{
    @Override
    public void start(Stage menuDiGioco) 
    {
        Pane root = new Pane();
        
        Scene scene = new Scene(root, 800, 550);
        scene.getStylesheets().add(Menu.class.getResource("StyleSheet.css").toExternalForm());
        
        menuDiGioco.setResizable(false);
        menuDiGioco.setTitle("Menu");
        menuDiGioco.setScene(scene);
        menuDiGioco.show();
        
        Media menuMusic = new Media(new File("./src/MenuMusic.mp3").toURI().toString());
        MediaPlayer playMusic = new MediaPlayer(menuMusic);
        playMusic.play();
        
        particleSystem.ParticleSystem system = new ParticleSystem(scene, root);
        system.start();
        
        //oggetti grafici
        DropShadow shadow = new DropShadow();
        shadow.setOffsetY(3.0f);
        shadow.setColor(Color.color(0.4f, 0.4f, 0.4f));
        
        InnerShadow is = new InnerShadow();
        is.setOffsetX(4.0f);
        is.setOffsetY(4.0f);
        
        //tasto per registrarsi o per il login
        Registrazione_Login registrazioneLogin = new Registrazione_Login(root);
        
        //testo per titoli
        Text preTitolo = new Text(120, 200, "Welcome to:");
        preTitolo.setFont(Font.font ("Verdana", 20));
        preTitolo.setId("fancytext2");
        preTitolo.setEffect(shadow);
        preTitolo.setEffect(is);
        root.getChildren().add(preTitolo);
        
        Text titolo = new Text(100, 250, "Falling Ball");
        titolo.setFont(Font.font ("Verdana", 50));
        titolo.setId("fancytext");
        titolo.setEffect(shadow);
        preTitolo.setEffect(is);
        root.getChildren().add(titolo);
        
        //dimensione immagini
        final int dimensioneIcon = 30;
        
        //vari bottoni di scelta
        Button play = new Button("  Play");
        play.setMinSize(150, 50);
        Image PlayImage = new Image("PlayButton.png", dimensioneIcon, dimensioneIcon, true, true);
        ImageView PlayView = new ImageView(PlayImage);
        play.setGraphic(PlayView);
        root.getChildren().add(play);
        
        Button shop = new Button("  Shop / Skin");
        play.setMinSize(150, 50);
        Image shopImage = new Image("ShopIcon.png", dimensioneIcon, dimensioneIcon, true, true);
        ImageView shopView = new ImageView(shopImage);
        shop.setGraphic(shopView);
        root.getChildren().add(shop);
        
        Button score = new Button("  Score");
        score.setMinSize(150, 50);
        Image ScoreImage = new Image("Score.png", dimensioneIcon, dimensioneIcon, true, true);
        ImageView ScoreView = new ImageView(ScoreImage);
        score.setGraphic(ScoreView);
        root.getChildren().add(score);
        
        Button about = new Button("  About");
        about.setMinSize(150, 50);
        Image AboutImage = new Image("Tutorial.png", dimensioneIcon, dimensioneIcon, true, true);
        ImageView AboutView = new ImageView(AboutImage);
        about.setGraphic(AboutView);
        root.getChildren().add(about);
        
        Button quit = new Button("  Quit");
        quit.setMinSize(150, 50);
        Image QuitImage = new Image("Quit.png", dimensioneIcon, dimensioneIcon, true, true);
        ImageView QuitView = new ImageView(QuitImage);
        quit.setGraphic(QuitView);
        root.getChildren().add(quit);
        
        BackgroundImage myBI = new BackgroundImage(new Image("/BackGroundImage2.png",scene.getWidth(), scene.getHeight(),false,true),
        BackgroundRepeat.ROUND, BackgroundRepeat.ROUND, BackgroundPosition.CENTER,
        BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBI));
        
        //settare l'ID per il css
        play.setId("bottone");
        shop.setId("bottone");
        score.setId("bottone");
        about.setId("bottone");
        quit.setId("bottone");
        
        //contenitore per i vari pulsanti 
        VBox contenitorePulsanti = new VBox(13, play, shop, score, about, quit);
        contenitorePulsanti.relocate((scene.getWidth() - 300), (scene.getHeight() - 420));
        root.getChildren().add(contenitorePulsanti);
        
        //Oggetto palla con il suo relativo colore ecc...
        Circle palla = new Circle(20, Color.web("hsl(0, 0%, 100%)"));
        palla.setStroke(Color.web("#323232"));
        palla.setStrokeWidth(1);
        
        //gioco principale
        Stage secondoStage = new Stage();
        fallingball.FallingBall game = new FallingBall(palla, menuDiGioco);
        
        play.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                if(registrazioneLogin.logged == false)
                {
                    JOptionPane.showConfirmDialog(null, "Si prega di effettuare prima il login", "Messaggio di errore", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    playMusic.stop();
                    game.start(secondoStage);
                    menuDiGioco.hide();
                }
            }
        });
        
        shop.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {  
                if(registrazioneLogin.logged == false)
                {
                    JOptionPane.showConfirmDialog(null, "Si prega di effettuare prima il login", "Messaggio di errore", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                }
                else if(registrazioneLogin.logged == true)
                {
                    Pane finestra = new Pane();

                    Image image = new Image("CorniceShop.png", 600, 450, false, true);
                    ImageView imageView = new ImageView(image);

                    double corniceSuperiore = 40;
                    double corniceDestro = 40;

                    Rectangle rettangolo = new Rectangle(image.getWidth() - (corniceDestro * 2) - 5, image.getHeight() - (corniceSuperiore * 2) - 5, Color.web("hsla(65, 60%, 80%, 0.7)"));
                    rettangolo.relocate(corniceDestro, corniceSuperiore);

                    finestra.relocate((scene.getWidth() / 2 ) - (image.getWidth() / 2), (scene.getHeight() / 2 ) - (image.getHeight() / 2));

                    finestra.getChildren().add(imageView);
                    finestra.getChildren().add(rettangolo);

                    //disattivo i bottoni in background
                    play.setDisable(true);
                    shop.setDisable(true);
                    score.setDisable(true);
                    about.setDisable(true);
                    quit.setDisable(true);

                    //BOTTONE DI CHIUSURA
                    Image closeImage = new Image("Close.png", dimensioneIcon, dimensioneIcon, false, true);
                    ImageView closeView = new ImageView(closeImage);

                    Button close = new Button("", closeView);
                    close.relocate(image.getWidth() - corniceDestro*2, corniceSuperiore);
                    close.setId("bottoneChiusura");
                    finestra.getChildren().add(close);

                    close.setOnAction(new EventHandler<ActionEvent>() 
                    {
                        @Override
                        public void handle(ActionEvent event) 
                        {
                            //rimuovo gli oggetti
                            root.getChildren().remove(finestra);

                            //riattivo i pulsanti in background
                            play.setDisable(false);
                            shop.setDisable(false);
                            score.setDisable(false);
                            about.setDisable(false);
                            quit.setDisable(false);
                        }
                    });

                    ColoreCerchio coloriInVendita = new ColoreCerchio(palla, finestra, registrazioneLogin.nomeUtente);
                    finestra.getChildren().add(coloriInVendita.contenitoreGenerale);
                    coloriInVendita.contenitoreGenerale.relocate(corniceDestro + 50, corniceSuperiore * 2 + 50);

                    root.getChildren().add(finestra);
                }
                }
        });
        
        score.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                Pane finestra = new Pane();

                Image image = new Image("Cornice.png", 500, 500, false, true);
                ImageView imageView = new ImageView(image);

                double corniceSuperiore = 63;
                double corniceDestro = 52;

                Rectangle rettangolo = new Rectangle(image.getWidth() - (corniceDestro * 2), image.getHeight() - (corniceSuperiore * 2), Color.web("hsla(61, 100%, 40%, 0.8)"));
                rettangolo.relocate(corniceDestro, corniceSuperiore);

                finestra.relocate((scene.getWidth() / 2 ) - (image.getWidth() / 2), (scene.getHeight() / 2 ) - (image.getHeight() / 2));

                finestra.getChildren().add(imageView);
                finestra.getChildren().add(rettangolo);
                
                //disattivo i bottoni in background
                play.setDisable(true);
                shop.setDisable(true);
                score.setDisable(true);
                about.setDisable(true);
                quit.setDisable(true);
                
                //BOTTONE DI CHIUSURA
                Image closeImage = new Image("Close.png", dimensioneIcon, dimensioneIcon, false, true);
                ImageView closeView = new ImageView(closeImage);
                
                Button close = new Button("", closeView);
                close.relocate(image.getWidth() - corniceDestro*2, corniceSuperiore);
                close.setId("bottoneChiusura");
                finestra.getChildren().add(close);
                
                close.setOnAction(new EventHandler<ActionEvent>() 
                {
                    @Override
                    public void handle(ActionEvent event) 
                    {
                        //rimuovo gli oggetti
                        root.getChildren().remove(finestra);
                        
                        //riattivo i pulsanti in background
                        play.setDisable(false);
                        shop.setDisable(false);
                        score.setDisable(false);
                        about.setDisable(false);
                        quit.setDisable(false);
                    }
                });
                
                //bottoni salvataggi e tutto il necessario
                VBox contenitoreSalvataggi = new VBox(40);
                FileReader fr = null;
                try 
                {
                    fr = new FileReader("Saves.txt");
                } 
                catch (FileNotFoundException ex) 
                {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                BufferedReader br = new BufferedReader(fr); 
                
                for(int cont = 0; cont < 3; cont++)
                {
                    String suppNome = "";
                    double suppTempo = 0;
                    
                    try 
                    {
                        suppNome = br.readLine();
                        suppTempo = Double.parseDouble(br.readLine());
                    } 
                    catch (IOException ex) 
                    {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    Button salvataggio = new Button("Salvataggio "+ cont +"        Utente: "+ suppNome +"\n                               Tempo: "+ suppTempo);
                    salvataggio.setId("bottoneScore");
                    
                    contenitoreSalvataggi.getChildren().add(salvataggio);
                }
                

                contenitoreSalvataggi.relocate(corniceDestro + 50, corniceSuperiore + 80);
                finestra.getChildren().add(contenitoreSalvataggi);
                
                root.getChildren().add(finestra);
            }
        });
        
        about.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) 
            {
                Image scroll = new Image("Scroll.png", 700, 460, false, true);
                ImageView scrollView = new ImageView(scroll);
                scrollView.relocate(80, 70);
                scrollView.setOpacity(0.94);
                
                root.getChildren().add(scrollView);
                
                play.setDisable(true);
                shop.setDisable(true);
                score.setDisable(true);
                about.setDisable(true);
                quit.setDisable(true);
                
                Text testo = new Text(scrollView.getLayoutX() + 180, 
                        scrollView.getLayoutY() + 80,"Benvenuti, nel gioco FallingBall\n" +
                                                "\n" +
                                                "Su Questo gioco sarete una pallina in una mappa,\n" +
                                                "vi muoverete con i classici comandi WASD,\n" +
                                                "questa pallina col passare del tempo cadrà verso il basso,\n" +
                                                "ogni tanto da fondo mappa spawneranno delle piattaforme,\n" +
                                                "i quali vanni verso l'alto su cui è possibile appoggiarsi\n" +
                                                "l'obbiattivo del gioco è sopravvivere il più a \n" +
                                                "lungo possibile senza andare fuori mappa.\n" +
                                                "\n" +
                                                "Attenzione ogni tanto spawneranno dei laser \n" +
                                                "(preceduti da un segnale di pericolo) che vi ostacoleranno,\n" +
                                                "cercate di evitarli.\n" +
                                                "si ricorda che ad ogni account è corrisposto i propri salvataggi\n"+
                                                "e compere, quindi si prega di creare il proprio account personale!!!\n"+
                                                "\n" +
                                                "Vi auguro un buon Game.\n" +
                                                "\n" +
                                                "Creator: Kevin Wang \n" +
                                                "Instagram: @_kevin_wang._");
                
                testo.setId("fancytext3");
                root.getChildren().add(testo);
                
                Image closeImage = new Image("Close.png", dimensioneIcon, dimensioneIcon, false, true);
                ImageView closeView = new ImageView(closeImage);
                
                Button close = new Button("", closeView);
                close.relocate(scroll.getWidth() + scrollView.getLayoutX() - dimensioneIcon - 130, scrollView.getLayoutY() + 10);
                close.setId("bottoneChiusura");
                root.getChildren().add(close);
                
                close.setOnAction(new EventHandler<ActionEvent>() 
                {
                    @Override
                    public void handle(ActionEvent event) 
                    {
                        root.getChildren().remove(scrollView);
                        root.getChildren().remove(close);
                        root.getChildren().remove(testo);
                        play.setDisable(false);
                        shop.setDisable(false);
                        score.setDisable(false);
                        about.setDisable(false);
                        quit.setDisable(false);
                    }
                });
            }
        });
        
        quit.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                System.exit(1);
            }
        });
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
    
}