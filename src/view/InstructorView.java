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
import util.Backup;
import model.Instructor;

public class InstructorView {
	private PersonBag personBag;
	private VBox root;

	public InstructorView(PersonBag personBag) {
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

		TextField rankField = new TextField();
		rankField.setPrefSize(150, 30);
		rankField.setPromptText("Rank");

		TextField salaryField = new TextField();
		salaryField.setPrefSize(150, 30);
		salaryField.setPromptText("Salary");

		TextField idField = new TextField();
		idField.setPrefSize(150, 30);
		idField.setPromptText("Id");

		HBox inputBox = new HBox(20);
		inputBox.setAlignment(Pos.CENTER);
		inputBox.getChildren().addAll(firstNameField, lastNameField, rankField, salaryField, idField);

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

		insertBtn.setOnAction(e -> {
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String rank = rankField.getText();
			double salary = Double.parseDouble(salaryField.getText());
			Instructor instructor = new Instructor(new Name(firstName, lastName), rank, salary);
			personBag.insert(instructor);
			firstNameField.clear();
			lastNameField.clear();
			rankField.clear();
			salaryField.clear();
			outputField.setText("Entered information: ");
			listView.getItems().addAll(instructor);
			Backup.backupPersonBag(personBag);
		});

		searchBtn.setOnAction(e -> {
			String id = idField.getText();
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String rank = rankField.getText();
			String salary = salaryField.getText();

			if (!idField.getText().isEmpty()) {
				Person[] personFoundId = personBag.search(p -> {
					return (p instanceof Instructor && ((Instructor) p).getId().equals(id));
				});
				idField.clear();
				outputField.setText("Person found: ");
				listView.getItems().addAll(personFoundId);
			}
			
			if (!firstNameField.getText().isEmpty()) {
				Person[] personFoundFN = personBag.search(p -> {
					return (p instanceof Instructor
							&& ((Instructor) p).getName().getFirstName().equalsIgnoreCase(firstName));
				});
				firstNameField.clear();
				outputField.setText("Person found: ");
				listView.getItems().addAll(personFoundFN);
			}

			if (!lastNameField.getText().isEmpty()) {
				Person[] personFoundLN = personBag.search(p -> {
					return (p instanceof Instructor
							&& ((Instructor) p).getName().getLastName().equalsIgnoreCase(lastName));
				});
				lastNameField.clear();
				outputField.setText("Person found: ");
				listView.getItems().addAll(personFoundLN);
			}

			if (!rankField.getText().isEmpty()) {
				Person[] personFoundRank = personBag.search(p -> {
					return (p instanceof Instructor && ((Instructor) p).getRank().equalsIgnoreCase(rank));
				});
				rankField.clear();
				outputField.setText("Person found: ");
				listView.getItems().addAll(personFoundRank);
			}

			if (!salaryField.getText().isEmpty()) {
				Double salaryDouble = Double.parseDouble(salary);
				Person[] personFoundSalary = personBag.search(p -> {
					return (p instanceof Instructor && ((Instructor) p).getSalary() == (salaryDouble));
				});
				salaryField.clear();
				outputField.setText("Person found: ");
				listView.getItems().addAll(personFoundSalary);
			}
		});

		removeBtn.setOnAction(e -> {
			String id = idField.getText();
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String rank = rankField.getText();
			String salary = salaryField.getText();

			if (!idField.getText().isEmpty()) {
				Person[] personFoundId = personBag.remove(p -> {
					return (p instanceof Instructor && ((Instructor) p).getId().equalsIgnoreCase(id));
				});
				idField.clear();
				outputField.setText("Person removed: ");
				listView.getItems().addAll(personFoundId);
			}

			if (!firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty()) {
				Person[] personFoundName = personBag.remove(p -> {
					return (p instanceof Instructor && ((Instructor) p).getName().getFirstName().equalsIgnoreCase(firstName)
							&& ((Instructor) p).getName().getLastName().equalsIgnoreCase(lastName));
				});
				firstNameField.clear();
				lastNameField.clear();
				outputField.setText("Person removed: ");
				listView.getItems().addAll(personFoundName);
			}
			
			if (!rankField.getText().isEmpty()) {
				Person[] personFoundRank = personBag.remove(p -> {
					return (p instanceof Instructor && ((Instructor) p).getRank().equalsIgnoreCase(rank));
				});
				rankField.clear();
				outputField.setText("Person removed: ");
				listView.getItems().addAll(personFoundRank);
			}
			
			if (!salaryField.getText().isEmpty()) {
				Double salaryDouble = Double.parseDouble(salary);
				Person[] personFoundSalary = personBag.remove(p -> {
					return (p instanceof Instructor && ((Instructor) p).getSalary() == (salaryDouble));
				});
				salaryField.clear();
				outputField.setText("Person removed: ");
				listView.getItems().addAll(personFoundSalary);
			}
			Backup.backupPersonBag(personBag);
		});

		updateBtn.setOnAction(e -> {
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String rank = rankField.getText();
			String salary = salaryField.getText();

			Instructor i = (Instructor) listView.getSelectionModel().getSelectedItem();

			if (!firstNameField.getText().isEmpty()) {
				i.getName().setFirstName(firstName);
			}

			if (!lastNameField.getText().isEmpty()) {
				i.getName().setLastName(lastName);
			}

			if (!rankField.getText().isEmpty()) {
				i.setRank(rank);
			}
			
			if (!salaryField.getText().isEmpty()) {
				Double salaryDouble = Double.parseDouble(salary);
				i.setSalary(salaryDouble);
			}

			firstNameField.clear();
			lastNameField.clear();
			rankField.clear();
			salaryField.clear();
			outputField.setText("Updated Person: ");
			listView.getItems().addAll(i);
			Backup.backupPersonBag(personBag);
		});

		clearBtn.setOnAction(e -> {
			listView.getItems().clear();
			outputField.clear();
		});

		Text title = new Text("Instructor View");
		root = new VBox(50);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(inputBox, btnBox, outputBox, title);
	}

	public VBox getInstructorRoot() {
		return root;
	}

}