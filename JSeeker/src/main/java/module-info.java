module com.example.jseeker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.jseeker to javafx.fxml;
    exports com.example.jseeker;
}