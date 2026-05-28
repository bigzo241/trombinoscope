module com.mamadou.trombinoscope {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.mamadou.trombinoscope to javafx.fxml;
    exports com.mamadou.trombinoscope;
    exports com.mamadou.trombinoscope.controller;
    opens com.mamadou.trombinoscope.controller to javafx.fxml;
    exports com.mamadou.trombinoscope.metier;
    opens com.mamadou.trombinoscope.metier to javafx.fxml;
}