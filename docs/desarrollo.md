# Desarrollo

La aplicación está completamente desarrollada en el lenguaje de programación **Java**, en particular haciendo uso de las tecnologías de **JavaFX** para el desarrollo del proyecto.

En lo referente a tecnologías para el desarrollo de el aplicativo, se han utilizado tres programas principalmente: **Eclipse**, **SceneBuilder** y **VisualStudioCode**.

- **Eclipse** es un IDE de desarrollo, que hemos usado para programar en JavaFX la base, los controladores y el desarrollo de la aplicación. (https://eclipseide.org/)
  
  - Lo hemos complementado con **Maven**, una herramienta para desarrollar y gestionar el proyecto. Las *dependencies* que hemos ñadido a Maven son:
    
    - *JavaFX-Controls*: Para los controladores de javaFx.
    
    - *JavaFX-fxml*: Para los los ficheros fxml para las interfaces.
    
    - *Controlsfx*: Elementos para el desarrollo de interfaces.
    
    - *Jfoenix*: Elementos para el desarrollo de interfaces.
    
    - *Ikonli*: Paquetes de iconos para javafx, incluyendo Fontawesome-5 y MaterialDesign2.

- **SceneBuilder** es una aplicación de desarrollo de interfaces de JavaFX, que se ha usado para el desarrollo de la interfaz del aplicativo. (https://gluonhq.com/products/scene-builder/)

- **VisualStudioCode** se ha usado para desarrollar los ficheros de la documentación, el estilo y otros ficheros escritos. (https://code.visualstudio.com/)

- Además. se ha hecho uso de **Inno Setup** (https://jrsoftware.org/isinfo.php) para la compilación del archivo *.exe* que se utilizará como parte de la distribución.

En cuanto al funcionamiento del aplicativo, hay dos partes principales: la programática y la de la interfaz, que están interrelacionadas.

Para la parte de interfaz, se ha hecho uso de un modelo Vista-Controlador, usando ficheros fxml para la vista y ficheros java para los controladores. En estos últimos se realiza toda la parte programática de la interfaz, desde funcionamiento de la interfaz hasta calculo de resultados.

Para analizar el _string_ introducido por el usuario, se ha diseñado una serie de clases, parseadores y ejecutores que permiten leer, analizar y convertir a un resultado la función que el usuario escriba (todas ellas disponibles en la carpeta [*functiongrapher*](https://github.com/eguerod/integracion-dam/tree/main/GraphMath/src/main/java/graphMath/functiongrapher/grapher)). Por otra parte, dentro de los controladores se analizarán los resultados dados (con una precisión de 0.001 unidades) para poder crear la gráfica en un hilo en segundo plano, mostrándola al usuario una vez haya finalizado el cálculo.
