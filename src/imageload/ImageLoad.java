/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageload;

import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;

/**
 *
 * @author kmech
 */
public class ImageLoad extends Application {
    int WIDTH=250;
    int HEIGHT=300;
    IntegerProperty count= new SimpleIntegerProperty(0);
    IntegerProperty total= new SimpleIntegerProperty(0);
    //int count=0;
    //int total=0;
    double xb=0;
    double yb=0;
    int cint=0;
    int iter,wsize;
    String dword;
    Timeline fall;
    
    double speed=0.0500;
    @Override
    public void start(Stage primaryStage) {
        //count=0;
        //total=0;
        String s=randomString();
        dword=s;
       //startgame(primaryStage,s);
        firstStartfunction(primaryStage); 
    }
    void startgame(Stage primaryStage,String wor){
        iter=0;
        wsize = wor.length();
        
        count.setValue(0);
        total.setValue(0);
        dword=wor;
        Pane root=new Pane();
        Random rand=new Random();
        
        root.setMinHeight(HEIGHT);
        root.setMinWidth(WIDTH);
        
        primaryStage.setOnCloseRequest(e->{
            fall.stop();
            e.consume();
            exitgame(primaryStage);
        });
        
        Label com=new Label("Catch the Apples");
        Label contain=new Label();
        Label word=new Label(wor);
        
        Image bim= new Image("image/forest.jpg");
        BackgroundSize bs=new BackgroundSize(WIDTH,HEIGHT,false,false,false,false);
        BackgroundImage bgi=new BackgroundImage(bim,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bs);
        Background bg= new Background(bgi);
        BorderPane bp=new BorderPane();
        
        Image img= resetImage((int)wor.charAt(iter));
        Image img2= new Image("image/bask.png");
        Image img3=resetImage(65+rand.nextInt(26));
        ImageView imv= new ImageView();
        ImageView im2= new ImageView();
        ImageView im3= new ImageView();
        
        xb=WIDTH/2 -25;
        imv.setImage(img);
        imv.setFitHeight(20);
        imv.setFitWidth(20);
        imv.setX(rand.nextInt(WIDTH-20));
        im3.setImage(img3);
        im3.setFitHeight(20);
        im3.setFitWidth(20);
        
        int x=rand.nextInt(WIDTH-20);
        while(Math.abs(x-imv.getX())<20)
           x=rand.nextInt(WIDTH-20);
        im3.setX(x);
        fall=new Timeline();
        fall.setCycleCount(Animation.INDEFINITE);
      
        im2.setImage(img2);
        im2.setFitHeight(25);
        im2.setFitWidth(40);
        im2.setX(WIDTH/2 -25);
        im2.setY(HEIGHT-30);
       
        KeyFrame mfall=new KeyFrame(Duration.seconds(speed),e->{
       
        cint++;
        cint=cint%100;
        if(cint==33){
            com.setText("Catch the Apples");
        }
        im2.setX(xb);
        
        double y=imv.getY();
        imv.setY(y+3);
        im3.setY(y+3);
       
        if(y>(HEIGHT-30)){
            if(imv.getX()>=im2.getX() && imv.getX()<im2.getX()+20){
                com.setText("Good! you caught it");
                contain.setText(wor.substring(0, iter+1));
                word.setText(wor.substring(iter+1,wsize));
                word.setTranslateX(WIDTH-((wsize-iter+1)*10));
                count.setValue(count.getValue()+1);
                
                iter++;
                if(iter==(wsize)){
                    System.out.println("you won");
                    fall.stop();
                    stageClear(primaryStage);
                }
                imv.setImage(resetImage(wor.charAt(iter)));
                int imn=65+rand.nextInt(26);
                while((int)wor.charAt(iter)==imn)
                    imn=65+rand.nextInt(26);
                im3.setImage(resetImage(imn));
                System.out.println("collected "+count.get()+" apples!"+speed);
            }
            else if(im3.getX()>=im2.getX() && im3.getX()<im2.getX()+20){
//       com.setText("Good! you caught it");
//       
            com.setText("Sorry! you missed it");
            int imn=65+rand.nextInt(26);
            while((int)wor.charAt(iter)==imn)
            imn=65+rand.nextInt(26);
            im3.setImage(resetImage(imn));
        
            total.setValue(total.getValue()+1);
            if((total.get()-count.get())>=3){
            fall.stop();
       
            func2(primaryStage);
            }
            }
            else{
                int imn=65+rand.nextInt(26);
                while((int)wor.charAt(iter)==imn)
                imn=65+rand.nextInt(26);
                im3.setImage(resetImage(imn));
                com.setText("Sorry! you missed it");
            }
       //total++;
            total.setValue(total.getValue()+1);
            if((total.get()-count.get())>=3){
            fall.stop();
       
            func2(primaryStage);
            }
            imv.setX(rand.nextInt(WIDTH-20));   
            imv.setY(0);
            int x1=rand.nextInt(WIDTH-20);
            while(Math.abs(x1-imv.getX())<20)
            x1=rand.nextInt(WIDTH-20);  
            im3.setX(x1);
            im3.setY(0);
        }
        }); 
        fall.getKeyFrames().add(mfall);
        fall.play();
       
        VBox rbar=new VBox();
        HBox h=new HBox(),up=new HBox();
        Button bl=new Button("Move Left");
        Button br=new Button("Move Right");
        Label score=new Label("Score : "+count.get());
        Label miss=new Label("Missed : "+(total.get()-count.get()));
        
        rbar.setId("rbar");
        word.setId("dword");    
        contain.setId("cword");       
        word.setText(wor);
        rbar.getChildren().addAll(word,contain);
        
        word.setAlignment(Pos.TOP_RIGHT);
        word.setTranslateX(WIDTH-(wsize*10));
       
        root.getChildren().addAll(word,contain);
       
        up.getChildren().addAll(score,miss,com);
        up.setId("upbar");
        count.addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o, Object oldVal, Object newVal) {
        score.setText("Score :"+ newVal.toString());
        }
        });
      
      total.addListener(new ChangeListener() {
      @Override
        public void changed(ObservableValue o, Object oldVal, Object newVal) {
        miss.setText("Missed :"+ (total.get()-count.get()));
        }
        });
      bl.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(5))));
      br.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(5))));
      bl.setOnMouseClicked(e->{
       xb=xb-5;
       if(xb<0)
           xb=0;
      });
      
       br.setOnMouseClicked(e->{
       xb=xb+5;
       if(xb>(WIDTH-40))
           xb=WIDTH-40;
       });
       
       bp.setOnKeyPressed(e->{
       if(e.getCode()==KeyCode.LEFT){
       xb=xb-5;
       if(xb<0)
           xb=0;
       }else if(e.getCode()==KeyCode.RIGHT){
       xb=xb+5;
       
        if(xb>(WIDTH-40))
           xb=WIDTH-40;
       }
       });
       bl.setId("leftButton");
       br.setId("rightButton");
       
       h.getChildren().addAll(bl,br);       
       h.setId("hbx");
       
      bp.setCenter(root);
      bp.setBottom(h);
      bp.setTop(up);
      bp.setRight(rbar);
     
       root.getChildren().addAll(imv,im2,im3);
      
        Scene scene = new Scene(bp, WIDTH+35, HEIGHT+55);
        scene.getStylesheets().add("style.css");
        
        root.setBackground(bg);
        primaryStage.setTitle("FALLING WORDS!");
        primaryStage.setScene(scene);
        primaryStage.setMaxHeight(HEIGHT+90);
        primaryStage.setMinHeight(HEIGHT+90);
        primaryStage.setMaxWidth(WIDTH);
        primaryStage.setMinWidth(WIDTH);
        
        primaryStage.show();
     //   scene.windowProperty().
                
   }
   Image resetImage(int val){
//   
//       Random rand=new Random();
//   int val=65 + rand.nextInt(26);
   char ch= (char)val;
   return new Image("image/"+ch+".png");
   
   }
   void exitgame(Stage mys)
    {
//        mys.close();
        
        BorderPane bp=new BorderPane();
        Pane ext = new Pane();
        Pane noext = new Pane();
        Image nim=new Image("image/yes.PNG");
        Image rim=new Image("image/no.PNG");
        ImageView nv=new ImageView(nim);
        ImageView rv=new ImageView(rim);
        
        nv.setFitHeight(35);
        nv.setFitWidth(80);
        
        rv.setFitHeight(35);
        rv.setFitWidth(80);
        
        noext.getChildren().add(rv);
        ext.getChildren().add(nv);
        
        VBox vbox = new VBox(5);
        HBox hbox = new HBox(10);
//        CBox cbox = new CBox();
        
        Label l1 = new Label("Are you sure you want to exit?");
        ext.setOnMouseClicked(e -> mys.close());        
        noext.setOnMouseClicked(e -> startgame(mys,randomString()));
        
//        hbox.getChildren().addAll(noext,ext);
        vbox.getChildren().addAll(l1,noext,ext);
        vbox.setAlignment(Pos.CENTER);
        hbox.getChildren().add(vbox);
        hbox.setAlignment(Pos.CENTER);
//        bp.getChildren().add(vbox);
//        bp.setCenter(vbox);
        
        Scene scene2 = new Scene(hbox,WIDTH,HEIGHT);
        
        mys.setScene(scene2);
        scene2.getStylesheets().add("style.css");
        mys.show();        
    }
    void func2(Stage mys)
    {        
        BorderPane bp=new BorderPane();
        Pane ext = new Pane();
        Pane pla = new Pane();
        Image nim=new Image("image/again.PNG");
        Image rim=new Image("image/exit.PNG");
        ImageView nv=new ImageView(nim);
        ImageView rv=new ImageView(rim);
        
        nv.setFitHeight(45);
        nv.setFitWidth(100);
        
        rv.setFitHeight(45);
        rv.setFitWidth(100);
        
        ext.getChildren().add(rv);
        pla.getChildren().add(nv);
        
        VBox vbox = new VBox();
        HBox hbox = new HBox(10);
        
        mys.setOnCloseRequest(e -> {
            fall.stop();
            e.consume();
            
            exitgame(mys);
        });
        
        Label  l1;
        
            l1 = new Label("You Lost\n Your Score was: "+count.get()+"\n Total apples was fell: "+total.get());
        
//        LeftButton lefy = new LeftButton();
        hbox.getChildren().addAll(pla,ext);
        vbox.getChildren().addAll(l1,hbox);
        
        hbox.setAlignment(Pos.CENTER);        
        vbox.setAlignment(Pos.CENTER);        
        Scene scene2 = new Scene(vbox,WIDTH,HEIGHT);
        scene2.getStylesheets().add("style.css");
        pla.autosize();
        ext.autosize();
        pla.setOnMouseClicked(e -> startgame(mys,randomString()));        
        ext.setOnMouseClicked(e -> exitgame(mys));
        
        mys.setScene(scene2);
        mys.show();
    }   
    void stageClear(Stage mys)
    { 
        fall.stop();
        
        BorderPane bp=new BorderPane();
        HBox downBar=new HBox();
        Pane replay=new Pane();
        Pane next=new Pane();
        Label text=new Label("Congradulations! you have cleared this round!");
        Image nim=new Image("image/next.png");
        Image rim=new Image("image/replay.png");
        ImageView nv=new ImageView(nim);
        ImageView rv=new ImageView(rim);
        
        nv.setFitHeight(30);
        nv.setFitWidth(90);
        
        rv.setFitHeight(50);
        rv.setFitWidth(50);
        
        replay.getChildren().add(rv);
        next.getChildren().add(nv);
        
        
        replay.setId("rep");
        next.setId("next");
        
       
        replay.minHeight(50);
        replay.minWidth(50);
        next.minHeight(50);
        next.minWidth(50);
        next.autosize();
        
        
        downBar.getChildren().addAll(replay,next);
        downBar.setAlignment(Pos.CENTER);
       // downBar.setSpacing(30);
        
        bp.setBottom(downBar);
        bp.setCenter(text);
        
        mys.setOnCloseRequest(e -> {
            fall.stop();
            e.consume();
            exitgame(mys);
        });
        
        Scene scene2 = new Scene(bp,WIDTH,HEIGHT);
        scene2.getStylesheets().add("styleWon.css");
        replay.setOnMouseClicked(e -> {  
           startgame(mys,dword);
        });        
        
        next.setOnMouseClicked(e -> {
            speed-=0.0100;
            startgame(mys,randomString());
        });
        mys.setScene(scene2);
        mys.show();        
    }
static String randomString()
    {
        int no=new Random().nextInt(100);
        String str = "";
            try {
			File file = new File("words.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
//			StringBuffer stringBuffer = new StringBuffer();
//			String line;
                        int i=0;
			while (i<no) {
				bufferedReader.readLine();
                                i++;
			}
                        str = bufferedReader.readLine();
			fileReader.close();			                        
		} catch (IOException e) {
			e.printStackTrace();
                        
                }
    return str.toUpperCase();
    }
 /*String randomString()
    {
        String str = "FISH";
        int no=new Random().nextInt(37197);
       
        
         FileReader in ;
         try{
         in = new FileReader("words.txt");
         int c;
         int cr=0;
         while ((c = in.read()) != -1) {
           if(c==13){
           cr++;
           }
           if(cr==no&&c!=13){
               
           str=str+(char)c;
           }
         }
         }catch(IOException e){
         e.printStackTrace();
         }
      
   return str.toUpperCase();
    }
*/
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    void firstStartfunction(Stage primaryStage)                                 //start manu function of a game first time control transfer here
    {
	StackPane bp = new StackPane();        
        VBox vbox = new VBox(3);
        HBox hbox = new HBox();
        Pane start1=new Pane();
        Pane option1=new Pane();
	Pane about1=new Pane();
	Pane exit1=new Pane();
	Image sta=new Image("image/start.PNG");
        Image opt=new Image("image/option.PNG");
        Image abt=new Image("image/about.PNG");
        Image ext=new Image("image/exit1.PNG");

        ImageView sta1=new ImageView(sta);
        ImageView opt1=new ImageView(opt);
        ImageView abt1=new ImageView(abt);
        ImageView ext1=new ImageView(ext);

               
	sta1.setFitHeight(30);
        sta1.setFitWidth(90);
        
        opt1.setFitHeight(30);
        opt1.setFitWidth(90);

	abt1.setFitHeight(30);
        abt1.setFitWidth(90);
        
        ext1.setFitHeight(30);
        ext1.setFitWidth(90);

        sta1.minHeight(50);
        sta1.minWidth(50);
        opt1.minHeight(50);
        opt1.minWidth(50);
        abt1.minHeight(50);
        abt1.minWidth(50);
        ext1.minHeight(50);
        ext1.minWidth(50);        

        start1.getChildren().add(sta1);
        option1.getChildren().add(opt1);
        about1.getChildren().add(abt1);
        exit1.getChildren().add(ext1);
        
        vbox.getChildren().addAll(start1,option1,about1,exit1);                
        hbox.getChildren().add(vbox);
        hbox.setAlignment(Pos.CENTER);
        vbox.setAlignment(Pos.CENTER);
        bp.setAlignment(Pos.CENTER);
        
        bp.getChildren().add(hbox);
        
        start1.setOnKeyPressed(e -> startgame(primaryStage,dword));                      //buttons action
        start1.setOnMouseClicked(e -> startgame(primaryStage,dword));        
        option1.setOnKeyPressed(e -> optionfunc(primaryStage));                      //buttons action
        option1.setOnMouseClicked(e -> optionfunc(primaryStage));       
        about1.setOnKeyPressed(e -> aboutfunc(primaryStage));                      //buttons action
        about1.setOnMouseClicked(e ->aboutfunc(primaryStage));        
        exit1.setOnKeyPressed(e -> exitgame(primaryStage));                      //buttons action
        exit1.setOnMouseClicked(e -> exitgame(primaryStage));
        
        primaryStage.setOnCloseRequest(e -> {                                   //direct close issue
            e.consume();
            exitgame(primaryStage);
        });
                                
        Scene scene = new Scene(bp,WIDTH,HEIGHT);                             //scene creation
        scene.getStylesheets().add("style.css");
        primaryStage.setMaxHeight(HEIGHT+38);
        primaryStage.setMinHeight(HEIGHT+38);
        primaryStage.setMaxWidth(WIDTH+15);
        primaryStage.setMinWidth(WIDTH+15);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }                                                                           //start manu functions ends here
    void optionfunc(Stage mys)                                                  //options function of our game will start here
    {
        Label l1 = new Label("OPTION FUNCTION");                                //label           
        Pane backbt=new Pane();	
	Image sta=new Image("image/back.PNG");        
        ImageView back=new ImageView(sta);        
        
        back.setFitHeight(30);
        back.setFitWidth(90);
        back.minHeight(50);
        back.minWidth(50);
        
        Button music = new Button("MUSIC");                                     //button        
        backbt.getChildren().add(back);        
        
        HBox hbox = new HBox(10);                                               
        VBox vbox = new VBox(10),vbox1 = new VBox();
        StackPane root = new StackPane();
        
        backbt.setOnMouseClicked(e -> firstStartfunction(mys));                       //button will work on        
       // music.setOnAction(e -> music());
        backbt.setOnKeyPressed(e->{
            if(e.getCode()==KeyCode.ENTER){                                     //buttons will also work by pressing of a enter
                firstStartfunction(mys);
            }
        });
        music.setOnKeyPressed(e->{
            if(e.getCode()==KeyCode.ENTER){                                     //buttons will also work by pressing of a enter
//                music();
            }
        });
        mys.setOnCloseRequest(e -> {                                            //if user close the program
            e.consume();
            exitgame(mys);
        });
        
        vbox.getChildren().addAll(l1,music);                                    //vbox
        vbox.setAlignment(Pos.CENTER);                                          //hbox
        vbox1.getChildren().add(backbt);                                         //add all thing
        vbox1.setAlignment(Pos.BOTTOM_LEFT);
        hbox.getChildren().addAll(vbox,vbox1);
        hbox.setAlignment(Pos.BOTTOM_LEFT);                                     //alignment setting        
        
        
        Scene scene = new Scene(root,WIDTH,HEIGHT);                             //scene creation
        scene.getStylesheets().add("style.css");
        root.getChildren().addAll(vbox,hbox);                                   //add all thing in root
        root.setAlignment(Pos.CENTER);                                          //root alingment setting
//        Scene scene = new Scene(root,WIDTH,HEIGHT);                             //scene formation
        mys.setScene(scene);                                                    //complting scene
        mys.show();                                                             //scene displaying
    }                                                                           //option functions ends here
    
    void aboutfunc(Stage mys)                                                   //about/help function of a game start here
    {
        Label l1 = new Label("ABOUT FUNCTION");
        Button but1 = new Button("BACK");
        HBox hbox = new HBox();
        
        StackPane root = new StackPane();
        Scene scene = new Scene(root,WIDTH,HEIGHT);                             //scene creation
                
        hbox.setAlignment(Pos.BOTTOM_LEFT);
        root.setAlignment(Pos.CENTER);
        hbox.getChildren().add(but1);
        root.getChildren().addAll(hbox,l1);
        
        but1.setOnAction(e -> firstStartfunction(mys));                       //button will work on        
       // music.setOnAction(e -> music());
        but1.setOnKeyPressed(e->{
            if(e.getCode()==KeyCode.ENTER){                                     //buttons will also work by pressing of a enter
                firstStartfunction(mys);
            }
        });
        scene.getStylesheets().add("style.css");
        mys.setScene(scene);
        mys.show();                
    }
    
}
