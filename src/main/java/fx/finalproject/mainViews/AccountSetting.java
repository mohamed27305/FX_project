package fx.finalproject.mainViews;

import fx.finalproject.DataBase;
import fx.finalproject.Navigator;
import fx.finalproject.auth.Login;
import fx.finalproject.interfaces.UIClass;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AccountSetting implements UIClass {
    private BorderPane root;
    private final Navigator navigator;

    private Label userName;
    private Label userEmail;
    private Label userPassword;

    private TextField newName;
    private TextField newEmail;
    private TextField newPassword;

    private final Alert alert = new Alert(Alert.AlertType.ERROR);
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
        Label name_l = new Label("Currant user name: ");
        name_l.setStyle("-fx-font-weight:900");
        userName = new Label(Login.getUserName());
        Label email_l = new Label("Currant email: ");
        email_l.setStyle("-fx-font-weight:900");
        userEmail = new Label(Login.getUserEmail());
        Label password_l = new Label("Currant password: ");
        password_l.setStyle("-fx-font-weight:900");
        userPassword = new Label(Login.getUserPassword());

        body.addRow(0,name_l,userName);
        body.addRow(1,email_l,userEmail);
        body.addRow(2,password_l, userPassword);
        Line l = new Line();
        l.setStrokeWidth(1);
        body.add(l,0,4,2,2);
        l.setStartX(0);
        l.setEndX(300);

        Button changeName = new Button("Change name");
        changeName.setMinWidth(120);
        newName = new TextField();
        newName.setPromptText("Enter new name");
        body.addRow(6,changeName,newName);
        Button changeEmail = new Button("Change email");
        changeEmail.setMinWidth(120);
        newEmail = new TextField();
        newEmail.setPromptText("Enter new email");
        body.addRow(7,changeEmail,newEmail);
        Button changePassword = new Button("Change password");
        changePassword.setMinWidth(120);
        newPassword = new TextField();
        newPassword.setPromptText("Enter new password");
        body.addRow(8,changePassword,newPassword);
        Line l2 = new Line();
        l2.setStartX(0);
        l2.setEndX(300);
        body.add(l2,0,9,2,2);
        Button deleteAccount = new Button("Delete my account");
        deleteAccount.setMinWidth(200);
        deleteAccount.setStyle("-fx-background-color:#AA2222;-fx-text-fill:#FFF;-fx-font-weight:900;-fx-boarder-radius:20;");
        deleteAccount.setOnAction((var)->deleteAccountAction());
        body.add(deleteAccount,0,11,2,2);


        body.setHgap(20);
        body.setVgap(20);
        body.setAlignment(Pos.CENTER);

        Button back = new Button("Back");
        VBox footer = new VBox(back);
        back.setStyle("-fx-background-color:#AA2222;-fx-text-fill:#FFF;-fx-font-weight:900;-fx-boarder-radius:20;");
        footer.setStyle("-fx-padding:10;");
        footer.setAlignment(Pos.BOTTOM_RIGHT);
        root.setTop(header);
        root.setCenter(body);
        root.setBottom(footer);
        back.setOnAction((var) -> backAction());
        changeName.setOnAction((var) -> changeNameAction());
        changeEmail.setOnAction((var) -> changeEmailAction());
        changePassword.setOnAction((var) -> changePasswordAction());
    }

    @Override
    public BorderPane getRoot() {
        return root;
    }
    private void changeNameAction() {
        String name = newName.getText();
        if (name.isBlank()){
            alert.setTitle("Missing field");
            alert.setContentText("new name is required");
            alert.show();
            return;
        }
        try {
            Connection con = DataBase.getConnect();
            String query = String.format("UPDATE Admin SET Name = '%s' WHERE Email = '%s'",name,Login.getUserEmail());
            assert con != null;
            System.out.println(query);
            Statement stmt= con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                Login.setUserName(name);
                userName.setText(name);
                newName.setText("");
                con.close();
            }
        } catch (Exception e) {
            alert.setTitle("Error");
            alert.setContentText("Invalid new name!");
            alert.show();
            System.out.println("[-] Error "+e);
        }

    }
    private void changeEmailAction() {
        String email = newEmail.getText();
        if (email.isBlank()){
            alert.setTitle("Missing field");
            alert.setContentText("new email is required");
            alert.show();
            return;
        }
        try {
            Connection con = DataBase.getConnect();
            String query = String.format("UPDATE Admin SET Email = '%s' WHERE Email = '%s'",email,Login.getUserEmail());
            assert con != null;
            System.out.println(query);
            Statement stmt= con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                Login.setUserEmail(email);
                userEmail.setText(email);
                newEmail.setText("");
                con.close();
            }
        } catch (Exception e) {
            alert.setTitle("Error");
            alert.setContentText("Invalid new email!");
            alert.show();
            System.out.println("[-] Error "+e);
        }
    }
    private void changePasswordAction() {
        String password = newPassword.getText();
        if (password.isBlank()){
            alert.setTitle("Missing field");
            alert.setContentText("new password is required");
            alert.show();
            return;
        }
        try {
            Connection con = DataBase.getConnect();
            String query = String.format("UPDATE Admin SET Password = '%s' WHERE Email = '%s'",password,Login.getUserEmail());
            assert con != null;
            System.out.println(query);
            Statement stmt= con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                Login.setUserPassword(password);
                userPassword.setText(password);
                newPassword.setText("");
                con.close();
            }
        } catch (Exception e) {
            alert.setTitle("Error");
            alert.setContentText("Invalid new password!");
            alert.show();
            System.out.println("[-] Error "+e);
        }
    }
    private void deleteAccountAction() {
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.setContentText("Are you sure you want to delete your account?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
        if (result == ButtonType.YES){
            try {
                Connection con = DataBase.getConnect();
                String query = String.format("DELETE FROM Admin WHERE Email = '%s'",Login.getUserEmail());
                assert con != null;
                System.out.println(query);
                Statement stmt= con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if(rs.next()){
                    Login.setUserName("");
                    Login.setUserEmail("");
                    Login.setUserPassword("");
                    navigator.navigateToLogin();
                    con.close();
                }
            } catch (Exception e) {
                alert.setTitle("Error");
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Can't delete the account!");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.show();
                System.out.println("[-] Error "+e);
            }
        }
    }

    private void backAction() {
        navigator.navigateToHome();
    }
}

