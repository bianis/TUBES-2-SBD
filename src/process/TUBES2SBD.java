/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bian
 */
public class TUBES2SBD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {


     ///buat class untuk dapat menggunakan method didalamnya
        ProcessOneTwo op12 = new ProcessOneTwo();
        ProcessReadFile opRF = new ProcessReadFile();
        ProcessFour op4 = new ProcessFour();
        ProcessThree op3 = new ProcessThree();
        
        
        // Semua inisialisasi
        int menu = 1;
        Scanner input = new Scanner(System.in);
        List<List<String>> data = new ArrayList();
        List<String> dataPB, dataPembeli, dataBarang, dataPembelian;
        String in;
        data = opRF.readFile(); //read semua data tabel (langsung pake) --->isinya semua data tabel

//        ini buat cek data yang ada dalam tabel aja       
//        System.out.println("cek");
//        System.out.println(data.get(0));
//        System.out.println(data.get(1));
//        System.out.println(data.get(2));
//        System.out.println(data.get(3));
       
               
//        String dataDictionary = "src/data/DataDictionary.txt";
//        String line = "";
//        String splitBy = ";";
//        String[] tabelPB, tabelPembeli, tabelPembelian, tabelBarang, tempTable;
//        List<String[]> allTableAL = new ArrayList<>();
//
//        // read dataDictionary lalu masukan ke tabel semetara (allTable) yang akan di add ke arraylist seluruh table yang terbaca (allTableAL) *AL = ArrayList
//        // aku bikin di main biar bisa dipake menu 3 juga
//        try (BufferedReader br = new BufferedReader(new FileReader(dataDictionary))) {
//            while ((line = br.readLine()) != null) {
//                tempTable = line.split(splitBy);
//                allTableAL.add(tempTable);
//            }        
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        
              
        // Mengkategorikan table dari allTableAL
//        tabelPB = allTableAL.get(0);   
//        tabelPembeli = allTableAL.get(1);
//        tabelBarang = allTableAL.get(2);
//        tabelPembelian = allTableAL.get(3);
        dataPB = data.get(0);
        dataPembeli = data.get(1);
        dataBarang = data.get(2);
        dataPembelian = data.get(3);

        // Ambil nilai P dan B dari tabelPB  
//        int P = Integer.parseInt(tabelPB[0].split(" ")[1]);
//        System.out.println("p="+P);
//        int B = Integer.parseInt(tabelPB[1].split(" ")[1]);
//        System.out.println("b="+B);
        int P = Integer.parseInt(dataPB.get(0));
        int B = Integer.parseInt(dataPB.get(1));
        
        // Pembeli
//        int Rp = Integer.parseInt(tabelPembeli[4].split(" ")[1]);
//        int np = Integer.parseInt(tabelPembeli[5].split(" ")[1]);
//        int Vp = Integer.parseInt(tabelPembeli[6].split(" ")[1]);
        int Rp = Integer.parseInt(dataPembeli.get(4));
        int np = Integer.parseInt(dataPembeli.get(5));
        int Vp = Integer.parseInt(dataPembeli.get(6)); 
        
        // Barang
//        int Rb = Integer.parseInt(tabelBarang[4].split(" ")[1]);
//        int nb = Integer.parseInt(tabelBarang[5].split(" ")[1]);
//        int Vb = Integer.parseInt(tabelBarang[6].split(" ")[1]);
        int Rb = Integer.parseInt(dataBarang.get(4));
        int nb = Integer.parseInt(dataBarang.get(5));
        int Vb = Integer.parseInt(dataBarang.get(6));

        // Pembelian
//        int Rpn = Integer.parseInt(tabelPembelian[6].split(" ")[1]);
//        int npn = Integer.parseInt(tabelPembelian[7].split(" ")[1]);
//        int Vpn = Integer.parseInt(tabelPembelian[8].split(" ")[1]);
        int Rpn = Integer.parseInt(dataPembelian.get(6));
        int npn = Integer.parseInt(dataPembelian.get(7));
        int Vpn = Integer.parseInt(dataPembelian.get(8));

        
        

      
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
            menu = input.nextInt(); System.out.println("");
            System.out.println(">>Result:"); System.out.println("");
            if (menu == 1) { 
                op12.tampil1(B, P, Rp, Vp, dataPembeli);
                op12.tampil1(B, P, Rb, Vb, dataBarang);
                op12.tampil1(B, P, Rpn, Vpn, dataPembelian);
            } else if (menu == 2) {
                op12.tampil2(B, P, Rp, Vp, np, dataPembeli);
                op12.tampil2(B, P, Rb, Vb, nb, dataBarang);
                op12.tampil2(B, P, Rpn, Vpn, npn, dataPembelian);
            } else if (menu == 3) { 
                op3.SearchRecord(data);
            } else if (menu == 4) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Query: ");   
                in = sc.nextLine();
                String[] syntax = in.split(" ");
                List<String> initials = new ArrayList();
                if (op4.parserQuery(syntax, initials, data)) {
                    op4.printDataTable(initials);
                    op4.QEPandCost(in,initials, data);
                } else {
                    System.out.println("Syntax Error");
                }
            } else if (menu == 5) {

            }
        }
    }   
}
