# TOAST y SNACKBAR

Toast.makeText(this, "Botón presionado", Toast.LENGTH_SHORT).show();

Snack.make(view, “Mensaje”, Snack.Toast.LENGTH_SHORT).show();

**`private void** showSnackbar(String s) {    Snackbar.*make*(findViewById(android.R.id.***content***),s,Snackbar.***LENGTH_SHORT***).show();}`