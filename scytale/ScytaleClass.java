package scytale;

public class ScytaleClass  {

	 public static String scytaleEncode(String plainText, int numOfRows) {
		 String encodedText = "";
		 if(numOfRows>=plainText.length() || numOfRows <= 0) {
			 return plainText;
		 } else {
			while(plainText.length()%numOfRows!=0) {
				plainText += " ";
			}
			int numOfCols = plainText.length()/numOfRows;
			for(int i = 0; i < numOfCols; i++) {
				for(int y = i; y <plainText.length(); y += numOfCols) {
					encodedText += plainText.charAt(y);
				}
			}
		 }
		 return encodedText;
	 }
	 
	 public static String scytaleDecode(String encodedString, int numOfRows) {
		 String decodedString = "";
		 int numOfCols = encodedString.length()/numOfRows;
		 decodedString = scytaleEncode(encodedString, 	numOfCols);
		 return decodedString;
	 }
	 
	 public static void main(String[] args) {
		 String text = "Example for Scytale";
		 String encoded = scytaleEncode(text, 4);
		 System.out.println(encoded);
		 String decoded = scytaleDecode(encoded, 4);
		 System.out.println(decoded);
	 }
}
