module me.projects.baldur.betrayal_at_baldurs_gate {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens me.projects.baldur.betrayal_at_baldurs_gate to javafx.fxml;
    exports me.projects.baldur.betrayal_at_baldurs_gate;
}