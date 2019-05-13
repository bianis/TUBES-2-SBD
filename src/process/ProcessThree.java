/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author anasya
 */
public class ProcessThree {
    
    
    public static void SearchRecord(List<List<String>> dataTable) { //belom dikoreksi rumusnya, menu nomer 3
        Scanner sc = new Scanner(System.in);
        System.out.print("-- Cari Rekord ke- : ");
        int cari = sc.nextInt();
        System.out.print("-- Nama Tabel      : ");
        String tabel = sc.next();
        boolean benar = false; //buat cek inputan
//        if (tabel.matches("[a-zA-Z_]")) {
        for (int i = 1; i < dataTable.size(); i++) {
            if (tabel.equalsIgnoreCase(dataTable.get(i).get(0))) {
                double jumRekord = Float.parseFloat(dataTable.get(i).get(dataTable.get(i).size() - 2));
                double rekord = Float.parseFloat(dataTable.get(i).get(dataTable.get(i).size() - 3));
                double jumBlok = Math.ceil((jumRekord * rekord) / Float.parseFloat(dataTable.get(0).get(1))); // (jumlah rekord * size rekord)/block size = banyak blok tersedia
                double FOR = Math.floor(Float.parseFloat(dataTable.get(0).get(1)) / (Float.parseFloat(dataTable.get(i).get(dataTable.get(i).size() - 1)) + Float.parseFloat(dataTable.get(0).get(0)))); //fan-out ratio
                double BFR = (Math.ceil(Float.parseFloat(dataTable.get(0).get(1)) / Float.parseFloat(dataTable.get(i).get(dataTable.get(i).size() - 3))));
                // ceil(cari/bfr)
                double notIndexed = Math.ceil(cari / BFR);
                double indexed = Math.ceil(notIndexed / FOR) + 1; // banyak blok yang diakses lewat index (jumlah blok di main/fan-out)
                System.out.println("Menggunakan indeks, jumlah blok yang diakses : " + indexed);
                System.out.println("Tanpa indeks, jumlah blok yang diakses       : " + notIndexed);
                benar = true;
                break;
            }
        }
        if (benar == false){
         System.out.println("Invalid input");  
        }
           
    }
}