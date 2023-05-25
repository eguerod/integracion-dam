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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

/**
 * Controlador de la vista de inicio.
 */
public class StartController implements Initializable {

	// Parser de expresiones matemáticas
	private ExpressionParser parser;
	// Función a graficar
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

	/**
	 * Constructor de la clase StartController. Carga la vista de inicio desde el
	 * archivo FXML correspondiente.
	 */
	public StartController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StartView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Inicializa el controlador cuando se carga la vista.
	 * 
	 * @param location  La ubicación del recurso.
	 * @param resources Los recursos utilizados.
	 */
	public void initialize(URL location, ResourceBundle resources) {
		parser = new ExpressionParser();
		functionButton.disableProperty().bind(functionTextField.textProperty().isEmpty());

		minXText.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		maxXText.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		minYText.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		maxYText.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));

		Platform.runLater(() -> functionTextField.requestFocus());
	}

	/**
	 * Método que se ejecuta al hacer clic en el botón "Hoja de Operaciones",
	 * mostrando la hoja de operaciones en una nueva ventana.
	 * 
	 * @param event El evento que se produce al hacer clic en el botón.
	 */
	@FXML
	void onCheatSheetClicked(ActionEvent event) {
		CheatSheetController cheatSheetController = new CheatSheetController();
		Stage stage = new Stage();
		stage.setScene(new Scene(cheatSheetController.getView(), 600, 400));
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
	}

	/**
	 * Método que se ejecuta al hacer clic en el botón "Resolver". Si la ecuación a
	 * graficar está mal escrita o no se puede parsear, muestra una alerta de error.
	 * Si es correcta, lanza la ventana que carga una gráfica para la ecuación dada.
	 * 
	 * @param event El evento que se produce al hacer clic en el botón.
	 */
	@FXML
	void onResolveAction(ActionEvent event) {
		if (functionTextField.getText().contains("=")) {
			String[] functions = functionTextField.getText().split("=");

			Function function1 = parser.parse(functions[0]);
			Function function2 = parser.parse(functions[1]);

			if (functionTextField.getText().contains("ceil") || functionTextField.getText().contains("floor")
					|| functionTextField.getText().contains("%")) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred.", ButtonType.OK);
				alert.setTitle("GraphMath");
				alert.setHeaderText("Funciones no implementadas");
				alert.setContentText(
						"Lamentablemente, las funciones ceiling, floor y modulo están contempladas por nuestro resolutor,"
								+ " pero debido a limitaciones de desarrollo se han tenido que desactivar."
								+ "\n\nPor favor, pruebe con otra ecuación.");
				alert.setOnHidden(e -> {
					if (alert.getResult() == ButtonType.OK) {
						functionTextField.setText("");
					}
				});
				alert.showAndWait();
			} else if (functions.length == 2) {
				System.out.println(functions[0] + " , " + functions[1]);
				if (function1 == null || function2 == null) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred.", ButtonType.OK);
					alert.setTitle("GraphMath");
					alert.setHeaderText("Error al parsear");
					alert.setContentText("La ecuación no se ha podido parsear correctamente."
							+ "\n\nPara asegurarte de que las funciones estén bien escritas, puedes consultar la hoja de operaciones."
							+ "\n\nRecuerda que las funciones deben tener 'x' e 'y' como incógnitas.");
					alert.setOnHidden(e -> {
						if (alert.getResult() == ButtonType.OK) {
							functionTextField.setText("");
							System.out.println("Not function");
						}
					});
					alert.showAndWait();
				} else {
					if (functions[0].contains("x")) {
						if (functions[0].contains("y") || functions[0].contains("z") || functions[1].contains("z")
								|| !functions[1].contains("y") || functions[1].contains("x")) {
							Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred.", ButtonType.OK);
							alert.setTitle("GraphMath");
							alert.setHeaderText("Error al parsear");
							alert.setContentText("La ecuación no se ha podido parsear correctamente."
									+ "\n\nPara asegurarte de que las funciones estén bien escritas, puedes consultar la hoja de operaciones."
									+ "\n\nRecuerda que las funciones deben tener 'x' e 'y' como incógnitas.");
							alert.setOnHidden(e -> {
								if (alert.getResult() == ButtonType.OK) {
									functionTextField.setText("");
									System.out.println("Not function");
								}
							});
							alert.showAndWait();
						} else {
							launchEcuation(functions[0], functions[1]);
						}
					} else if (functions[0].contains("y")) {
						if (functions[0].contains("x") || functions[0].contains("z") || functions[1].contains("z")
								|| functions[1].contains("y") || !functions[1].contains("x")) {
							Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred.", ButtonType.OK);
							alert.setTitle("GraphMath");
							alert.setHeaderText("Error al parsear");
							alert.setContentText("La ecuación no se ha podido parsear correctamente."
									+ "\n\nPara asegurarte de que las funciones estén bien escritas, puedes consultar la hoja de operaciones."
									+ "\n\nRecuerda que las funciones deben tener 'x' e 'y' como incógnitas.");
							alert.setOnHidden(e -> {
								if (alert.getResult() == ButtonType.OK) {
									functionTextField.setText("");
									System.out.println("Not function");
								}
							});
							alert.showAndWait();
						} else {
							launchEcuation(functions[1].strip(), functions[0].strip());
						}
					} else {
						Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred.", ButtonType.OK);
						alert.setTitle("GraphMath");
						alert.setHeaderText("Error al parsear");
						alert.setContentText("La ecuación no se ha podido parsear correctamente."
								+ "\n\nPara asegurarte de que las funciones estén bien escritas, puedes consultar la hoja de operaciones."
								+ "\n\nRecuerda que las funciones deben tener 'x' e 'y' como incógnitas.");
						alert.setOnHidden(e -> {
							if (alert.getResult() == ButtonType.OK) {
								functionTextField.setText("");
								System.out.println("Not function");
							}
						});
						alert.showAndWait();
					}
				}
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred.", ButtonType.OK);
				alert.setTitle("GraphMath");
				alert.setHeaderText("Error al parsear");
				alert.setContentText("La ecuación no se ha podido parsear correctamente."
						+ "\n\nRecuerda que las funciones deben tener 'x' e 'y' como incógnita, y que la ecuación sólo puede tener dos partes, separadas por un igual.");
				alert.setOnHidden(e -> {
					if (alert.getResult() == ButtonType.OK) {
						functionTextField.setText("");
						System.out.println("Not function");
					}
				});
				alert.showAndWait();
			}

		} else {
			function = parser.parse(functionTextField.getText());
			if (function == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred.", ButtonType.OK);
				alert.setTitle("GraphMath");
				alert.setHeaderText("Error al parsear");
				alert.setContentText("La función no se ha podido parsear correctamente."
						+ "\n\nPara asegurarte de que la función esté bien escrita, puedes consultar la hoja de operaciones."
						+ "\n\nRecuerda que las funciones deben tener 'x' como incógnita.");
				alert.setOnHidden(e -> {
					if (alert.getResult() == ButtonType.OK) {
						functionTextField.setText("");
						System.out.println("Not function");
					}
				});
				alert.showAndWait();
			} else if (!functionTextField.getText().contains("x") || functionTextField.getText().contains("y")
					|| functionTextField.getText().contains("z")) {
				Alert alert = new Alert(Alert.AlertType.WARNING, "An error occurred.", ButtonType.CANCEL,
						ButtonType.OK);
				alert.setTitle("GraphMath");
				alert.setHeaderText("Incógnita no encontrada");
				alert.setContentText("Recuerda que las funciones deben tener 'x' como incógnita."
						+ "\n\nSi aún así estás segur@ de ver la expresión de forma gráfica, pulsa 'Aceptar'");
				alert.setOnHidden(e -> {
					if (alert.getResult() == ButtonType.OK) {
						launchGraph();
					}
				});
				alert.showAndWait();
			} else if (functionTextField.getText().contains("ceil") || functionTextField.getText().contains("floor")
					|| functionTextField.getText().contains("%")) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred.", ButtonType.OK);
				alert.setTitle("GraphMath");
				alert.setHeaderText("Funciones no implementadas");
				alert.setContentText(
						"Lamentablemente, las funciones ceiling, floor y modulo están contempladas por nuestro resolutor,"
								+ " pero debido a limitaciones de desarrollo se han tenido que desactivar."
								+ "\n\nPor favor, pruebe con otra función.");
				alert.setOnHidden(e -> {
					if (alert.getResult() == ButtonType.OK) {
						functionTextField.setText("");
					}
				});
				alert.showAndWait();
			} else {
				launchGraph();
			}
		}
	}

	/**
	 * Lanza un nuevo gráfico para dos ecuaciones, con valores mínimos y
	 * máximos para los ejes X e Y.
	 * 
	 * @param functionX La función en términos de X.
	 * @param functionY La función en términos de Y.
	 */
	private void launchEcuation(String functionX, String functionY) {
		int minX = minXText.textProperty().isEmpty().get() ? -10 : Integer.parseInt(minXText.getText());
		int maxX = maxXText.textProperty().isEmpty().get() ? 10 : Integer.parseInt(maxXText.getText());
		int minY = minYText.textProperty().isEmpty().get() ? -10 : Integer.parseInt(minYText.getText());
		int maxY = maxYText.textProperty().isEmpty().get() ? 10 : Integer.parseInt(maxYText.getText());

		if (!getView().getScene().getWindow().equals(App.primaryStage)) {
			((Stage) getView().getScene().getWindow()).close();
		}

		EcuationController graph = new EcuationController(functionX, functionY, minX, maxX, minY, maxY);
		App.primaryStage.setMinHeight(600);
		App.primaryStage.setMinWidth(1000);
		App.primaryStage.centerOnScreen();
		App.primaryStage.setResizable(true);
		App.primaryStage.setScene(new Scene(graph.getView(), 1000, 600));
	}

	/**
	 * Lanza un nuevo gráfico para una ecuación, con valores mínimos y
	 * máximos para los ejes X e Y.
	 * 
	 * @param functionX La función en términos de X.
	 * @param functionY La función en términos de Y.
	 */
	private void launchGraph() {
		int minX = minXText.textProperty().isEmpty().get() ? -10 : Integer.parseInt(minXText.getText());
		int maxX = maxXText.textProperty().isEmpty().get() ? 10 : Integer.parseInt(maxXText.getText());
		int minY = minYText.textProperty().isEmpty().get() ? -10 : Integer.parseInt(minYText.getText());
		int maxY = maxYText.textProperty().isEmpty().get() ? 10 : Integer.parseInt(maxYText.getText());

		if (!getView().getScene().getWindow().equals(App.primaryStage)) {
			((Stage) getView().getScene().getWindow()).close();
		}

		GraphController graph = new GraphController(functionTextField.getText(), minX, maxX, minY, maxY);
		App.primaryStage.setMinHeight(600);
		App.primaryStage.setMinWidth(1000);
		App.primaryStage.centerOnScreen();
		App.primaryStage.setResizable(true);
		App.primaryStage.setScene(new Scene(graph.getView(), 1000, 600));
	}

	/**
	 * Retorna la vista del controlador.
	 * 
	 * @return La vista del controlador.
	 */
	public BorderPane getView() {
		return view;
	}
}
