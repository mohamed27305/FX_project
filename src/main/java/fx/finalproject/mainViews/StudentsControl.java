package fx.finalproject.mainViews;

import fx.finalproject.DataBase;
import fx.finalproject.Navigator;
import fx.finalproject.interfaces.UIClass;
import fx.finalproject.model.Course;
import fx.finalproject.model.Student;
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

public class StudentsControl implements UIClass {
    private BorderPane root;
    private final Navigator navigator;
    TextField newstudentId;
    TextField newstudentName;
    TextField studentId;
    TextField studentIdSearch;
    TextField courseId;
    TextField removecourse;
    TableView<Student> studentTable;
    ObservableList<Student> data = FXCollections.observableArrayList();
    TableView<Course> courseTable;
    ObservableList<Course> courses = FXCollections.observableArrayList();
    Alert alert = new Alert(Alert.AlertType.ERROR);

    public StudentsControl(Navigator navigator) {
        this.navigator = navigator;
        setRoot();
        setData();


    }

    private void setRoot() {
        root = new BorderPane();
        Label title = new Label("Students Control Panel");
        title.setStyle("-fx-font-weight:900;-fx-text-fill: #fff;-fx-padding:10px;-fx-font-size:25;");  // Inline CSS
        VBox header = new VBox(title);
        header.setStyle("-fx-background-color:blue;");
        header.setAlignment(Pos.CENTER);

        GridPane body = new GridPane();
        body.setHgap(20);
        body.setVgap(20);
        body.setAlignment(Pos.CENTER);

        Button addStudent = new Button("Add new Student");
        addStudent.setOnAction((var) -> addStudentAction());
        newstudentId = new TextField();
        newstudentId.setPromptText("student id");
        newstudentName = new TextField();
        newstudentName.setPromptText("student name");
        body.add(addStudent, 0, 0);
        body.add(newstudentId, 1, 0);
        body.add(newstudentName, 2, 0);

        Button addCourse = new Button("add course");
        addCourse.setOnAction((var)-> addCourse());
        courseId = new TextField();
        studentId = new TextField();
        courseId.setPromptText("course Id");
        studentId.setPromptText("student Id");
        body.add(addCourse,0,1);
        body.add(studentId,1,1);
        body.add(courseId,2,1);


        Button back = new Button("Back");
        VBox footer = new VBox(back);
        footer.setStyle("-fx-padding:10;");
        footer.setAlignment(Pos.BOTTOM_RIGHT);

        studentTable = new TableView<>();
        TableColumn<Student, String> namecolumn = new TableColumn<>("student name");
        namecolumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        TableColumn<Student, String> idcolumn = new TableColumn<>("student id");
        idcolumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentTable.getColumns().add(namecolumn);
        studentTable.getColumns().add(idcolumn);
        studentTable.setItems(data);

        courseTable = new TableView<>();
        TableColumn<Course, String> nameCoursecolumn = new TableColumn<>("course name");
        nameCoursecolumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        TableColumn<Course, String> idCoursecolumn = new TableColumn<>("course id");
        idCoursecolumn.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        courseTable.getColumns().add(nameCoursecolumn);
        courseTable.getColumns().add(idCoursecolumn);
        courseTable.setItems(courses);

        Button search = new Button("search");
        search.setOnAction((var) -> setCourses());
        studentIdSearch = new TextField();
        studentIdSearch.setPromptText("search student");
        body.add(search, 0,2);
        body.add(studentIdSearch,1,2);

        Button refresh = new Button("refresh");
        refresh.setOnAction((var) -> setData());
        body.add(refresh,3,3);


        VBox right = new VBox(studentTable,courseTable);
        right.setSpacing(10);
        right.setAlignment(Pos.TOP_CENTER);


        root.setTop(header);
        root.setCenter(body);
        root.setBottom(footer);
        root.setRight(right);

        back.setOnAction((var) -> backAction());
    }

    @Override
    public BorderPane getRoot() {
        return root;
    }

    private void addStudentAction() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        String studentId = newstudentId.getText();
        String studentName = newstudentName.getText();

        if (studentId.isBlank() || studentId.isEmpty()) {
            alert.setContentText("student id is required!");
            alert.show();
            return;
        }
        if (studentName.isBlank() || studentName.isEmpty()) {
            alert.setContentText("student name is required!");
            alert.show();
            return;
        }

        try {
            Connection con = DataBase.getConnect();
            String query = String.format("INSERT INTO Student VALUES ('%s','%s')", studentId, studentName);
            assert con != null;
            Statement stmt = con.createStatement();
            stmt.executeQuery(query);
            con.close();
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Added new student!");
            alert.show();
            newstudentId.setText("");
            newstudentName.setText("");
            data.add(new Student(studentId, studentName));
        } catch (Exception e) {
            System.out.println("[-] Error " + e);
            alert.setContentText("Error Can't add new student!");
            alert.show();
        }
    }


    private void setData() {
        data.clear();
        try {
            Connection con = DataBase.getConnect();
            String query = "SELECT * FROM Student";
            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                data.add(new Student(resultSet.getString(1), resultSet.getString(2)));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }
    private void addCourse(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        String studentId = this.studentId.getText();
        String courseId = this.courseId.getText();

        if (studentId.isBlank() || studentId.isEmpty()){
            alert.setContentText("Student id is required!");
            alert.show();
            return;
        }
        if (courseId.isBlank() || courseId.isEmpty()){
            alert.setContentText("Course id is required!");
            alert.show();
            return;
        }

        try {
            Connection con = DataBase.getConnect();
            String query = String.format("INSERT INTO ENROLLED VALUES ('%s','%s')",studentId,courseId);
            assert con != null;
            Statement stmt= con.createStatement();
            stmt.executeQuery(query);
            con.close();
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Added new course to student!");
            alert.show();
            this.studentId.setText("");
            this.courseId.setText("");
        }
        catch (Exception e) {
            System.out.println("[-] Error "+e);
            alert.setContentText("Error Can't add new course to student!");
            alert.show();
        }
    }

    private void setCourses() {
        data.clear();
        String studentIdSearch = this.studentIdSearch.getText();
        try {
            Connection con = DataBase.getConnect();
            String query = String.format("SELECT Course.ID_course, Course.Name FROM ENROLLED JOIN Course ON ENROLLED.ID_course = Course.ID_course WHERE ENROLLED.ID_student = '%s'",studentIdSearch);
            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                courses.add(new Course(resultSet.getString(1), resultSet.getString(2)));
            }
            this.studentIdSearch.setText("");
            con.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }
    private void backAction() {
        navigator.navigateToHome();
    }
}
