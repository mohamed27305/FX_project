package fx.finalproject.auth;

import fx.finalproject.Navigator;
import fx.finalproject.interfaces.UIClass;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;


public class Signup implements UIClass {
    private final Navigator navigator;
    private BorderPane root;

    // region root nodes
    private Button login;
    private Label userName;
    private TextField userName_F;
    private Label email;
    private TextField email_F;

    private Label password;
    private PasswordField password_F;
    private Label c_password;
    private PasswordField c_password_F;
    // endregion

    public Signup(Navigator navigator){
        this.navigator = navigator;
        setRoot();
    }


    private void setRoot(){
        root = new BorderPane();
        Label title = new Label("System Signup");
        title.setStyle("-fx-font-weight:900;-fx-text-fill: #00a;-fx-padding:30px;-fx-font-size:25;");  // Inline CSS
        VBox header = new VBox(title);
        header.setAlignment(Pos.CENTER);
        VBox.setVgrow(title, Priority.ALWAYS);

        GridPane body = new GridPane();
        Button signup = new Button("Signup");
        signup.setOnAction((var)-> signupAction());
        body.add(header,0,0,2,1);

        userName = new Label("User name: ");
        userName_F = new TextField();
        userName_F.setPromptText("Enter your name");
        body.addRow(1,userName,userName_F);
        email = new Label("Email: ");
        email_F = new TextField();
        email_F.setPromptText("Enter your email");
        body.addRow(2,email,email_F);

        password = new Label("Password: ");
        password_F = new PasswordField();
        password_F.setPromptText("Enter your password");
        body.addRow(3,password, password_F);

        c_password = new Label("Confirm password: ");
        c_password_F = new PasswordField();
        c_password_F.setPromptText("Enter your password again");
        body.addRow(4,c_password, c_password_F);
        body.add(signup,1,5);

        Label already_have_an_account = new Label("already have an account?");
        already_have_an_account.setOnMouseClicked((var)-> navigator.navigateToLogin());
        body.add(already_have_an_account,1,6);

        body.setHgap(20);
        body.setVgap(20);
        body.setAlignment(Pos.CENTER);
        root.setCenter(body);
    }
    @Override
    public BorderPane getRoot(){return root;}

    private void signupAction(){
        navigator.navigateToLogin();
    }
}

