/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

/**
 *
 * @author Daniel Cascante
 */
public class ServerThread extends Thread{
    private int id;
    private Socket socketRef;
    private DataInputStream reader;
    private DataOutputStream writer;
    private String nombre;
    private boolean running = true;
    MonopolyServer server;

    public ServerThread(Socket socketRef, MonopolyServer server, int id) throws IOException {
        this.socketRef = socketRef;
        reader = new DataInputStream(socketRef.getInputStream());
        writer = new DataOutputStream(socketRef.getOutputStream());
        this.server = server;
        this.id = id;
    }
    
    public void run (){
        
        int instruccionId = 1;
        while (running){
            try {
                instruccionId = reader.readInt(); // esperar hasta que reciba un entero
                
                switch (instruccionId){
                    case 1: // pasan el nombre del usuario
                        nombre = reader.readUTF();      
                        writer.writeInt(1);
                        writer.writeInt(id);
                        writer.writeInt(server.getTurno());
                        writer.writeUTF(server.conexiones.get(server.getTurno()).nombre);
                    break;
                    case 2: // pasan un mensaje por el chat
                        String mensaje = reader.readUTF();
                        
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ServerThread current = server.conexiones.get(i);
                            current.writer.writeInt(2);
                            current.writer.writeUTF(nombre);
                            current.writer.writeUTF(mensaje);
                        }
                        
                    break;
                    case 3: //LANZAR DADOS
                        
                        int primero = (new Random()).nextInt(6) + 1;
                        int segundo = (new Random()).nextInt(6) + 1;
                        //server.lanzamientoInicial[this.id] = primero + segundo;
                        //server.refPantalla.addMensaje(server.printArregloDados());
                        
                        /*writer.writeInt(3);
                        writer.writeUTF(nombre);
                        writer.writeInt(primero);
                        writer.writeInt(segundo);
                         */
                        int turno = server.getTurnoSiguiente();
                        String nombreDelTurno = server.conexiones.get(turno).nombre;
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            
                            ServerThread current = server.conexiones.get(i);
                            current.writer.writeInt(3);
                            current.writer.writeUTF(nombre);
                            current.writer.writeInt(primero);
                            current.writer.writeInt(segundo);
                            current.writer.writeInt(turno);
                            current.writer.writeUTF(nombreDelTurno);
                        }
                        
                    break;
                    case 4:
                       //server.iniciarPartida();
                       //for (int i = 0; i < server.conexiones.size(); i++) {
                            //ThreadServidor current = server.conexiones.get(i);
                            //current.writer.writeInt(4);
                            
                       //}
                       
                    break;
                    
                    
                }
            } catch (IOException ex) {
                
            }
        }
    }    
    
    
}
