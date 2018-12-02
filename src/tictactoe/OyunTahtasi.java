package run;

import java.util.*;

import fileIOModule.*;

public class OyunTahtasi {
	
	ArrayList<Oyuncu> OyuncuListesi = new ArrayList<Oyuncu>();
	Scanner inpt = new Scanner(System.in);
	char [][] grid;
	
	public OyunTahtasi() {
		int n = SetGameBoard();
		grid = new char[n][n];
		for(char [] row : grid)
			Arrays.fill(row, (char)46/*9, 32*/);
		
		System.out.print("\nHizli Baslangic icin 0, Ozel Secenekler icin 1 tuslayiniz...:\t" );
		if((n = inpt.nextInt()) == 0) {
			OyuncuListesi.add(new Oyuncu());
			OyuncuListesi.add(new Oyuncu(false, false));
		}
		else {
			boolean b;
			System.out.println("\n'X' isaretcisi icin 1, 'O' isareticisi icin 0 tusayiniz...:\t");
			
			if((n = inpt.nextInt()) == 1)
				b = true;
			else b = false;
			
			OyuncuListesi.add(new Oyuncu(true, b));
			OyuncuListesi.add(new Oyuncu(false, !b));
		}
			}
	
	public OyunTahtasi(char [][] oyunTahtasi) {
		int i = 0;
		grid = new char[oyunTahtasi[0].length][oyunTahtasi[0].length];
		
		for(char [] c: oyunTahtasi)
			grid[i++] = Arrays.copyOf(c, c.length);
				
		registPlayer(true, FileIOMod.getLoadPlayerMarker(), FileIOMod.getLoadPlayerName());
		registPlayer(false, !FileIOMod.getLoadPlayerMarker(), "CPU");
	}
	
	/***	CLASS METHODS	**/
	
	static char [][] OyunTahtasiAl(){
		
		FileIOMod Load = new FileIOMod();
		char [][]subgrid = null;
		subgrid = Load.LoadGame();
		return subgrid;
	}
	
	/***	CLASS METHODS ***/
	
	public void registPlayer(boolean Human, boolean Marker, String Name) {
		OyuncuListesi.add(new Oyuncu(Human, Marker, Name));
	}
	
	int SetGameBoard() {
		int n;
		do {
			System.out.print("Oyun Tahtasinin Boyutlarini Giriniz : \t");
			n = inpt.nextInt();			
		}while(n!=3 && n!=5 && n!=7);
		
		return n;
	}
	
	public void OyunTahtasiYazdir() {
		
		System.out.print("\t");
		for(int m = 0; m < grid[0].length; m++)
			System.out.print((char)(65 + m) + "\t");
		System.out.print("\n\t");
		for(int m = 0; m < grid[0].length - 1; m++)
			System.out.print("________");
		System.out.println("\n");
		
		for(int i = 0; i < grid[0].length; i++) {
			System.out.print(i + "|\t");
			for(int j = 0; j < grid[0].length; j++)
				System.out.print(grid[i][j] + "\t");
			System.out.print("\n\n");
		}
	}
	
	public void StartGame() {
		
		while(true) {
			
			/*	Format Screen	*/
			//System.out.println("___________________________");
			//System.out.flush();	// NOT WORKING !!!
			OyunTahtasiYazdir();
			
			/*	Sleep	*/
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*	Players Move 	*/
			do {
				System.out.println("\nHamle sirasi sizde. Lutfen hamlenizi '5J' "
						+ "formatinda giriniz...\nOyunu kaydedip cikmak icin '00' giriniz : ");
			}
			while(!HamleyiYaz(OyuncuListesi.get(0).OyuncununHamlesiniAl(), 0));
			
			if(Kazanan(OyuncuListesi.get(0).getCoordinates(), 0)) {
				System.out.println("!!! " + OyuncuListesi.get(0).getPlayerName() + " is WINNER!!!\n\n");
				break;
			}
			
			/*	Check Game Conditions	*/
			if(BeraberlikKontrol()) {
				System.out.println("\nBeraberlik, Tahtada Bos Hucre Kalmadi...\n");
				break;
			}
			
			/*	CPUs Move*/
			StringBuilder CPUMove = new StringBuilder(2);
			
			do {
				
				CPUMove.delete(0, 2);
				CPUMove.append(OyuncuListesi.get(1).BilgisayarHamlesiUret(grid[0].length));
				
			}while(!HamleyiYaz(CPUMove.toString(), 1));
			
			System.out.println("\nBilgisayar Hamlesi : " + CPUMove + "\n" );
			
			if(Kazanan(OyuncuListesi.get(1).getCoordinates(), 1)) {
				System.out.println("!!! " + OyuncuListesi.get(1).getPlayerName() + " is WINNER!!!\n\n");
				break;
			}
			
			if(BeraberlikKontrol()) {
				System.out.println("\nBeraberlik, Tahtada Bos Hucre Kalmadi...\n");
				break;
			}
		}
		/*
		OyuncuListesi.get(0).PrintCoordinates();
		OyuncuListesi.get(1).PrintCoordinates();*/
	}
	
	boolean HamleyiYaz(String Position, int p) {
		
		if(Position.equals("00")) {
			FileIOMod Save = new FileIOMod();
			System.out.println("\nKaydediliyor...\n");
			Save.SaveGame(grid, OyuncuListesi.get(0).getPlayerName(), OyuncuListesi.get(0).KarakteriAl());
			System.out.println("\nOyun Sonlandiriliyor\n");
			System.exit(0);
		}
		
		int line = Integer.parseInt(Position.substring(0, 1));
		int row = (int)(Position.charAt(1));
		row -= 65;
		
		if(grid[line][row] != '.') {
			return false;
		}else {
			grid[line][row] = OyuncuListesi.get(p).KarakteriAl();
			OyuncuListesi.get(p).SaveCoord(line, row);
			return true;
		}
	
	}
	
	boolean Kazanan(ArrayList<int []> coord, int p) {
		
		for(int c = 0; c < coord.size(); c++) {
			for(int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					
					if(i == 0 && j == 0)
						continue;
					
					if((coord.get(c)[0] + i) < grid[0].length && (coord.get(c)[1] + j) < grid[0].length
							&& (coord.get(c)[0] + i) >= 0 && (coord.get(c)[1] + j) >= 0) {
						
						if(grid[(coord.get(c)[0] + i)][(coord.get(c)[1] + j)] == OyuncuListesi.get(p).KarakteriAl()) {
														
							if((coord.get(c)[0] + i + i) < grid[0].length && (coord.get(c)[1] + j + j) < grid[0].length
								&& (coord.get(c)[0] + i + i) >= 0 && (coord.get(c)[1] + j + j) >= 0) {
	
								if(grid[(coord.get(c)[0] + i + i)][(coord.get(c)[1] + j + j)] == OyuncuListesi.get(p).KarakteriAl()) {
									return true;
									
								}else continue;
								
							}//else continue;
							
						}//else continue;
					
					}//else continue;
				
				}
			}
		}
		
		return false;
	}
	
	boolean BeraberlikKontrol() {
		for(int i = 0; i < grid[0].length; i++)
			for(int j = 0; j < grid[0].length; j++) {
				if(grid[i][j] == '.') return false;
			}
				
		return true;
	}
}
