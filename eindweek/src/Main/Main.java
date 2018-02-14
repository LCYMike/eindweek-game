package Main;

import Main.Assets.Checkers.Checker;
import Main.Assets.MineSweeper.MineSweeper;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application{

    public static void main(String[] args)
    {
        launch(args);
    }

    public boolean walking = false;

    @Override
    public void start(Stage primaryStage)
    {
        Image backgroundImage = new Image("Main/Images/Grass.png");
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitHeight(850);
        backgroundView.setFitWidth(900);

        Image player = new Image("Main/Images/Player.png");
        ImageView imgView1 = new ImageView(player);
        imgView1.setFitWidth(100);
        imgView1.setFitHeight(100);
        imgView1.setX(0);
        imgView1.setY(0);

        Image enemy1 = new Image("Main/Images/EnemyKnive.png");
        ImageView imgView2 = new ImageView(enemy1);
        imgView2.setFitWidth(100);
        imgView2.setFitHeight(100);
        imgView2.setX(500);
        imgView2.setY(400);

        Rectangle rec1 = new Rectangle();
        rec1.setX(500);
        rec1.setY(400);
        rec1.setWidth(100);
        rec1.setHeight(100);
        rec1.setStrokeWidth(5);
        rec1.setFill(Color.CYAN);
        rec1.setVisible(false);

        Button ms = new Button();
        Button ch = new Button();

        HBox buttons = new HBox();

        ms.setText("MineSweeper");
        ms.setOnAction(event -> {
            MineSweeper mine = new MineSweeper();
            mine.start(primaryStage);
            primaryStage.setWidth(800);
            primaryStage.setHeight(635);
        });

        ch.setText("Checkers");
        ch.setOnAction(event -> {
            Checker check = new Checker();
            try {
                check.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        Group root = new Group();
        Scene scene1 = new Scene(root);

        Pane pane = new Pane();
        pane.getChildren().add(backgroundView);
        pane.getChildren().add(imgView1);
        pane.getChildren().add(imgView2);
        pane.getChildren().add(rec1);
        pane.getChildren().add(buttons);

        buttons.getChildren().add(ms);
        buttons.getChildren().add(ch);

        primaryStage.setScene(scene1);
        root.getChildren().add(pane);
        primaryStage.setHeight(850);
        primaryStage.setWidth(800);

        scene1.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                walking = false;
            }
        });

        scene1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                switch (event.getCode())
                {
                    //up
                    case W:
                    imgView1.setRotate(270);
                    if (imgView1.getY() >= 50 && walking == false) {
                        imgView1.setY(imgView1.getY() - 50);
                        walking = true;

                    }
                    break;
                    //down
                    case S:
                        imgView1.setRotate(90);
                        if (imgView1.getY() <= 650 && walking == false) {
                            imgView1.setY(imgView1.getY() + 50);
                            walking = true;
                        }
                        break;
                    //left
                    case A:
                        imgView1.setRotate(180);
                        if (imgView1.getX() >= 50 && walking == false) {
                            imgView1.setX(imgView1.getX() - 50);
                            walking = true;
                        }
                        break;
                    //right
                    case D:
                        imgView1.setRotate(0);
                        if (imgView1.getX() <= 650 && walking == false) {
                            imgView1.setX(imgView1.getX() + 50);
                            walking = true;
                        }
                        break;
                    default:
                        System.out.println("Input error");
                        break;
                }
                System.out.println(walking);
                System.out.println(imgView1.getX());
                System.out.println(imgView1.getY());
            }
        });
        primaryStage.setResizable(false);
        primaryStage.requestFocus();
        primaryStage.show();

    }
}
