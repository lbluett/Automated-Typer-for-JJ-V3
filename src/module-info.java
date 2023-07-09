module com.infamous.jjbot {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires com.github.kwhat.jnativehook;

    // Export the package to javafx.graphics
    exports com.infamous.jjbot to javafx.graphics, javafx.fxml;
}