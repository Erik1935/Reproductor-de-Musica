/*Link de los archivos necesarios para el funcionamiento del programa
 http://www.mediafire.com/file/n0zmiyogmzy/MP3seth.rar/file
*Funcionamiento basico del programa
*Se utilizan los metodos: abrirFicheros, play, stop,pausa y continuar
*El metodo siguiente cancion verifica que la Pista actual halla terminado para reproducir la siguiente
*El metodo cancionAnteriorOSiguiente verifica si la lista llego al final, si esto es verdad, no pasa nada
en caso de que sea falso le suma 1 a la variable numeroDePista y reproduce la siguiente cancion, 
manda un 1 si la condicion es true
*En caso de atrazar las pistas pasa lo mismo solo que se verifica de que el indice no sea 0 ya que no existen indices negativos
de la misma forma envia un 1 en caso de que la condicion sea verdadera, al ser falso le resta 1 a la variable numeroDePista
*El metodo Opened observa e informa de cualquier movimiento que se haga, abrir archivo, pausar, detenerse etc.
Ademas establece los valores del componente JSlider
*El metodo progress va mostrando cada segundo el progreso de la cancion, mendiante una formula, ademas actualiza el valor actual
del JSlider
*El metodo repetir detiene la pista actual, abre la direccion actual y utiliza el metodo play para reiniciar la pista
*El metodo vinculoAUrl lo unico que se encarga es abrir youtube
*Cuando se agregan canciones a la lista de reproduccion, esta se borra completamente y se remplaza el contenido, se crea una
nueva lista................
*/
