# CardView

![card1.PNG](CardView%20a094722d47b4408db0ad4d2a5ca4e6ac/card1.png)

*`<?***xml version="1.0" encoding="utf-8"***?>*`

`<**LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"    xmlns:tools="http://schemas.android.com/tools"    xmlns:card_view="http://schemas.android.com/apk/res-auto"    android:layout_width="match_parent"    //ancho de la tarjeta**`

**`android:layout_height="match_parent"` //largo de la tarjeta**

`>`    

`<**androidx.cardview.widget.CardView**`        

**`android:id="@+id/cardView"`**        

**`android:layout_width="match_parent"`**        

**`android:layout_height="200dp"`**        

**`card_view:cardBackgroundColor="#fffffe91"   //color de la tarjeta`**     

**`android:layout_margin="8dp"** >`

`<**TextView        //TEXTO DEL TITULO DE LA TARJETA**`

**`android:id="@+id/txt2"`**        

**`android:layout_width="match_parent"`**        

**`android:layout_height="wrap_content"`**        

**`android:padding="10dp"`**        

**`android:text="Plaza de España"    //TEXTO DEL TITULO`**    

**`android:layout_gravity="top"//EL TITULO SE PONE ARRIBA DE LA TARJETA (bottom abajo)`**

**`android:background="#8c000000"       //COLOR DEL RECUADRO DEL TITULO`**

**`android:textColor="#ffe3e3e3"` //COLOR DE LA LETRA DEL TITULO**

**`android:textSize="30sp"`**        

**`android:textStyle="bold"` //EL TEXTO EN NEGRITA**

`/>`

`</**androidx.cardview.widget.CardView**></**LinearLayout**>`

![card2.PNG](CardView%20a094722d47b4408db0ad4d2a5ca4e6ac/card2.png)

*`<?***xml version="1.0" encoding="utf-8"***?>*`

`<**LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"    xmlns:tools="http://schemas.android.com/tools"    xmlns:card_view="http://schemas.android.com/apk/res-auto"    android:layout_width="match_parent"**`    

**`android:layout_height="match_parent"** >`    

`<**androidx.cardview.widget.CardView**`        

**`android:id="@+id/cardView"`**        

**`android:layout_width="match_parent"`**        

**`android:layout_height="200dp"`**        

**`android:layout_margin="8dp"** >`    //SIN COLOR EN LA TARJETA YA QUE LA TARJETA ES UNA IMGAGEN

`<**ImageView`        //IMAGEN COMO LA TARJETA**

**`android:layout_width="match_parent"`**        

**`android:layout_height="match_parent"`**        

**`android:src="@drawable/plaza_espania"`**        

**`android:scaleType="centerCrop"`**

`/>`    

`<**TextView`        //TEXTO COMO TITULO DE LA TARJETA**

**`android:id="@+id/txt2"`**        

**`android:layout_width="match_parent"`**        

**`android:layout_height="wrap_content"`**        

**`android:padding="10dp"`**        

**`android:text="Plaza de España"`**        

**`android:layout_gravity="top"`**        

**`android:background="#8c000000"`**        

**`android:textColor="#ffe3e3e3"`**        

**`android:textSize="30sp"`**        

**`android:textStyle="bold"`**

`/>`

`</**androidx.cardview.widget.CardView**></**LinearLayout**>`