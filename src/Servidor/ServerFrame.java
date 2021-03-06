/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel Cascante
 */
public class ServerFrame extends javax.swing.JFrame {
    MonopolyServer serverRef;
    boolean partidaIniciada =false;
    /**
     * Creates new form ServerFrame
     */
    public ServerFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txaServerMsj = new javax.swing.JTextArea();
        btnIniciar = new javax.swing.JButton();
        lblAdmin = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txaServerMsj.setColumns(20);
        txaServerMsj.setRows(5);
        jScrollPane1.setViewportView(txaServerMsj);

        btnIniciar.setBackground(new java.awt.Color(0, 255, 0));
        btnIniciar.setText("Iniciar Partida");
        btnIniciar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        lblAdmin.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblAdmin.setText("Administrador:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(lblAdmin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 158, Short.MAX_VALUE)
                .addComponent(btnIniciar)
                .addGap(44, 44, 44))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIniciar, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                    .addComponent(lblAdmin))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        if(!partidaIniciada&&serverRef.conexiones.size()>1){
            JOptionPane.showMessageDialog(null, "La partida ha iniciado");
            serverRef.iniciarPartida();
            ServerThread aux;
            try{
                for(int i=0;i<serverRef.conexiones.size();i++) {
                    aux=serverRef.conexiones.get(i);
                    aux.writer.writeInt(4);//
                }
            }catch(IOException ex){
                JOptionPane.showMessageDialog(null, "Algo salio mal revisa SeverFrame");
            }
        }else
            JOptionPane.showMessageDialog(null, "Jugadores insuficientes");
    }//GEN-LAST:event_btnIniciarActionPerformed

    /**
     * @param args the command line arguments
     */
    
    void setSrv(MonopolyServer serverRef) {
        this.serverRef = serverRef;
    }
    
    public void addMsj(String msj){
        txaServerMsj.append(msj + "\n");
    }
    
    public void setAdmin(String srvName){
        lblAdmin.setText("Administrador: " + srvName);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAdmin;
    private javax.swing.JTextArea txaServerMsj;
    // End of variables declaration//GEN-END:variables
}
