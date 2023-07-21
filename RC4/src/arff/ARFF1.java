package arff;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


import rc4_algorithm.RC4;

public class ARFF1 {
	
		
	
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
	    // Parametre
        String fileName = "RC4_SECURE_REGULAR";
        String klase = "{ RC4-SECURE, RC4-REGULAR}";
        // Parametre

        // Generisanje template-a
        FileOutputStream fos = new FileOutputStream("D:\\Eclipse Workspace\\RC4\\Arff\\" + fileName + ".arff");
        PrintWriter pw = new PrintWriter(fos);

        // Deklarisanje imema relacije
        pw.println("@relation " + fileName);

        // Deklarisem promenljive koja varijacija sadrzi
        pw.println("");
        for (int i = 0; i <= 16; i++) {
            pw.println("@attribute " + i + " numeric");
        }

        // Deklarisanje klasa koje ce se uporedjivati
        pw.println("@attribute klasifikacija " + klase);

        // Deklarisemo mesto gde skladistimo podatke
        pw.println("");
        pw.println("@data");

        // Generisanje template-a
        
        //Polje za unos podataka
        String poruka = readTextFile("D:\\Eclipse Workspace\\RC4\\Fajlovi\\Tekst");
       
        List<String> parts = separateString(poruka, 50);

        for (String part : parts) {
        	RC4 rc4 = new RC4(part.getBytes(), generateSRKey(16, "ARCFOUR"));
            byte[] sifrat = rc4.rc4_encrypt();
            String s = RC4.fromBytesToHexString(sifrat);
            System.out.println("Hexadecimalna vrednost sifrata1: " + s);
            
            pw.println(RC4.calculateHexFrequency(s, " RC4-SECURE"));
            
          
            RC4 rc4_2 = new RC4(part.getBytes(), generateRRKey(16, "ARCFOUR"));
            byte[] sifrat2 = rc4_2.rc4_encrypt();
            String s2 = RC4.fromBytesToHexString(sifrat2);
            System.out.println("Hexadecimalna vrednost sifrata2: "+ s2 + "  \n");
            
            pw.println(RC4.calculateHexFrequency(s2, " RC4-REGULAR"));

        }

        //Polje za unos podataka
        pw.flush();
        pw.close();
        fos.close();
    }
	
	public static String readTextFile(String path) {

        try {
            FileInputStream fis = new FileInputStream(path);
            Scanner s = new Scanner(fis);

            String rezultat = "";
            while (s.hasNext()) {
                rezultat += s.nextLine();
            }

            return rezultat;

        } catch (FileNotFoundException ex) {
            System.out.println("Fajl ne postoji");
        }

        return null;
    }

    public static List<String> separateString(String s, int n) {
        List<String> parts = new ArrayList<>();

        int celobrojniDeo = s.length() / n;

        for (int i = 0; i < n - 1; i++) {
            String part = s.substring(i * celobrojniDeo, (i + 1) * celobrojniDeo);
            parts.add(part);
        }

        parts.add(s.substring((n - 1) * celobrojniDeo));
        //123|123|123|12
        return parts;
    }
    
    public static SecretKey generateSRKey(int n, String algorithm) {
        byte[] key = new byte[n];
        SecureRandom sRandom = new SecureRandom();
        sRandom.nextBytes(key);

        SecretKey secretKey = new SecretKeySpec(key, algorithm);

        return secretKey;
    }

    public static SecretKey generateRRKey(int n, String algorithm) {
        byte[] key = new byte[n];
        Random rRandom = new Random();
        rRandom.nextBytes(key);

        SecretKey secretKey = new SecretKeySpec(key, algorithm);

        return secretKey;
    }
}
