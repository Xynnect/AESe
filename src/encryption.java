import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class encryption {
	
	private static String input;
	private static String inputString = "";
	private static String encryptedString;
	static String IV = "AAAAAAAAAAAAAAAA";
	static String encryptionKey = "1234123412341234";

	public static void main(String[] args) throws Exception {
		String outputFromFile = readFromFile();
		if(outputFromFile.length()==0){
			System.out.println("You have no text");
			writingToFile("EmptyFileException");
		}
		else if(outputFromFile.length()!=0){
			byte[] encryptedBytesFromFile = encrypt(outputFromFile, encryptionKey);
			encryptedString = Base64.encodeBase64String(encryptedBytesFromFile);
			writingToFile(encryptedString);
		}	
	}
	
	private static void writingToFile(String content) {
		try {
			File file = new File("C:/Users/Xelnect/Desktop/encrypted.txt");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String readFromFile() {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(
					"C:/Users/Xelnect/Desktop/messageToBeDecrypted.txt"));
			while ((input = br.readLine()) != null) {
				inputString += input;
				System.out.println(input);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return inputString;
	}

	public static byte[] encrypt(String plainText, String encryptionKey)
			throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"),
				"AES");
		cipher.init(Cipher.ENCRYPT_MODE, key,
				new IvParameterSpec(IV.getBytes("UTF-8")));
		return cipher.doFinal(plainText.getBytes("UTF-8"));
	}

}
