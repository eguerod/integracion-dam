package graphMath;

import graphMath.controller.StartController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	public static Stage primaryStage;
	private StartController controller = new StartController();

	/**
	 * Método que se utiliza para iniciar la aplicación, creando la ventana e
	 * iniciándola.
	 * 
	 * @param primaryStage El objeto Stage principal de la aplicación.
	 * @throws Exception Si hay un error al iniciar la aplicación.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		App.primaryStage = primaryStage;
		primaryStage.setTitle("GraphMath");
		primaryStage.setScene(new Scene(controller.getView()));
		primaryStage.setMinWidth(500);
		primaryStage.setMinHeight(500);
		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}
}
