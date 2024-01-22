module AgendaTarefas {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.media;
	
	opens application to javafx.graphics, javafx.fxml;
	opens application.Entity to javafx.base;
	exports application;
}
