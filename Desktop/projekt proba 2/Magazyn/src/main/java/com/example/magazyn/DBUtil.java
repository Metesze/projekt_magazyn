


package com.example.magazyn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;



public class DBUtil {

    public static ObservableList<Product> get_Produkty() {
        ObservableList<Product> produkty = FXCollections.observableArrayList();
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:magazyn.db");
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM produkty");
            while (rs.next()) {
                produkty.add(new Product(rs.getInt("Id_produktu"), rs.getString("Nazwa"), rs.getInt("Ilosc"), rs.getDouble("Cena")));
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
        return produkty;
    }

    public static ObservableList<Zamowienia> get_Zam() {
        ObservableList<Zamowienia> zamowienia = FXCollections.observableArrayList();
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:magazyn.db");
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM zamowienia");
            while (rs.next()) {
                zamowienia.add(new Zamowienia(rs.getInt("id_zamowienia"),  rs.getDouble("koszt")));
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
        return zamowienia;
    }

}
