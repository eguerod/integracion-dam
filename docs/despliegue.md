# Instalación/Despliegue

Para el despliegue, se ha hecho uso de [JavaPackager](https://github.com/fvarrui/JavaPackager), un plugin híbrido para Maven y Gradle que permite compilar la aplicación java desarrollada en eclipse, y generando así un instalador.

Lo primero que se ha hecho ha sido añadir el plugin de Maven al proyecto, y empaquetar este mismo haciendo uso del comando de Maven <mvn package>.

Una vez se ha empaquetado el proyecto, generando así los instaladores *.zip* y el archivo *.iss* necesario para generar el instalador *.exe*, se ha ejecutado este último con [Inno Setup](https://jrsoftware.org/isinfo.php), generando así el instalador *.exe* que se ha usado en la distribución.

Finalmente, la distribución se ha hecho a traves del apartado de [Releases](https://github.com/eguerod/integracion-dam/releases) del proyecto, siendo la versión presentada "[GraphMath 0.0.1 (Alpha)](https://github.com/eguerod/integracion-dam/releases/tag/v.0.0.1_alpha)". Desde esa *release* se podrán descargar tanto el código fuente (en formato *zip* o en formato *tar.gz*) como la versión ejecutable en *.zip* y el instalador para Windows *.exe*.

En cuanto a la instalación, es simple, permitiendo elegir el idioma, así como si se quiere crear un enlace al escritorio.

![imgs\install\Idioma.png](imgs\install\Idioma.png)

![Acceso.png](imgs\install\Acceso.png)

![Listo.png](imgs\install\Listo.png)

![Ventana.png](imgs\install\Ventana.png)
