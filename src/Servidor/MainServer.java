/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.IOException;

/**
 *
 * @author Daniel Cascante
 */
public class MainServer {
    public static void main(String[] args) throws IOException {
        ServerFrame  pantalla = new ServerFrame();
        MonopolyServer srv = new MonopolyServer(pantalla);
        pantalla.setVisible(true);
        srv.runServer();
        
    }
}
