##### ALERT DIALOG SI/NO
~~~
dialogSi.setOnClickListener(new View.OnClickListener() {  
    @Override  
    public void onClick(View v) {  
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);  
        //dialog.setCancelable(false);  
        dialog.setMessage("¿Salir?");  
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
  
            }  
        });  
        dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
  
            }  
        });  
        dialog.show();  
    }  
});
~~~

##### PROGRESS DIALOG
~~~
pbDialog.setOnClickListener(new View.OnClickListener() {  
    @Override  
    public void onClick(View view) {  
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);  
        builder.setView(R.layout.dialog_pb);  
  
    AlertDialog alertDialog = builder.create();  
    alertDialog.setMessage("Cargando datos");  
    alertDialog.show();  
    }  
});
~~~

EN EL XML
~~~
<ProgressBar  
    android:id="@+id/progressBar"  
    style="?android:attr/progressBarStyle"  
    android:layout_width="wrap_content"  
    android:layout_height="wrap_content"  
    app:layout_constraintBottom_toBottomOf="parent"  
    app:layout_constraintEnd_toEndOf="parent"  
    app:layout_constraintHorizontal_bias="0.044"  
    app:layout_constraintStart_toStartOf="parent"  
    app:layout_constraintTop_toTopOf="parent"  
    app:layout_constraintVertical_bias="0.423" />
~~~
#### DATEPICKER DIALOG
~~~
dialogDate.setOnClickListener(new View.OnClickListener() {  
    @Override  
    public void onClick(View view) {  
        Calendar calendar = Calendar.getInstance();  
        int anio = calendar.get(Calendar.YEAR);  
        int mes = calendar.get(Calendar.MONTH);  
        int day = calendar.get(Calendar.DAY_OF_MONTH);  
  
        DatePickerDialog datePickerDialog = new DatePickerDialog(  
                MainActivity.this,  
                new DatePickerDialog.OnDateSetListener() {  
                    @Override  
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {  
                        // Aquí puedes manejar la fecha seleccionada  
                        String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;  
                        Toast.makeText(MainActivity.this, "Fecha seleccionada: " + fechaSeleccionada, Toast.LENGTH_SHORT).show();  
                    }  
                }, anio, mes, day  
        );  
        datePickerDialog.show();  
    }  
});
~~~

##### TIMEPICKER DIALOG
~~~
dialogTime.setOnClickListener(new View.OnClickListener() {  
    @Override  
    public void onClick(View view) {  
        Calendar calendar = Calendar.getInstance();  
        int hora = calendar.get(Calendar.HOUR);  
        int minutos = calendar.get(Calendar.MINUTE);  
  
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {  
            @Override  
            public void onTimeSet(TimePicker timePicker, int i, int i1) {  
                String tiempoSeleccionado = "Has seleccionado: " + i + ":" + i1;  
                Toast.makeText(MainActivity.this, tiempoSeleccionado, Toast.LENGTH_SHORT).show();  
            }  
        },hora,minutos, true // false para usar el formato de 24 horas, true para usar el formato de 12 horas  
        );  
        timePickerDialog.show();  
    }  
});
~~~

##### DIALOG LISTA(ELEGIR 1)
~~~
public class DialogLista extends DialogFragment {  
  
    @Override  
   public Dialog onCreateDialog(Bundle savedInstanceState){  
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  
        builder.setTitle("Lista compra");  
        builder.setItems(R.array.Frutas, new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialogInterface, int i) {  
  
            }  
        });  
        return builder.create();  
    }  
}
~~~

EN EL MAIN
~~~
dialogLista.setOnClickListener(new View.OnClickListener() {  
    @Override  
    public void onClick(View view) {  
        DialogLista dialogLista = new DialogLista();  
        dialogLista.show(getSupportFragmentManager(), "DialogLista");  
    }  
});
~~~



##### DIALOG MULTIPLE CHOICE
~~~
public class DialogMultiplesOpciones extends DialogFragment {  
   @Override  
    public Dialog onCreateDialog(Bundle savedInstanceState){  
       String[] arrayFrutas = {"Manzana", "Plátano"};  
       AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  
  
       builder.setMessage("Frutas");  
  
       builder.setMultiChoiceItems(arrayFrutas, null, new DialogInterface.OnMultiChoiceClickListener(){  
           @Override  
           public void onClick(DialogInterface dialogInterface, int i, boolean isChecked){  
                //Lógica para interactuar con los objetos seleccionados  
           }  
       });  
  
       builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {  
           @Override  
           public void onClick(DialogInterface dialogInterface, int i) {  
                //Para guardar múltiples datos, en este caso las frutas que he seleccionado  
           }  
       });  
  
       builder.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {  
           @Override  
           public void onClick(DialogInterface dialogInterface, int i) {  
               //Cancela y cierra la ventana  
           }  
       });  
  
       return builder.create();  
   }  
}
~~~

EN EL MAIN
~~~
dialogMO.setOnClickListener(new View.OnClickListener() {  
    @Override  
    public void onClick(View view) {  
        DialogMultiplesOpciones dialogMO = new DialogMultiplesOpciones();  
        dialogMO.show(getSupportFragmentManager(), "DialogRadio");  
    }  
});
~~~

##### DIALOG PERSONALIZADO

EN EL MAIN
~~~
dialogCustom.setOnClickListener(new View.OnClickListener() {  
    @Override  
    public void onClick(View view) {  
        DialogPersonalizado dialogPersonalizado = new DialogPersonalizado();  
        dialogPersonalizado.show(getSupportFragmentManager(), "Dialog personalizado");  
    }  
});
~~~


EN EL JAVA

~~~
public class DialogPersonalizado extends DialogFragment {  
  
    private EditText username;  
    private EditText password;  
    private DialogListener listener;  
  
    @Override  
    public Dialog onCreateDialog(Bundle savedInstanceState){  
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  
        LayoutInflater inflator = requireActivity().getLayoutInflater();  
        View view = inflator.inflate(R.layout.custom_dialog, null);  
        builder.setView(view);  
        username = view.findViewById(R.id.username);  
        password = view.findViewById(R.id.password);  
        builder.setPositiveButton("Iniciar sesión", new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int id) {  
                if(username.getText().toString().length() > 0){  
                    String user = username.getText().toString();  
                    String pass = password.getText().toString();  
                    listener.aplicarTextos(user, pass);  
                }  
            }  
        });  
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int id) {  
                    }  
                });  
  
        return builder.create();  
    }  
  
    @Override  
    public void onAttach(Context context){  
        super.onAttach(context);  
        try {  
            listener = (DialogListener) context;  
            // Intenta realizar un casting del contexto (context) a un objeto que implemente la interfaz DialogListener.  
            // Esto significa que esperas que la actividad que contiene el fragmento implemente la interfaz DialogListener.        } catch (ClassCastException e) {  
            throw new ClassCastException(context.toString() + " debe implementar DialogListener");  
        }  
    }  
  ~~~
  
    public interface DialogListener{  
        void aplicarTextos(String username, String password);  
    }
EN EL MAIN SE DESARROLLA ESTE MÉTODO

public void aplicarTextos(String nombre, String contrasena,String d){  
    username.setText(nombre);  
    password.setText(contrasena);

EN EL XML SE RELACIONA CON 2 EDITEXT (id-->username,password)
~~~
<EditText  
    android:id="@+id/username"  
    android:inputType="textEmailAddress"  
    android:layout_width="match_parent"  
    android:layout_height="wrap_content"  
    android:layout_marginTop="16dp"  
    android:layout_marginLeft="4dp"  
    android:layout_marginRight="4dp"  
    android:layout_marginBottom="4dp"  
    android:hint="Nombre" />  
<EditText  
    android:id="@+id/password"  
    android:inputType="textPassword"  
    android:layout_width="match_parent"  
    android:layout_height="wrap_content"  
    android:layout_marginTop="4dp"  
    android:layout_marginLeft="4dp"  
    android:layout_marginRight="4dp"  
    android:layout_marginBottom="16dp"  
    android:fontFamily="sans-serif"  
    android:hint="Contraseña"/>
~~~

