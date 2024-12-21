package fx.finalproject.auth;

import fx.finalproject.DataBase;
import fx.finalproject.Navigator;
import fx.finalproject.interfaces.UIClass;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class Login implements UIClass {

    private final Navigator navigator;
    private BorderPane root;

    private TextField email_F;
    private PasswordField password_F;
    // endregion

    public Login(Navigator navigator){
        this.navigator = navigator;
        setRoot();
    }

    private void setRoot(){

        root = new BorderPane();
        GridPane body = new GridPane();
        Label title = new Label("System Login");
        title.setStyle("-fx-font-weight:900;-fx-text-fill: #00a;-fx-padding:30px;-fx-font-size:25;");  // Inline CSS
        VBox header = new VBox(title);
        VBox.setVgrow(title, Priority.ALWAYS);
        GridPane.setHgrow(header, Priority.ALWAYS);
        header.setAlignment(Pos.CENTER);

        title.setId("title");// For css

        Label email = new Label("Email: ");
        email_F = new TextField();
        email_F.setPromptText("Enter your email");
        body.add(header,0,0,2,1);
        body.addRow(1, email, email_F);

        Label password = new Label("Password: ");
        password_F = new PasswordField();
        password_F.setPromptText("Enter your password");
        body.addRow(2, password, password_F);

        // region root nodes
        Button login = new Button("Login");
        login.setOnAction((var)-> loginAction());

        Label don_t_have_an_account = new Label("don't have an account?");
        don_t_have_an_account.setOnMouseClicked((var)-> navigator.navigateToSignup());

        body.add(login,1,3);
        body.add(don_t_have_an_account,1,4);


        body.setHgap(20);
        body.setVgap(20);
        body.setAlignment(Pos.CENTER);

        root.setCenter(body);
    }
    @Override
    public BorderPane getRoot() {return root;}

    private void loginAction(){
        String email = email_F.getText();
        String password = password_F.getText();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Authentication error");
        if (Objects.equals(email, "")){
            alert.setContentText("Email is required!");
            alert.show();
            return;
        }
        if (Objects.equals(password, "")){
            alert.setContentText("Password is required!");
            alert.show();
            return;
        }
        try {
            Connection con = DataBase.getConnect();
            String query = "SELECT * FROM Admin WHERE Email = '" + email + "' AND Password = '" + password + "'";
            assert con != null;
            Statement stmt= con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                navigator.navigateToHome();
            }else{
                alert.setContentText("Invalid email or password!");
                alert.show();
            }
        } catch (Exception e) {
            System.out.println("[-] Error "+e);
        }
    }
}
