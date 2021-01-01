/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Tablero.Fichas;
import Tablero.Tablero;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel Cascante
 */
public class ClientThread extends Thread{
    private Socket socketRef;
    public DataInputStream reader;
    public DataOutputStream writer;
    public ObjectInputStream readerObject;
    public ObjectOutputStream writerObject;
    private String nombre;
    private boolean running = true;
    private Tablero  refPantalla;
    private int id ;
    private int turnoActual = 0;
    private int dinero = 0;

    public ClientThread(Socket socketRef, Tablero refPantalla) throws IOException {
        this.socketRef = socketRef;
        reader = new DataInputStream(socketRef.getInputStream());
        writer = new DataOutputStream(socketRef.getOutputStream());
        readerObject = new ObjectInputStream(socketRef.getInputStream());
        writerObject = new ObjectOutputStream(socketRef.getOutputStream());
        this.refPantalla = refPantalla;
    }
    
    public void run (){
        
        int instruccionId = 1;
        while (running){
            try {
                instruccionId = reader.readInt(); // esperar hasta que reciba un entero
                
                switch (instruccionId){
                    case 1: // Nombre
                        this.id = reader.readInt();
                        this.turnoActual = reader.readInt();
                        refPantalla.setTurno(reader.readUTF());
                        /*if(this.id>=7){
                            System.out.println("entre");
                            JOptionPane.showMessageDialog(null, "No hay mas campos");
                            System.exit(0);
                        }*/
                    break;
                    case 2: // pasan un mensaje por el chat
                        String usuario = reader.readUTF();
                        String mensaje = reader.readUTF();
                        refPantalla.addMensaje(usuario+"> " + mensaje);
                    break;
                    case 3: // Lanzar Dados
                        String usuario2 = reader.readUTF();
                        int dado1 = reader.readInt();
                        int dado2 = reader.readInt();
                        this.turnoActual = reader.readInt();
                        refPantalla.setTurno(reader.readUTF());
                        refPantalla.lanzarDados(dado1, dado2);
                    break;
                    case 4:
                        this.dinero=1500;
                        refPantalla.setDinero(dinero);
                        writer.write(4);
                        String n;
                        int rango= reader.readInt();
                        for(int i=0;i<rango;i+=20){
                            n = reader.readUTF();
                            refPantalla.setLabelInGO(i,n); 
                        }
                        
                    break;
                    case 5:
                        boolean disponible =true;
                        Fichas f= new Fichas(refPantalla);
                        //System.out.println("1");
                        f.setVisible(true);
                        while(disponible){
                            disponible = reader.readBoolean();
                            if(!disponible) {
                                //agregar el label
                                
                                f.dispose();
                                this.nombre = reader.readUTF();
                                refPantalla.crearFicha(this.nombre);
                            }
                            else{
                                f.no_Disponible();
                            }
                        }
                    break;
                    case 6:
                        
                    break;
                        
                }
            } catch (IOException ex) {
                
            }
        }
    }
    public void writeIntUtf(int p,String n){
        try{
            writer.writeInt(p);
            writer.writeUTF(n);
         }catch(IOException e){}
    }

    public int getIdentificador() {
        return id;
    }

    public int getTurnoActual() {
        return turnoActual;
    }
}
