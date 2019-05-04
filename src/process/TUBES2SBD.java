/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bian
 */
public class TUBES2SBD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {

        // Semua inisialisasi
        int menu = 1;
        Scanner input = new Scanner(System.in);
        ProcessOneTwo op12 = new ProcessOneTwo();
        String dataDictionary = "src/data/DataDictionary.txt";
        String line = "";
        String splitBy = ";";
        String[] tabelPB, tabelPembeli, tabelPembelian, tabelBarang, tempTable;
        List<String[]> allTableAL = new ArrayList<>();

        // read dataDictionary lalu masukan ke tabel semetara (allTable) yang akan di add ke arraylist seluruh table yang terbaca (allTableAL) *AL = ArrayList
        // aku bikin di main biar bisa dipake menu 3 juga
        try (BufferedReader br = new BufferedReader(new FileReader(dataDictionary))) {
            while ((line = br.readLine()) != null) {
                tempTable = line.split(splitBy);
                allTableAL.add(tempTable);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Mengkategorikan table dari allTableAL
        tabelPB = allTableAL.get(0);
        tabelPembeli = allTableAL.get(1);
        tabelBarang = allTableAL.get(2);
        tabelPembelian = allTableAL.get(3);

        // Ambil nilai P dan B dari tabelPB  
        int P = Integer.parseInt(tabelPB[0].split(" ")[1]);
        int B = Integer.parseInt(tabelPB[1].split(" ")[1]);
        // Pembeli
        int Rp = Integer.parseInt(tabelPembeli[4].split(" ")[1]);
        int np = Integer.parseInt(tabelPembeli[5].split(" ")[1]);
        int Vp = Integer.parseInt(tabelPembeli[6].split(" ")[1]);
        // Barang
        int Rb = Integer.parseInt(tabelBarang[4].split(" ")[1]);
        int nb = Integer.parseInt(tabelBarang[5].split(" ")[1]);
        int Vb = Integer.parseInt(tabelBarang[6].split(" ")[1]);
        // Pembelian
        int Rpn = Integer.parseInt(tabelPembelian[6].split(" ")[1]);
        int npn = Integer.parseInt(tabelPembelian[7].split(" ")[1]);
        int Vpn = Integer.parseInt(tabelPembelian[8].split(" ")[1]);

        
        // MAIN MENU
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
            if (menu == 1) {
                op12.tampil1(B, P, Rp, Vp, tabelPembeli);
                op12.tampil1(B, P, Rb, Vb, tabelBarang);
                op12.tampil1(B, P, Rpn, Vpn, tabelPembelian);
            } else if (menu == 2) {
                op12.tampil2(B, P, Rp, Vp, np, tabelPembeli);
                op12.tampil2(B, P, Rb, Vb, nb, tabelBarang);
                op12.tampil2(B, P, Rpn, Vpn, npn, tabelPembelian);
            } else if (menu == 3) {

            } else if (menu == 4) {

            } else if (menu == 5) {

            }
        }
    }

}
