package fx.finalproject.model;

import javafx.beans.property.SimpleStringProperty;

public class Student {
    private final SimpleStringProperty studentId;
    private final SimpleStringProperty studentName;

    public Student(String studentId, String studentName) {
        this.studentId = new SimpleStringProperty(studentId);
        this.studentName = new SimpleStringProperty(studentName);
    }
   public String getStudentId(){
        return studentId.getValue();
   }
   public void setStudentName(String studentName){
        this.studentName.setValue(studentName);
   }

    public String getStudentName() {
        return studentName.getValue();
    }

}
