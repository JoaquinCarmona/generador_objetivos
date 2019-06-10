# generador_objetivos
Proyecto de algoritmos MTI Mayo 2019

Requerimientos
   * Cuenta en GitHub (https://github.com/) para poder contribuir al proyecto
   * Deben instalar el motor de Git para poder usarlo con IntelliJ
      El motor lo pueden descargar en https://git-scm.com/download
   * JDK 8 (https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
      Requerido por el servidor de aplicaciones GlashFish
      Recomiendo descargar la version 8 update 212; se require registro para descargar
      Pueden requerir configurar el PATH para que reconozca las librerias de Java
   * Glassfish (https://javaee.github.io/glassfish/download)
      Servidor de aplicaciones web 
      Pueden usar otro, pero deberian cambiar la configuracion del proyecto para correr al app con el servidor que elijan.
      Require pasos adicionales de instalacion.
      La version 5.0 solo funciona con JDK 8.0
   * IntelliJ 
   
Para ejecutar la app, debido a la carga de archivos es necesario crear la carpeta "C:\Temp\" en su equipo.

No se require bajar ninguna libreria extra, debido a que el proyecto usa Maven, las dependencias registradas son descargadas en tiempo de compilacion.

Al dia de hoy -NO INCLUYE- la libreria de Stanford NLP

# Configurar IntelliJ para usar Git
Deben tener instalado IntelliJ
Deben tener instalado el motor de Git.

1.- Abrir IntelliJ
2.- En la pantalla de inicio, elijan "Configure" en la parte inferior derecha.
3.- Seleccionen la opcion "Settings"
4.- Expandan la seccion "Version Control", elijan "Git"
5.- Den clic al boton test en la parte superior derecha, si funciona, es todo.
    Si marca error, deben ubicar el ejecutable de Git a mano. La ruta por default deberia ser "C:\Program Files\Git\bin\git.exe"
    Repitan la prueba. Si funciona, es todo; ya pueden usar IntelliJ para descargar el proyecto.

# Abrir projecto desde IntelliJ
Deben tener instalado al menos el JDK 8
Deben haber configurado Git como sistema de control de Versiones en IntelliJ

1.- Descarguen todos los archivos del repositorio
2.- En IntelliJ le dan "Check Out from Version Control". Seleccionan "Git"
3.- Ponen la siguiente URL "https://github.com/JoaquinCarmona/generador_objetivos.git" y lo mapean a una carpeta en su Computadora.
