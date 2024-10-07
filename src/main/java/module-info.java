module com.example.picturecolorfinder {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.picturecolorfinder to javafx.fxml;
    exports com.example.picturecolorfinder;
}