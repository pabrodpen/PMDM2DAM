# Navigation Drawer

![drawer.PNG](Navigation%20Drawer%20ec3b5952e7d6482e84c8d7fbbad7c759/drawer.png)

APP_BAR_MAIN.XML

*`<?***xml version="1.0" encoding="utf-8"***?>*`

`<**androidx.coordinatorlayout.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"    xmlns:app="http://schemas.android.com/apk/res-auto"    xmlns:tools="http://schemas.android.com/tools"**`    

**`android:layout_width="match_parent"`**    

**`android:layout_height="match_parent"`**    

**`tools:context=".MainActivity"**>`    

`<**com.google.android.material.appbar.AppBarLayout     //EL APPBAR**`   

**`android:layout_width="match_parent"`**        

**`android:layout_height="wrap_content"        android:theme="@style/Theme.NavigationDrawer.AppBarOverlay"**>`

`<**androidx.appcompat.widget.Toolbar`            //DENTRO DEL APPBAR EL TOOLBAR**

**`android:id="@+id/toolbar"`**            

**`android:layout_width="match_parent"`**            

**`android:layout_height="?attr/actionBarSize"`**            

**`android:background="?attr/colorPrimary"            app:popupTheme="@style/Theme.NavigationDrawer.PopupOverlay"** />` 

`</**com.google.android.material.appbar.AppBarLayout**>   //LA PARTE DE ENMEDIO` 

//EL BOTON FLOTANTE

`<**include layout="@layout/content_main"** />    <**com.google.android.material.floatingactionbutton.FloatingActionButton        android:id="@+id/fab"**`        

**`android:layout_width="wrap_content"`**        

**`android:layout_height="wrap_content"`**        

**`android:layout_gravity="bottom|end"`**        

**`android:layout_marginEnd="@dimen/fab_margin"`**        

**`android:layout_marginBottom="16dp"`**        

**`app:srcCompat="@android:drawable/ic_dialog_email"** /></**androidx.coordinatorlayout.widget.CoordinatorLayout**>`

![drawer2.PNG](Navigation%20Drawer%20ec3b5952e7d6482e84c8d7fbbad7c759/drawer2.png)

NAV_HEADER_MAIN.XML

*`<?***xml version="1.0" encoding="utf-8"***?>*`

`<**LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"    xmlns:app="http://schemas.android.com/apk/res-auto"**`    

**`android:layout_width="match_parent"`**    

**`android:layout_height="@dimen/nav_header_height"    android:background="@drawable/side_nav_bar"`**    

**`android:gravity="bottom"`**    

**`android:orientation="vertical"`**    

**`android:paddingLeft="@dimen/activity_horizontal_margin"    android:paddingTop="@dimen/activity_vertical_margin"    android:paddingRight="@dimen/activity_horizontal_margin"    android:paddingBottom="@dimen/activity_vertical_margin"    android:theme="@style/ThemeOverlay.AppCompat.Dark"**>`    

//IMAGEN DE ARRIBA DEL NOMBRE

`<**ImageView**`        

**`android:id="@+id/imageView"`**        

**`android:layout_width="wrap_content"`**        

**`android:layout_height="wrap_content"        android:contentDescription="@string/nav_header_desc"        android:paddingTop="@dimen/nav_header_vertical_spacing"        app:srcCompat="@mipmap/ic_launcher"`** 

`/>`    

NOMBRE

`<**TextView**`        

**`android:layout_width="match_parent"`**        

**`android:layout_height="wrap_content"        android:paddingTop="@dimen/nav_header_vertical_spacing"`**        

**`android:text="Pablo Rodriguez"        android:textAppearance="@style/TextAppearance.AppCompat.Body1"`** 

`/>`    

TEXTO DE ABAJO DEL NOMBRE

`<**TextView**`        

**`android:id="@+id/textView"`**        

**`android:layout_width="wrap_content"`**        

**`android:layout_height="wrap_content"`**        

**`android:text="hola que tal"** /></**LinearLayout**>`

EN EL APARTADO DESIGN DEL ACTIVITY_MAIN PULSAS SOBRE CADA SIMBOLO Y AL VOLVER AL APARTADO CODE PUEDES CAMBIAR TANTO EL TEXTO COMO EL SIMBOLO