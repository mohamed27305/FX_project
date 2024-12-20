module fx.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires ojdbc6;


    opens fx.finalproject to javafx.fxml;
    exports fx.finalproject;
    exports fx.finalproject.auth;
    opens fx.finalproject.auth to javafx.fxml;
    exports fx.finalproject.mainViews;
    opens fx.finalproject.mainViews to javafx.fxml;
    exports fx.finalproject.interfaces;
    opens fx.finalproject.interfaces to javafx.fxml;
}