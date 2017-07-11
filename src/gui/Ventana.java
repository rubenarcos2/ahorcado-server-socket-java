package gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Ventana del SERVIDOR de comunicaciones
 * --------------------------------------
 * @author Rubén
 */
public class Ventana extends javax.swing.JFrame {

    /**
     * Creates new form VentanaServidor
     */
    public Ventana() {
        initComponents();
        this.setTitle("Gestión del servidor de chat");
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaUsuarios = new javax.swing.JList();
        lblUsuarios = new javax.swing.JLabel();
        btnDesconectar = new javax.swing.JButton();
        btnEnviar = new javax.swing.JButton();
        txtMensajesEnviados = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaMensajesRecibidos = new javax.swing.JTextArea();
        lblChat = new javax.swing.JLabel();
        lblEstadoConexion = new javax.swing.JLabel();
        lblArchivos = new javax.swing.JLabel();
        lblArchivos1 = new javax.swing.JLabel();
        cmbPalabras = new javax.swing.JComboBox();
        btnCambiarPalabraAleatoria = new javax.swing.JButton();
        txtLetrasFalladas = new javax.swing.JTextField();
        lblLetrasFallidas = new javax.swing.JLabel();
        btnCambiarPalabra = new javax.swing.JButton();

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("frmVentanaServido"); // NOI18N

        listaUsuarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listaUsuarios.setName("listaUsuarios"); // NOI18N
        jScrollPane2.setViewportView(listaUsuarios);

        lblUsuarios.setText("Lista de usuarios conectados");
        lblUsuarios.setName("etiquetaListaUsuarios"); // NOI18N

        btnDesconectar.setText("Desconectar a todos y cerrar servidor");
        btnDesconectar.setActionCommand("btnDesconectar");
        btnDesconectar.setName("btnDesconectar"); // NOI18N

        btnEnviar.setText("Enviar");
        btnEnviar.setActionCommand("btnEnviar");
        btnEnviar.setName("btnEnviar"); // NOI18N

        txtMensajesEnviados.setName("cajaMensajesEnviados"); // NOI18N

        txtAreaMensajesRecibidos.setEditable(false);
        txtAreaMensajesRecibidos.setColumns(20);
        txtAreaMensajesRecibidos.setRows(5);
        txtAreaMensajesRecibidos.setFocusable(false);
        txtAreaMensajesRecibidos.setName("cajaMensajesRecibidos"); // NOI18N
        jScrollPane3.setViewportView(txtAreaMensajesRecibidos);

        lblChat.setText("Actividad del chat");
        lblChat.setName("etiquetaActividadChat"); // NOI18N

        lblEstadoConexion.setForeground(new java.awt.Color(102, 102, 102));
        lblEstadoConexion.setName("lblEstadoConexion"); // NOI18N

        lblArchivos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblArchivos.setText("Palabra a acertar:");
        lblArchivos.setName("etiquetaPalabra"); // NOI18N

        lblArchivos1.setText("Envío de mensaje a todos los usuarios");
        lblArchivos1.setName("etiquetaMesanjeEnviado"); // NOI18N

        cmbPalabras.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        cmbPalabras.setName("cmbPalabra"); // NOI18N

        btnCambiarPalabraAleatoria.setText("Seleccionar palabra aleatoria");
        btnCambiarPalabraAleatoria.setName("btnCambiarPalabraAleatoria"); // NOI18N

        txtLetrasFalladas.setEditable(false);
        txtLetrasFalladas.setName("cajaMensajesEnviados"); // NOI18N

        lblLetrasFallidas.setText("Letras falladas:");
        lblLetrasFallidas.setName("etiquetaMesanjeEnviado"); // NOI18N

        btnCambiarPalabra.setText("Elegir palabra");
        btnCambiarPalabra.setName("btnCambiarPalabra"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblEstadoConexion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(87, 87, 87)
                        .addComponent(lblUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnDesconectar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtLetrasFalladas, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbPalabras, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblArchivos1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblArchivos, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLetrasFallidas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMensajesEnviados, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3)
                            .addComponent(lblChat, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCambiarPalabra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCambiarPalabraAleatoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuarios)
                    .addComponent(lblChat))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane2))
                .addGap(4, 4, 4)
                .addComponent(lblArchivos1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEnviar)
                    .addComponent(txtMensajesEnviados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblArchivos)
                        .addGap(18, 18, 18)
                        .addComponent(cmbPalabras, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblLetrasFallidas))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCambiarPalabra, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCambiarPalabraAleatoria, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLetrasFalladas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(lblEstadoConexion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDesconectar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCambiarPalabra;
    private javax.swing.JButton btnCambiarPalabraAleatoria;
    private javax.swing.JButton btnDesconectar;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JComboBox cmbPalabras;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblArchivos;
    private javax.swing.JLabel lblArchivos1;
    private javax.swing.JLabel lblChat;
    private javax.swing.JLabel lblEstadoConexion;
    private javax.swing.JLabel lblLetrasFallidas;
    private javax.swing.JLabel lblUsuarios;
    private javax.swing.JList listaUsuarios;
    private javax.swing.JTextArea txtAreaMensajesRecibidos;
    private javax.swing.JTextField txtLetrasFalladas;
    private javax.swing.JTextField txtMensajesEnviados;
    // End of variables declaration//GEN-END:variables

    public JButton[] getListaBotones(){
        JButton[] listaBotones = new JButton[4];
        listaBotones[0] = btnEnviar;
        listaBotones[1] = btnCambiarPalabra;
        listaBotones[2] = btnDesconectar;
        listaBotones[3] = btnCambiarPalabraAleatoria;
        return listaBotones;
    }
    
    public JList getListaUsuarios(){
        return listaUsuarios;
    }
    
    public JTextArea getTxtAreaMensajesRecibidos(){
        return txtAreaMensajesRecibidos;
    }
    
    public JTextField getTxtMensajesEnviados(){
        return txtMensajesEnviados;
    }
    
    public JLabel getLblEstadoConexion(){
        return lblEstadoConexion;
    }
    
    public JComboBox getCmbPalabras(){
        return cmbPalabras;
    }
    
    public void setConectado(){
        lblEstadoConexion.setText("Estado del servidor: conectado");
    }
    
    public void setDesconectado(){
        lblEstadoConexion.setText("Estado del servidor: desconectado");
    }
}
