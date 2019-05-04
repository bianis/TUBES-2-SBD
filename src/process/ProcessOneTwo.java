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

    public void tampil1(int B, int P, int R, int V, String[] tabel) {
        System.out.println("BFR " + tabel[0] + " : " + hitungBfr(B, R));;
        System.out.println("Fan Out Ratio " + tabel[0] + " : " + hitungFr(B, P, V));
        System.out.println("");
    }

    public void tampil2(int B, int P, int R, int V, int n, String[] tabel) {
        System.out.println("Tabel Data " + tabel[0] + " : " + countBlockData(B, R, n));
        System.out.println("Indeks " + tabel[0] + " : " + countBlockIndex(B, P, n, V));
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
