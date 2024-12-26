package fx.finalproject.mainViews;

import fx.finalproject.DataBase;
import fx.finalproject.Navigator;
import fx.finalproject.interfaces.UIClass;
import fx.finalproject.model.Course;
import fx.finalproject.model.Professor;
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

public class ProfessorsControl implements UIClass {
    private final Navigator navigator;
    private BorderPane root;
    TextField newProfId;
    TextField newProfName;
    TextField profId;
    TextField courseId;
//    TextField removeCourse;
    TextField profIdSearch;
    TableView<Professor> profTable;
    ObservableList<Professor> data = FXCollections.observableArrayList();
    TableView<Course> courseTable;
    ObservableList<Course> courses = FXCollections.observableArrayList();
    Alert alert = new Alert(Alert.AlertType.ERROR);

    public ProfessorsControl(Navigator navigator) {
        this.navigator = navigator;
        setRoot();
        setData();
    }

    private void setRoot() {
        root = new BorderPane();
        Label title = new Label("Professor Control Panel");
        VBox header = new VBox(title);
        header.setId("header");
        header.setAlignment(Pos.CENTER);


        GridPane body = new GridPane();
        body.setHgap(20);
        body.setVgap(20);
        body.setAlignment(Pos.CENTER);

        Button addProfessor = new Button("Add new professor");
        addProfessor.setOnAction((var) -> addProfessorAction());
        newProfId = new TextField();
        newProfId.setPromptText("professor id");
        newProfName = new TextField();
        newProfName.setPromptText("professor name");
        body.add(addProfessor, 0, 0);
        body.add(newProfId, 1, 0);
        body.add(newProfName, 2, 0);

        Button addCourse = new Button("add course");
        addCourse.setOnAction((var)-> addCourseToProfessorAction());
        courseId = new TextField();
        profId = new TextField();
        courseId.setPromptText("course Id");
        profId.setPromptText("professor Id");
        body.add(addCourse,0,1);
        body.add(profId,1,1);
        body.add(courseId,2,1);



        Button back = new Button("Back");
        back.setId("back");
        VBox footer = new VBox(back);
        footer.setStyle("-fx-padding:10;");
        footer.setAlignment(Pos.BOTTOM_RIGHT);

        profTable = new TableView<>();
        TableColumn<Professor, String> nameColumn = new TableColumn<>("professor name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("profName"));
        TableColumn<Professor, String> idColumn = new TableColumn<>("professor id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("profId"));
        profTable.getColumns().add(nameColumn);
        profTable.getColumns().add(idColumn);
        profTable.setItems(data);

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
        profIdSearch = new TextField();
        profIdSearch.setPromptText("search student");
        body.add(search, 0,2);
        body.add(profIdSearch,1,2);

        VBox right = new VBox(profTable,courseTable);
        right.setSpacing(10);
        right.setAlignment(Pos.TOP_CENTER);

        root.setTop(header);
        root.setCenter(body);
        root.setBottom(footer);
        root.setRight(right);

        back.setOnAction((var) -> backAction());
    }
    private void addProfessorAction() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        String profId = newProfId.getText();
        String profName = newProfName.getText();

        if (profId.isBlank() || profId.isEmpty()) {
            alert.setContentText("student id is required!");
            alert.show();
            return;
        }
        if (profName.isBlank() || profName.isEmpty()) {
            alert.setContentText("student name is required!");
            alert.show();
            return;
        }

        try {
            Connection con = DataBase.getConnect();
            String query = String.format("INSERT INTO PROFESSOR VALUES ('%s','%s')", profId, profName);
            assert con != null;
            Statement stmt = con.createStatement();
            stmt.executeQuery(query);
            con.close();
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Added new professor!");
            alert.show();
            newProfId.setText("");
            newProfName.setText("");
            data.add(new Professor(profId, profName));
        } catch (Exception e) {
            System.out.println("[-] Error " + e);
            alert.setContentText("Error Can't add new professor!");
            alert.show();
        }
    }
    private void addCourseToProfessorAction(){
        alert.setTitle("Error");
        String profId = this.profId.getText();
        String courseId = this.courseId.getText();

        if (profId.isBlank() || profId.isEmpty()){
            alert.setContentText("professor id is required!");
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
            String query = String.format("INSERT INTO TEACHING VALUES ('%s','%s')",profId,courseId);
            assert con != null;
            Statement stmt= con.createStatement();
            stmt.executeQuery(query);
            con.close();
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Added new course to professor!");
            alert.show();
            this.profId.setText("");
            this.courseId.setText("");
        }
        catch (Exception e) {
            System.out.println("[-] Error "+e);
            alert.setContentText("Error Can't add new course to professor!");
            alert.show();
        }
    }
    private void setData() {
        data.clear();
        try {
            Connection con = DataBase.getConnect();
            String query = "SELECT * FROM Professor";
            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                data.add(new Professor(resultSet.getString(1), resultSet.getString(2)));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }
    private void searchAction() {
        courses.clear();
        String profIdSearch = this.profIdSearch.getText();
        try {
            Connection con = DataBase.getConnect();
            String query = String.format("SELECT Course.ID_course, Course.Name FROM TEACHING JOIN Course ON TEACHING.ID_course = Course.ID_course WHERE TEACHING.ID_PROFESSOR    = '%s'",profIdSearch);
            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                courses.add(new Course(resultSet.getString(1), resultSet.getString(2)));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }
    @Override
    public BorderPane getRoot() {
        return root;
    }

    private void backAction() {
        navigator.navigateToHome();
    }
}
