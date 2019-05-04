/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Bian
 */
public class TUBES2SBD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int menu = 1;
        
//        Semua inisialisasi
        String dictionary = "src/data/DataDictionary.txt";
        Scanner input = new Scanner(System.in);
        BfrFr op1 = new BfrFr();
        String[] tableSemua = new String[100];
        String line = "";
        
        try (BufferedReader br = new BufferedReader(new FileReader(dictionary))) {
            while (((line = br.readLine()) != null) && ((line = br.readLine()) != "#")) {
                System.out.println(line);
                System.out.println("");
                tableSemua = line.split(";");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        
        
        while ((menu >= 1) && (menu <= 5)) {
            System.out.println("");
            System.out.println(">> Main Menu");
            System.out.println("   1. Print BFR & Fan-out Ratio for each Table");
            System.out.println("   2. Print Total data blocks & Index blocks for each Table");
            System.out.println("   3. Calculate Total Block of Finding Record");
            System.out.println("   4. Create QEP and calculate Query cost");
            System.out.println("   5. Print the content of Shared Pool file");
            System.out.print(">> Insert menu: ");
            menu = input.nextInt();        
            if (menu == 1){
                op1.tesuto();
            } else if (menu == 2){

            } else if (menu == 3){

            } else if (menu == 4){

            } else if (menu == 5){

            }
        }
        
    }
    
}
