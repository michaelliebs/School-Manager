package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Name;
import model.Person;
import model.PersonBag;
import model.Student;
import util.Backup;

public class StudentView {
	private PersonBag personBag;
	private VBox root;

	public StudentView(PersonBag personBag) {
		this.personBag = personBag;

		Button insertBtn = new Button("Insert");
		insertBtn.setPrefSize(100, 30);

		Button searchBtn = new Button("Search");
		searchBtn.setPrefSize(100, 30);

		Button updateBtn = new Button("Update");
		updateBtn.setPrefSize(100, 30);

		Button removeBtn = new Button("Remove");
		removeBtn.setPrefSize(100, 30);

		Button clearBtn = new Button("Clear");
		clearBtn.setPrefSize(100, 30);

		TextField firstNameField = new TextField();
		firstNameField.setPromptText("First Name");
		firstNameField.setPrefSize(150, 30);

		TextField lastNameField = new TextField();
		lastNameField.setPromptText("Last Name");
		lastNameField.setPrefSize(150, 30);

		TextField gpaField = new TextField();
		gpaField.setPrefSize(150, 30);
		gpaField.setPromptText("GPA");

		TextField majorField = new TextField();
		majorField.setPrefSize(150, 30);
		majorField.setPromptText("Major");

		TextField idField = new TextField();
		idField.setPrefSize(150, 30);
		idField.setPromptText("Id");

		HBox inputBox = new HBox(20);
		inputBox.setAlignment(Pos.CENTER);
		inputBox.getChildren().addAll(firstNameField, lastNameField, gpaField, majorField, idField);

		HBox btnBox = new HBox(30);
		btnBox.setAlignment(Pos.CENTER);
		btnBox.getChildren().addAll(insertBtn, searchBtn, removeBtn, updateBtn, clearBtn);

		TextField outputField = new TextField();
		outputField.setMaxSize(300, 30);

		ListView<Person> listView = new ListView<>();
		listView.setMaxSize(600, 300);

		VBox outputBox = new VBox(20);
		outputBox.setAlignment(Pos.CENTER);
		outputBox.getChildren().addAll(outputField, listView);

		insertBtn.setOnAction(e -> { // insert user entered objects
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			double gpa = Double.parseDouble(gpaField.getText());
			String major = majorField.getText();
			Student student = new Student(new Name(firstName, lastName), gpa, major);
			personBag.insert(student);
			firstNameField.clear();
			lastNameField.clear();
			gpaField.clear();
			majorField.clear();
			outputField.setText("Entered information: ");
			listView.getItems().addAll(student);
			Backup.backupPersonBag(personBag);
		});

		searchBtn.setOnAction(e -> { // search by one of the following fields
			String id = idField.getText();
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String major = majorField.getText();
			String gpa = gpaField.getText();

			if (!idField.getText().isEmpty()) {
				Person[] personFoundId = personBag.search(p -> {
					return (p).getId().equals(id);
				});
				idField.clear();
				outputField.setText("Person found: ");
				listView.getItems().addAll(personFoundId);
			}

			if (!firstNameField.getText().isEmpty()) {
				Person[] personFoundFN = personBag.search(p -> {
					return (p instanceof Student && ((Student) p).getName().getFirstName().equalsIgnoreCase(firstName));
				});
				firstNameField.clear();
				outputField.setText("Person found: ");
				listView.getItems().addAll(personFoundFN);
			}

			if (!lastNameField.getText().isEmpty()) {
				Person[] personFoundLN = personBag.search(p -> {
					return (p instanceof Student && ((Student) p).getName().getLastName().equalsIgnoreCase(lastName));
				});
				lastNameField.clear();
				outputField.setText("Person found: ");
				listView.getItems().addAll(personFoundLN);
			}

			if (!gpaField.getText().isEmpty()) {
				Double gpaDouble = Double.parseDouble(gpa);
				Person[] personFoundGpa = personBag.search(p -> {
					return (p instanceof Student && ((Student) p).getGpa() == (gpaDouble));
				});
				gpaField.clear();
				outputField.setText("Person found: ");
				listView.getItems().addAll(personFoundGpa);
			}

			if (!majorField.getText().isEmpty()) {
				Person[] personFoundMajor = personBag.search(p -> {
					return (p instanceof Student && ((Student) p).getMajor().equalsIgnoreCase(major));
				});
				majorField.clear();
				outputField.setText("Person found: ");
				listView.getItems().addAll(personFoundMajor);
			}
		});

		removeBtn.setOnAction(e -> { // remove an object from the bag
			String id = idField.getText();
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();

			if (!idField.getText().isEmpty()) {
				Person[] personRemoveId = personBag.remove(p -> {
					return (p instanceof Student && ((Student) p).getId().equalsIgnoreCase(id));
				});
				idField.clear();
				outputField.setText("Person removed: ");
				listView.getItems().addAll(personRemoveId);
			}

			if (!firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty()) {
				Person[] personRemoveName = personBag.remove(p -> {
					return (p instanceof Student && ((Student) p).getName().getFirstName().equalsIgnoreCase(firstName)
							&& ((Student) p).getName().getLastName().equalsIgnoreCase(lastName));
				});
				firstNameField.clear();
				lastNameField.clear();
				outputField.setText("Person removed: ");
				listView.getItems().addAll(personRemoveName);
			}

		});

		updateBtn.setOnAction(e -> { // update an object by one of the 
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String gpa = gpaField.getText();
			String major = majorField.getText();

			Student s = (Student) listView.getSelectionModel().getSelectedItem();

			if (!firstNameField.getText().isEmpty()) {
				s.getName().setFirstName(firstName);
			}

			if (!lastNameField.getText().isEmpty()) {
				s.getName().setLastName(lastName);
			}

			if (!gpaField.getText().isEmpty()) {
				Double gpaDouble = Double.parseDouble(gpa);
				s.setGpa(gpaDouble);
			}

			if (!majorField.getText().isEmpty()) {
				s.setMajor(major);
			}

			firstNameField.clear();
			lastNameField.clear();
			gpaField.clear();
			majorField.clear();
			outputField.setText("Updated Person: ");
			listView.getItems().addAll(s);
			Backup.backupPersonBag(personBag);
		});

		clearBtn.setOnAction(e -> {
			listView.getItems().clear();
			outputField.clear();
		});

		Text title = new Text("Student View");
		root = new VBox(50);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(inputBox, btnBox, outputBox, title);
	}

	public VBox getStudentRoot() {
		return root;
	}
}