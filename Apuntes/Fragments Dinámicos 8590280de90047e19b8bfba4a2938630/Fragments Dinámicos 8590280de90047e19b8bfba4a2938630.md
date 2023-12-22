# Fragments Dinámicos

[Peek 2023-11-20 09-20.mp4](Fragments%20Dina%CC%81micos%208590280de90047e19b8bfba4a2938630/Peek_2023-11-20_09-20.mp4)

CARGAR DINÁMICAMENTE FRAMENTS SIGNIFICA QUE VAN CAMBANDO SEGÚN UN EVENTO

`getSupportActionBar().setTitle(**"Fragments Dinamicos"**);`//para cambiar el nombre del toolbar

Usaremos la actividad del botón flotante

Al usar esta actividad se nos crea directamente 2 fragmentos con sus respectivos xml y un content_main.xml. Este xml trata el espacio(o contenedor) que hay entre el toolbar y el botón flotante

![Captura desde 2023-11-20 09-32-08.png](Fragments%20Dina%CC%81micos%208590280de90047e19b8bfba4a2938630/Captura_desde_2023-11-20_09-32-08.png)

Este content_main lo usaremos de contenedor vacío en el que iremos poniendo los fragments según el evento

1º)Para que al iniciar la app haya cargado un fragmento, en el onCreate creamos una transacción para que cargue el primer fragmento por ejemplo

*`// Rescatamos el contenedor y le vamos a cargar un fragmento`*

`getSupportFragmentManager().beginTransaction()`        

`.add(R.id.*container*, **new** FirstFragment())        .`

`commit();`

Como iniciamos si ningún fragmento, añadimos el fragment con add, pero si hubiese otro usamos replace

container es el id del fragment del content_main(este fragment es creado por defecto)

2º)Para alternar entre un fragment y otro, usamos un booleano y vamos cambiando en el evento click del botón

boolean cargarFragmento2=true;(antes del OnCreate)

**`binding**.**fab**.setOnClickListener(**new** View.OnClickListener() {`    

*`//modificar el fragmento que sale en la pantalla`*    

`@Override    **public void** onClick(View view) {`        

**`if**(**cargarFragmento2**){`            

`getSupportFragmentManager().beginTransaction()                    .replace(R.id.*container*, **new** SecondFragment())     //USAMOS REPLACE`               

`.commit();`            

**`cargarFragmento2**=**false**;`        

`}**else**{`            

`getSupportFragmentManager().beginTransaction()                    .replace(R.id.*container*, **new** FirstFragment())  //USAMOS REPLACE`                  

`.commit();`            

**`cargarFragmento2**=**true**;`        

`}`    

`}`

`});`