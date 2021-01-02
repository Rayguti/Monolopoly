/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Tablero.Fichas;
import Tablero.Tablero;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel Cascante
 */
public class MonopolyCliente {
    public Socket socketRef;
    Tablero refPantalla;
    JLabel ficha;
    public ClientThread hiloCliente;

    public MonopolyCliente(Tablero refPantalla) {
        this.refPantalla = refPantalla;
        refPantalla.setRefCliente(this);
    }
    
    public void conectar(){
 
        try{
           
            socketRef = new Socket("localhost", 20000);
            //if(socketRef.isConnected()) System.out.println("Conectado");
            hiloCliente = new ClientThread(socketRef, refPantalla);
            hiloCliente.start();
            //System.exit(0);
            String nombre = JOptionPane.showInputDialog("Introduzca un Nick:");
            hiloCliente.writer.writeInt(1); //instruccion para el switch del thraed servidor
            hiloCliente.writer.writeUTF(nombre); //instruccion para el switch del thraed servidor
            refPantalla.setTitle(nombre);
            refPantalla.setUser(nombre);
            hiloCliente.writer.writeInt(5);
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        
        
    }

    public JLabel getFicha() {
        return ficha;
    }

    public void setFicha(JLabel ficha) {
        this.ficha = ficha;
    }
}
