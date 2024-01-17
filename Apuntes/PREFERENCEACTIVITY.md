 Android ofrece una forma alternativa a *SharedPreferences* de definir mediante XML un conjunto de opciones para una aplicación y crear por nosotros las pantallas necesarias para permitir al usuario modificarlas a su antojo.

![[Main 1.png]]
**MAIN**

![[Settings.png]]
**SETTINGS



##### SETTINGS JAVA

![[cap1.png]]
**onCreate(Bundle savedInstanceState):**

Este método se llama cuando la actividad está siendo creada.
super.onCreate(savedInstanceState) llama al método de la superclase para realizar las operaciones de inicialización estándar.
addPreferencesFromResource(R.xml.preferences) infla las preferencias desde el archivo XML llamado preferences.xml, estableciendo la interfaz de usuario de la actividad basada en este archivo.
PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this) registra un OnSharedPreferenceChangeListener para escuchar los cambios en las preferencias.


![[cap2.png]]
**onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key):**

Este método se llama cuando una preferencia cambia.
Si la preferencia que cambió es "pref_key_username", se llama al método updateUsernameSummary() para actualizar el resumen de esa preferencia.
Si la preferencia que cambió es "pref_key_dark_theme", se llama al método updateTheme() para actualizar el tema de la aplicación.


![[cap3 1.png]]
**updateUsernameSummary():**

Este método actualiza el resumen de la preferencia de nombre de usuario.
Obtiene una referencia a la preferencia de nombre de usuario.
Establece el resumen de la preferencia con el valor actual de la preferencia de nombre de usuario.


![[cap4.png]]
**updateTheme():**
Este método actualiza el tema de la aplicación según la preferencia "pref_key_dark_theme".
Obtiene el valor actual de la preferencia "pref_key_dark_theme" utilizando PreferenceManager.
Si el tema oscuro está habilitado, establece el modo noche en "MODE_NIGHT_YES"; de lo contrario, lo establece en "MODE_NIGHT_NO".
Llama a recreate() para recrear la actividad y aplicar el nuevo tema.



![[cap5.png]]
**onDestroy():**
Este método se llama cuando la actividad está siendo destruida.
PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this) elimina el registro del OnSharedPreferenceChangeListener para evitar posibles fugas de memoria al destruir la actividad.


##### MAIN JAVA
![[Main1.png]]
usernameTextView = findViewById(R.id.usernameTextView) obtiene una referencia al TextView con el id usernameTextView del diseño.
showCurrentUsername() se llama para mostrar el nombre de usuario actual en el TextView.



![[Main2.png]]
1. **openSettings(View view):**
    
    - Este método se llama cuando se hace clic en el botón "Abrir Configuraciones" en la interfaz de usuario.
    - Crea una nueva instancia de `Intent` para abrir la actividad `SettingsActivity`.
    - Llama a `startActivity(intent)` para iniciar la actividad de configuraciones.
2. **showCurrentUsername():**
    
    - Este método muestra el nombre de usuario actual en el `TextView` basado en el estado de la preferencia "pref_key_enable_feature".
    - Obtiene el valor de la preferencia "pref_key_enable_feature" utilizando `PreferenceManager`.
    - Si la función está habilitada, obtiene el nombre de usuario de la preferencia "pref_key_username" y lo muestra en el `TextView`.
    - Si la función no está habilitada, establece el texto del `TextView` en espacios en blanco.


##### PREFERENCES XML(res/xml/preferences.xml)
~~~
<PreferenceScreen xmlns:android="http://schemas.android.com/apk/res/android">  
  
    <PreferenceCategory  
        android:title="Configuración General">  
  
        <EditTextPreference  
            android:key="pref_key_username"  
            android:title="Nombre de Usuario"  
            android:summary="Introduce tu nombre de usuario"  
            android:inputType="text"  
            android:defaultValue="usuario"  
            />  
        <CheckBoxPreference  
            android:key="pref_key_enable_feature"  
            android:title="Habilitar Función"  
            android:summary="Habilitar o deshabilitar esta función"  
            android:defaultValue="false"  
            />  
        <CheckBoxPreference  
            android:key="pref_key_dark_theme"  
            android:title="Tema Oscuro"  
            android:summary="Habilitar el tema oscuro"  
            android:defaultValue="false"  
            />  
  
    </PreferenceCategory>  
  
    <PreferenceScreen  
        android:key="pref_key_back_to_main"  
        android:title="Volver a la pantalla principal"  
        android:icon="@drawable/volver"  
        android:summary="Haz clic para volver a la pantalla principal">  
  
        <intent  
            android:action="android.intent.action.MAIN"  
            android:targetPackage="com.example.preferencesactivityej"  
            android:targetClass="com.example.preferencesactivityej.MainActivity"  
            />  
    </PreferenceScreen>  
  
</PreferenceScreen>
~~~