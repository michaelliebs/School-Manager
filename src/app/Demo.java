package app;

import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.PersonBag;
import model.TextbookBag;
import util.Backup;
import util.Restore;
import util.Utilities;
import view.InstructorView;
import view.StudentView;
import view.TextbookView;;

public class Demo extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		PersonBag personBag; 
		if (new File("dataFolder/Persons.dat").exists()) { 
			personBag = Restore.restorePersonBag();
		} else {
			personBag = new PersonBag(2000);
			Backup.backupPersonBag(personBag);
		}
		
		TextbookBag textbookBag;
		if (new File("dataFolder/Textbooks.dat").exists()) {
			textbookBag = Restore.restoreTextbookBag();
		} else {
			textbookBag = new TextbookBag(40000);
			Backup.backupTextbookBag(textbookBag); 
		}
		
		StudentView studentView = new StudentView(personBag);
		InstructorView instructorView = new InstructorView(personBag);
		TextbookView textbookView = new TextbookView(textbookBag);
		BorderPane root = new BorderPane();

		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
		Menu editMenu = new Menu("Edit");

		MenuItem importItems = new MenuItem("Import Items");
		MenuItem endProgram = new MenuItem("Exit");
		MenuItem backupProgram = new MenuItem("Backup");
		MenuItem restoreProgram = new MenuItem("Restore");

		MenuItem studView = new MenuItem("Student View");
		MenuItem instructView = new MenuItem("Instructor View");
		MenuItem bookView = new MenuItem("Textbook View");

		importItems.setOnAction(e -> {
			Utilities.importStudents(personBag);
			Utilities.importInstructors(personBag);
			Utilities.importBooks(textbookBag);
		});

		endProgram.setOnAction(e -> {
			Backup.backupPersonBag(personBag);
			Backup.backupTextbookBag(textbookBag);
			Platform.exit();
		});

		backupProgram.setOnAction(e -> {
			Backup.backupPersonBag(personBag);
			Backup.backupTextbookBag(textbookBag);
		});

		restoreProgram.setOnAction(e -> {
			Restore.restorePersonBag();
			Restore.restoreTextbookBag();
		});

		studView.setOnAction(e -> {
			root.setCenter(studentView.getStudentRoot());
		});

		instructView.setOnAction(e -> {
			root.setCenter(instructorView.getInstructorRoot());
		});

		bookView.setOnAction(e -> {
			root.setCenter(textbookView.getTextbookRoot());
		});

		fileMenu.getItems().addAll(importItems, backupProgram, restoreProgram, endProgram);
		editMenu.getItems().addAll(studView, instructView, bookView);
		menuBar.getMenus().addAll(fileMenu, editMenu);

		root.setTop(menuBar);
		root.setCenter(studentView.getStudentRoot());

		Scene scene = new Scene(root, 1000, 800);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Lieberman Final Project");
		primaryStage.show();
	}
}

