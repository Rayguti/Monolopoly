/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Tablero.Tablero;
/**
 *
 * @author Daniel Cascante
 */
public class MainCliente {
   public static void main(String[] args){
       try{
           //System.out.println("1");
           Tablero pantalla = new Tablero();
           MonopolyCliente client = new MonopolyCliente(pantalla);
           pantalla.setVisible(true);
           client.conectar();
       } catch(Exception e) {
       }
   } 
}
