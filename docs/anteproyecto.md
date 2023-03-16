# Anteproyecto

## OBJETIVOS

El objetivo de este proyecto será desarrollar una aplicación de escritorio a través de la cual, pasándole una función matemática, nos devuelva su representación gráfica. Además, también podremos mostrar un estudio de la propia gráfica al usuario (límites de la función, continuidad, etc.). Finalmente, como parte final del proyecto, se tratará que la propia aplicación permita no sólo introducir funciones, si no también introducir ecuaciones, que la aplicación tratará de resolver.

El objetivo del aplicativo será facilitar el trabajo de estudio de funciones, en ocasiones tedioso, a estiudiantes de matemáticas avanzadas, ya sea para comprobar sus resultados o para resolver los propios problemas.

## PREANÁLISIS DE LO EXISTENTE

Si bien el proyecto se realizará desde 0, se untilizarán algunos programas ya existentes de referencia, como puede ser GeoGebra. Sin embargo, somo ya se ha establecido, la idea del proyecto es realizarlo desde 0, así que estas aplicaciones no serán más que una guía parcial de como se puede enfocar el diseño del aplicativo.

## ANÁLISIS DEL SOFTWARE

El objetivo primero de este software será que, a través de un input de texto, el usuario introduzca una función en el aplicativo, así como el rango de estudio (tanto en dominio como en recorrido). De esta manera, el programa leerá dicha función, la analizará y generará una imagen con su representación gráfica.

El segundo objetivo, una vez logrado el primero, será hacer que el software haga un estudio de la función. Esto es, revisando los valores posibles de la función, determinar el dominio de la función (los posibles valores que puede tomar x en la función), el recorrido (los valores que puede devolver la función en su dominio), la continuidad de la función (determinar si hay algún punto en su dominio en el que no exista solución) y los límites (ver hacia que valores tiende la función cuando x es muy grande o muy pequeña).

El objetivo final a implementar, una vez se hayan logrado los dos anteriores, será permitir al usuario no sólo introducir funciones, si no también introducir ecuaciones, en cuyo caso lo que hará la aplicación será evolver la soluión de la misma.

*Como se ha comentado con anterioridad, el proyecto se desarrollará desde 0, usando programas existentes únicamente como referencia a nivel gráfico o de lógica, nunca usando código de las mismas para el aplicativo.*

## DISEÑO DEL SOFTWARE

El Software lo diseñaremos íntegramente en Java, creando así una aplicación en JavaFX usando las herramientas que hemos aprendido a manejar a lo largo del ciclo para ello (Eclipse, SceneBuilder, etc.).

A un nivel básico, el funcionamiento del aplicativo será el siguiente:

![PMR Proyecto Niv 0.jpg](imgs/anteproyecto/PMR%20Proyecto%20Niv%200.jpg)

El usuario, a través de una interfaz desarrllada con JavaFX, introducirá la función o ecuación deseada. El aplicativo la procesará y devolverá al usuario los resultados pertinentes.

Si ahondamos un poco más, vemos que se puede seprar en dos partes: Procesamiento de la consulta y Devolución de resultados

![PMR Proyecto Niv 1.jpg](imgs/anteproyecto/PMR%20Proyecto%20Niv%201.jpg)

La devolución de resultados es simple: Únicamente se tendrán que mostrar, a través de la interfaz desarrollada en JavaFX, los resultados obtenidos en la fase de procesamiento.

El procesamiento de la consulta, por otra parte, será un poco más complejo: Se analizará el *imput* del usuario, y se decidirá si se trata de una función, una ecuación o otra cosa. 

![PMR Proyecto Niv 2 Proc Dat.jpg](imgs/anteproyecto/PMR%20Proyecto%20Niv%202%20Proc%20Dat.jpg)

Si es una ecuación, se calculará el resultado, y este dato será el que se le mostrará al usuario.

Por otra parte, si los datos introducidos no son ni una función ni una ecuación, simplemente se le comunicará al usuario este hecho, y no hará nada más.

Lo complicado viene en caso de ser una función: En esa situación se tendrá que dibujar el gráfico, se estudiarán las condiciones de la función, y serán esos datos los que mostrará el aplicativo al usuario.

![PMR Proyecto Niv 3 Est Fun.jpg](imgs/anteproyecto/PMR%20Proyecto%20Niv%203%20Est%20Fun.jpg)

## ESTIMACIÓN DE COSTES

En cuanto a costes, no se espera que el desarrollo del aplicativo tenga costes económicos, ya que se intentará en la medida de lo posible usar servicios y librerias conocidos y gratuitos. Por ende, el coste del aplicativo será sólo temporal, estimando tardar en desarrollarlo los dos meses que durarán las prácticas de empresa, dedicando para el desarrollo el tiempo libre del que se disponga, tanto en las tardes como en fines de semana. Por ende, se espera dedicarle al proyecto entre 100 y 120 horas a lo largo de los meses de marzo, abril y mayo.
