/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Tablero.Tablero;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel Cascante
 */
public class MonopolyCliente {
    Socket socketRef;
    Tablero refPantalla;
    public ClientThread hiloCliente;

    public MonopolyCliente(Tablero refPantalla) {
        this.refPantalla = refPantalla;
        refPantalla.setRefCliente(this);
    }
    
    public void conectar(){
 
        try{
            socketRef = new Socket("localhost", 35775);
            hiloCliente = new ClientThread(socketRef, refPantalla);
            hiloCliente.start();
            
            String nombre = JOptionPane.showInputDialog("Introduzca un Nick:");
            hiloCliente.writer.writeInt(1); //instruccion para el switch del thraed servidor
            hiloCliente.writer.writeUTF(nombre); //instruccion para el switch del thraed servidor
            refPantalla.setTitle(nombre);
            refPantalla.setUser(nombre);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        
        
    }
}
