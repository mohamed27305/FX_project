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
    TextField newStudentId;
    TextField newStudentName;
    TextField studentId;
    TextField studentIdRemove;
    TextField studentIdSearch;
    TextField courseId;
    TextField removeCourse;
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
        VBox header = new VBox(title);
        header.setId("header");
        header.setAlignment(Pos.CENTER);

        GridPane body = new GridPane();
        body.setHgap(20);
        body.setVgap(20);
        body.setAlignment(Pos.CENTER);

        Button addStudent = new Button("Add new Student");
        addStudent.setOnAction((var) -> addStudentAction());
        newStudentId = new TextField();
        newStudentId.setPromptText("student id");
        newStudentName = new TextField();
        newStudentName.setPromptText("student name");
        body.add(addStudent, 0, 0);
        body.add(newStudentId, 1, 0);
        body.add(newStudentName, 2, 0);

        Button addCourse = new Button("add course");
        addCourse.setOnAction((var)-> addCourseToStudentAction());
        courseId = new TextField();
        studentId = new TextField();
        courseId.setPromptText("course Id");
        studentId.setPromptText("student Id");
        body.add(addCourse,0,1);
        body.add(studentId,1,1);
        body.add(courseId,2,1);


        Button back = new Button("Back");
        back.setId("back");
        VBox footer = new VBox(back);
        footer.setStyle("-fx-padding:10;");
        footer.setAlignment(Pos.BOTTOM_RIGHT);

        studentTable = new TableView<>();
        TableColumn<Student, String> nameColumn = new TableColumn<>("student name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        TableColumn<Student, String> idColumn = new TableColumn<>("student id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentTable.getColumns().add(nameColumn);
        studentTable.getColumns().add(idColumn);
        studentTable.setItems(data);

        courseTable = new TableView<>();
        TableColumn<Course, String> nameCourseColumn = new TableColumn<>("course name");
        nameCourseColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        TableColumn<Course, String> idCourseColumn = new TableColumn<>("course id");
        idCourseColumn.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        courseTable.getColumns().add(nameCourseColumn);
        courseTable.getColumns().add(idCourseColumn);
        courseTable.setItems(courses);

        Button search = new Button("search");
        search.setOnAction((var) -> searchAction());
        studentIdSearch = new TextField();
        studentIdSearch.setPromptText("search student");
        body.add(search, 0,2);
        body.add(studentIdSearch,1,2);

        Button remove = new Button("remove");
        remove.setOnAction((var) -> removeCourseAction() );
        removeCourse = new TextField();
        studentIdRemove = new TextField();
        studentIdRemove.setPromptText("student Id");
        removeCourse.setPromptText("course Id");
        body.add(remove,0,3);
        body.add(removeCourse,1,3);
        body.add(studentIdRemove,2,3);



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
        String studentId = newStudentId.getText();
        String studentName = newStudentName.getText();

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
            newStudentId.setText("");
            newStudentName.setText("");
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
    private void addCourseToStudentAction(){
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

    private void searchAction() {
        courses.clear();
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
    private void removeCourseAction(){
        courses.clear();
        String courseId = removeCourse.getText();
        String studentId = this.studentIdRemove.getText();
        try{
            Connection con = DataBase.getConnect();
            String query = String.format("delete from enrolled where ID_COURSE='%s' And ID_student='%s'",courseId, studentId);
            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while(resultSet.next()){
                courses.remove(new Course(resultSet.getString(1), resultSet.getString(2) ));
            }
            this.studentIdRemove.setText("");
            this.removeCourse.setText("");
            con.close();

        }catch (Exception e){
            System.out.println("Error " + e);
        }
    }

    private void backAction() {
        navigator.navigateToHome();
    }
}
