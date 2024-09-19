package util;

import java.util.Random;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

import model.Name;
import model.Student;
import model.Instructor;
import model.PersonBag;
import model.Textbook;
import model.TextbookBag;

public class Utilities {
	private static Random random = new Random();
	private static DecimalFormat df = new DecimalFormat("#.##");
	private static DecimalFormat df2 = new DecimalFormat("#.#");
	private static String firstNames = "rawData/names/FirstNames.txt";
	private static String lastNames = "rawData/names/LastNames.txt";
	private static String majors = "rawData/majors/Majors.txt";
	private static String isbns = "rawData/Textbooks/textbook_isbns.txt";
	private static String titles = "rawData/Textbooks/textbook_titles.txt";
	private static String ranks = "rawData/ranks/Ranks.txt";

	private static String[] firstNameArr = makeNameArray(firstNames);
	private static String[] lastNameArr = makeNameArray(lastNames);
	private static String[] majorArr = makeMajorArray(majors);
	private static String[][] titleAndIsbnArr = makeBookAndIsbnArray(titles, isbns);
	private static String[] rankArr = makeNameArray(ranks);

	private static String[] makeNameArray(String fileName) { // create an array of first names, last names, or ranks
		File file = new File(fileName);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int count = 0;
		while (sc.hasNextLine()) {
			sc.nextLine();
			count++;
		}
		sc.close();
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String[] arr = new String[count];
		count = 0;
		while (sc.hasNextLine()) {
			arr[count++] = sc.nextLine();
		}
		sc.close();
		return arr;
	}

	public static String[] makeMajorArray(String majorFile) { // create an array of majors
		String[] arr;
		File file = new File(majors);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = sc.nextLine();
		arr = line.split(", "); // feeding each major into an array

		return arr;
	}

	public static String[][] makeBookAndIsbnArray(String titleFile, String isbnFile) { // create an array of titles and their corresponding isbn number
		String[][] arr = new String[38639][2];
		try {
			FileReader frt = new FileReader(titles);
			FileReader fri = new FileReader(isbns);
			Scanner sct = new Scanner(frt);
			Scanner sci = new Scanner(fri);

			for (int i = 0; i < arr.length; i++) {
				arr[i][0] = sct.nextLine();
				arr[i][1] = sci.nextLine();
			}
			sct.close();
			sci.close();
			return arr;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Name emitName() { // creates random name objects

		String firstName = firstNameArr[(int) (Math.random() * firstNameArr.length)];
		String lastName = lastNameArr[(int) (Math.random() * lastNameArr.length)];

		return new Name(firstName, lastName);

	}

	public static String[] emitTitleAndIsbn() { // goes through titleAndIsbnArr and puts random element into book array
		String[] book = titleAndIsbnArr[(int) (Math.random() * titleAndIsbnArr.length)];
		return book;

	}

	public static double emitPrice() { // random double ranging from 0.0 - 200.00
		double min = 0.0;
		double max = 200.00;
		double price = min + (max - min) * random.nextDouble();
		return Double.parseDouble(df.format(price));
	}

	public static String emitMajor() { // goes through major file, splits it by each comma, and puts each major into
		String major = majorArr[(int) (Math.random() * majorArr.length)];
		return major;
	}

	public static void importBooks(TextbookBag textbookBag) { // creating textbook objects by putting returns from given
		Textbook[] arr = new Textbook[40000];														// // methods into book
		for (int i = 0; i < titleAndIsbnArr.length; i++) {
			Textbook book = new Textbook(titleAndIsbnArr[i][0], titleAndIsbnArr[i][1], emitName(), emitPrice());
			arr[i] = book;
			textbookBag.insert(book);
		}
	}

	public static void importStudents(PersonBag personBag) { // adds student objects to person bag
		Student[] arr = new Student[1000];
		double min = 0.0;
		double max = 4.0;
		for (int i = 0; i < arr.length; i++) { 
			double gpa = min + (max - min) * random.nextDouble();
			Student student = new Student(emitName(), Double.parseDouble(df2.format(gpa)), emitMajor());
			arr[i] = student;
			personBag.insert(student);
		}
	}

	public static void importInstructors(PersonBag personBag) { // adds instructor objects to person bag
		Instructor[] arr = new Instructor[500];
		double min = 10000.00;
		double max = 100000.00;
		for (int i = 0; i < arr.length; i++) { // creating 500 instructor objects and adding it to an array
			String rank = rankArr[(int) (Math.random() * rankArr.length)];
			double salary = min + (max - min) * random.nextDouble();
			Instructor instructor = new Instructor(emitName(), rank, Double.parseDouble(df.format(salary)));
			arr[i] = instructor;
			personBag.insert(instructor);
		}
	}
}