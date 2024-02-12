

1º Si tenemos un formulario en español, tenemos que tener un string(en values) en español con todas las palabras para cada editText o TextView con su name=””

`<**resources**>`    

`<**string name="app_name"**>Formulario</**string**>`    

`<**string name="intro"**>Bienvenido al formulario de registro!</**string**>`    

`<**string name="action_settings"**>Configuración</**string**>`    

`<**string name="nombre"**>Nombre:</**string**>`    

`<**string name="apellidos"**>Apellidos:</**string**>    <**string name="email"**>Email:</**string**>    <**string name="password"**>Clave:</**string**>`

`</**resources**>`

Cada name es el id del textView

2º Creamos un string en ingles por ejemplo de la siguiente manera(Values Resource File):

![cap3.PNG](Cambiar%20de%20idioma%20ecbef07a8b2c470f84257e16f9636f90/cap3.png)

Le damos a la flecha del medio

![cap4.PNG](Cambiar%20de%20idioma%20ecbef07a8b2c470f84257e16f9636f90/cap4.png)

Pulsamos OK y se nos crea el archivo

Despué rellenamos el archivo con lo que tiene que poner cada EditText

<?xml version="1.0" encoding="utf-8"?>
<resources>
<string name="app_name">Internacionalization</string>
<string name="intro">Welcome to sign up form</string>
<string name="action_settings">Settings</string>
<string name="nombre">Name:</string>
<string name="apellidos">Surname:</string>
<string name="email">Email:</string>
<string name="password">Password:</string>
</resources>

Finalmente cambiamos de idioma deslizando hacia arriba en la parte inferior del emulador, en Settings<Languages<Buscamos ingles y lo colocamos primero