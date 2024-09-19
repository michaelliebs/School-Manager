package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import model.Person;
import model.PersonBag;
import model.TextbookBag;

public class Restore {

	public static PersonBag restorePersonBag() {
		FileInputStream fis;
		try {
			fis = new FileInputStream("dataFolder/Persons.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			PersonBag personBag = (PersonBag) ois.readObject();
			Person.setIdCount((Integer)ois.readObject());
			ois.close();
			return personBag;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static TextbookBag restoreTextbookBag() {
		FileInputStream fis;
		try {
			fis = new FileInputStream("dataFolder/Textbooks.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			TextbookBag textbookBag = (TextbookBag) ois.readObject();
			ois.close();
			return textbookBag;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}