module com.orik.minesweeperfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.orik.minesweeperfx to javafx.fxml;
    exports com.orik.minesweeperfx;
    exports com.orik.minesweeperfx.Controllers;
    opens com.orik.minesweeperfx.Controllers to javafx.fxml;
}