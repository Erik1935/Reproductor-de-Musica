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
package metodos;
import java.awt.Desktop;
import javazoom.jlgui.basicplayer.BasicPlayer;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
/**
 *
 * @author erikj
 */
public class Reproductor implements BasicPlayerListener{
    private BasicPlayer player;
    private String [] Direcciones;
    private String [] Nombre;
    private int numeroDePista=0;
    private double bytesLength;
    private JLabel titulo;
    private JSlider avanzeCancion;
    private int Siguiente=0;
 public Reproductor(String arreglo[],JLabel Titulo,JSlider barra){
     player = new BasicPlayer();
     player.addBasicPlayerListener(this);
     this.Direcciones=arreglo;
     this.titulo=Titulo;
     this.avanzeCancion=barra;
    this.Nombre=ObtenerNombresdeCanciones(Direcciones);
     //--------------------------------------
    
 }
 
 public void AbrirFichero() throws Exception {
  player.open(new File(Direcciones[numeroDePista]));
}
 public void Play() throws Exception {
  titulo.setText(Nombre[numeroDePista]);
  player.play();
}
 public void Pausa() throws Exception {
  player.pause();
}

public void Continuar() throws Exception {
  player.resume();
}

public void Stop() throws Exception {
  player.stop();
}
public void SiguienteCancion(int numero){
    if(numero==Siguiente){
      if(numeroDePista==(Direcciones.length-1)){
          
      }else{
          try {
              //Stop();
              numeroDePista+=1;
              AbrirFichero();
              Play();
          } catch (Exception ex) {
              Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
          }
          
      }  
    }
}
//En base a la variable numero verifica si no llego al final de la lista o al inicio, para no pasar el rango
public int CancionAnteriorOsiguiente(int numero){
        int respuesta = 0;
        if (numero == 1) {
            //Final de la lista
            if (numeroDePista == Direcciones.length - 1) {
                respuesta = 1;
            } else {
                 try {
                    Stop();
                    numeroDePista += 1;
                    //AbrirFichero();
                    //Play();
                    titulo.setText(Nombre[numeroDePista]);
                    respuesta = 0;
                } catch (Exception ex) {
                    Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } else {
            //Inicio de la lista
            if (numero == 0) {
                if (numeroDePista == 0) {
                    respuesta = 1;
                } else {
                    try {
                        System.out.println("N Pista_____ "+numeroDePista);
                        respuesta = 0;
                        Stop();
                        numeroDePista-=1;
                        //Play();
                        titulo.setText(Nombre[numeroDePista]);
                    } catch (Exception ex) {
                        Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

        }
        return respuesta;
    }
//Repite la cancion que esta sonando actualmente
public void Repetir(){
        try {
             System.out.println("Repetir numero "+numeroDePista);
            Stop();
            AbrirFichero();
            Play();
        } catch (Exception ex) {
            Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
        }
 
}
  public String[] ObtenerNombresdeCanciones(String[]arreglo){
      String[]NombreCompleto = new String[arreglo.length];
      for(int i=0;i<arreglo.length;i++){
      File nombres = new File(arreglo[i]);
       NombreCompleto[i]=nombres.getName();
    //Obtiene el nombre del archivo y lo guarda en un arreglo aparte......
              }
      return NombreCompleto;
  }
//Abre el link hacia Youtube
public void VinculoAURL(){
        try {
            
                Desktop.getDesktop().browse(new URI("https://www.youtube.com/"));
            } catch (IOException ex) {
                Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
            }
         catch (URISyntaxException ex) {
            Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    @Override
    public void opened(Object o, Map map) {
       if (map.containsKey("audio.length.bytes")) {
           ///Esta linea muestra hasta donde llega la cancion(El final)
  bytesLength = Double.parseDouble(map.get("audio.length.bytes").toString());
           System.out.println("Final "+bytesLength);
           int ProgresoMaximo =(int)(bytesLength);
           Siguiente=ProgresoMaximo;
          avanzeCancion.setMinimum(0);
          avanzeCancion.setMaximum(ProgresoMaximo);
    }

}
//Metodo que muestra el progreso de la pista de audio
    @Override
    public void progress(int i, long l, byte[] bytes, Map map) {
        float progressUpdate = (float) (i * 1.0f / bytesLength * 1.0f);
 int progressNow = (int) (bytesLength * progressUpdate);
 // Descomentando la siguiente línea se mosrtaría el progreso
 //Esta linea muestra el progreso de la cancion---------
 //System.out.println(" -&gt; " + progressNow);
 avanzeCancion.setValue(progressNow);
 SiguienteCancion(progressNow);//METODO PROBLEMATICO....................
    }

    @Override
    public void stateUpdated(BasicPlayerEvent bpe) {
        
       
    }

    @Override
    public void setController(BasicController bc) {
       //Un identificador para BasicPlayer, los complementos pueden controlar el reproductor a través del controlador (reproducir, detener, ...)
       
    }
}
