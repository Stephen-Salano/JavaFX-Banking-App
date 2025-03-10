// This is a module descriptor, a declarative config file that the Java Module System reads at compile time and runtime
module com.devs.mazebank {
    /**
    requires: Specifies dependencies and other modules or libraries that the project requires
    Why?: Without `requires` your module won't be able to use the classes from these dependencies
     */
    requires javafx.controls; // -> Allows using JavaFX UI components (buttons, textfields)
    requires javafx.fxml; // -> Allows working with fxml files for design
    requires de.jensd.fx.glyphs.fontawesome;//  -> a UI library for icons and fonts
    requires java.sql; // -> Enables Database operations
    requires org.xerial.sqlitejdbc;
    requires org.jetbrains.annotations;
    requires java.desktop; //-> Provides the SQLite database driver

    /**
    Opens: Allows reflection-based access to the `com.devs.mazebank` package for `javafx.xml`
    Why? : JavaFX uses reflection to inject FXML elements into controllers. Without `opens`, JavaFX
        cannot access private fields/methods in controllers
     */
    opens com.devs.mazebank to javafx.fxml;

    /**
     * exports: Make these packages accessible to other modules,
     * Why?: If a package is not exported, other modules cannot use its classes
     */
    exports com.devs.mazebank; //-> Allows other modules to access the main application package
    exports com.devs.mazebank.Controllers; //-> Allows access to controllers
    exports com.devs.mazebank.Controllers.AdminControllers;
    exports com.devs.mazebank.Controllers.Client;
    exports com.devs.mazebank.Models;
    exports com.devs.mazebank.Views;

}