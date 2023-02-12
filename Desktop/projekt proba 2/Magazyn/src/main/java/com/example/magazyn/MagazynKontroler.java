package com.example.magazyn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class MagazynKontroler {
    @FXML
    public TableView<Product> produktyTabView;
    @FXML
    public TableColumn<Product, Integer> ID;
    @FXML
    public TableColumn<Product, String> Nazwa;
    @FXML
    public TableColumn<Product, Double> Cena;
    @FXML
    public TableColumn<Product, Integer> Ilosc;
    @FXML
    public TableView<Product> produktyTabView1;
    @FXML
    public TableColumn<Product, Integer> ID1;
    @FXML
    public TableColumn<Product, String> Nazwa1;
    @FXML
    public TableColumn<Product, Double> Cena1;
    @FXML
    public TableColumn<Product, Integer> Ilosc1;
    @FXML
    public TableView<Zamowienia> zamTabView;
    @FXML
    public TableColumn<Zamowienia, Integer> ID2;
    @FXML
    public TableColumn<Zamowienia, Double> Koszt;
        @FXML
        public TextField txtId;
        @FXML
        public TextField txtName;
        @FXML
        public TextField txtQuantity;
        @FXML
        public TextField txtPrice;
    @FXML
    public TextField txtId1;

    @FXML
    public TextField txtName1;
    @FXML
    public TextField txtQuantity1;
    @FXML
    public TextField txtPrice1;
    @FXML
    public TextField ilosczam;
    @FXML
    public TextField txtZam;

@FXML
    public void Add(ActionEvent event) {

            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            int quantity = Integer.parseInt(txtQuantity.getText());
            double price = Double.parseDouble(txtPrice.getText());
            Product.DodajDoBazy(id,name,quantity,price);
            txtId.clear();
            txtName.clear();
            txtQuantity.clear();
            txtPrice.clear();

    }
    @FXML
    public void AddZ(ActionEvent event) {

    int id_zamowienia = Integer.parseInt(txtZam.getText());
    double pom = Double.parseDouble(txtPrice1.getText());
    double pom2 = Double.parseDouble(ilosczam.getText());
    double koszt = pom * pom2;
    double pom3= Double.parseDouble(ilosczam.getText());
    double pom4=Double.parseDouble(txtQuantity1.getText());
    if(pom3<=pom4) {
        Zamowienia.DodajZamowienie(id_zamowienia, koszt);
    }
    else { Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Błąd");
        alert.setHeaderText(null);
        alert.setContentText("Ilość podana przez użytkownika ma być mniejsza lub równa ilości produktu w bazie danych!!");
        alert.showAndWait();
    }
    txtId1.clear();
    txtName1.clear();
    txtQuantity1.clear();
    txtPrice1.clear();



}
@FXML
        public void buttonRefresh(ActionEvent event) {
            try {
                // pobierz aktualne dane z bazy danych
                ObservableList<Product> productsData = FXCollections.observableArrayList();
                // uzupełnij listę danymi
                produktyTabView.setItems(productsData);
                wyswietl();
            } catch (Exception e) {
                e.printStackTrace();
            }
    txtId.clear();
    txtName.clear();
    txtQuantity.clear();
    txtPrice.clear();
        }
    @FXML
    private void usunZaznaczonyProdukt()  {
        Product selectedProduct = produktyTabView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                Connection con = DriverManager.getConnection("jdbc:sqlite:magazyn.db");
                PreparedStatement stmt = con.prepareStatement("DELETE FROM produkty WHERE Id_produktu = ?");
                stmt.setInt(1, selectedProduct.getId_produktu());
                ((PreparedStatement) stmt).executeUpdate();
                stmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            produktyList.remove(selectedProduct);
        }
        txtId.clear();
        txtName.clear();
        txtQuantity.clear();
        txtPrice.clear();
    }
    @FXML
    private void usunZ(){
        Zamowienia selectedProduct = zamTabView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                Connection con = DriverManager.getConnection("jdbc:sqlite:magazyn.db");
                PreparedStatement stmt = con.prepareStatement("DELETE FROM zamowienia WHERE id_zamowienia = ?");
                stmt.setInt(1, selectedProduct.getId_zamowienia());
                ((PreparedStatement) stmt).executeUpdate();
                stmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            zamList.remove(selectedProduct);
        }
    }

    private ObservableList<Product> produktyList = FXCollections.observableArrayList();

    public void wyswietl() {
        // Łączenie kolumn z właściwościami klasy Produkt
        ID.setCellValueFactory(new PropertyValueFactory<>("Id_produktu"));
       Nazwa.setCellValueFactory(new PropertyValueFactory<>("Nazwa"));
        Cena.setCellValueFactory(new PropertyValueFactory<>("Cena"));
        Ilosc.setCellValueFactory(new PropertyValueFactory<>("Ilosc"));


        // Pobieranie produktów z bazy danych
        produktyList = DBUtil.get_Produkty();

        // Dodanie produktów do TableView
        produktyTabView.setItems(produktyList);

        produktyTabView.setOnMouseClicked(event -> {
            Product selectedProduct = produktyTabView.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                txtId.setText(String.valueOf(selectedProduct.getId_produktu()));
                txtName.setText(selectedProduct.getNazwa());
                txtPrice.setText(String.valueOf(selectedProduct.getCena()));
                txtQuantity.setText(String.valueOf(selectedProduct.getIlosc()));

            }
        });

    }

    public void modify() {
        Product selectedProduct = produktyTabView.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            // wyświetl komunikat o błędzie, jeśli nie zaznaczono żadnego produktu
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("Nie wybrano produktu do modyfikacji");
            alert.showAndWait();
            return;
        }

        int id = selectedProduct.getId_produktu();
        String name = txtName.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        double price = Double.parseDouble(txtPrice.getText());

        // aktualizuj dane produktu w bazie danych
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:magazyn.db");
            try {
                PreparedStatement stmt = con.prepareStatement("UPDATE produkty SET nazwa = ?, ilosc = ?, cena = ? WHERE Id_produktu = ?");
                try {
                    stmt.setString(1, name);
                    stmt.setInt(2, quantity);
                    stmt.setDouble(3, price);
                    stmt.setInt(4, id);
                    stmt.executeUpdate();
                } finally {
                    stmt.close();
                }
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("Nie udało się zaktualizować produktu");
            alert.showAndWait();
            return;
        }

        // aktualizuj dane produktu na liście
        selectedProduct.setNazwa(name);
        selectedProduct.setIlosc(quantity);
        selectedProduct.setCena(price);
        produktyTabView.refresh();
        txtId.clear();
        txtName.clear();
        txtQuantity.clear();
        txtPrice.clear();
    }
    private ObservableList<Product> produktyList1 = FXCollections.observableArrayList();
    public void wyswietl1() {

        ID1.setCellValueFactory(new PropertyValueFactory<>("Id_produktu"));
        Nazwa1.setCellValueFactory(new PropertyValueFactory<>("Nazwa"));
        Cena1.setCellValueFactory(new PropertyValueFactory<>("Cena"));
        Ilosc1.setCellValueFactory(new PropertyValueFactory<>("Ilosc"));



        produktyList1 = DBUtil.get_Produkty();


        produktyTabView1.setItems(produktyList1);
        produktyTabView1.setOnMouseClicked(event -> {
            Product selectedProduct = produktyTabView1.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                txtId1.setText(String.valueOf(selectedProduct.getId_produktu()));
                txtName1.setText(selectedProduct.getNazwa());
                txtPrice1.setText(String.valueOf(selectedProduct.getCena()));
                txtQuantity1.setText(String.valueOf(selectedProduct.getIlosc()));

            }
        });

    }
    @FXML
    public void buttonRefresh1(ActionEvent event) {
        try {
            // pobierz aktualne dane z bazy danych
            ObservableList<Product> productsData = FXCollections.observableArrayList();
            // uzupełnij listę danymi
            produktyTabView1.setItems(productsData);
            wyswietl1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ObservableList<Zamowienia> zamList = FXCollections.observableArrayList();

    public void wyswietl2() {

        ID2.setCellValueFactory(new PropertyValueFactory<>("id_zamowienia"));
        Koszt.setCellValueFactory(new PropertyValueFactory<>("koszt"));


        zamList = DBUtil.get_Zam();


        zamTabView.setItems(zamList);

        }
    @FXML
    public void buttonRefresh2(ActionEvent event) {
        try {
            // pobierz aktualne dane z bazy danych
            ObservableList<Zamowienia> zamowieniaData = FXCollections.observableArrayList();
            // uzupełnij listę danymi
        zamTabView.setItems(zamowieniaData);
            wyswietl2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
