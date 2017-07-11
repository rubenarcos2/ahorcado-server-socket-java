
import Utilidades.DatosPartida;
import gui.Ventana;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * SERVIDOR de comunicaciones --------------------------
 *
 * @author Rubén
 */
public class Servidor {

    private static final int PUERTO = 44444;
    private static final int MAX_CONEXIONES = 4;

    private static ArrayList<ServidorConexionUsuario> listaConexionesUsuarios;
    private static ArrayList<Socket> listaUsuarios;
    private static ServerSocket servidor;
    private static Ventana ventana;
    private static JButton[] listaBotones;
    private static JTextArea txtMensajesRecibidos;
    private static JTextField txtMensajeEnviado;
    private static JList listaUsuariosConectados;
    private static JComboBox cmbPalabras;
    private static String[] listaPalabras;

    private static String palabraSecreta;
    private static DatosPartida datosPartida;
    private static boolean partidaComenzada;

    public static void main(String[] args) {
        partidaComenzada = false;
        listaConexionesUsuarios = new ArrayList<>();
        listaUsuarios = new ArrayList<>();
        ventana = new Ventana();
        ventana.setVisible(true);
        ventana.getContentPane().setBackground(Color.pink);
        listaUsuariosConectados = ventana.getListaUsuarios();
        txtMensajesRecibidos = ventana.getTxtAreaMensajesRecibidos();
        txtMensajeEnviado = ventana.getTxtMensajesEnviados();
        cmbPalabras = ventana.getCmbPalabras();
        listaBotones = ventana.getListaBotones();

        /**
         * ******************************************************
         * LECTURA DEL LISTADO DE PALABRAS Y CARGA DEL MISMO
         * *******************************************************
         */
        try {
            listaPalabras = Utilidades.LeerPalabrasFichero.getListaPalabras();
        } catch (IOException ex) {
            System.out.println(Utilidades.Utils.getHoraFechaActual() + "ERROR al leer el listado de palabras: " + ex);
        }

        //Creación del combo con las palabras leídas del fichero
        cmbPalabras.addItem("Seleccione una palabra para jugar...");
        for (int i = 0; i < listaPalabras.length; i++) {
            cmbPalabras.addItem(listaPalabras[i]);
        }
        cmbPalabras.setEnabled(false);

        /**
         * ******************************************************
         * ASIGNACIÓN DE LOS EVENTOS DE LOS BOTONES DE LA VENTANA
         * *******************************************************
         */
        //Botón enviar mensaje
        listaBotones[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensaje("[SERVIDOR] " + txtMensajeEnviado.getText());
                txtMensajeEnviado.setText("");
            }
        });

        //Botón cerrar conexiones desde el servidor
        listaBotones[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    enviarMensaje("[SERVIDOR] Debido a problemas en el servidor, se cerrarán todas las conexiones.");
                    for (int i = 0; i < listaConexionesUsuarios.size(); i++) {
                        listaConexionesUsuarios.get(i).cerrarComunicacion();
                    }
                    servidor.close();
                    ventana.setDesconectado();
                    System.exit(0);
                } catch (IOException ex) {
                    System.out.println(Utilidades.Utils.getHoraFechaActual() + "ERROR en el cierre del servidor: " + ex);
                }
            }
        });

        //Botón Inicio/Parada del juego, eligiendo la palabra manualmente
        listaBotones[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Elegir palabra") || e.getActionCommand().equals("Elegir palabra / Parar partida")) {
                    cmbPalabras.setEnabled(true);
                    listaBotones[3].setEnabled(false);
                    listaBotones[1].setText("Comenzar partida");
                    datosPartida = null;
                    partidaComenzada = false;
                    enviarMensaje("[PARAR PARTIDA]");
                    enviarMensaje("[SERVIDOR] Se va ha cambiar la palabra de la partida,\n los datos y turnos actuales serán reiniciados");
                } else {
                    if (listaConexionesUsuarios.size() > 1) {
                        cmbPalabras.setEnabled(false);
                        listaBotones[3].setEnabled(true);
                        listaBotones[1].setText("Elegir palabra / Parar partida");
                        datosPartida = new DatosPartida();
                        String cadena = "";
                        palabraSecreta = cmbPalabras.getSelectedItem().toString();
                        for (int i = 0; i < palabraSecreta.length(); i++) {
                            cadena += "_";
                        }
                        try {
                            enviarListaUsuarios();
                        } catch (IOException ex) {
                            System.out.println(Utilidades.Utils.getHoraFechaActual() + "ERROR al enviar la lista de usuarios: " + ex);
                        }
                        datosPartida.setTurnoActual(listaConexionesUsuarios.get(0).getNombreUsuario());
                        datosPartida.setPalabra(cadena);
                        mostrarMensaje("Se jugará con la palabra: " + datosPartida.getPalabra());
                        try {
                            partidaComenzada = true;
                            enviarDatosPartida();
                            enviarMensaje("[INICIAR PARTIDA]");
                            enviarMensaje("[SERVIDOR] Se ha comenzado una partida nueva, ¡a jugar!");
                        } catch (IOException ex) {
                            System.out.println(Utilidades.Utils.getHoraFechaActual() + "ERROR en la actualización de los datos de la partida: " + ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(ventana, "Aún no hay usuarios conectados, espere hasta que haya al menos 2 conexiones");
                    }
                    ventana.pack();
                }
            }
        });

        //Botón Inicio/Parada del juego, eligiendo la palabra automáticamente
        listaBotones[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cmbPalabras.setSelectedIndex((int) (Math.random() * cmbPalabras.getItemCount()));
                listaBotones[3].setEnabled(false);
                listaBotones[3].setText("Seleccionar palabra aleatoria / Parar partida");
                listaBotones[1].setText("Comenzar partida");
                datosPartida = null;
                partidaComenzada = false;
                enviarMensaje("[PARAR PARTIDA]");
                enviarMensaje("[SERVIDOR] Se va ha cambiar la palabra de la partida,\n los datos y turnos actuales serán reiniciados");
                ventana.pack();
            }
        });
        mostrarMensaje("Debe elegir una palabra para comenzar el juego y la comunicación");
        conectar();
    }

    private static void conectar() {
        try {
            servidor = new ServerSocket(PUERTO);
            System.out.println(Utilidades.Utils.getHoraFechaActual() + "[SERVIDOR] Servidor iniciado...");
            ventana.setConectado();
            gestorConexiones();
        } catch (Exception e) {
            System.out.println(Utilidades.Utils.getHoraFechaActual() + "[SERVIDOR] ERROR en la creación del servidor: " + e);
        }
    }

    private static void gestorConexiones() throws IOException {
        System.out.println(Utilidades.Utils.getHoraFechaActual() + "[SERVIDOR] Esperando conexiones...");
        while (true) {
            //Aceptación de conexiones de usuarios en estado de espera, antes de la partida. Hasta máximo de conexiones.
            if (listaUsuarios.size() < MAX_CONEXIONES) {
                Socket usuario = new Socket();
                usuario = servidor.accept();//esperando cliente
                System.out.println(Utilidades.Utils.getHoraFechaActual() + "[SERVIDOR] Se ha aceptado la conexión con el usuario " + usuario.toString());
                ServidorConexionUsuario comunicacionUsuario = new ServidorConexionUsuario(usuario);
                comunicacionUsuario.start();

                //Denegación de nuevos usuarios con la partida comenzada.
                if (partidaComenzada) {
                    enviarMensajePrivado(comunicacionUsuario.getNombreUsuario(), "La partida está empezada, no se perminten conexiones en este momento.\nInténtelo más tarde.");
                    comunicacionUsuario.enviarMensaje("[DESCONECTAR]");
                    usuario.close();
                    usuario = null;
                } else {
                    listaUsuarios.add(usuario);
                    listaConexionesUsuarios.add(comunicacionUsuario);
                }

                //Denegación de usuarios por exceder el límite máximo de conexiones permitidas.
            } else {
                System.out.println(Utilidades.Utils.getHoraFechaActual() + "[SERVIDOR] Se ha llegado al máximo de conexiones permitidas, actualmenta hay " + listaUsuarios.size() + " usuarios conectados.");
                Socket usuario = new Socket();
                usuario = servidor.accept();//esperando cliente
                ServidorConexionUsuario comunicacionUsuario = new ServidorConexionUsuario(usuario);
                comunicacionUsuario.start();
                enviarMensajePrivado(comunicacionUsuario.getNombreUsuario(), "[SERVIDOR] El servidor ha rechazado la conexión, se ha superado el límite de conexiones.\nInténtelo más tarde....\nSe desconectará automáticamente.");
                System.out.println(Utilidades.Utils.getHoraFechaActual() + "[SERVIDOR] Se ha enviado la petición de desconexión al usuario. " + comunicacionUsuario.getNombreUsuario());
                comunicacionUsuario.enviarMensaje("[DESCONECTAR]");
                usuario.close();
                usuario = null;
            }
        }
    }

    private static void mostrarMensaje(String mensaje) {
        txtMensajesRecibidos.append(Utilidades.Utils.getHoraFechaActual() + mensaje + "\n");
    }

    //Envío de mensajes a todos los usuarios
    public static void enviarMensaje(String mensaje) {
        try {
            for (int i = 0; i < listaConexionesUsuarios.size(); i++) {
                listaConexionesUsuarios.get(i).enviarMensaje(mensaje);
            }
            mostrarMensaje(mensaje);
        } catch (IOException ex) {
            System.out.println(Utilidades.Utils.getHoraFechaActual() + "[SERVIDOR] ERROR en el envío del mensaje: " + ex);
        }
    }

    //Envío de mensajes al usuario especificado
    public static void enviarMensajePrivado(String usuario, String mensaje) {
        try {
            for (int i = 0; i < listaConexionesUsuarios.size(); i++) {
                if (listaConexionesUsuarios.get(i).getNombreUsuario().equals(usuario)) {
                    listaConexionesUsuarios.get(i).enviarMensaje(mensaje);
                    mostrarMensaje(mensaje);
                }
            }
        } catch (IOException ex) {
            System.out.println(Utilidades.Utils.getHoraFechaActual() + "[SERVIDOR] ERROR en el envío del mensaje privado: " + ex);
        }
    }

    //Envío de un objeto lista, con los usuarios conectados
    public static void enviarListaUsuarios() throws IOException {
        String[] listaUsuariosConect = new String[listaConexionesUsuarios.size()];
        for (int i = 0; i < listaConexionesUsuarios.size(); i++) {
            listaUsuariosConect[i] = listaConexionesUsuarios.get(i).getNombreUsuario();
        }
        for (int i = 0; i < listaUsuariosConect.length; i++) {
            listaConexionesUsuarios.get(i).enviarMensaje("[OBJETO]");
            listaConexionesUsuarios.get(i).enviarObjeto(listaUsuariosConect);
        }
        DefaultListModel listaModelo = new DefaultListModel();
        for (int i = 0; i < listaUsuariosConect.length; i++) {
            listaModelo.add(i, listaUsuariosConect[i]);
        }
        listaUsuariosConectados.setModel(listaModelo);
    }

    //Envio de un objeto DatosPartida con los datos de la misma (clase externa)
    public static void enviarDatosPartida() throws IOException {
        for (int i = 0; i < listaConexionesUsuarios.size(); i++) {
            listaConexionesUsuarios.get(i).enviarMensaje("[OBJETO]");
            listaConexionesUsuarios.get(i).enviarObjeto(datosPartida);
        }
    }

    public static void eliminarConexion(ServidorConexionUsuario conexion) throws IOException {
        for (int i = 0; i < listaConexionesUsuarios.size(); i++) {
            if (listaConexionesUsuarios.get(i).equals(conexion)) {
                listaConexionesUsuarios.remove(i);
                listaUsuarios.remove(i);
                System.out.println(Utilidades.Utils.getHoraFechaActual() + "Eliminado el usuario " + conexion.getNombreUsuario() + " hay " + listaConexionesUsuarios.size() + " usuarios activos.");
            }
        }
        enviarListaUsuarios();
    }

    public static void cambiarTurnoPartida() throws IOException {
        boolean cambiado = false;
        for (int i = 0; i < listaConexionesUsuarios.size(); i++) {
            if (listaConexionesUsuarios.get(i).getNombreUsuario().equals(datosPartida.getTurnoActual()) && !cambiado && partidaComenzada) {
                if (listaConexionesUsuarios.size() > 1 && i != listaConexionesUsuarios.size() - 1) {
                    datosPartida.setTurnoActual(listaConexionesUsuarios.get(i + 1).getNombreUsuario());
                    cambiado = true;
                } else if (i == listaConexionesUsuarios.size() - 1 && !cambiado) {
                    datosPartida.setTurnoActual(listaConexionesUsuarios.get(0).getNombreUsuario());
                    cambiado = true;
                }
            }
        }
    }

    //Gestiona le creación y actualización del objeto DatosPartida
    public static void setDatosPartida(DatosPartida datosPartidaNueva) throws IOException {
        datosPartida = datosPartidaNueva;
        String[] listaUsuariosConect = new String[listaConexionesUsuarios.size()];
        for (int i = 0; i < listaConexionesUsuarios.size(); i++) {
            listaUsuariosConect[i] = listaConexionesUsuarios.get(i).getNombreUsuario();
        }
        datosPartida.setListaOrdenTurno(listaUsuariosConect);
        if (!datosPartida.getConsultaLetra().isEmpty() || datosPartida.getConsultaLetra() != null) {
            if (datosPartida.getConsultaLetra().length() > 1) {
                comprobarPalabra(datosPartida.getConsultaLetra());
            } else if (datosPartida.getConsultaLetra().length() == 1) {
                comprobarLetra(datosPartida.getConsultaLetra());
            }
        }
    }

    //Comprobación de la resolución final de la palabra, tanto por letras sueltas, como por petición explícita de adivinación.
    public static boolean comprobarPalabra(String palabra) throws IOException {
        if (Utilidades.Utils.eliminarAcentos(palabra).equals(Utilidades.Utils.eliminarAcentos(palabraSecreta))) {
            datosPartida.setConsultaLetra("");
            datosPartida.setPalabra(palabraSecreta.toUpperCase());
            enviarDatosPartida();
            enviarMensaje("[PARAR PARTIDA FIN]");
            enviarMensaje("La palabra ha sido resuelta por el usuario: " + datosPartida.getTurnoActual());
            return true;
        } else {
            datosPartida.setConsultaLetra("");
            String usuarioError = datosPartida.getTurnoActual();
            cambiarTurnoPartida();
            enviarMensajePrivado(usuarioError, "[LETRA FALLIDA]");
            enviarDatosPartida();
            return false;
        }
    }

    //Comprobación de la consulta de una letra que contenga la palabra. (Botón consultar letra)
    public static void comprobarLetra(String letra) throws IOException {
        char[] listaLetrasPalabraSecreta = Utilidades.Utils.eliminarAcentos(palabraSecreta.toUpperCase()).toCharArray();
        char[] listaLetrasPalabraPartida = datosPartida.getPalabra().toUpperCase().toCharArray();
        String cadena = "";
        int encontrada = 0;
        for (int i = 0; i < listaLetrasPalabraSecreta.length; i++) {
            if (listaLetrasPalabraSecreta[i] == letra.charAt(0)) {
                listaLetrasPalabraPartida[i] = palabraSecreta.toUpperCase().charAt(i);
                encontrada++;
            }
            cadena += listaLetrasPalabraPartida[i];
        }
        if (encontrada > 0) {
            enviarMensaje("[SERVIDOR] la palabra contiene la " + letra + ", " + encontrada + " veces.");
            datosPartida.setPalabra(cadena);
            datosPartida.setConsultaLetra("");
            enviarMensajePrivado(datosPartida.getTurnoActual(), "[LETRA ACERTADA]");
        } else {
            enviarMensaje("[SERVIDOR] La letra " + letra + " no se encuentra en la palabra.");
            datosPartida.setLetrasFalladas(datosPartida.getLetrasFalladas() + letra);
            datosPartida.setConsultaLetra("");
            String usuarioError = datosPartida.getTurnoActual();
            cambiarTurnoPartida();
            enviarMensajePrivado(usuarioError, "[LETRA FALLIDA]");

        }
        enviarDatosPartida();
        if (cadena.equalsIgnoreCase(palabraSecreta)) {
            enviarMensaje("[SERVIDOR] ¡LA PALABRA HA SIDO ADIVINADA!");
            datosPartida.setConsultaLetra("");
            datosPartida.setPalabra(palabraSecreta.toUpperCase());
            enviarDatosPartida();
            enviarMensaje("[PARAR PARTIDA FIN]");
            enviarMensaje("[SERVIDOR] La palabra ha sido resuelta por el usuario: " + datosPartida.getTurnoActual());
        }
    }

}
