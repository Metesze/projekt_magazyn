package com.example.magazyn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Zamowienia {
    public int getId_zamowienia() {
        return id_zamowienia;
    }

    public void setId_zamowienia(int id_zamowienia) {
        this.id_zamowienia = id_zamowienia;
    }

    public double getKoszt() {
        return koszt;
    }

    public void setKoszt(double koszt) {
        this.koszt = koszt;
    }

    private int id_zamowienia;
    private double koszt;
    public Zamowienia(int id_zamowienia,double koszt){
        this.id_zamowienia=id_zamowienia;
        this.koszt=koszt;

    }
    public static void DodajZamowienie(int  id_zamowienia, double koszt) {
        Zamowienia p = new Zamowienia(id_zamowienia, koszt);
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:magazyn.db");
             PreparedStatement stmt = con.prepareStatement("INSERT INTO zamowienia (id_zamowienia,koszt) VALUES (?,?)")) {
            stmt.setInt(1, p.getId_zamowienia());
            stmt.setDouble(2, p.getKoszt());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
