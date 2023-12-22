
**EJEMPLO-->ListView de un objeto Elemento
PARA ELLO HAY QUE TENER EL JAVA DE LA LISTA, EL ELEMENTO Y EL ADAPTADOR
EL XML DE LA LISTA Y EL ELEMENTO**

# JAVA DEL ELEMENTO

Solo incluye los atributos,constructor Getters y Setters

# JAVA DEL ADAPTADOR

![[Captura 1.png]]

Le pasamos su constructor con el contexto y la lista
![[Captura2.png]]
**el método getView se encarga de inflar cada elemento y relacionarlo con el xml devolviendo la vista**
![[cap3.png]]

# JAVA DE LA LISTA 

~~~
ListView l;  
ArrayList<Elemento> itemList;  
ArrayAdapter<Elemento> adapter;
~~~
EN EL ONCREATE RELACIONAMOS EL LISTVIEW,EL ELEMENTO Y EL ADAPTADOR(INCLUIDO EL SETADAPTER)




### PARA AÑADIR UN ELEMENTO

(vemos los atributos de Elemento y su constructor)
~~~
itemList.add(new Elemento("Portatil",R.drawable.portatil,600));
~~~

### PARA BORRAR UN ELEMENTO 
~~~
itemList.remove(i);` ` ` 
adapter.notifyDataSetChanged();-->actualizamos el adaptador
~~~
