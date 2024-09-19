package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import model.PersonBag;
import model.TextbookBag;

public class Backup {

	public static void backupPersonBag(PersonBag personBag) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("dataFolder/Persons.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(personBag);
			oos.writeObject(personBag.getNElems());
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void backupTextbookBag(TextbookBag textbookBag) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("dataFolder/Textbooks.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(textbookBag);
			oos.writeObject(textbookBag.getNElems());
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}