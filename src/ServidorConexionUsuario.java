
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Comunicación individual servidor-cliente. Hilo de comunicaciones.
 * -----------------------------------------------------------------
 * 
 * @author Rubén
 */
public class ServidorConexionUsuario extends Thread {

    private final String DESCONEXION = "[DESCONECTAR]";
    private final String NOMBRE = "[NOMBRE]";
    private final String OBJETO = "[OBJETO]";

    private String nombreUsuario;
    private Socket usuario = null;
    private DataInputStream flujoEntrada;
    private DataOutputStream flujoSalida;
    private ObjectOutputStream flujoSalidaObjeto;
    private ObjectInputStream flujoEntradaObjeto;

    public ServidorConexionUsuario(Socket usuario) {
        try {
            nombreUsuario = "";
            this.usuario = usuario;
            //se crean flujos de entrada y salida
            flujoSalida = new DataOutputStream(usuario.getOutputStream());
            flujoEntrada = new DataInputStream(usuario.getInputStream());
            flujoSalidaObjeto = new ObjectOutputStream(usuario.getOutputStream());
            flujoEntradaObjeto = new ObjectInputStream(usuario.getInputStream());

        } catch (IOException e) {
            System.out.println(Utilidades.Utils.getHoraActual() + "[CONEXION SERVIDOR] ERROR en la creación de la comunicación con el usuario: " + usuario.toString() + ", error: " + e);
        }
    }

    @Override
    public void run() {
        System.out.println(Utilidades.Utils.getHoraActual() + "[CONEXION SERVIDOR] Se ha realizado la comunicación con el usuario");
        try {
            gestionarComunicacion();
        } catch (IOException e) {
            System.out.println(Utilidades.Utils.getHoraFechaActual() + "[CONEXION SERVIDOR] ERROR en la gestión de la comunicación: " + e);

        } catch (ClassNotFoundException ex) {
            System.out.println(Utilidades.Utils.getHoraFechaActual() + "[CONEXION SERVIDOR] ERROR interno en la gestión de la comunicación: " + ex);
        }
    }

    private void gestionarComunicacion() throws IOException, ClassNotFoundException {
        String mensajeRecibido = "";
        while (!mensajeRecibido.contains(DESCONEXION)) {
            mensajeRecibido = recibirMensaje();
            if (mensajeRecibido.contains(DESCONEXION)) {
                cerrarComunicacion();
            } else if (mensajeRecibido.contains(NOMBRE)) {
                enviarMensaje("[SERVIDOR] Bienvenido al chat...");
                nombreUsuario = mensajeRecibido.substring(mensajeRecibido.indexOf(NOMBRE) + NOMBRE.length(), mensajeRecibido.length());
                Servidor.enviarMensaje("[SERVIDOR] Se ha conectado " + nombreUsuario);
                Servidor.enviarListaUsuarios();
                Servidor.enviarDatosPartida();
            } else if (mensajeRecibido.contains(OBJETO)) {
                recibirObjeto();
            } else {
                System.out.println(Utilidades.Utils.getHoraFechaActual() + "[CONEXION SERVIDOR] -> " + mensajeRecibido);
                Servidor.enviarMensaje(mensajeRecibido);
            }
        }
    }

    private String recibirMensaje() throws IOException {
        return flujoEntrada.readUTF();
    }
    
    private void recibirObjeto() throws IOException, ClassNotFoundException {
        Object objeto = flujoEntradaObjeto.readObject();
        if (objeto instanceof Utilidades.DatosPartida) {
            Utilidades.DatosPartida datosPartidaNuevos = (Utilidades.DatosPartida) objeto;
            Servidor.setDatosPartida(datosPartidaNuevos);
        }
    }

    public void enviarMensaje(String mensaje) throws IOException {
        flujoSalida.writeUTF(mensaje);
    }

    public void enviarObjeto(Object objeto) throws IOException {
        flujoSalidaObjeto.writeObject(objeto);
    }

    public void cerrarComunicacion() throws IOException {
        System.out.println(Utilidades.Utils.getHoraFechaActual() + "[CONEXION SERVIDOR] Se ha recibido petición de desconexión del usuario: " + usuario.toString());
        Servidor.enviarMensaje("[SERVIDOR] Abandona el chat " + nombreUsuario);
        //Se cierran los flujos y el socket - Desconexión
        flujoSalida.close();
        flujoSalidaObjeto.close();
        flujoEntrada.close();
        flujoEntradaObjeto.close();
        usuario.close();
        System.out.println(Utilidades.Utils.getHoraFechaActual() + "[CONEXION SERVIDOR] Se ha realizado la desconexión con el usuario: " + usuario.toString());
        Servidor.eliminarConexion(this);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
}
