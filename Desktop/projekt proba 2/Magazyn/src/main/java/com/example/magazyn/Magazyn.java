package com.example.magazyn;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Magazyn extends Application {
    @Override
    public void start(Stage stage) throws IOException {
    stage.setTitle("Logowanie");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);


        Label lblUserName = new Label("Login:");
        grid.add(lblUserName, 0, 1);

        TextField txtUserName = new TextField();
        grid.add(txtUserName, 1, 1);

        Label lblPassword = new Label("Hasło:");
        grid.add(lblPassword, 0, 2);

        PasswordField pf = new PasswordField();
        grid.add(pf, 1, 2);

        Button btn = new Button("Zaloguj");
        grid.add(btn, 1, 4);

        btn.setOnAction(e -> {
            String userName = txtUserName.getText();
            String password = pf.getText();

            if (userName.equals("admin") && password.equals("admin")) {


                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Magazyn.class.getResource("magazyn.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 500, 440);
                    stage.setTitle("Magazyn");
                    stage.setScene(scene);
                    stage.show();
                    Connection conn = DriverManager.getConnection("jdbc:sqlite:magazyn.db");
                    // Tworzenie tabeli "produkty"
                    Statement stmt = conn.createStatement();
                    stmt.execute("CREATE TABLE IF NOT EXISTS produkty (Id_produktu INTEGER PRIMARY KEY , nazwa TEXT, ilosc INTEGER, cena INT)");

                    // Tworzenie tabeli "zamówienia"
                    stmt.execute("CREATE TABLE IF NOT EXISTS zamowienia (id_zamowienia INTEGER  PRIMARY KEY , koszt DOUBLE)");

                    // Zamknięcie połączenia z bazą danych
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText("Błąd logowania");
                alert.setContentText("Podany login lub hasło jest nieprawidłowe");
                alert.showAndWait();
            }
        });

Scene scene = new Scene(grid, 500, 400);
stage.setScene((scene));
stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}