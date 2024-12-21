package fx.finalproject.mainViews;

import fx.finalproject.DataBase;
import fx.finalproject.Navigator;
import fx.finalproject.interfaces.UIClass;
import fx.finalproject.model.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CoursesControl implements UIClass {
    private final Navigator navigator;
    private BorderPane root;
    TextField newCourseId;
    TextField newCourseName;
    TextField courseId;
    TextField courseNewName_F;
    TextField courseIdForChangeName_F;
    TableView<Course> courseTable;
    ObservableList<Course> data = FXCollections.observableArrayList();
    Alert alert = new Alert(Alert.AlertType.ERROR);


    public CoursesControl(Navigator navigator) {
        this.navigator = navigator;
        setRoot();
        setData();
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
        newCourseId = new TextField();
        newCourseId.setPromptText("course id");
        newCourseName = new TextField();
        newCourseName.setPromptText("course name");
        body.add(addCourse,0,0);
        body.add(newCourseId,1,0);
        body.add(newCourseName,2,0);

        Button removeCourse = new Button("Remove course");
        removeCourse.setOnAction((var)->removeCourseAction());
        courseId = new TextField();
        courseId.setPromptText("course id");
        body.add(removeCourse,0,1);
        body.add(courseId,1,1);

        Button changeCourseName = new Button("Change Course name");
        changeCourseName.setOnAction((var)-> changeCourseNameAction());
        courseIdForChangeName_F = new TextField();
        courseIdForChangeName_F.setPromptText("course Id for change name");
        courseNewName_F = new TextField();
        courseNewName_F.setPromptText("New name");
        body.add(changeCourseName,0,2);
        body.add(courseIdForChangeName_F,1,2);
        body.add(courseNewName_F,2,2);

        body.setHgap(20);
        body.setVgap(20);
        body.setStyle("-fx-padding:30;");
        body.setAlignment(Pos.TOP_CENTER);

        Button back = new Button("Back");
        VBox footer = new VBox(back);
        footer.setStyle("-fx-padding:10;");
        footer.setAlignment(Pos.BOTTOM_RIGHT);

        courseTable = new TableView<>();

        TableColumn<Course, String> nameColumn = new TableColumn<>("course Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        TableColumn<Course, String> idColumn = new TableColumn<>("Course Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        courseTable.getColumns().add(nameColumn);
        courseTable.getColumns().add(idColumn);
        courseTable.setItems(data);

        VBox right = new VBox(courseTable);
        right.setSpacing(10);
        right.setAlignment(Pos.TOP_CENTER);

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
        String id = courseIdForChangeName_F.getText();
        String newName = courseNewName_F.getText();
        for (Course course: data){
            if (course.getCourseId().equals(id)){
                course.setCourseName(newName);
                System.out.println("Changing Name");
                try {
                    Connection con = DataBase.getConnect();
                    String query = String.format("UPDATE Course SET Name = '%s' WHERE Id = '%s'",newName,id);
                    assert con != null;
                    Statement stmt= con.createStatement();
                    stmt.executeQuery(query);
                    con.close();
                    courseNewName_F.setText("");
                    courseIdForChangeName_F.setText("");
                    setData();
                } catch (Exception e) {
                    System.out.println("[-] Error "+e);
                    alert.setContentText("Error Can't rename the course!");
                    alert.show();
                }
                return;
            }
        }
        System.out.printf("Cant find the Id %s\n",id);
    }
    private void addCourseAction() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        String courseId = newCourseId.getText();
        String courseName = newCourseName.getText();

        if (courseId.isBlank() || courseId.isEmpty()){
            alert.setContentText("Course id is required!");
            alert.show();
            return;
        }
        if (courseName.isBlank() || courseName.isEmpty()){
            alert.setContentText("Course name is required!");
            alert.show();
            return;
        }

        try {
            Connection con = DataBase.getConnect();
            String query = String.format("INSERT INTO Course VALUES ('%s','%s')",courseId,courseName);
            assert con != null;
            Statement stmt= con.createStatement();
            stmt.executeQuery(query);
            con.close();
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Added new course!");
            alert.show();
            newCourseId.setText("");
            newCourseName.setText("");
            data.add(new Course(courseId,courseName));
        }
        catch (Exception e) {
            System.out.println("[-] Error "+e);
            alert.setContentText("Error Can't add new course!");
            alert.show();
        }
    }
    private void removeCourseAction() {
        alert.setTitle("Error");
        String courseId = this.courseId.getText();

        if (courseId.isBlank() || courseId.isEmpty()){
            alert.setContentText("Course id is required!");
            alert.show();
            return;
        }
        if (!data.removeIf(course -> course.getCourseId().equals(courseId))){
            alert.setContentText("Error Can't find the course!");
            alert.show();
            return;
        }
        try {
            Connection con = DataBase.getConnect();
            String query = String.format("DELETE FROM Course WHERE Id = '%s'",courseId);
            assert con != null;
            Statement stmt= con.createStatement();
            stmt.executeQuery(query);
            con.close();
            this.courseId.setText("");
        } catch (Exception e) {
            System.out.println("[-] Error "+e);
            alert.setContentText("Error Can't delete course!");
            alert.show();
        }
        System.out.println("Removing course");
    }
    private void setData(){
        data.clear();
        try{
            Connection con = DataBase.getConnect();
            String query = "SELECT * FROM Course";
            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                data.add(new Course(resultSet.getString(1), resultSet.getString(2)));
            }
            con.close();
        }catch (Exception e){
            System.out.println("Error "+e);
        }
    }
}
