# Intent Explicitos

Sirven para comunicar varias actividades del mismo proyecto

Por ejemplo, tenemos un formulario de 2 editText y 2 Button que llamamos MainActivity

![cap1.PNG](Intent%20Explicitos%202b7ad1ceea85456e80587d905971f1a5/cap1.png)

DENTRO DEL ON CREATE

**`datosNombre**=findViewById(R.id.*editTextTNombre*);`

**`datosEdad**=findViewById(R.id.*editTextEdad*);`

Recogemos lo que escribamos los 2 editText

`String nombre=getIntent().getStringExtra(**"EtiquetaNombre"**);`

`String edad=getIntent().getStringExtra(**"EtiquetaEdad"**);`

Recogemos el String que escribamos de la otra actividad con getIntent() y getStringExtra y los llamamos EtiquetaNombre y EtiquetaEdad

**`datosNombre**.setText(nombre);`

**`datosEdad**.setText(edad);`

Sustituimos el String de los editText del Main con los String del intent

`Button bNombre=findViewById(R.id.*botonNombre*); **`

Cogemos el boton del Nombre

*```````Button* bNombre.setOnClickListener(**new** View.OnClickListener() {`    

`@Override`    

**`public void** onClick(View view) {            metodoBotonNombre();    }`

`});`

Hacemos que al pulsar haga el metodo que describo ahora FUERA DEL ONCREATE:

**`public void** metodoBotonNombre(){`    

`Intent intent=**new** Intent(MainActivity.**this**, MainActivity2.**class**);`

Se crea un intent que coja los datos de Main2    ``

`Bundle b=**new** Bundle();`    

`b.putString(**"EtiquetaNombre"**,**datosNombre**.getText().toString());`

Creamos Un Bundle que asignamos al String del Main que hemos puesto en el onCreate     *``*

*`/*Un Bundle es una estructura de datos utilizada para pasar datos entre componentes de una aplicación Android,    como actividades, fragmentos e incluso entre distintas aplicaciones en ciertos casos.    Un Bundle es un tipo de contenedor que almacena pares clave-valor de datos. Cada dato se asocia con una clave única,    lo que facilita la recuperación de datos específico*/`*    

`intent.putExtras(b);`

les pasamos los datos que hemos recogido con el bundle al intent  *``*

`startActivity(intent);`

iniciamos la actividad con el intent

`}`

Exactamente lo mismo con el segundo editText de edad:

`Button bEdad=findViewById(R.id.*botonEdad*);`

`bEdad.setOnClickListener(**new** View.OnClickListener() {`    

`@Override`    

**`public void** onClick(View view) {        metodoBotonEdad();    }`

`});`

**`public void** metodoBotonEdad(){`    

`Intent intent=**new** Intent(MainActivity.**this**, MainActivity3.**class**);`    

`Bundle b=**new** Bundle();`    

`b.putString(**"EtiquetaEdad"**,String.*valueOf*(**datosEdad**.getText()));`

Como al editText hay que pasar String, pasamos de int a String con String.valueof()

`intent.putExtras(b);`    

`startActivity(intent);}`

MAIN2

![cap2.PNG](Intent%20Explicitos%202b7ad1ceea85456e80587d905971f1a5/cap2.png)

DENTRO DEL ONCREATE

**`dNombre**=findViewById(R.id.*editTextNombre2*);`

Asignamos el editText

`Button aceptar=findViewById(R.id.*b1Aceptar*);`

Asignamos el botón de Aceptar

`aceptar.setOnClickListener(**new** View.OnClickListener() {`    

`@Override`    

**`public void** onClick(View view) {`        

`metodoBotonAceptar1();`    

`}`

`});`

Hacemos que cuando se pulse el botón de Aceptar haga el siguiente metodo(EL METODO FUERA DEL ONCREATE)

**`public void** metodoBotonAceptar1(){`    

`Intent intent=**new** Intent(**this**, MainActivity.**class**);`    

`Bundle b=**new** Bundle();`    

`b.putString(**"EtiquetaNombre"**,**dNombre**.getText().toString());`    

`intent.putExtras(b);`    

`startActivity(intent);`

`}`

Hacemos lo mismo para el botón Cancelar

`Button cancelar=findViewById(R.id.*b1Cancelar*);`

`cancelar.setOnClickListener(**new** View.OnClickListener() {`    

`@Override`    

**`public void** onClick(View view) {`        

`metodoBotonCancelar1();`    

`}`

`});`