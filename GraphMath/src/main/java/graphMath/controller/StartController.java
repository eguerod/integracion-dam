package graphMath.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import graphMath.App;
import graphMath.functiongrapher.grapher.expression.Function;
import graphMath.functiongrapher.grapher.parser.ExpressionParser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class StartController implements Initializable {
	private ExpressionParser parser;
	private Function function;

	@FXML
	private JFXButton functionButton;

	@FXML
	private TextField maxXText;

	@FXML
	private TextField maxYText;

	@FXML
	private TextField minXText;

	@FXML
	private TextField minYText;

	@FXML
	private TextField functionTextField;
	
	@FXML
    private JFXButton cheatSheetButton;

	@FXML
	private BorderPane view;

	public StartController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StartView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void initialize(URL location, ResourceBundle resources) {
		parser = new ExpressionParser();
		functionButton.disableProperty().bind(functionTextField.textProperty().isEmpty());

		minXText.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		maxXText.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		minYText.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		maxYText.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		

		Platform.runLater(() -> functionTextField.requestFocus());
	}
	
	@FXML
    void onCheatSheetClicked(ActionEvent event) {
		CheatSheetController cheatSheetController = new CheatSheetController();
		Stage stage = new Stage();
		stage.setScene(new Scene(cheatSheetController.getView(), 600, 400));
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
    }

	@FXML
	void onResolveAction(ActionEvent event) {
		function = parser.parse(functionTextField.getText());
		if (function == null) {
			functionTextField.setText("");
			System.out.println("Not function");
		} else {
			int minX = minXText.textProperty().isEmpty().get() ? -10 : Integer.parseInt(minXText.getText());
			int maxX = maxXText.textProperty().isEmpty().get() ? 10 : Integer.parseInt(maxXText.getText());
			int minY = minYText.textProperty().isEmpty().get() ? -10 : Integer.parseInt(minYText.getText());
			int maxY = maxYText.textProperty().isEmpty().get() ? 10 : Integer.parseInt(maxYText.getText());

//			System.out.println(1/0);
//			loading();

			GraphController graph = new GraphController(functionTextField.getText(), minX, maxX, minY, maxY);
			App.primaryStage.setMinHeight(600);
			App.primaryStage.setMinWidth(1000);
			App.primaryStage.centerOnScreen();
			App.primaryStage.setResizable(true);
			App.primaryStage.setScene(new Scene(graph.getView(), 1000, 600));
			
			if (!getView().getScene().getWindow().equals(App.primaryStage)) {
				((Stage) getView().getScene().getWindow()).close();;
			}
		}
	}

	public BorderPane getView() {
		return view;
	}
}
