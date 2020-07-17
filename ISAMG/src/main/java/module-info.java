module com.REU2020 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires org.apache.commons.io;
    requires opencv;

    opens com.REU2020 to javafx.fxml;
    exports com.REU2020;
}