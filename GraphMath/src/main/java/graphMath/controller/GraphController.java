package graphMath.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import graphMath.App;
import graphMath.functiongrapher.grapher.expression.Function;
import graphMath.functiongrapher.grapher.parser.ExpressionParser;
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
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.DoubleStringConverter;

/**
 * Controlador de la vista de un gráfico para una ecuación.
 */
public class GraphController implements Initializable {

	private StringProperty functionProperty = new SimpleStringProperty();
	private IntegerProperty minXProperty = new SimpleIntegerProperty();
	private IntegerProperty maxXProperty = new SimpleIntegerProperty();
	private IntegerProperty minYProperty = new SimpleIntegerProperty();
	private IntegerProperty maxYProperty = new SimpleIntegerProperty();
	private ExpressionParser parser;
	private Function function;

	private StringProperty minProperty = new SimpleStringProperty();
	private StringProperty maxProperty = new SimpleStringProperty();

	private Double minY;
	private Double maxY;
	private Double minX;
	private Double maxX;

//	private String dominio;
	private boolean discontinuidad;

	@FXML
	private HBox hbView;

	@FXML
	private VBox vbView;

	@FXML
	private MenuBar graphMenu;

	@FXML
	private BorderPane view;

	@FXML
	private TextField maxText;

	@FXML
	private TextField minText;

	@FXML
	private Button limitButton;

	@FXML
	private TextField limitFx;

	@FXML
	private TextField limitX;

	@FXML
	private TextField domText;

	@FXML
	private TextField limInfText;

	@FXML
	private TextField limMinusText;

	@FXML
	private TextField recText;

	@FXML
	private TextField contText;

	/**
	 * Retorna la vista del controlador.
	 * 
	 * @return La vista del controlador.
	 */
	public BorderPane getView() {
		return view;
	}

	/**
	 * Constructor de la clase GraphController que crea una instancia del
	 * controlador de gráficos y establece los valores de las propiedades de
	 * función, mínimos y máximos de coordenadas en 4 dimensiones.
	 * 
	 * @param function Define la función a graficar.
	 * @param minX     Define el valor mínimo del eje X en el gráfico.
	 * @param maxX     Define el valor máximo del eje X en el gráfico.
	 * @param minY     Define el valor mínimo del eje Y en el gráfico.
	 * @param maxY     Define el valor máximo del eje Y en el gráfico.
	 */
	public GraphController(String function, int minX, int maxX, int minY, int maxY) {
		functionProperty.setValue(function);

		minXProperty.setValue(minX);
		maxXProperty.setValue(maxX);
		minYProperty.setValue(minY);
		maxYProperty.setValue(maxY);

		this.minY = (double) maxYProperty.get();
		this.maxY = (double) minYProperty.get();
		this.minX = (double) maxXProperty.get();
		this.maxX = (double) minXProperty.get();

		minProperty.set("");
		maxProperty.set("");

//		dominio = "[";

		discontinuidad = false;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GraphView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Inicializa el controlador de la gráfica. Crea un nuevo objeto
	 * ExpressionParser para analizar la función definida por la propiedad
	 * functionProperty. Luego, crea una nueva tarea con un método para generar la
	 * gráfica a partir de la función analizada, la cual se ejecuta en un hilo
	 * separado. Cuando la tarea termina, la gráfica resultante se agrega a la vista
	 * de la ventana principal y se actualizan los valores de las propiedades
	 * minProperty, maxProperty, limInfText, limMinusText y contText con los
	 * respectivos valores calculados.
	 * 
	 * @param location  la ubicación del recurso FXML utilizado para inicializar el
	 *                  controlador
	 * @param resources los recursos utilizados por la aplicación
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		parser = new ExpressionParser();
		function = parser.parse(functionProperty.get());

		minText.textProperty().bind(minProperty);
		maxText.textProperty().bind(maxProperty);

		limitX.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
		limitButton.disableProperty().bind(limitX.textProperty().isEmpty());

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
				minProperty.set(String.format(Locale.US, "(%.3f,%.3f)", minX, minY));
				maxProperty.set(String.format(Locale.US, "(%.3f,%.3f)", maxX, maxY));
//				domText.setText(dominio);
//				domText.appendText("");
				limInfText.setText(getLimInf());
				limInfText.appendText("");
				limMinusText.setText(getLimMinusInf());
				limMinusText.appendText("");
				contText.setText(getContinuidad());
				contText.appendText("");
//				recText.setText(getRecorrido(task.get()));
//				recText.appendText("");
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		});
		new Thread(task).start();
	}

	/*
	 * private String getRecorrido(LineChart<Number, Number> lineChart) { String
	 * recorrido = ""; if (lineChart.getData().size() == 1) { recorrido =
	 * String.format(Locale.US, "[%.2f,%.2f]", minY, maxY); } else { recorrido =
	 * "["; for (Series<Number, Number> serie : lineChart.getData()) { Double yMin =
	 * Double.parseDouble(serie.getData().get(0).getYValue() + ""); Double yMax =
	 * Double.parseDouble(serie.getData().get(0).getYValue() + ""); for
	 * (javafx.scene.chart.XYChart.Data<Number, Number> data : serie.getData()) { if
	 * (Double.parseDouble(data.getYValue() + "") < yMin) yMin =
	 * Double.parseDouble(data.getYValue() + ""); if
	 * (Double.parseDouble(data.getYValue() + "") > yMax) yMax =
	 * Double.parseDouble(data.getYValue() + ""); } recorrido +=
	 * String.format(Locale.US, "%.2f,%.2f]", yMin, yMax); if
	 * (!serie.equals(lineChart.getData().get(lineChart.getData().size() - 1)))
	 * recorrido += " U ["; } } return recorrido; }
	 */

	/**
	 * Devuelve una cadena de texto que indica la continuidad de la función. Si la
	 * variable discontinuidad es verdadera, la función es discontinua y se devuelve
	 * la cadena "Discontinua". Si la variable discontinuidad es falsa, la función
	 * es continua y se devuelve la cadena "Continua".
	 * 
	 * @return una cadena de texto que indica la continuidad de la función
	 */
	private String getContinuidad() {
		String continuidad;
		if (discontinuidad) {
			continuidad = "Discontinua";
		} else {
			continuidad = "Continua";
		}
		return continuidad;
	}

	/**
	 * Crea una gráfica de línea a partir de la función definida en la propiedad
	 * functionProperty. Se establecen los ejes x e y con los valores mínimos y
	 * máximos de sus propiedades correspondientes. Se define un objeto Series para
	 * agregar los datos de la función evaluados en el rango de los valores del eje
	 * x, utilizando el objeto FunctionParser. Si la función tiene discontinuidades,
	 * se agregan múltiples objetos Series para representar las diferentes partes
	 * continuas de la función. La gráfica resultante se configura sin símbolos y
	 * con tamaño máximo y se devuelve como un objeto LineChart.
	 * 
	 * @return una gráfica de línea que representa la función definida en la
	 *         propiedad functionProperty
	 */
	private LineChart<Number, Number> createGraph() {
		NumberAxis xAxis = new NumberAxis("x", minXProperty.get(), maxXProperty.get(),
				Math.max((maxXProperty.get() - minXProperty.get()) / 20, 1));
		NumberAxis yAxis = new NumberAxis("f(x)", minYProperty.get(), maxYProperty.get(),
				Math.max((maxYProperty.get() - minYProperty.get()) / 20, 1));

		LineChart<Number, Number> graphLineChart = new LineChart<>(xAxis, yAxis);
		graphLineChart.setTitle("Function Graph");

		XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
		Double ratio = round((maxXProperty.get() - minXProperty.get()) / 20000.0, 3);
		System.out.println(ratio);
		for (Double x = (double) minXProperty.get(); x <= maxXProperty.get(); x += ratio) {
			Double y = function.evaluateAt(x, 0, 0);
			Double xRound = round(x, 3);

			if (Double.isInfinite(function.evaluateAt(xRound, 0, 0))) {
				if (!Double.isNaN(function.evaluateAt(xRound + ratio, 0, 0))) {
					if (function.evaluateAt(xRound, 0, 0) >= 0) {
//						dominio += String.format(Locale.US, "%.2f) U (%.2f,", xRound, xRound);
						if (dataSeries.getData().size() > 0) {
							discontinuidad = true;
							graphLineChart.getData().add(dataSeries);
							dataSeries.setName(functionProperty.get() + " (x<" + xRound + ")");
							dataSeries = new XYChart.Series<>();
							dataSeries.setName(functionProperty.get() + " (x>" + xRound + ")");
						}
						dataSeries.getData().add((new XYChart.Data<>(xRound, maxYProperty.get())));

					} else {
						if (dataSeries.getData().size() > 0) {
							discontinuidad = true;
							graphLineChart.getData().add(dataSeries);
							dataSeries.setName(functionProperty.get() + " (x<" + xRound + ")");
							dataSeries = new XYChart.Series<>();
							dataSeries.setName(functionProperty.get() + " (x>" + xRound + ")");

						}
						dataSeries.getData().add((new XYChart.Data<>(xRound, minYProperty.get())));
					}
				} else {
					if (function.evaluateAt(xRound, 0, 0) >= 0) {
						dataSeries.getData().add((new XYChart.Data<>(xRound, maxYProperty.get())));
					} else {
						dataSeries.getData().add((new XYChart.Data<>(xRound, minYProperty.get())));
					}
				}
			} else {
				if (!Double.isNaN(y)) {
//					if (dominio.charAt(dominio.length() - 1) == '[') {
//						dominio += String.format(Locale.US, "%.2f,", xRound);
//					}

					Double yRound = round(y, 3);
					/*
					 * if((function.evaluateAt(xRound, 0, 0) % 1 == 0 && function.evaluateAt(xRound
					 * + ratio, 0, 0) % 1 == 0 && function.evaluateAt(xRound - ratio, 0, 0) % 1 ==
					 * 0)) { if(xRound%1==0) { dataSeries.getData().add((new XYChart.Data<>(xRound,
					 * xRound))); discontinuidad = true; graphLineChart.getData().add(dataSeries);
					 * dataSeries.setName(functionProperty.get() + " (x<" + xRound + ")");
					 * dataSeries = new XYChart.Series<>(); dataSeries.getData().add((new
					 * XYChart.Data<>(xRound, yRound))); dataSeries.setName(functionProperty.get() +
					 * " (x>" + xRound + ")"); } else dataSeries.getData().add((new
					 * XYChart.Data<>(xRound, yRound))); System.out.println(xRound + " , " +
					 * yRound); if (yRound > maxY) { maxX = xRound; maxY = yRound; } if (yRound <
					 * minY) { minY = yRound; minX = xRound; } } else {
					 */
					if (((Double.isFinite(function.evaluateAt(xRound - ratio, 0, 0))
							&& round(function.evaluateAt(xRound - ratio, 0, 0), 3) != 0
							&& yRound / function.evaluateAt(xRound - ratio, 0, 0) < 0))
							&& dataSeries.getData().size() > 0) {
						discontinuidad = true;
						graphLineChart.getData().add(dataSeries);
						dataSeries.setName(functionProperty.get() + " (x<" + xRound + ")");
						dataSeries = new XYChart.Series<>();
						dataSeries.setName(functionProperty.get() + " (x>" + xRound + ")");
					}
					System.out.println(xRound + " , " + yRound);
					if (yRound > maxY) {
						maxX = xRound;
						maxY = yRound;
					}
					if (yRound < minY) {
						minY = yRound;
						minX = xRound;
					}
					dataSeries.getData().add((new XYChart.Data<>(xRound, yRound)));
//					}
				}
			}
		}

		if (dataSeries.getName() == null || dataSeries.getName().isBlank()) {
			dataSeries.setName(functionProperty.get());
		}
		graphLineChart.getData().add(dataSeries);
//		dominio += Double.isFinite(function
//				.evaluateAt((double) dataSeries.getData().get(dataSeries.getData().size() - 1).getXValue(), 0, 0))
//						? String.format(Locale.US, "%.2f]",
//								dataSeries.getData().get(dataSeries.getData().size() - 1).getXValue())
//						: String.format(Locale.US, "%.2f)",
//								dataSeries.getData().get(dataSeries.getData().size() - 1).getXValue());
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
	 * Evalúa la función en el límite ingresado por el usuario. Si el valor evaluado
	 * en el límite es Infinito, calcula los límites inferior y superior para
	 * verificar si la función diverge en ese punto. En caso contrario, establece el
	 * valor del límite evaluado.
	 * 
	 * @param event el evento que desencadena la evaluación del límite
	 */
	@FXML
	private void onLimitAction(ActionEvent event) {
		Double limitFunction = function.evaluateAt(Double.parseDouble(limitX.getText()), 0, 0);
		if (limitFunction.isInfinite()) {
			double limitFunctionBelow = function.evaluateAt(Double.parseDouble(limitX.getText()) - 0.0000001, 0, 0);
			double limitFunctionAbove = function.evaluateAt(Double.parseDouble(limitX.getText()) + 0.0000001, 0, 0);

			if (!Double.isNaN(limitFunctionAbove) && !Double.isNaN(limitFunctionBelow)
					&& !(limitFunctionBelow / limitFunctionAbove > 0)) {
				limitFx.setText("Divergente");
			} else {
				limitFx.setText(limitFunction < 0 ? "-Infinito" : "Infinito");
			}
		} else {
			limitFx.setText(limitFunction + "");
		}
	}

	/**
	 * Invierte el eje X de la gráfica.
	 * 
	 * @param event el evento que desencadena la inversión del eje X
	 */
	@FXML
	private void inInvertAxisX(ActionEvent event) {
		GraphController graph = new GraphController("-" + functionProperty.get(), minXProperty.get(),
				maxXProperty.get(), minYProperty.get(), maxYProperty.get());
		App.primaryStage.setMinHeight(600);
		App.primaryStage.setMinWidth(1000);
		App.primaryStage.centerOnScreen();
		App.primaryStage.setResizable(true);
		App.primaryStage.setScene(new Scene(graph.getView(), 1000, 600));
	}

	/**
	 * Invierte el eje Y de la gráfica.
	 * 
	 * @param event el evento que desencadena la inversión del eje Y
	 */
	@FXML
	private void inInvertAxisY(ActionEvent event) {
		String newFunction = functionProperty.get().replace("x", "-x");
		GraphController graph = new GraphController(newFunction, minXProperty.get(), maxXProperty.get(),
				minYProperty.get(), maxYProperty.get());
		App.primaryStage.setMinHeight(600);
		App.primaryStage.setMinWidth(1000);
		App.primaryStage.centerOnScreen();
		App.primaryStage.setResizable(true);
		App.primaryStage.setScene(new Scene(graph.getView(), 1000, 600));
	}

	/**
	 * Cierra la aplicación después de mostrar una alerta de confirmación.
	 * 
	 * @param event el evento que desencadena la salida de la aplicación
	 */
	@FXML
	private void onExit(ActionEvent event) {
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
	private void onNewFunction(ActionEvent event) {
		StartController controller = new StartController();
		Stage stage = new Stage();
		stage.setScene(new Scene(controller.getView(), 500, 500));
		stage.setTitle("Nueva función");
		stage.getIcons().add(new Image("images/mathgraph.png"));
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
	 * Devuelve el límite de la función en el infinito negativo. Si el valor
	 * evaluado en infinito es NaN, retorna "Indeterminado". Si el valor evaluado en
	 * infinito es Infinito negativo, retorna "-Infinito". Si el valor evaluado en
	 * infinito es Infinito positivo, retorna "Infinito". De lo contrario, retorna
	 * el valor evaluado en infinito.
	 * 
	 * @return el límite inferior de la función
	 */
	private String getLimMinusInf() {
		Double value = function.evaluateAt(Double.NEGATIVE_INFINITY, 0, 0);
		if (Double.isNaN(value)) {
			return "Indeterminado";
		} else if (Double.isInfinite(value)) {
			return value < 0 ? "-Infinito" : "Infinito";
		} else {
			return value + "";
		}
	}

	/**
	 * Devuelve el límite de la función en el infinito. Si el valor evaluado en
	 * infinito es NaN, retorna "Indeterminado". Si el valor evaluado en infinito es
	 * Infinito negativo, retorna "-Infinito". Si el valor evaluado en infinito es
	 * Infinito positivo, retorna "Infinito". De lo contrario, retorna el valor
	 * evaluado en infinito.
	 * 
	 * @return el límite inferior de la función
	 */
	private String getLimInf() {
		Double value = function.evaluateAt(Double.POSITIVE_INFINITY, 0, 0);
		if (Double.isNaN(value)) {
			return "Indeterminado";
		} else if (Double.isInfinite(value)) {
			return value < 0 ? "-Infinito" : "Infinito";
		} else {
			return value + "";
		}
	}
}
