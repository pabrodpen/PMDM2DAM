# DISEÑOS MULTISCREEN FRAGMENTS

Queremos hacer que si es un móvil cargue 1 solo fragmento, pero si es una tablet, cargue 1 fragmento a la izq y otro a la derecha

1º)Creamos 2 fragmentos y los personalizamos como queramos

2º)En el main(hacemos que este sea el main del movil) añadimos un Fragment(Design→Buscamos FragmentContainerView y en name buscamos uno de los fragmentos que hayamos creado

3º)Creamos otro main(New→Layout Resource File) para las tablet→mínimo de pantalla 600dp

![cap.PNG](DISEN%CC%83OS%20MULTISCREEN%20FRAGMENTS%20b87cfdbbdee5493fa5e3269ceaa528bb/cap.png)

![cap2.PNG](DISEN%CC%83OS%20MULTISCREEN%20FRAGMENTS%20b87cfdbbdee5493fa5e3269ceaa528bb/cap2.png)

Le damos a Smallest Screen y nos aparece esto:

![cap3.PNG](DISEN%CC%83OS%20MULTISCREEN%20FRAGMENTS%20b87cfdbbdee5493fa5e3269ceaa528bb/cap3.png)

Le damos a la flecha

![cap4.PNG](DISEN%CC%83OS%20MULTISCREEN%20FRAGMENTS%20b87cfdbbdee5493fa5e3269ceaa528bb/cap4.png)

En este nuevo main creamos los 2 fragmentos(Buscamos FragmentContainerView y los asociamos a los 2 fragmentos que hemos creado antes)

Para poner uno a la izq y otro a la derecha cambiamos el main xml de 600 a LinearLayout y orientación horizontal, y ponemos que los 2 fragments tengan match_parent de ancho y largo)

SI QUEREMOS HACER ALGUN EVENTO EN CADA FRAGMENT LO UNICO QUE HAY QUE HACER ES:

 EN EL MAIN JAVA DENTRO DEL ONCREATE:

`LinearLayout layoutContenedor=(android.widget.LinearLayout) findViewById(R.id.*contenedor*);`

contenedor siempre es el id del main xml(en este caso, como el que cambia es el de 600)

IMP HACER EL CAST AL TIPO DE LAYOUT QUE TENEMOS EN EL MAIN XML 600

**`if**(layoutContenedor!=**null**){`    

*`//es una tablet(tiene minimo 600dp) y el layoutContenedor existe`*    

`showSnackbar(**"Estas en una tablet"**);`

`}**else**{`    

*`//movil`*    

`showSnackbar(**"Estas en un movil"**);`

`}`

DESCARGAR EMULADOR

1. **Abrir Android Studio:**
    - Abre Android Studio en tu computadora.
2. **Abrir AVD Manager:**
    - Ve a "Tools" en la barra de menú.
    - Selecciona "AVD Manager" (AVD significa Android Virtual Device).
3. **Crear un Nuevo Emulador:**
    - En AVD Manager, haz clic en "Create Virtual Device".
4. **Seleccionar un Hardware Profile:**
    - En la lista de perfiles de hardware, selecciona un dispositivo que tenga una resolución de pantalla más grande y sea más adecuado para tabletas, como "Nexus 9" o "Pixel C". Luego, haz clic en "Next".
5. **Elegir una Imagen del Sistema:**
    - Selecciona una imagen del sistema que desees para tu emulador. Puedes descargar diferentes versiones de Android para tabletas. Haz clic en "Next".
6. **Configurar el Emulador:**
    - Configura opciones como la orientación de la pantalla, el modo de inicio, etc. Asegúrate de configurar la orientación en "Portrait" o "Landscape" según tus necesidades. Haz clic en "Next".
7. **Revisar Configuraciones:**
    - Revisa la configuración del emulador y haz clic en "Finish".
8. **Iniciar el Emulador:**
    - En AVD Manager, selecciona el emulador que acabas de crear y haz clic en el botón verde "Play" para iniciarlo.