package fx.finalproject;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage){
        Navigator navigator = new Navigator(stage);
        navigator.navigateToHome();
    }

    public static void main(String[] args) {launch();}
}