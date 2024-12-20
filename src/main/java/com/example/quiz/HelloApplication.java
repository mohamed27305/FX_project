package com.example.quiz;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.TextStyle;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Button about = new Button("about");
        Button circle = new Button("circle");
        GridPane g1 = new GridPane();
        g1.add(about,0,0);
        g1.add(circle,0,1);
        g1.setVgap(10);
        g1.setHgap(10);
        g1.setAlignment(Pos.CENTER);
        Label welcome = new Label("welcome");
        Button back1 = new Button("back");
        GridPane g2 = new GridPane();
        g2.add(welcome , 0,0);
        g2.add(back1,0,1);
        g2.setVgap(10);
        g2.setHgap(10);
        g2.setAlignment(Pos.CENTER);
        Label circleC = new Label("circle calculator");
        Label radiusL = new Label("radius");
        TextField radiusT = new TextField();
        Button areaB = new Button("Area");
        Label areaL = new Label("?");
        Button perB = new Button("parameter");
        Label perL = new Label("?");
        Button clear = new Button("clear");
        GridPane g3 = new GridPane();
        g3.add(circleC,0,0);
        g3.add(radiusL,0,1);
        g3.add(radiusT,1,1);
        g3.add(areaB,0,2);
        g3.add(areaL,1,2);
        g3.add(perB,0,3);
        g3.add(perL,1,3);
        g3.add(clear,1,4);
        g3.setVgap(10);
        g3.setHgap(10);
        g3.setAlignment(Pos.CENTER);

        Scene scene = new Scene(g1, 500, 500);
        Scene scene1 = new Scene(g2,500,500);
        Scene scene2 = new Scene(g3,500,500);
        about.setOnAction((ActionEvent event ) -> {
            stage.setScene(scene1);
        });
        back1.setOnAction((ActionEvent event ) -> {
            stage.setScene(scene);
        });
        circle.setOnAction((ActionEvent event) -> {
            stage.setScene(scene2);
        });
        areaB.setOnAction((ActionEvent event) -> {
        double r = Double.parseDouble(radiusT.getText());
        double area = Math.PI * r *r ;
        areaL.setText(Double.toString(area));
        });
        perB.setOnAction((ActionEvent event) -> {
            double r = Double.parseDouble(radiusT.getText());
            double per = Math.PI * 2 *r ;
            perL.setText(Double.toString(per));
        });
        clear.setOnAction((ActionEvent event) -> {
            radiusT.clear();
            perL.setText("?");
            areaL.setText("?");
        });
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}