module me.projects.baldur.betrayal_at_baldurs_gate {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.rmi;

    opens me.projects.baldur.betrayal_at_baldurs_gate to javafx.fxml;
    exports me.projects.baldur.betrayal_at_baldurs_gate;
    exports me.projects.baldur.betrayal_at_baldurs_gate.rmi to java.rmi;
    exports me.projects.baldur.betrayal_at_baldurs_gate.InGameChat to java.rmi;
}