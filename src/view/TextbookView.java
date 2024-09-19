package view;

import java.text.DecimalFormat;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Name;
import model.Textbook;
import model.TextbookBag;
import util.Backup;

public class TextbookView {

	private TextbookBag textbookBag = new TextbookBag(40000);
	private VBox root;
	private static DecimalFormat df = new DecimalFormat("#.##");

	public TextbookView(TextbookBag textbookBag) {
		this.textbookBag = textbookBag;

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

		HBox btnBox = new HBox(30);
		btnBox.setAlignment(Pos.CENTER);
		btnBox.getChildren().addAll(insertBtn, searchBtn, removeBtn, updateBtn, clearBtn);

		TextField titleField = new TextField();
		titleField.setPromptText("Title");
		titleField.setPrefSize(150, 30);

		TextField isbnField = new TextField();
		isbnField.setPrefSize(150, 30);
		isbnField.setPromptText("ISBN");

		TextField firstNameField = new TextField();
		firstNameField.setPrefSize(150, 30);
		firstNameField.setPromptText("First Name");

		TextField lastNameField = new TextField();
		lastNameField.setPrefSize(150, 30);
		lastNameField.setPromptText("Last Name");

		TextField priceField = new TextField();
		priceField.setPrefSize(150, 30);
		priceField.setPromptText("Price");

		HBox inputBox = new HBox(20);
		inputBox.setAlignment(Pos.CENTER);
		inputBox.getChildren().addAll(titleField, isbnField, firstNameField, lastNameField, priceField);

		TextField outputField = new TextField();
		outputField.setMaxSize(300, 30);

		ListView<Textbook> listView = new ListView<>();
		listView.setMaxSize(600, 300);

		VBox outputBox = new VBox(20);
		outputBox.setAlignment(Pos.CENTER);
		outputBox.getChildren().addAll(outputField, listView);

		insertBtn.setOnAction(e -> {
			String title = titleField.getText();
			String isbn = isbnField.getText();
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			double price = Double.parseDouble(priceField.getText());
			Textbook textbook = new Textbook(title, isbn, new Name(firstName, lastName), price);
			textbookBag.insert(textbook);
			titleField.clear();
			isbnField.clear();
			firstNameField.clear();
			lastNameField.clear();
			priceField.clear();
			outputField.setText("Entered information: ");
			listView.getItems().addAll(textbook);
			Backup.backupTextbookBag(textbookBag);
		});

		searchBtn.setOnAction(e -> {
			String title = titleField.getText();
			String isbn = isbnField.getText();
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String price = priceField.getText();

			if (!titleField.getText().isEmpty()) {
				Textbook[] textbookFoundTitle = textbookBag.search((Textbook t) -> {
					return ((Textbook) t).getTitle().equalsIgnoreCase(title);
				});
				titleField.clear();
				outputField.setText("Textbook found: ");
				listView.getItems().addAll(textbookFoundTitle);
			}

			if (!isbnField.getText().isEmpty()) {
				Textbook[] textbookFoundIsbn = textbookBag.search((Textbook t) -> {
					return ((Textbook) t).getIsbn().equalsIgnoreCase(isbn);
				});
				isbnField.clear();
				outputField.setText("Textbook found: ");
				listView.getItems().addAll(textbookFoundIsbn);
			}

			if (!firstNameField.getText().isEmpty()) {
				Textbook[] textbookFoundFN = textbookBag.search((Textbook t) -> {
					return ((Textbook) t).getAuthor().getFirstName().equalsIgnoreCase(firstName);
				});
				firstNameField.clear();
				outputField.setText("Textbook found: ");
				listView.getItems().addAll(textbookFoundFN);
			}

			if (!lastNameField.getText().isEmpty()) {
				Textbook[] textbookFoundLN = textbookBag.search((Textbook t) -> {
					return ((Textbook) t).getAuthor().getLastName().equalsIgnoreCase(lastName);
				});
				lastNameField.clear();
				outputField.setText("Textbook found: ");
				listView.getItems().addAll(textbookFoundLN);
			}

			if (!priceField.getText().isEmpty()) {
				Double priceDouble = Double.parseDouble(price);
				Textbook[] textbookFoundPrice = textbookBag.search((Textbook p) -> {
					return Double.compare(((Textbook) p).getPrice(), priceDouble) == 0;
				});
				priceField.clear();
				outputField.setText("Textbook found: ");
				listView.getItems().addAll(textbookFoundPrice);
			}
			Backup.backupTextbookBag(textbookBag);
		});

		removeBtn.setOnAction(e -> {
			String title = titleField.getText();
			String isbn = isbnField.getText();
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String price = priceField.getText();

			if (!titleField.getText().isEmpty()) {
				Textbook[] textbookFoundTitle = textbookBag.remove((Textbook t) -> {
					return ((Textbook) t).getTitle().equalsIgnoreCase(title);
				});
				titleField.clear();
				outputField.setText("Textbook found: ");
				listView.getItems().addAll(textbookFoundTitle);
			}

			if (!isbnField.getText().isEmpty()) {
				Textbook[] textbookFoundIsbn = textbookBag.remove((Textbook t) -> {
					return ((Textbook) t).getIsbn().equalsIgnoreCase(isbn);
				});
				isbnField.clear();
				outputField.setText("Textbook found: ");
				listView.getItems().addAll(textbookFoundIsbn);
			}

			if (!firstNameField.getText().isEmpty()) {
				Textbook[] textbookFoundFN = textbookBag.remove((Textbook t) -> {
					return ((Textbook) t).getAuthor().getFirstName().equalsIgnoreCase(firstName);
				});
				firstNameField.clear();
				outputField.setText("Textbook found: ");
				listView.getItems().addAll(textbookFoundFN);
			}

			if (!lastNameField.getText().isEmpty()) {
				Textbook[] textbookFoundLN = textbookBag.remove((Textbook t) -> {
					return ((Textbook) t).getAuthor().getLastName().equalsIgnoreCase(lastName);
				});
				lastNameField.clear();
				outputField.setText("Textbook found: ");
				listView.getItems().addAll(textbookFoundLN);
			}

			if (!priceField.getText().isEmpty()) {
				Double priceDouble = Double.parseDouble(price);
				Textbook[] textbookFoundPrice = textbookBag.remove((Textbook p) -> {
					return Double.compare(((Textbook) p).getPrice(), priceDouble) == 0;
				});
				priceField.clear();
				outputField.setText("Textbook found: ");
				listView.getItems().addAll(textbookFoundPrice);
			}
			Backup.backupTextbookBag(textbookBag);
		});

		updateBtn.setOnAction(e -> {
			String title = titleField.getText();
			String isbn = isbnField.getText();
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String price = priceField.getText();

			Textbook t = (Textbook) listView.getSelectionModel().getSelectedItem();

			if (!titleField.getText().isEmpty()) {
				t.setTitle(title);
			}

			if (!isbnField.getText().isEmpty()) {
				t.setIsbn(isbn);
			}

			if (!firstNameField.getText().isEmpty()) {
				t.getAuthor().setFirstName(firstName);
			}

			if (!lastNameField.getText().isEmpty()) {
				t.getAuthor().setLastName(lastName);
			}

			if (!priceField.getText().isEmpty()) {
				Double priceDouble = Double.parseDouble(price);
				t.setPrice(priceDouble);
			}
			
			titleField.clear();
			isbnField.clear();
			firstNameField.clear();
			lastNameField.clear();
			priceField.clear();
			outputField.setText("Updated Textbook: ");
			listView.getItems().addAll(t);
			Backup.backupTextbookBag(textbookBag);
		});

		clearBtn.setOnAction(e -> {
			listView.getItems().clear();
			outputField.clear();

		});

		Text title = new Text("Textbook View");
		root = new VBox(50);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(inputBox, btnBox, outputBox, title);
	}

	public VBox getTextbookRoot() {
		return root;
	}

}