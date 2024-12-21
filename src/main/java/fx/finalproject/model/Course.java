package fx.finalproject.model;

import javafx.beans.property.SimpleStringProperty;

public class Course {
    private final SimpleStringProperty courseId;
    private final SimpleStringProperty  courseName;

    public Course(String courseId, String courseName) {
        this.courseId = new SimpleStringProperty(courseId);
        this.courseName = new SimpleStringProperty(courseName);
    }
    public String getCourseId() {
        return courseId.getValue();
    }
    public String getCourseName() {
        return courseName.getValue();
    }
    public void setCourseName(String courseName) {
        this.courseName.setValue(courseName);
    }
}
