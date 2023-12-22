# Intent Implícitos

Sirven para iniciar una nueva acción genérica

Intent más típicos:

INTENT PARA NAVEGAR EN UN SITIO WEB

**`url**=**"https://www.google.com/?hl=es"**;*// Crea un Intent con la acción ACTION_VIEW y la URL*`

`Intent intent = **new** Intent(Intent.***ACTION_VIEW***, Uri.*parse*(**url**));`

`startActivity(intent);`

INTENT PARA ABRIR LOS CONTACTOS

*`// Crea un Intent con la acción ACTION_VIEW y el URI de los contactos`*

`Intent intent = **new** Intent(Intent.***ACTION_VIEW***,`

`ContactsContract.Contacts.***CONTENT_URI***);`

`startActivity(intent);`

INTENT PARA INICIAR UNA LLAMADA A UN TFNO

*`// Número de teléfono al que deseas llamar`*

`String numeroTelefono = **"123456789"**;*// Crea un Intent con la acción ACTION_DIAL y el URI del número de teléfono*`

`Intent intent = **new** Intent(Intent.***ACTION_DIAL***);`

`intent.setData(Uri.*parse*(**"tel:"** + numeroTelefono));`

`startActivity(intent);`

INTENT PARA INICIAR UNA BUSQUEDA CON UNA CADENA

*`// Cadena de búsqueda en Google`*

`String busqueda = **"Android programming"**;*// Crea un Intent con la acción ACTION_WEB_SEARCH y la cadena de búsqueda*`

`Intent intent = **new** Intent(Intent.***ACTION_WEB_SEARCH***);`

`intent.putExtra(**"query"**, busqueda);`

`startActivity(intent);`

INTENT PARA COMANDO DE VOZ

*`// Crea un Intent con la acción ACTION_RECOGNIZE_SPEECH`*

`Intent intent = **new** Intent(RecognizerIntent.***ACTION_RECOGNIZE_SPEECH***);*// Establece el modelo de lenguaje y el número de resultados a obtener*`

`intent.putExtra(RecognizerIntent.***EXTRA_LANGUAGE_MODEL***, RecognizerIntent.***LANGUAGE_MODEL_FREE_FORM***);`

`startActivity(intent);`

TOASTS

`Toast.makeText(this, "Este es un mensaje Toast", Toast.LENGTH_SHORT).show();`