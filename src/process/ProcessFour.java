/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Mayaaaaaaa
 */
public class ProcessFour {
       
    List<List<String>> usedData = new ArrayList();
    List<List<String>> dataQEP = new ArrayList();

    
    public boolean parserQuery(String[] textquery, List<String> initials, List<List<String>> dataTable) throws IOException {
        boolean syntax = false;
        if (textquery.length > 3) {
            if (textquery[textquery.length - 1].charAt(textquery[textquery.length - 1].length() - 1) == ';') { // cek untuk ';' diakhir
                textquery[textquery.length - 1] = textquery[textquery.length - 1].substring(0, textquery[textquery.length - 1].length() - 1); // menghilangkan indeks terakhir pada kata[kata.length-1]
                if (textquery[0].toLowerCase().equals("select")) { //jika kata pertama select
                    if (textquery[2].toLowerCase().equals("from")) { //jika kata ketiga from
                        int i = 3;
                        syntax = true;
                        boolean tempSyntax = true; // digunakan ketika membaca join
                        boolean initialsCheck = false; //expect selanjutnya adalah inisial tabel, contoh:"mahasiswa m"
                        boolean joinState = false; //untuk cek kalo kata selanjutnya "join"
                        boolean whereState = false;
                        boolean join = false;
                        boolean where = false;
                        while (i < textquery.length && syntax){  //loop kata sepanjang i
                            if (!textquery[i].equalsIgnoreCase("join") && !textquery[i].equalsIgnoreCase("on") && !textquery[i].equalsIgnoreCase("using") && !textquery[i].equalsIgnoreCase("where")) { //cek kalo bukan join atau on
                                if (whereState) {
                                    String[] kataWhere = Arrays.copyOfRange(textquery, i, textquery.length);
                                    syntax = parserWhere(kataWhere, dataTable, initials);
                                    i = textquery.length - 1;
                                } else if (initialsCheck == true) { //jika ditemukan inisial tabel setelah nama tabel
                                    initials.add(textquery[i]); //tambahkan ke list initial
                                    initialsCheck = false; //kembalikan ke value semula 

                                } else { //jika kata selanjutnya bukan initial
                                    syntax = parserTabel(textquery[i], dataTable); //lakukan parserTabel yang mengembalikan boolean
                                    initialsCheck = true; //telah dilakukan parserTable maka initial check diisi true
                                    initials.add(textquery[i]); //tambahkan nama tabel ke list initial
                                    if (textquery.length - 1 == i) {
                                        initials.add(""); //kalau inisial tabel tidak ada tambahkan string kosong 
                                    }
                                }
                            } else if (textquery[i].equalsIgnoreCase("join")) { //jika kata indeks ke-i adalah join tanpa peduli uppercase atau lowercase
                                if (initialsCheck == true) { //jika ditemukan inisial tabel setelah nama tabel
                                    initials.add(" "); //tambahkan space di initial
                                    initialsCheck = false; //kembalikan initial check ke value semula
                                }
                                if (joinState == true) { //jika hanya ada join tanpa on maka joinState true
                                    syntax = false; //karna tidak ada "on" maka query dianggap salah
                                } 
                                joinState = true;
                                tempSyntax = false; //jika tidak ditemukan join maka tempSyntax false
                                join = true;
                              } else if (textquery[i].equalsIgnoreCase("on")) { //jika kata ke-i merupakan "on"
                                if (initialsCheck == true) { //jika ditemukan inisial tabel setelah nama tabel
                                    initials.add(" "); //tambahkan whitespace ke list initial
                                    initialsCheck = false; //kembalikan ke semula
                                } else {
                                    System.out.println("SQL Error: Not found defined attribute in table");
                                }
                                i++; //list kata maju
                                if (i < textquery.length) { //jika belum mencapai indeks terakhir dari list kata
                                    for (int j = 0; j < initials.size() / 2 + 1; j++) { //looping untuk mendapat nama tabel
                                        List<String> templist = new ArrayList();
                                        usedData.add(templist); //append templist ke usedData untuk print
                                    }
                                    syntax = parserOn(textquery[i], dataTable, initials); //lakukan parserOn yang mengembalikan boolean
                                    usedData.clear(); //hapus isi dari usedData
                                } else { //jika list kata sudah mencapai indeks terakhir
                                    syntax = false;
                                    System.out.println("Error");//syntaks dianggap false
                                }
                                joinState = false; //assign joinstate ke false 
                                tempSyntax = true; //jika ditemukan join maka tempSyntax true
                            } else if (textquery[i].equalsIgnoreCase("using")) {
                                i++;
                                syntax = parserUsing(textquery[i], dataTable, initials);
                                tempSyntax = true;
                            } else if (textquery[i].equalsIgnoreCase("where")) {
                                whereState = true;
                                where = true;
                            }
                            i++; //list kata maju
                        }
                        if (syntax) { //jika syntax masih benar
                            for (i = 1; i <= initials.size() + 1 / 2; i++) { //loop sebanyak size dari list initial dibagi 2 untuk mendapat nama tabel
                                List<String> templist = new ArrayList();
                                usedData.add(templist);
                            }
                            String[] temp; //define list temp
                            temp = textquery[1].split(","); //tampung nama kolom atau kata apapun yang sudah dipisahkan berdasar "," (koma)
                            for (i = 0; i < temp.length; i++) { //loop sebanyak list temp 
                                syntax = parserKolom(temp[i], dataTable, initials); //lakukan parserKolom dari kata yang tertampung di list temp
                                if (!syntax) { //jika parserKolom mengembalikan false karna query salah
                                    break; //hentikan for karna dianggap query salah
                                }
                            }
                        }
                        if (!tempSyntax) { //jika tidak ditemukan join 
                            syntax = false; //query salah
                        }
                    }
                }
            }
        }
        return syntax;
    }

    public boolean parserWhere(String[] kata, List<List<String>> dataTable, List<String> initial) {
        if(kata[1].equals("=")){
            List<String> temp = new ArrayList();
            for (int j = 0; j < initial.size(); j = j + 2) {
                for (int k = 0; k < dataTable.size(); k++) {
                    if (dataTable.get(k).get(0).equals(initial.get(j))) {
                        if (dataTable.get(k).indexOf(kata[0]) != -1) {
                            for (int i = 0; i < kata.length; i++) {
                                temp.add(kata[i]);
                            }
                            usedData.add(temp);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean parserUsing(String kata, List<List<String>> dataTable, List<String> initial) {
        kata = kata.substring(1, kata.length() - 1);
        int jmlcheck = 0;

        for (int i = 0; i < initial.size(); i = i + 2) {
            int a = 0;
            for (int l = 0; l < dataTable.size(); l++) {
                a = dataTable.get(l).indexOf(kata);
                if (a == 1) {
                    jmlcheck++;
                    break;
                }
            }
        }
        if (jmlcheck == 2) {
            return true;
        }

        return false;
    }

    public boolean parserOn(String kata, List<List<String>> dataTable, List<String> initial) {
        String[] pisahtitik = null;
        String[] temp;
        ArrayList<String> temp2 = new ArrayList();
        kata = kata.substring(1, kata.length() - 1);
        temp = kata.split("=");

        if (temp.length == 2) {
            for (int i = 0; i < temp.length; i++) {
                if (parserKolom(temp[i].toString(), dataTable, initial)) {
                    pisahtitik = temp[i].split("\\.");
                    if (pisahtitik.length == 2) {
                        temp2.add(pisahtitik[0]);
                    } else {
                        return false;
                    }
                }
            }
            if (temp2.size() == 2) {
                if (!temp2.get(0).equals(temp2.get(1))) {
                    return true;
                }
            }
        }
        return false;
    }

    
    public boolean parserKolom(String kata, List<List<String>> dataTable, List<String> initial) { //dipakai kalo dipanggil nama kolomnya
        String[] temp;
        temp = kata.split("\\.");
        boolean check = false;
        if (temp.length == 2) {
            int j = initial.indexOf(temp[0]);
            if (j == -1) {
                return false;
            }
            for (int k = 0; k < dataTable.size(); k++) {
                if (dataTable.get(k).get(0).equals(initial.get(j - 1))) {
                    if (dataTable.get(k).indexOf(temp[1]) != -1) {
                        usedData.get((j - 1) / 2 + 1).add(temp[1]);
                        check = true;
                    }
                }
            }
        } else {
            for (int j = 0; j < initial.size(); j = j + 2) {
                for (int k = 0; k < dataTable.size(); k++) {
                    if (dataTable.get(k).get(0).equals(initial.get(j))) {
                        if (dataTable.get(k).indexOf(temp[0]) != -1) {
                            usedData.get(((j) / 2) + 1).add(temp[0]);
                            check = true;
                        }
                    }
                }
            }
        }
        
        if (check) {
            return true;
        }
        return false;
    }

    public boolean parserTabel(String kata, List<List<String>> csv) {
        for (int i = 0; i < csv.size(); i++) { // cari tabel
            if (kata.equals(csv.get(i).get(0))) {
                return true;
            }
        }
        return false;
    }


    public boolean checkSharedPool(String query) throws FileNotFoundException, IOException {
        File file = new File("src/data/Shared Pool.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            if (query.equals(st)) {
                System.out.println("QEP from Shared Pool File: ");
                while (!(st = br.readLine()).equals("")) {
                    System.out.println(st);
                }
                return true;
            }
        }
        return false;
    }
    
    
    public void QEPandCost(String inQuery, List<String> initial, List<List<String>> dataTable) throws IOException {
        boolean onkey = false;

        List<Double> cost = new ArrayList();
        String selection;
        String projection;
        String join;
        String table;
        String strCost;

        if (checkSharedPool(inQuery)) {
            System.out.println("");
        } else {
            if (initial.size() < 3) {
                // untuk query tanpa join
                int tabel = -1;
                for (int it = 1; it < dataTable.size(); it++) {
                    if (initial.get(0).equalsIgnoreCase(dataTable.get(it).get(0))) {
                        tabel = it;
                    }
                }
                if (tabel > -1 && !usedData.get(0).isEmpty()) {
                    // jumlah block
                    System.out.println("");
                    double Br = Math.ceil(Math.ceil(Float.parseFloat(dataTable.get(tabel).get(dataTable.get(tabel).size() - 3)) * Float.parseFloat(dataTable.get(tabel).get(dataTable.get(tabel).size() - 2))) / Float.parseFloat(dataTable.get(0).get(1)));
                    for (int i = 1; i <= 2; i++) {
                        selection = "SELECTION ";
                        projection = "PROJECTION ";
                        table = "";
                        strCost = "Cost: ";
                        List<String> tempDataQEP = new ArrayList();
                        System.out.println("QEP #" + i);
                        System.out.print("PROJECTION ");
                        for (int j = 0; j < usedData.get(1).size(); j++) {
                            projection += usedData.get(1).get(j) + ", ";
                            System.out.print(usedData.get(1).get(j) + ", ");
                        }
                        System.out.print("-- on the fly");
                        projection += "-- on the fly";
                        boolean where = false;
                        if (!usedData.get(0).isEmpty()) { // usedData.get(0) atribut where yg dipake
                            where = true;
                            selection += usedData.get(0).get(0) + usedData.get(0).get(1) + usedData.get(0).get(2) + " -- A" + i;
                            System.out.print("\nSELECTION " + usedData.get(0).get(0) + usedData.get(0).get(1) + usedData.get(0).get(2));
                            System.out.print(" -- A" + i);
                        }
                        if (i == 1 && where) {
                            for (int j = 1; j < dataTable.size(); j++) {
                                if (usedData.get(0).get(0).equals(dataTable.get(j).get(1))) {
                                    selection += " key";
                                    System.out.print(" key");
                                    onkey = true;
                                }
                            }
                                    
                        }
                        table = initial.get(0);
                        System.out.println("\n" + initial.get(0));
                        System.out.print("Cost: ");
                        if (i == 1) {
                            if (onkey) {
                                // br/2
                                cost.add(Math.ceil(Br / 2));
                                strCost += Double.toString(Math.ceil(Br / 2));
                            } else if (!onkey) {
                                // br
                                cost.add(Br);
                                strCost += Double.toString(Math.ceil(Br));
                            }
                        }
                        if (i == 2) {
                            double FOR = Math.floor(Float.parseFloat(dataTable.get(0).get(1)) / (Float.parseFloat(dataTable.get(i).get(dataTable.get(i).size() - 1)) + Float.parseFloat(dataTable.get(0).get(0))));
                            double hi = Math.ceil(Math.log10(Float.parseFloat(dataTable.get(0).get(1)) / Math.log10(FOR)));
                            cost.add(hi + 1);
                            strCost += Double.toString(hi + 1);
                        }
                        strCost += " Blocks";
                        System.out.print(cost.get(i - 1) + " Blocks\n");
                        System.out.println("");

                        tempDataQEP.add(inQuery);
                        tempDataQEP.add(projection);
                        if (where) {
                            tempDataQEP.add(selection);
                        }
                        tempDataQEP.add(table);
                        tempDataQEP.add(strCost);
                        dataQEP.add(tempDataQEP);
                    }
                }
            } else if (usedData.get(0).isEmpty() && initial.size() > 2) {
                List<Integer> usedTable = new ArrayList();
                List<String> alreadyPrinted = new ArrayList(); // list yg menyimpan data yang sudah diambil
                for (int h = 0; h < 3; h = h + 2) {
                    for (int i = 1; i < dataTable.size(); i++) {
                        if (initial.get(h).equalsIgnoreCase(dataTable.get(i).get(0))) {
                            usedTable.add(i);
                        }
                    }
                }
                for (int i = 1; i <= 2; i++) {
                    List<String> tempDataQEP = new ArrayList();
                    projection = "PROJECTION ";
                    join = "JOIN ";
                    table = "";
                    strCost = "Cost: ";

                    System.out.println("QEP #" + i);
                    System.out.print("PROJECTION ");
                    if (i == 1) {
                        for (int j = 1; j <= 2; j++) {
                            for (int k = 0; k < usedData.get(j).size(); k++) {
                                boolean found = false; // found in alreadyPrinted
                                for (int l = 0; l < alreadyPrinted.size(); l++) {
                                    if (alreadyPrinted.get(l).equals(usedData.get(j).get(k))) {
                                        found = true;
                                    }
                                }
                                if (!found) {
                                    System.out.print(usedData.get(j).get(k) + ", ");
                                    projection += usedData.get(j).get(k) + ", ";
                                    alreadyPrinted.add(usedData.get(j).get(k));
                                }
                            }
                        }
                    } else {
                        for (int j = 0; j < alreadyPrinted.size(); j++) {
                            System.out.print(alreadyPrinted.get(j) + ", ");
                        }
                    }
                    projection += "-- on the fly";
                    System.out.print("-- on the fly \n");
                    System.out.print("JOIN ");
                    join += dataTable.get(usedTable.get(0)).get(0) + "." + dataTable.get(usedTable.get(0)).get(1) + " = ";
                    System.out.print(dataTable.get(usedTable.get(0)).get(0) + "." + dataTable.get(usedTable.get(0)).get(1) + " = ");
                    join += dataTable.get(usedTable.get(1)).get(0) + "." + dataTable.get(usedTable.get(0)).get(1) + " -- BNLJ";
                    System.out.print(dataTable.get(usedTable.get(1)).get(0) + "." + dataTable.get(usedTable.get(0)).get(1) + " -- BNLJ \n");

                    if (i == 2) {
                        // procedure tukar
                        int temp = usedTable.get(0);
                        usedTable.set(0, usedTable.get(1));
                        usedTable.set(1, temp);
                    }
                    for (int j = 0; j < usedTable.size(); j++) {
                        System.out.print(dataTable.get(usedTable.get(j)).get(0) + "    ");
                        table += dataTable.get(usedTable.get(j)).get(0) + "    ";
                    }
                    System.out.print("\nCost: ");
                    double Br = Math.ceil(Math.ceil(Float.parseFloat(dataTable.get(usedTable.get(0)).get(dataTable.get(usedTable.get(0)).size() - 3)) * Float.parseFloat(dataTable.get(usedTable.get(0)).get(dataTable.get(usedTable.get(0)).size() - 2))) / Float.parseFloat(dataTable.get(0).get(1)));
                    double Bs = Math.ceil(Math.ceil(Float.parseFloat(dataTable.get(usedTable.get(1)).get(dataTable.get(usedTable.get(1)).size() - 3)) * Float.parseFloat(dataTable.get(usedTable.get(1)).get(dataTable.get(usedTable.get(1)).size() - 2))) / Float.parseFloat(dataTable.get(0).get(1)));
                    cost.add(Br * Bs + Br);
                    strCost += Double.toString(Br * Bs + Br) + " Blocks";
                    System.out.print(cost.get(cost.size() - 1) + " Blocks\n");
                    System.out.println("");
                    tempDataQEP.add(inQuery);
                    tempDataQEP.add(projection);
                    tempDataQEP.add(join);
                    tempDataQEP.add(table);
                    tempDataQEP.add(strCost);
                    dataQEP.add(tempDataQEP);
                }
            }
            if (!cost.isEmpty()) {
                int smallest;
                if (cost.get(0) < cost.get(1)) {
                    smallest = 0;
                } else {
                    smallest = 1;
                }
                System.out.println("Optimal QEP  : QEP#" + (smallest + 1));
                QEPtoText(smallest);
                
            } else {
                System.out.println(usedData);
                System.out.println("QEP belum bisa dideteksi");
            }
        }
        dataQEP.clear();
        usedData.clear();
    }
        
    public void QEPtoText(int optimal) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("src/data/Shared Pool.txt"), true));
            bw.write("\n\n");
            for (int j = 0; j < dataQEP.get(optimal).size(); j++) {
                bw.write(dataQEP.get(optimal).get(j));
                bw.newLine();
            }
            bw.newLine();
            bw.close();
        } catch (Exception e) {
        }
    }
    
    
    public void printDataTable(List<String> in) {
        System.out.println("");
        for (int i = 0; i < in.size(); i = i + 2) {
            System.out.println("Tabel : " + in.get(i)); //print nama tabel
            System.out.print("Kolom : ");
            for (int j = 0; j < usedData.get(i/2 + 1).size(); j++) {
                System.out.print(usedData.get(i/2 + 1).get(j) + ", "); //print nama kolom tabel
            }
        }
        System.out.println("");
    }

   
    
}
