package fx.finalproject.mainViews;

import fx.finalproject.Navigator;
import fx.finalproject.auth.Login;
import fx.finalproject.interfaces.UIClass;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Home implements UIClass {
    private final Navigator navigator;
    private BorderPane root;

    public Home(Navigator navigator){
        this.navigator = navigator;
        setRoot();
    }
    private void setRoot(){
        root = new BorderPane();
        Label title = new Label("Welcome To The Control Panel");
        title.setStyle("-fx-font-weight:900;-fx-text-fill: #fff;-fx-padding:10px;-fx-font-size:25;");  // Inline CSS
        VBox header = new VBox(title);
        header.setStyle("-fx-background-color:blue;");
        header.setAlignment(Pos.CENTER);

        GridPane body = new GridPane();
        Button studentsControl = new Button("Students Control");
        Button professorsControl = new Button("Professors Control");
        Button coursesControl = new Button("Course Control");
        Button accountsSetting = new Button("Account Setting");
        studentsControl.setMinWidth(200);
        professorsControl.setMinWidth(200);
        coursesControl.setMinWidth(200);
        accountsSetting.setMinWidth(200);
        body.addRow(0,studentsControl);
        body.addRow(1,professorsControl);
        body.addRow(2,coursesControl);
        body.addRow(3,accountsSetting);

        body.setHgap(20);
        body.setVgap(20);
        body.setAlignment(Pos.CENTER);

        Button logout = new Button("logout");
        logout.setStyle("-fx-background-color:#AA2222;-fx-text-fill:#FFF;-fx-font-weight:900;-fx-boarder-radius:20;");
        VBox footer = new VBox(logout);
        footer.setStyle("-fx-padding:10;");
        footer.setAlignment(Pos.BOTTOM_RIGHT);

        root.setTop(header);
        root.setCenter(body);
        root.setBottom(footer);

        logout.setOnAction((var)-> logoutAction());
        studentsControl.setOnAction((var)-> navigator.navigateToStudentsControl());
        professorsControl.setOnAction((var)-> navigator.navigateToProfessorsControl());
        coursesControl.setOnAction((var)-> navigator.navigateToCourseControl());
        accountsSetting.setOnAction((var)-> navigator.navigateToAccountSettingControl());

    }
    @Override
    public BorderPane getRoot(){return root;}

    private void logoutAction(){
        System.out.println(Login.getUserName()+" Logged out");
        Login.setUserName("");
        Login.setUserPassword("");
        Login.setUserEmail("");
        navigator.navigateToLogin();
    }
}
