/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel Cascante
 */
public class MonopolyServer {
    ServerFrame refPantalla;
    public ArrayList<ServerThread> conexiones;
    private boolean running = true;
    private ServerSocket srv;
    private int turno;
    private boolean partidaIniciada = false;
    public int lanzamientoInicial[] = new int[6];
    public boolean[] fichasDiponibles=new boolean[8];

    public MonopolyServer(ServerFrame refPantalla) {
        this.refPantalla = refPantalla;
        conexiones = new ArrayList<ServerThread>();
        this.refPantalla.setSrv(this);
        for (int i = 0; i < fichasDiponibles.length; i++) {
            fichasDiponibles[i]=false;
        }
    }
    
    
    
    public String printArregloDados(){
        String str = "Arreglo:  ";
        for (int i = 0; i < conexiones.size(); i++) {
            str += lanzamientoInicial[i] + "   ";
        }
        return str;
    }
    
    public void iniciarPartida(){
        this.partidaIniciada = true;
    }
    
    public int getTurnoSiguiente(){
        if (++turno >= conexiones.size())
            turno = 0;
        
        return turno;
    }

    public int getTurno() {
        return turno;
    }
    
    
    
    public void stopserver(){
        running = false;
    }
    
    public void runServer() throws IOException{
        
        int contador = 0;
        srv = new ServerSocket(35775);
        String nombre = JOptionPane.showInputDialog("Introduzca Nombre de Administrador:");
        refPantalla.setAdmin(nombre);
        
        while(running){
            refPantalla.addMsj(".: Esperando conexiones");
            Socket refSocket = srv.accept();
            
            if(!partidaIniciada){
                refPantalla.addMsj(".: Conexion realizada: " + (++contador));

                // Thread
                ServerThread newThread = new ServerThread(refSocket, this, conexiones.size());
                conexiones.add(newThread);
                newThread.start();
            }
            else
                refPantalla.addMsj(".: Conexion denegada, partida ya inicio");
        }
    }
    
}
