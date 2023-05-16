package graphMath.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

public class CheatSheetController implements Initializable {
	
	@FXML
    private GridPane view;
	
	public GridPane getView() {
		return view;
	}
	
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
