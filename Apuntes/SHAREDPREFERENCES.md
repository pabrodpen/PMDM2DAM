Las preferencias no son más que datos que una aplicación debe guardar para personalizar la experiencia del usuario, por ejemplo información personal, opciones de presentación, etc

Uno de los métodos disponibles en la plataforma Android para almacenar datos, como son las bases de datos SQLite. Las preferencias de una aplicación se podrían almacenar por su puesto utilizando este método, y no tendría nada de malo, pero Android proporciona otro método alternativo diseñado específicamente para administrar este tipo de datos: las preferencias compartidas o shared preferences. Cada preferencia se almacenará en forma de clave-valor, es decir, cada una de ellas estará compuesta por un identificador único (p.e. «email») y un valor asociado a dicho identificador (p.e. «prueba@email.com»). Además, y a diferencia de SQLite, los datos no se guardan en un fichero binario de base de datos, sino en ficheros XML.

### EJEMPLO

Esta aplicación Android parece ser un formulario de inicio de sesión simple que utiliza SharedPreferences para recordar las credenciales del usuario incluso después de cerrar y volver a abrir la aplicación. 

![[Main.png]]
AL LOGEARNOS
![[Success.png]]
AL VOLVER NOS RECUERDA EL USUARIO E INCLUSO AL CERRAR LA APP

##### MAIN JAVA
![[MainCod.png]]![[Captura 2.png]]

Si pulsamos el botón de inicio de sesión
![[caaaap.png]]
Se abre la otra actividad
![[Captura2 1.png]]