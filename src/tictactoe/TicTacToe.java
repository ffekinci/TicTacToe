/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.Scanner;

/**
 *
 * @author ffe
 */
public class TicTacToe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner inpt = new Scanner(System.in);
		
		System.out.println("Yeni Oyun icin 0; kayitli oyun icin 1 tuslayiniz : ");
		
		int n = inpt.nextInt();
				
		OyunTahtasi TicTacToe;
		
		if(n == 0) {
			TicTacToe = new OyunTahtasi();
		}else {
			TicTacToe = new OyunTahtasi(OyunTahtasi.OyunTahtasiAl());
		}
				
		TicTacToe.StartGame();
		
		TicTacToe.OyunTahtasiYazdir();
    }
    
}
