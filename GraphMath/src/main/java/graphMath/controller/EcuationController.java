package graphMath.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import graphMath.App;
import graphMath.functiongrapher.grapher.expression.Function;
import graphMath.functiongrapher.grapher.parser.ExpressionParser;
import graphMath.utils.Point;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class EcuationController implements Initializable {

	private StringProperty functionXProperty = new SimpleStringProperty();
	private StringProperty functionYProperty = new SimpleStringProperty();
	private IntegerProperty minXProperty = new SimpleIntegerProperty();
	private IntegerProperty maxXProperty = new SimpleIntegerProperty();
	private IntegerProperty minYProperty = new SimpleIntegerProperty();
	private IntegerProperty maxYProperty = new SimpleIntegerProperty();
	private ExpressionParser parser;
	private Function functionX;
	private Function functionY;
	private List<Point> cutPoints;

	@FXML
	private MenuBar graphMenu;

	@FXML
	private HBox hbView;

	@FXML
	private TextField pointText;

	@FXML
	private VBox vbView;

	@FXML
	private BorderPane view;

	/**
	 * Constructor de la clase GraphController que crea una instancia del
	 * controlador de gráficos y establece los valores de las propiedades de
	 * función, mínimos y máximos de coordenadas en 4 dimensiones.
	 * 
	 * @param functionX Define la función de la incógnita x a graficar.
	 * @param functionY Define la función de la incógnita y a graficar.
	 * @param minX      Define el valor mínimo del eje X en el gráfico.
	 * @param maxX      Define el valor máximo del eje X en el gráfico.
	 * @param minY      Define el valor mínimo del eje Y en el gráfico.
	 * @param maxY      Define el valor máximo del eje Y en el gráfico.
	 */
	public EcuationController(String functionX, String functionY, int minX, int maxX, int minY, int maxY) {
		functionXProperty.setValue(functionX);
		functionYProperty.setValue(functionY);
		minXProperty.setValue(minX);
		maxXProperty.setValue(maxX);
		minYProperty.setValue(minY);
		maxYProperty.setValue(maxY);

		cutPoints = new ArrayList<>();

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EcuationView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Inicializa el controlador de gráficos. Crea un nuevo ExpressionParser para
	 * analizar las funciones en X y Y definidas por las propiedades
	 * correspondientes. Luego, crea una nueva tarea con un método para generar la
	 * gráfica a partir de la función analizada, la cual se ejecuta en un hilo
	 * separado. Cuando la tarea termina, la gráfica resultante se agrega a la vista
	 * de la ventana principal y el texto que muestra los puntos de corte se
	 * actualiza según los valores de los puntos de corte calculados.
	 * 
	 * @param location  la ubicación del recurso FXML utilizado para inicializar el
	 *                  controlador
	 * @param resources los recursos utilizados por la aplicación
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		parser = new ExpressionParser();
		functionX = parser.parse(functionXProperty.get());
		functionY = parser.parse(functionYProperty.get());

		Task<LineChart<Number, Number>> task = new Task<LineChart<Number, Number>>() {
			@Override
			protected LineChart<Number, Number> call() throws Exception {
				return createGraph();
			}
		};

		task.setOnSucceeded(ex -> {
			try {
				hbView.getChildren().clear();
				hbView.getChildren().add(task.get());
				HBox.setHgrow(task.get(), Priority.ALWAYS);
				pointText.setText(cutPoints.size() == 0 ? "No hay puntos de corte" : cutPoints.toString());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		});
		new Thread(task).start();
	}

	/**
	 * Cierra la aplicación después de mostrar una alerta de confirmación.
	 * 
	 * @param event el evento que desencadena la salida de la aplicación
	 */
	@FXML
	void onExit(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Salir");
		alert.setHeaderText("Vas a salir de GraphMath");
		alert.setContentText("¿Estás seguro de que quieres salir?");
		alert.setResizable(false);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			Platform.exit();
		}
	}

	/**
	 * Abre una ventana para introducir una nueva función.
	 * 
	 * @param event el evento que desencadena la apertura de la nueva ventana
	 */
	@FXML
	void onNewFunction(ActionEvent event) {
		StartController controller = new StartController();
		Stage stage = new Stage();
		stage.setScene(new Scene(controller.getView(), 500, 500));
		stage.initOwner(App.primaryStage);
		stage.setMinWidth(500);
		stage.setMinHeight(500);
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.getScene().setOnKeyPressed(t -> {
			if (t.getCode() == KeyCode.ESCAPE)
				stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		});
		stage.setOnCloseRequest(e -> {
			stage.close();
		});
		stage.show();
	}

	/**
	 * Crea una gráfica de línea a partir de las funciones definidas en la propiedad
	 * functionXProperty y functionYProperty. Se establecen los ejes x e y con los
	 * valores mínimos y máximos de sus propiedades correspondientes, considerando
	 * una separación adecuada de los intervalos según el rango de los valores del
	 * eje. Se define un objeto Series para agregar los datos de la función
	 * evaluados en el rango de los valores del eje x o y, utilizando los objetos
	 * FunctionParser. Si la función tiene discontinuidades, se agregan múltiples
	 * objetos Series para representar las diferentes partes continuas de la
	 * función. La gráfica resultante se configura sin símbolos y con tamaño máximo.
	 * 
	 * @return una gráfica de línea que representa las funciones definidas en la
	 *         propiedad functionXProperty y functionYProperty.
	 */
	private LineChart<Number, Number> createGraph() {

		NumberAxis xAxis = new NumberAxis("", minXProperty.get(), maxXProperty.get(),
				Math.max((maxXProperty.get() - minXProperty.get()) / 20, 1));
		NumberAxis yAxis = new NumberAxis("", minYProperty.get(), maxYProperty.get(),
				Math.max((maxYProperty.get() - minYProperty.get()) / 20, 1));

		LineChart<Number, Number> graphLineChart = new LineChart<>(xAxis, yAxis);
		graphLineChart.setTitle("Ecuation Graph");

		XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
		Double ratio = round((maxXProperty.get() - minXProperty.get()) / 20000.0, 3);
		System.out.println(ratio);
		for (Double x = (double) minXProperty.get(); x <= maxXProperty.get(); x += ratio) {
			Double fX = functionX.evaluateAt(x, 0, 0);
			Double xRound = round(x, 3);

			if (Double.isInfinite(functionX.evaluateAt(xRound, 0, 0))) {
				if (!Double.isNaN(functionX.evaluateAt(xRound + ratio, 0, 0))) {
					if (functionX.evaluateAt(xRound, 0, 0) >= 0) {
						if (dataSeries.getData().size() > 0) {
							graphLineChart.getData().add(dataSeries);
							dataSeries.setName(functionXProperty.get() + " (x<" + xRound + ")");
							dataSeries = new XYChart.Series<>();
							dataSeries.setName(functionXProperty.get() + " (x>" + xRound + ")");
						}
						dataSeries.getData().add((new XYChart.Data<>(xRound, maxYProperty.get())));

					} else {
						if (dataSeries.getData().size() > 0) {
							graphLineChart.getData().add(dataSeries);
							dataSeries.setName(functionXProperty.get() + " (x<" + xRound + ")");
							dataSeries = new XYChart.Series<>();
							dataSeries.setName(functionXProperty.get() + " (x>" + xRound + ")");

						}
						dataSeries.getData().add((new XYChart.Data<>(xRound, minYProperty.get())));
					}
				} else {
					if (functionX.evaluateAt(xRound, 0, 0) >= 0) {
						dataSeries.getData().add((new XYChart.Data<>(xRound, maxYProperty.get())));
					} else {
						dataSeries.getData().add((new XYChart.Data<>(xRound, minYProperty.get())));
					}
				}
			} else {
				if (!Double.isNaN(fX)) {
					Double fXRound = round(fX, 3);
					if (((Double.isFinite(functionX.evaluateAt(xRound - ratio, 0, 0))
							&& round(functionX.evaluateAt(xRound - ratio, 0, 0), 3) != 0
							&& fXRound / functionX.evaluateAt(xRound - ratio, 0, 0) < 0))
							&& dataSeries.getData().size() > 0) {
						graphLineChart.getData().add(dataSeries);
						dataSeries.setName(functionXProperty.get() + " (x<" + xRound + ")");
						dataSeries = new XYChart.Series<>();
						dataSeries.setName(functionXProperty.get() + " (x>" + xRound + ")");
					}
//					System.out.println(xRound + " , " + fXRound);
					dataSeries.getData().add((new XYChart.Data<>(xRound, fXRound)));
				}
			}
		}

		if (dataSeries.getName() == null || dataSeries.getName().isBlank()) {
			dataSeries.setName(functionXProperty.get());
		}
		graphLineChart.getData().add(dataSeries);

//-----------------------------------------------------------------------------------------------------------------------------------------

		dataSeries = new XYChart.Series<>();

		for (Double y = (double) minXProperty.get(); y <= maxXProperty.get(); y += ratio) {
			Double fY = functionY.evaluateAt(0, y, 0);
			Double yRound = round(y, 3);

			if (Double.isInfinite(functionY.evaluateAt(0, yRound, 0))) {
				if (!Double.isNaN(functionY.evaluateAt(0, yRound + ratio, 0))) {
					if (functionY.evaluateAt(0, yRound, 0) >= 0) {
						if (dataSeries.getData().size() > 0) {
							graphLineChart.getData().add(dataSeries);
							dataSeries.setName(functionYProperty.get() + " (y<" + yRound + ")");
							dataSeries = new XYChart.Series<>();
							dataSeries.setName(functionYProperty.get() + " (y>" + yRound + ")");
						}
						dataSeries.getData().add((new XYChart.Data<>(yRound, maxYProperty.get())));
					} else {
						if (dataSeries.getData().size() > 0) {
							graphLineChart.getData().add(dataSeries);
							dataSeries.setName(functionYProperty.get() + " (y<" + yRound + ")");
							dataSeries = new XYChart.Series<>();
							dataSeries.setName(functionYProperty.get() + " (y>" + yRound + ")");
						}
						dataSeries.getData().add((new XYChart.Data<>(yRound, minYProperty.get())));
					}
				} else {
					if (functionY.evaluateAt(0, yRound, 0) >= 0) {
						dataSeries.getData().add((new XYChart.Data<>(yRound, maxYProperty.get())));
					} else {
						dataSeries.getData().add((new XYChart.Data<>(yRound, minYProperty.get())));
					}
				}
			} else {
				if (!Double.isNaN(fY)) {
					Double fYRound = round(fY, 3);
					if (((Double.isFinite(functionY.evaluateAt(0, yRound - ratio, 0))
							&& round(functionY.evaluateAt(0, yRound - ratio, 0), 3) != 0
							&& fYRound / functionY.evaluateAt(0, yRound - ratio, 0) < 0))
							&& dataSeries.getData().size() > 0) {
						graphLineChart.getData().add(dataSeries);
						dataSeries.setName(functionYProperty.get() + " (y<" + yRound + ")");
						dataSeries = new XYChart.Series<>();
						dataSeries.setName(functionYProperty.get() + " (y>" + yRound + ")");
					}
//					System.out.println(yRound + " , " + fYRound);
					dataSeries.getData().add((new XYChart.Data<>(yRound, fYRound)));
					if (fYRound == round(functionX.evaluateAt(y, 0, 0), 3)) {
						cutPoints.add(new Point(yRound, fYRound));
//						System.out.println("Puntos de corte: "+cutPoints);
					}
				}
			}
		}

		if (dataSeries.getName() == null || dataSeries.getName().isBlank()) {
			dataSeries.setName(functionYProperty.get());
		}
		graphLineChart.getData().add(dataSeries);

		graphLineChart.setCreateSymbols(false);
		graphLineChart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		return graphLineChart;
	}

	/**
	 * Redondea el valor dado a la cantidad de decimales especificados.
	 * 
	 * @param value  el valor a redondear
	 * @param places la cantidad de decimales a los cuales se quiere redondear
	 * @return el valor redondeado al número de decimales especificado
	 * @throws IllegalArgumentException si la cantidad de decimales especificados es
	 *                                  negativa
	 */
	private double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
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
