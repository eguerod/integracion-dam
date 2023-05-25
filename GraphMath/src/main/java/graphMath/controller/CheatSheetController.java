package graphMath.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

/**
 * Controlador para la vista de la Hoja de
 * Operaciones del programa.
 */
public class CheatSheetController implements Initializable {

	@FXML
	private GridPane view;

	/**
	 * Devuelve la vista del controlador.
	 * @return la vista del controlador.
	 */
	public GridPane getView() {
		return view;
	}

	/**
	 * Constructor de la clase CheatSheetController. Carga la vista FXML correspondiente al controlador.
	 * @throws RuntimeException si ocurre un error al cargar la vista FXML.
	 */
	public CheatSheetController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CheatSheetView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
