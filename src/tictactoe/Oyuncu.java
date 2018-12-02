package run;

import java.util.*;

enum Marker {
    X,
    O
}

public class Oyuncu {

    Scanner inpt = new Scanner(System.in);

    private ArrayList<int[]> Coordinates = new ArrayList<int[]>();
    private boolean isHuman;
    private Marker PlayerMarker = null;
    private String PlayerName = "";

    public Oyuncu() {
        isHuman = true;
        PlayerMarker = Marker.X;
        System.out.println("Oyuncu Ismi : ");
        PlayerName = inpt.next();

        PrintPlayerData();
    }

    public Oyuncu(boolean InsanMi) {
        isHuman = InsanMi;

        if (isHuman) {
            PlayerMarker = Marker.X;
        } else {
            PlayerMarker = Marker.O;
        }

        if (isHuman) {
            System.out.println("Oyuncu Ismi : ");
            PlayerName = inpt.next();
        } else {
            PlayerName = "CPU";
        }

        PrintPlayerData();
    }

    public Oyuncu(boolean InsanMi, boolean _Marker) {
        isHuman = InsanMi;

        if (_Marker) {
            PlayerMarker = Marker.X;
        } else {
            PlayerMarker = Marker.O;
        }

        if (isHuman) {
            System.out.println("Oyuncu Ismi : ");
            PlayerName = inpt.next();
        } else {
            PlayerName = "CPU";
        }

        PrintPlayerData();
    }

    public Oyuncu(boolean InsanMi, boolean _Marker, String _PlayerName) {
        isHuman = InsanMi;

        if (_Marker) {
            PlayerMarker = Marker.X;
        } else {
            PlayerMarker = Marker.O;
        }

        PlayerName = new String(_PlayerName);

        PrintPlayerData();
    }

    public void PrintPlayerData() {
        System.out.printf("%-15s%-5s%-5s\n\n", PlayerName, ((PlayerMarker == Marker.X) ? "X" : "O"),
                 ((isHuman == true) ? "Oyuncu(Siz)" : "Bilgisayar"));
    }

    char KarakteriAl() {
        if (PlayerMarker == Marker.X) {
            return 'X';
        } else {
            return 'O';
        }
    }

    boolean OyuncuTurunuAl() {
        if (isHuman) {
            return true;
        } else {
            return false;
        }
    }

    public String OyuncununHamlesiniAl() {
        return InsanOyuncuHamlesiniKotrol();
    }

    private String InsanOyuncuHamlesiniKotrol() {
        //System.out.println("\nHamle Sirasi Sizde, Lutfen Hamlenizi 'RakamHarf' formatinda giriniz...:\t");
        String Hamle = inpt.next();
        return Hamle;
    }

    public String BilgisayarHamlesiUret(int size) {

        Random rand = new Random();
        String pos = "";
        String posit = pos.concat(Integer.toString(rand.nextInt(size)));
        String position = posit.concat(Character.toString((char) (65 + rand.nextInt(size))));

        //position.concat(Integer.toString(rand.nextInt(size)));
        //position.concat(Character.toString((char)(65 + rand.nextInt(size))));
        //System.out.println("\nBilgisayar Hamlesi : " + position + "\n" );
        return position;

    }

    public String getPlayerName() {
        return PlayerName;
    }

    public ArrayList<int[]> getCoordinates() {
        return Coordinates;
    }

    public void SaveCoord(int x, int y) {
        Coordinates.add(new int[2]);
        Coordinates.get(Coordinates.size() - 1)[0] = x;
        Coordinates.get(Coordinates.size() - 1)[1] = y;
    }

    public void PrintCoordinates() {
        System.out.println(PlayerName + "'s Markers : ");
        for (int i = 0; i < Coordinates.size(); i++) {
            System.out.println(Coordinates.get(i)[0] + " : " + Coordinates.get(i)[1]);
        }
    }

}
