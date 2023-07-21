package rc4_algorithm;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class RC4 {
	
	private byte []poruka;
	private SecretKey kljuc; 
	private Cipher cipher;
	
	
	//Rucno ubacujemo kljuc preko teksta metodom generateKeyFromText
	public RC4(byte []poruka, String kljuc) throws NoSuchAlgorithmException, NoSuchPaddingException {
		super();
		try {
			this.poruka = poruka;
			this.kljuc = generateKeyFromText(kljuc);
			this.cipher = Cipher.getInstance("ARCFOUR/");
		} catch (NoSuchAlgorithmException e) {	
			System.out.println("Kljuc nije string formata");
		}
	}
	
	//Automatski kljuc pravi KeyGenerator preko metode generateKey
	public RC4(byte []poruka) throws NoSuchAlgorithmException, NoSuchPaddingException {
		super();
		this.poruka = poruka;
		this.kljuc = generateKey();
		this.cipher = Cipher.getInstance("ARCFOUR/");
	}
	
	//Rucno ubacujemo duzinu kljuca metodom gen_keys
	public RC4(byte []poruka, int kljuc) throws NoSuchAlgorithmException, NoSuchPaddingException  {
		super();
		if(kljuc <= 16) {
			this.kljuc = gen_key(kljuc);			
		}else {
			System.out.println("Kljuc je predugacak");
			System.exit(0);
		}
		this.poruka = poruka;
		this.cipher = Cipher.getInstance("ARCFOUR/");
	}
	
	//Stavljamo secretkey preko konstruktora u arff1-u
	public RC4(byte []poruka, SecretKey kljuc) throws NoSuchAlgorithmException, NoSuchPaddingException {
		super();
		this.poruka = poruka;
		this.kljuc = kljuc;
		this.cipher = Cipher.getInstance("ARCFOUR/");
	}
	
	public void setKljuc(SecretKey kljuc) {
		this.kljuc = kljuc;
	}
	
	public SecretKey getKljuc() {
		return kljuc;
	}
	
	 public SecretKey generateKey() throws NoSuchAlgorithmException {
	        return KeyGenerator.getInstance("ARCFOUR").generateKey();
	}
	 
	
	public byte [] rc4_encrypt() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		cipher.init(Cipher.ENCRYPT_MODE, kljuc);
		return cipher.doFinal(poruka);
 	
    }

	public static SecretKey gen_key(int duzina_kljuca) {
		SecureRandom prng = new SecureRandom();
		byte []kljuc_prng = new byte[duzina_kljuca];
		prng.nextBytes(kljuc_prng);
		return new SecretKeySpec(kljuc_prng, "ARCFOUR");
	}
	
	 public SecretKey generateKeyFromText(String tajna) throws NoSuchAlgorithmException {
	        byte[] passwordBytes = tajna.getBytes();
	        SecretKey passwordKey = null;
	        if(tajna.length() <= 16) {
	        	passwordKey = new SecretKeySpec(passwordBytes, "ARCFOUR");
	        }else {
	        	System.out.println("Duzina kljuca ne sme biti veca od 16 bajtova.");
	        	System.exit(0);
	        }
			return passwordKey;
			
	 }
	
	public static byte [] xor(byte []poruka, byte []sifrat) {
		byte []rezultat = new byte[poruka.length];
		for(int i = 0; i < poruka.length; i++) {
			rezultat[i] = (byte) (poruka[i] ^ sifrat[i]);
		}
		return rezultat;		
	}	
	
	public static String calculateHexFrequency(String input, String klasa) {
        int[] frequencyArray = new int[16];

        String upperCaseInput = input.toUpperCase();

        for (char c : upperCaseInput.toCharArray()) {
            if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'F')) {
                int decimalValue = Character.digit(c, 16);
                frequencyArray[decimalValue]++;
            }
        }
        String rezultat = "";
        HashMap<Character, Integer> frequencyMap = new HashMap<>();
        for (int i = 0; i < frequencyArray.length; i++) {
            char hexDigit = Character.toUpperCase(Integer.toHexString(i).charAt(0));
            int frequency = frequencyArray[i];
            double brojac = frequency / (double) upperCaseInput.length();
            frequencyMap.put(hexDigit, frequency);
            rezultat += String.format("%.4f ", brojac) + ", ";
        }

        return rezultat + klasa;
    }
	
	 public static String fromBytesToHexString(byte[] byteArray) {
	        StringBuilder hexString = new StringBuilder(2 * byteArray.length);
	        for (byte b : byteArray) {
	            String hex = Integer.toHexString(b & 0xFF);
	            if (hex.length() == 1) {
	                hexString.append('0');
	            }
	            hexString.append(hex);
	        }
	        
	        return hexString.toString().toUpperCase();
	    }
}