package fx.finalproject;
import fx.finalproject.auth.Login;
import fx.finalproject.auth.Signup;
import fx.finalproject.mainViews.*;
import fx.finalproject.model.Course;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Navigator {
    private final Stage stage;
    private ArrayList<Course> courses;

    public Navigator(Stage stage){this.stage = stage;}

    public void navigateToLogin(){
        Login logIn = new Login(this);
        stage.setScene(new Scene(logIn.getRoot(), 500, 500));
        setStageTitle("Login");
        stage.show();
    }

    public void navigateToHome() {
        Home hello = new Home(this);
        stage.setScene(new Scene(hello.getRoot(), 500, 500));
        setStageTitle("College Administration System");
        stage.show();
    }

    public void navigateToSignup() {
        Signup signup = new Signup(this);
        stage.setScene(new Scene(signup.getRoot(), 500, 500));
        setStageTitle("Signup");
        stage.show();
    }

    public void navigateToProfessorsControl() {
        ProfessorsControl profControl = new ProfessorsControl(this);
        stage.setScene(new Scene(profControl.getRoot(),  800, 500));
        stage.show();
    }

    public void navigateToStudentsControl() {
        StudentsControl studentsControl = new StudentsControl(this);
        stage.setScene(new Scene(studentsControl.getRoot(),  800, 500));
        stage.show();
    }

    public void navigateToCourseControl() {
        CoursesControl coursesControl = new CoursesControl(this);
        stage.setScene(new Scene(coursesControl.getRoot(), 800, 500));
        stage.show();
    }

    public void navigateToAccountSettingControl() {
        AccountSetting accountSetting = new AccountSetting(this);
        stage.setScene(new Scene(accountSetting.getRoot(),  800, 500));
        stage.show();
    }

    public void setStageTitle(String title){
        stage.setTitle(title);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
}
