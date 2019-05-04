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
 * @author Febiana
 */
public class BfrFr {

    // Path punya alief
    String dataDictionary = "src/data/DataDictionary.txt";
    String kendaraan = "C:\\Users\\user\\Desktop\\SOURCE SBD\\TUBES-SBD-master/src/file/kendaraan.csv";
    String transactions = "C:\\Users\\user\\Desktop\\SOURCE SBD\\TUBES-SBD-master/src/file/transactions.csv";
    String users = "C:\\Users\\user\\Desktop\\SOURCE SBD\\TUBES-SBD-master/src/file/users.csv";

    
    String line = "";
    String csvSplitBy = ";";
    String[] tableKendaraan = new String[100];
    String[] tableTransactions = new String[100];
    String[] tableUsers = new String[100];
    String[] tableInit = new String[100];
    
    public void BFRandFR() {
        //masukkan data pada csv kendaraan kedalam array tableKendaraan
        try (BufferedReader br = new BufferedReader(new FileReader(dataDictionary))) {
            while ((line = br.readLine()) != null) {
                tableInit = line.split(csvSplitBy);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 8; i++) {
            System.out.println(tableInit[i]);
        }
        
    }
   
    public void showUsers(String name, int B, int P, int R, int v) {
        double x = countBfr(B,R);
        double y = countFanoutRatio(B,P,v);
        System.out.println("BFR - "+name+" : "+x);
        System.out.println("Fan Out Ratio - "+name+" : "+y);
    }
    
    public void showTransactions(String name, int B, int P, int R, int v) {
        double x = countBfr(B,R);
        double y = countFanoutRatio(B,P,v);
        System.out.println("BFR - "+name+" : "+x);
        System.out.println("Fan Out Ratio - "+name+" : "+y);
    }
    
    public void showKendaraan(String name, int B, int P, int R, int v) {
        double x = countBfr(B,R);
        double y = countFanoutRatio(B,P,v);
        System.out.println("BFR - "+name+" : "+x);
        System.out.println("Fan Out Ratio - "+name+" : "+y);
    }
    
    public double countBfr(int B, int R){
        int x = B/R;
        double hasil = Math.floor(x);
        return hasil;
    }
    
    public double countFanoutRatio(int B, int P, int v) {
        return Math.ceil(B/(v+P));
    }
}
