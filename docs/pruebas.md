# Pruebas

Como inicio de las pruebas, una vez desarrollada la base del programa, se comprobará cada una de las funciones, haciendo pruebas de manera manual para asegurarnos del correcto funcionamiento del aplicativo.

- Durante la prueba de multitud de posibles funciones, se ha detectado un problema en la representación de funciones no continuas, ya que los puntos de discontinuidad se representan unidos.
  
  - Para solucionarlo, se ha establecido un sistema de separación de gráficos en el código, que identifica los puntos de corte de las funciones y las separa.

- Del mismo modo, se encontró que cuando una función tiende al infinito, esta salía cortada en un punto de la gráfica debido al redondeo de los resultados.
  
  - Esto se solucionó implementando un sistema adherido al anterior que identifica estos casos y los representa correctamente.

- Un caso a destacar son las funciones 'tangente', 'cosecante' y 'cotangente', ya que debido al redondeo, el programa no era capaz de identificar pi/2 como un punto de discontinuidad.
  
  - Para solucionar esto, se ha establecido un sistema que identifica estos puntos, y hace la representación gráfica correcta.



Por otra parte, durante el desarrollo se han encontrado errores que no se han conseguido solucionar:

- Las funciones 'ceil', 'floor' y 'módulo' se han tenido que desactivar ya que el problema de discontinuidad en este caso no se ha conseguido solventar.
  
  - Esto es debido a que, por los redondeos, en ocasiones un punto que debería dar él mismo, daba el punto anterior o el siguiente.

- El estudio del dominio y el recorrido, así como de los puntos de discontinuidad, se ha tenido que desactivar, debido a la inexactitud para estudiar dichos puntos.
  
  - A la hora de calcularlos para hacer la representación gráfica, estos puntos se detectan de manera efectiva, pero después para usarlos para el estudio de la función, pasan a dar problemas.

- Finalmente, en cuanto a la solución de un sistema de ecuaciones con dos incógnitas, una vez se ha empezado a implementar, se ha visto que hay un problema de base: No se puede resolver un sistema de dos incógnitas con una sola ecuación.
  
  - En su lugar, se ha implementado un sistema que representa las dos funciones pasadas, y calcula el punto de corte entre ellas (si es que existe).



Una vez terminado el desarrollo del aplicativo (o al menos de la versión distribuida para el proyecto), se distribuirá a un pequeño grupo de usuarios, de manera que se obtenga una muestra más amplia de pruebas.
