/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mastermahasiswapbo;


/**
 *
 * @author User
 */
public class koneksimastermahasiswa {
    public static void main(String[] args) {
        koneksii dbKoneksii = new koneksii();
        dbKoneksii.connect(); 

        
        if (dbKoneksii.getConnection() != null) {
          
            System.out.println("Operasi database dapat dilakukan.");
        }
    }
}
