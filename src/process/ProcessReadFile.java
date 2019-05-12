/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Mayaaaaa
 */
public class ProcessReadFile {
       
    public List readFile() throws FileNotFoundException, IOException {
        File file = new File("src/data/Data Dictionary.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st = br.readLine();
        List<List<String>> dataAll = new ArrayList();
        List<String> a = new ArrayList();
        List<String> tempList = new ArrayList();
        List<String> pointerandblock = new ArrayList();
        String temp = "";
        a = Arrays.asList(st.split(";"));
        for (String content : a) {
            tempList = Arrays.asList(content.split(" "));
            pointerandblock.add(tempList.get(1).replace("#", ""));
        }
        dataAll.add(pointerandblock);
        
        while ((st = br.readLine()) != null) {
            temp = "";
            a = Arrays.asList(st.split(";"));
            temp = a.get(a.size() - 1);
            temp = temp.replace(temp, temp.substring(0, temp.length() - 1));
            a.set(a.size() - 1, temp);
            for (int i = 0; i < a.size(); i++) {
                tempList = Arrays.asList(a.get(i).split(" "));
                if (tempList.size() == 2) {
                    a.set(i, tempList.get(1));
                }
            }
            dataAll.add(a);
        }
        return dataAll; //data semua tabel dan PB
        
    }
    
  
}
