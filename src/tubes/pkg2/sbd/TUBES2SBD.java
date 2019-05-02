/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubes.pkg2.sbd;

import java.util.Scanner;

/**
 *
 * @author user
 */
public class TUBES2SBD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int pilihan;
        
        Scanner input = new Scanner(System.in);
        
        System.out.println(">> Main Menu");
        System.out.println("   1. Print BFR & Fan-out Ratio for each Table");
        System.out.println("   2. Print Total data blocks & Index blocks for each Table");
        System.out.println("   3. Calculate Total Block of Finding Record");
        System.out.println("   4. Create QEP and calculate Query cost");
        System.out.println("   5. Print the content of Shared Pool file");
        System.out.print(">> Insert menu: ");
        pilihan = input.nextInt();        
        if (pilihan == 1){
            System.out.println("   Coba 1");
        } else if (pilihan == 2){

        } else if (pilihan == 3){

        } else if (pilihan == 4){

        } else if (pilihan == 5){

        }
        
    }
    
}
