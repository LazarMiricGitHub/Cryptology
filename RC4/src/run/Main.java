package run;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.JFileChooser;

import rc4_algorithm.RC4;
import arff.ARFF1;

public class Main {
	
	private static ARFF1 arff;
	private static byte []msg;
	private static String kljuc;
	private static SecretKey tajna;
	
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
		//Polje za unos podataka
		String poruka = arff.readTextFile("D:\\Eclipse Workspace\\RC4\\Fajlovi\\Tekst");
		msg = poruka.getBytes();
		kljuc = "Ovo je kljuc";
	
		//1. Unosimo poruku i String kljuc
		RC4 rc4_1 = new RC4(msg,kljuc);
		byte []sifrat1 = rc4_1.rc4_encrypt();
		System.out.println("UUUUUUUUUUUUUUUU: \n" + new String(sifrat1));
		//System.out.println("Password kljuc: " + Arrays.toString(passwordKey.getEncoded()));
		System.out.println("\nSifrat1: " + Arrays.toString(sifrat1));
		
		//2. Unosimo poruku i automatski se unosi pseudorandom kljuc
		RC4 rc4_2 = new RC4(msg);
		byte []sifrat2 = rc4_2.rc4_encrypt();
		System.out.println("\nSifrat2:  " + Arrays.toString(sifrat2));
		
		//3. Unosimo poruku i ubacujemo duzinu int kljuca 
		RC4 rc4_3 = new RC4(msg, 13);
		byte []sifrat3 = rc4_3.rc4_encrypt();
		System.out.println("\nSifrat3:  " + Arrays.toString(sifrat3));
		
		//4. Unosimo poruku i SecretKey
		RC4 rc4_4 = new RC4(msg, tajna);
		tajna = KeyGenerator.getInstance("ARCFOUR").generateKey();
		rc4_4.setKljuc(tajna);
		byte []sifrat4 = rc4_4.rc4_encrypt();
		System.out.println("\nSifrat4: " + Arrays.toString(sifrat4));
		
		
		byte []xor1 = RC4.xor(sifrat1, poruka.getBytes(StandardCharsets.UTF_8));
		System.out.println("\nRadni kljuc1: " + Arrays.toString(xor1));
		
		byte []xor2 = RC4.xor(sifrat2, poruka.getBytes(StandardCharsets.UTF_8));
		System.out.println("\nRadni kljuc2: " + Arrays.toString(xor2));
		
		byte []xor3 = RC4.xor(sifrat3, poruka.getBytes(StandardCharsets.UTF_8));
		System.out.println("\nRadni kljuc3: " + Arrays.toString(xor3));
		
		byte []xor4 = RC4.xor(sifrat4, poruka.getBytes(StandardCharsets.UTF_8));
		System.out.println("\nRadni kljuc4: " + Arrays.toString(xor4));
		
		System.out.println("\n");
		
		JFileChooser jfc = new JFileChooser();
		jfc.showOpenDialog(jfc);
		byte[] xorPoruka = Files.readAllBytes(Paths.get(jfc.getSelectedFile().getAbsolutePath()));
		
		jfc.showOpenDialog(jfc);
		byte[] Sifrat = Files.readAllBytes(Paths.get(jfc.getSelectedFile().getAbsolutePath()));
		
		byte [] xorSifrat = RC4.xor(poruka.getBytes(), sifrat1);
		System.out.println("Duzina kljuca pronadjena XOR operacijom: " + Arrays.toString(Arrays.copyOf(xorSifrat, 256)));
		
		byte []xorSifrat2 = RC4.xor(poruka.getBytes(), sifrat2);
		System.out.println("Duzina kljuca pronadjena XOR operacijom: " +Arrays.toString(Arrays.copyOf(xorSifrat2, 256)));
		
		byte []xorSifrat3 = RC4.xor(poruka.getBytes(), sifrat3);
		System.out.println("Duzina kljuca pronadjena XOR operacijom: " + Arrays.toString(Arrays.copyOf(xorSifrat3, 256)));
		
		byte []xorSifrat4 = RC4.xor(poruka.getBytes(), sifrat4);
		System.out.println("Duzina kljuca pronadjena XOR operacijom: " + Arrays.toString(Arrays.copyOf(xorSifrat4, 256)));
		
	}

}
