package fx.finalproject.mainViews;

import fx.finalproject.DataBase;
import fx.finalproject.Navigator;
import fx.finalproject.interfaces.UIClass;
import fx.finalproject.model.Course;
import fx.finalproject.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
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
    TextField addcourse;
    TextField removecourse;
    TableView<Student> studentTable;
    ObservableList<Student> data = FXCollections.observableArrayList();
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


        VBox right = new VBox(studentTable);
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

    private void backAction() {
        navigator.navigateToHome();
    }
}
