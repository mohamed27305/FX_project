package fx.finalproject.mainViews;

import fx.finalproject.Navigator;
import fx.finalproject.interfaces.UIClass;
import fx.finalproject.model.Course;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import static javafx.collections.FXCollections.observableList;

public class CoursesControl implements UIClass {
    private final Navigator navigator;
    private BorderPane root;

    public CoursesControl(Navigator navigator) {
        this.navigator = navigator;
        setRoot();
    }

    private void setRoot() {
        root = new BorderPane();
        Label title = new Label("Course Control Panel");
        title.setStyle("-fx-font-weight:900;-fx-text-fill: #fff;-fx-padding:10px;-fx-font-size:25;");  // Inline CSS
        VBox header = new VBox(title);
        header.setStyle("-fx-background-color:blue;");
        header.setAlignment(Pos.CENTER);

        GridPane body = new GridPane();
        Button addCourse = new Button("Add new course");
        addCourse.setOnAction((var)->addCourseAction());
        TextField newCourseId = new TextField();
        newCourseId.setPromptText("course id");
        TextField newCourseName = new TextField();
        newCourseName.setPromptText("course name");
        body.add(addCourse,0,0);
        body.add(newCourseId,1,0);
        body.add(newCourseName,2,0);

        Button removeCourse = new Button("Remove course");
        removeCourse.setOnAction((var)->removeCourseAction());
        TextField courseId = new TextField();
        courseId.setPromptText("course id");
        body.add(removeCourse,0,1);
        body.add(courseId,1,1);

        Button changeCourseName = new Button("Change Course name");
        changeCourseName.setOnAction((var)-> changeCourseNameAction());
        TextField courseIdForChangeName_F = new TextField();
        courseIdForChangeName_F.setPromptText("course Id");
        TextField courseNewName_F = new TextField();
        courseNewName_F.setPromptText("New name");
        body.add(changeCourseName,0,2);
        body.add(courseIdForChangeName_F,1,2);
        body.add(courseNewName_F,2,2);

        body.setHgap(20);
        body.setVgap(20);
        body.setAlignment(Pos.CENTER);

        Button back = new Button("Back");
        VBox footer = new VBox(back);
        footer.setStyle("-fx-padding:10;");
        footer.setAlignment(Pos.BOTTOM_RIGHT);

        TableView<Course> courseTable = new TableView<>();
        Button refresh = new Button("Refresh");
        VBox right = new VBox(courseTable,refresh);
        right.setSpacing(10);
        right.setAlignment(Pos.CENTER);

        root.setTop(header);
        root.setCenter(body);
        root.setBottom(footer);
        root.setRight(right);

        back.setOnAction((var) -> navigator.navigateToHome());
    }

    @Override
    public BorderPane getRoot() {
        return root;
    }
    private void changeCourseNameAction() {
        System.out.println("Changing Name");
    }
    private void addCourseAction() {
        System.out.println("Adding New course");
    }
    private void removeCourseAction() {
        System.out.println("Removing course");
    }
}
