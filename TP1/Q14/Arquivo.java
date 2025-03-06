//package Q14;

import java.io.*;
import java.util.Scanner;

public class Arquivo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filename = "numbers.txt";
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            while (scanner.hasNext()) {
                String input = scanner.next();
                if (input.equalsIgnoreCase("FIM")) {
                    break;
                }
                try {
                    double value = Double.parseDouble(input);
                    writer.println(value);
                } catch (NumberFormatException e) {
                    System.err.println("Entrada inválida.");
                }
            }
        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
            return;
        }
        
        try (RandomAccessFile file = new RandomAccessFile(filename, "r")) {
            long length = file.length();
            long pos = length - 1;
            
            while (pos >= 0) {
                file.seek(pos);
                char ch = (char) file.readByte();
                if (ch == '\n') {
                    if (pos != length - 1) {
                        System.out.println(file.readLine());
                    }
                    length = pos;
                }
                pos--;
            }
            
            file.seek(0);
            System.out.println(file.readLine());
            
        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
