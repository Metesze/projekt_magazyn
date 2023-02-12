package com.example.magazyn;

import java.sql.*;
public class Product {
    private int Id_produktu;
    private String nazwa;
    private int ilosc;
    private double cena;


    public Product(int Id_produktu, String nazwa, int ilosc, double cena) {
        this.Id_produktu = Id_produktu;
        this.nazwa = nazwa;
        this.ilosc = ilosc;
        this.cena = cena;
    }



    public static void DodajDoBazy(int  Id_produktu, String nazwa, int ilosc, double cena) {
        Product p = new Product(Id_produktu, nazwa, ilosc, cena);
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:magazyn.db");
             PreparedStatement stmt = con.prepareStatement("INSERT INTO produkty (Id_produktu,nazwa, ilosc, cena) VALUES (?,?, ?, ?)")) {
            stmt.setInt(1, p.getId_produktu());
            stmt.setString(2, p.getNazwa());
            stmt.setInt(3, p.getIlosc());
            stmt.setDouble(4, p.getCena());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public int getId_produktu() {
        return Id_produktu;
    }

    public void setId_produktu(int Id_produktu) {
        this.Id_produktu = Id_produktu;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }
}