module com.mamadou.trombinoscope {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.mamadou.trombinoscope to javafx.fxml;
    exports com.mamadou.trombinoscope;
}