package fx.finalproject;
import fx.finalproject.auth.Login;
import fx.finalproject.auth.Signup;
import fx.finalproject.mainViews.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Navigator {
    private final Stage stage;
    private Scene scene;
//    private ArrayList<Course> courses;

    public Navigator(Stage stage){this.stage = stage;}

    public void navigateToLogin(){
        Login logIn = new Login(this);
        scene = new Scene(logIn.getRoot(), 500, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setScene(scene);
        setStageTitle("Login");
        stage.show();
    }

    public void navigateToHome() {
        Home home = new Home(this);
        scene = new Scene(home.getRoot(), 800, 550);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setScene(scene);
        setStageTitle("College Administration System");
        stage.show();
    }

    public void navigateToSignup() {
        Signup signup = new Signup(this);
        scene = new Scene(signup.getRoot(), 500, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setScene(scene);
        setStageTitle("Signup");
        stage.show();
    }

    public void navigateToProfessorsControl() {
        ProfessorsControl profControl = new ProfessorsControl(this);
        scene = new Scene(profControl.getRoot(), 800, 550);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void navigateToStudentsControl() {
        StudentsControl studentsControl = new StudentsControl(this);
        scene = new Scene(studentsControl.getRoot(), 800, 550);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void navigateToCourseControl() {
        CoursesControl coursesControl = new CoursesControl(this);
        scene = new Scene(coursesControl.getRoot(), 800, 550);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void navigateToAccountSettingControl() {
        AccountSetting accountSetting = new AccountSetting(this);
        scene = new Scene(accountSetting.getRoot(), 800, 550);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void setStageTitle(String title){
        stage.setTitle(title);
    }
}
