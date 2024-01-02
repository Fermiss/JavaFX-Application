module sample.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    requires org.junit.jupiter.api;

    opens mongodbCollections to javafx.base;
    opens usersClassesMySQL to javafx.base;
    opens sample to javafx.fxml;
    exports sample;
    exports controllers;
    opens controllers to javafx.fxml;
    exports controllers.MySQL;
    opens controllers.MySQL to javafx.fxml;
    exports controllers.MongoDB;
    opens controllers.MongoDB to javafx.fxml;
}