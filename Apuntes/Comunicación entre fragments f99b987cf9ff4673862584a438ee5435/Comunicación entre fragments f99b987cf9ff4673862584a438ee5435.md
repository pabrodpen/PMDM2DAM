# Comunicación entre fragments

![Captura.PNG](Comunicacio%CC%81n%20entre%20fragments%20f99b987cf9ff4673862584a438ee5435/Captura.png)

Queremos un fragmento con 2 botones, uno para enviar color y el otro para enviar texto al main

**1º)**Creamos un fragmento dinámico donde pondremos los botones en el xml

**2º)**En el [main.java](http://main.java) creamos una transacción del fragmento dinámico

**3º)**En el onCreateView del fragmento(donde mostramos la vista que tendrá la activity) creamos los eventos de los 2 botones

**`public** View onCreateView(LayoutInflater inflater, ViewGroup container,                         Bundle savedInstanceState) {`    

*`// Inflate the layout for this fragment`*    

`View view =inflater.inflate(R.layout.*fragment_controles*,container,**false**);`    

`Button btnColor=(Button) view.findViewById(R.id.*buttonColor*);`    

`Button btnTexto=(Button) view.findViewById(R.id.*buttonTexto*);    btnColor.setOnClickListener(**new** View.OnClickListener() {`        

`@Override`        

**`public void** onClick(View view) {`               

**`mListener**.botonColorClicked(**"Rojo"**);`        

`}`    

`});`    

`btnTexto.setOnClickListener(**new** View.OnClickListener() {`        

`@Override`        

**`public void** onClick(View view) {`            

**`mListener**.botonTextoClicked(**"Hola"**);`        

`}`    

`});`    

**`return** view;}`

**4º)**Lo que hacemos es crear una interfaz que comunica la actividad con el fragmento que carga. La actividad deberá cargar la interfaz para cargar los distintos métodos(Ej:`OnControlesFragmentListener`)

Creamos 2 métodos en la interfaz(metodoColor y metodoTexto) SIN ESPECIFICAR QUE SE HACE EN LOS MÉTODOS

**`public interface** OnControlesFragmentListener {`    

**`public void** botonColorClicked(String color);`    

**`public void** botonTextoClicked(String texto);`

`}`

**5º)**Implementamos la interfaz en el main:

**`public class** MainActivity **extends** AppCompatActivity **implements** OnControlesFragmentListener`

Con esto habrá que implementar también los métodos que hemos declarado antes de la interfaz, ASÍ QUE NOMBRAMOS ESTOS MÉTODOS EN LOS EVENTOS ONCLICK DE LOS BOTONES DEL FRAGMENTO—>AHORA SÍ ESPECIFICAMOS LO QUE HACEN LOS MÉTODOS

**`public void** botonColorClicked(String color) {`    

`showSnackbar(**"Color "**+color);`

`}`

**`public void** botonTextoClicked(String texto) {`    

`showSnackbar(**"Texto "**+texto);`

`}`

**6º)**Declaramos un objeto Interface en el Fragment

**`private** OnControlesFragmentListener **mListener**;`

**7º)**Implementamos EN EL FRAGMENTO el método onAttach y onDetach en el fragment

El método onAttach se lanza cuando se inserta (add, es decir una transacción) un fragmento dentro de un activity. **Recibe una referencia al activity(Context context)** 

**`public void** onAttach(@NonNull Context context) {`    

**`super**.onAttach(context);`    

**`if**(context **instanceof** OnControlesFragmentListener){`        

**`mListener**=(OnControlesFragmentListener) context;`    CUANDO SE PULSE UN BOTON(INSTANCIA DE LA INTERFAZ

`}**else**{`        

**`throw new** RuntimeException(context.toString());`    

`}`

`}`

El método **`onDetach()`** es un callback que se llama cuando el fragmento se ha desvinculado de su actividad principal.

**`public void** onDetach(){`    

**`super**.onDetach();`    

**`mListener**=**null**;`

`}`