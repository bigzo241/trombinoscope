module com.mamadou.trombinoscope {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.mamadou.trombinoscope to javafx.fxml;
    exports com.mamadou.trombinoscope;
}