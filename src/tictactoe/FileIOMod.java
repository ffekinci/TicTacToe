package fileIOModule;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.*;
import java.nio.file.Paths;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileIOMod {

    private static String LoadPlayerName = null;
    private static boolean LoadPlayerMarker;

    Path file = Paths.get("data/save.txt");

    public char[][] LoadGame() {

        char[][] grid = null;

        Charset charset = Charset.forName("US-ASCII");

        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {

            String[] splitter;
            String line = null;
            line = reader.readLine();
            int dim = Character.getNumericValue(line.charAt(0));
            grid = new char[dim][dim];

            reader.skip(1);
            //line = reader.readLine();

            int j = 0;
            while (j < dim) {
                line = reader.readLine();
                for (int i = 0; i < grid[j].length && i < line.length(); i++) {
                    grid[j][i] = line.charAt(i);
                }
                j++;
            }

            reader.skip(1);
            //line = reader.readLine();

            line = reader.readLine();
            //System.out.println(line);

            splitter = line.split("-");
            LoadPlayerName = splitter[0];
            if (splitter[1].equals("true")) {
                LoadPlayerMarker = true;
            } else {
                LoadPlayerMarker = false;
            }

        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        return grid;

    }

    public void SaveGame(char[][] grid, String PlayerName, char Mark) {

        Charset charset = Charset.forName("US-ASCII");
        //String gameSave = "XOXOX\nXOOOX\nXXXOX\nXOOOX\nXOXXX\n";
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            int size = grid[0].length;
            writer.write(String.valueOf(size));
            writer.write("\n");
            for (int i = 0; i < grid[0].length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    writer.write(grid[i][j]);
                }
                writer.write("\n");
            }
            writer.write("\n");
            writer.write(PlayerName + "-" + ((Mark == 'X') ? "true" : "false"));

        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

    }

    public static String getLoadPlayerName() {
        if (LoadPlayerName != null) {
            return LoadPlayerName;
        } else {
            return "\nKullanýcý Ismi Belirlenemedi...\n";
        }
    }

    /*
	public void setLoadPlayerName(String loadPlayerName) {
		LoadPlayerName = loadPlayerName;
	}
     */
    public static boolean getLoadPlayerMarker() {
        return LoadPlayerMarker;
    }
    /*
	public void setLoadPlayerMarker(char loadPlayerMarker) {
		LoadPlayerMarker = loadPlayerMarker;
	}
     */
}
