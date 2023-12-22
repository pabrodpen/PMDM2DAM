# Elementos Gráficos

# ***RadioGroup***

![cap1.PNG](Elementos%20Gra%CC%81ficos%206f0b274b2c6f4f279e082b0ee40da881/cap1.png)

![cap2.PNG](Elementos%20Gra%CC%81ficos%206f0b274b2c6f4f279e082b0ee40da881/cap2.png)

![cap3.PNG](Elementos%20Gra%CC%81ficos%206f0b274b2c6f4f279e082b0ee40da881/cap3.png)

`RadioGroup r = findViewById(R.id.*radioGroup*);`

`r.setOnCheckedChangeListener(**new** RadioGroup.OnCheckedChangeListener() {`    

`@Override`    

**`public void** onCheckedChanged(RadioGroup group, **int** a) {`        

`TextView t = (TextView) findViewById(R.id.*textView*);`        

**`if** (a == R.id.*radioButton2*) { *// Talavera*`            

`t.setText(**"Buena elección!: El Talavera promete!!"**);`

        ``

// Marcar el RadioButton "Opción 2" por defecto
radioButton2.setChecked(true);

`} **else if** (a == R.id.*radioButton3*) { *// Alcazar*`            

`t.setText(**"Gran equipo la gimnástica!!"**);`        

`} **else if** (a == R.id.*radioButton1*) { *// Albacete*`            

`t.setText(**"El Albacete no es el mismo desde que se fue Iniesta"**);`        

`} **else if** (a == R.id.*radioButton4*) { *// Otros*`            

`t.setText(**"El dinero no lo es todo...."**);`        

`}`    

`}`

`});`

# Checkbox

![cap4.PNG](Elementos%20Gra%CC%81ficos%206f0b274b2c6f4f279e082b0ee40da881/cap4.png)

![cap5.PNG](Elementos%20Gra%CC%81ficos%206f0b274b2c6f4f279e082b0ee40da881/cap5.png)

![cap6.PNG](Elementos%20Gra%CC%81ficos%206f0b274b2c6f4f279e082b0ee40da881/cap6.png)

`CheckBox c=findViewById(R.id.*checkBox*);`

`c.setOnCheckedChangeListener(**new** CompoundButton.OnCheckedChangeListener() {`    

`@Override`    

**`public void** onCheckedChanged(CompoundButton compoundButton, **boolean** c) {`        

`TextView t=(TextView)findViewById(R.id.*textView2*);`        

**`if**(c)`            

`t.setText(**"Te gusta el fútbol!!"**);`        

**`else`**            

`t.setText(**"No te gusta el fútbol?!??!!"**);`    

`}`

`});`

![cap.PNG](Elementos%20Gra%CC%81ficos%206f0b274b2c6f4f279e082b0ee40da881/cap.png)

# ImageButton

Al pulsar el imageButton, se cambia la imagen del button y del imageView de abajo

![cap7.PNG](Elementos%20Gra%CC%81ficos%206f0b274b2c6f4f279e082b0ee40da881/cap7.png)

![cap8.PNG](Elementos%20Gra%CC%81ficos%206f0b274b2c6f4f279e082b0ee40da881/cap8.png)

`ImageButton button2=findViewById(R.id.*imageButton*);`

`button2.setOnClickListener(**new** View.OnClickListener() {`    

`@Override    **public void** onClick(View view) {`        

`TextView t=(TextView) findViewById(R.id.*textView3*);`        

`ImageView img=(ImageView) findViewById(R.id.*imageView*);`        

`t.setText(**"Llamando a Walter"**);`        

`img.setImageResource(R.drawable.*walter2*);`        

`button2.setImageResource(R.drawable.*tfno2*);`    

`}});`