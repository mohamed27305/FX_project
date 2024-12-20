package fx.finalproject.mainViews;

import fx.finalproject.Navigator;
import fx.finalproject.interfaces.UIClass;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class AccountSetting implements UIClass {
    private BorderPane root;
    private final Navigator navigator;
    public AccountSetting(Navigator navigator){
        this.navigator = navigator;
        setRoot();
    }
    private void setRoot() {
        root = new BorderPane();
        Label title = new Label("Account Setting");
        title.setStyle("-fx-font-weight:900;-fx-text-fill: #fff;-fx-padding:10px;-fx-font-size:25;");  // Inline CSS
        VBox header = new VBox(title);
        header.setStyle("-fx-background-color:blue;");
        header.setAlignment(Pos.CENTER);

        GridPane body = new GridPane();
        body.setHgap(20);
        body.setVgap(20);
        body.setAlignment(Pos.CENTER);

        Button back = new Button("Back");
        VBox footer = new VBox(back);
        footer.setStyle("-fx-padding:10;");
        footer.setAlignment(Pos.BOTTOM_RIGHT);

        root.setTop(header);
        root.setCenter(body);
        root.setBottom(footer);

        back.setOnAction((var) -> backAction());
    }

    @Override
    public BorderPane getRoot() {
        return root;
    }

    private void backAction() {
        navigator.navigateToHome();
    }
}

