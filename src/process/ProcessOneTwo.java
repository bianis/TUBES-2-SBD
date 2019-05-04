/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Febiana
 */
public class ProcessOneTwo {

    // inisialisasi
    String dataDictionary = "src/data/DataDictionary.txt";
    String line = "";
    String splitBy = ";";
    String[] tabelPB, tabelPembeli, tabelPembelian, tabelBarang, tempTable;
    List<String[]> allTableAL = new ArrayList<>();

    public void BFRandFR() {
//      read dataDictionary lalu masukan ke tabel semetara (allTable) yang akan di add ke arraylist seluruh table yang terbaca (allTableAL) *AL = ArrayList
        try (BufferedReader br = new BufferedReader(new FileReader(dataDictionary))) {
            while ((line = br.readLine()) != null) {
                tempTable = line.split(splitBy);
                allTableAL.add(tempTable);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//      Mengkategorikan table dari allTableAL
        tabelPB = allTableAL.get(0);
        tabelPembeli = allTableAL.get(1);
        tabelBarang = allTableAL.get(2);
        tabelPembelian = allTableAL.get(3);

//      Ambil nilai P dan B dari tabelPB  
        int P = Integer.parseInt(tabelPB[0].split(" ")[1]);
        int B = Integer.parseInt(tabelPB[1].split(" ")[1]);

        System.out.println("");

//      Pembeli
        int Rp = Integer.parseInt(tabelPembeli[4].split(" ")[1]);
        int np = Integer.parseInt(tabelPembeli[5].split(" ")[1]);
        int Vp = Integer.parseInt(tabelPembeli[6].split(" ")[1]);
        System.out.println("BFR " + tabelPembeli[0] + " : " + hitungBfr(B, Rp));
        System.out.println("Fan Out Ratio " + tabelPembeli[0] + " : " + hitungFr(B, P, Vp));
        System.out.println("");

//      Barang
        int Rb = Integer.parseInt(tabelBarang[4].split(" ")[1]);
        int nb = Integer.parseInt(tabelBarang[5].split(" ")[1]);
        int Vb = Integer.parseInt(tabelBarang[6].split(" ")[1]);
        System.out.println("BFR " + tabelBarang[0] + " : " + hitungBfr(B, Rb));
        System.out.println("Fan Out Ratio " + tabelBarang[0] + " : " + hitungFr(B, P, Vb));
        System.out.println("");

//      Pembelian
        int Rpn = Integer.parseInt(tabelPembelian[6].split(" ")[1]);
        int npn = Integer.parseInt(tabelPembelian[7].split(" ")[1]);
        int Vpn = Integer.parseInt(tabelPembelian[8].split(" ")[1]);
        System.out.println("BFR " + tabelPembelian[0] + " : " + hitungBfr(B, Rpn));
        System.out.println("Fan Out Ratio " + tabelPembelian[0] + " : " + hitungFr(B, P, Vpn));
        System.out.println("");
    }

    public void dataAndIndexBlocks() {
        // inisialisasi
        String dataDictionary = "src/data/DataDictionary.txt";
        String line = "";
        String splitBy = ";";
        String[] tabelPB, tabelPembeli, tabelPembelian, tabelBarang, tempTable;
        List<String[]> allTableAL = new ArrayList<>();

//      read dataDictionary lalu masukan ke tabel semetara (allTable) yang akan di add ke arraylist seluruh table yang terbaca (allTableAL) *AL = ArrayList
        try (BufferedReader br = new BufferedReader(new FileReader(dataDictionary))) {
            while ((line = br.readLine()) != null) {
                tempTable = line.split(splitBy);
                allTableAL.add(tempTable);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//      Mengkategorikan table dari allTableAL
        tabelPB = allTableAL.get(0);
        tabelPembeli = allTableAL.get(1);
        tabelBarang = allTableAL.get(2);
        tabelPembelian = allTableAL.get(3);

//      Ambil nilai P dan B dari tabelPB  
        int P = Integer.parseInt(tabelPB[0].split(" ")[1]);
        int B = Integer.parseInt(tabelPB[1].split(" ")[1]);

        System.out.println("");
        
//      Pembeli
        int Rp = Integer.parseInt(tabelPembeli[4].split(" ")[1]);
        int np = Integer.parseInt(tabelPembeli[5].split(" ")[1]);
        int Vp = Integer.parseInt(tabelPembeli[6].split(" ")[1]);
        System.out.println("Tabel Data " + tabelPembeli[0] + " : " + countBlockData(B, Rp, np));
        System.out.println("Indeks " + tabelPembeli[0] + " : " + countBlockIndex(B, P, np, Vp));
        System.out.println("");

//      Barang
        int Rb = Integer.parseInt(tabelBarang[4].split(" ")[1]);
        int nb = Integer.parseInt(tabelBarang[5].split(" ")[1]);
        int Vb = Integer.parseInt(tabelBarang[6].split(" ")[1]);
        System.out.println("Tabel Data " + tabelBarang[0] + " : " + countBlockData(B, Rb, nb));
        System.out.println("Indeks " + tabelBarang[0] + " : " + countBlockIndex(B, P, nb, Vb));
        System.out.println("");

//      Pembelian
        int Rpn = Integer.parseInt(tabelPembelian[6].split(" ")[1]);
        int npn = Integer.parseInt(tabelPembelian[7].split(" ")[1]);
        int Vpn = Integer.parseInt(tabelPembelian[8].split(" ")[1]);
        System.out.println("Tabel Data " + tabelPembelian[0] + " : " + countBlockData(B, Rpn, npn));
        System.out.println("Indeks " + tabelPembelian[0] + " : " + countBlockIndex(B, P, npn, Vpn));
        System.out.println("");
    }

    public double hitungBfr(int B, int R) {
        return Math.floor(B / R);
    }

    public double hitungFr(int B, int P, int v) {
        return Math.ceil(B / (v + P));
    }

    public double countBlockData(int B, int R, int n) {
        return Math.floor(n / hitungBfr(B, R));
    }

    public double countBlockIndex(int B, int P, int n, int v) {
        return Math.floor(n / hitungFr(B, P, v));
    }
}
