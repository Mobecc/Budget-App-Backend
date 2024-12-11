package de.htwberlin.budgetapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "budget_item") // Explizit auf die Tabelle "budget_item" verweisen
public class BudgetItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String beschreibung;
    private double betrag;
    private String datum;
    private String kategorie;
    private String typ; // Neue Spalte für den Transaktionstyp

    // Default-Konstruktor
    public BudgetItem() {}

    // Konstruktor mit fünf Parametern
    public BudgetItem(String beschreibung, double betrag, String datum, String kategorie, String typ) {
        this.beschreibung = beschreibung;
        this.betrag = betrag;
        this.datum = datum;
        this.kategorie = kategorie;
        this.typ = typ; // Initialisierung des neuen Attributs
    }

    // Konstruktor mit vier Parametern (für Tests)
    public BudgetItem(String beschreibung, double betrag, String datum, String kategorie) {
        this(beschreibung, betrag, datum, kategorie, null); // Der Typ wird auf null gesetzt
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    @Override
    public String toString() {
        return "BudgetItem{" +
                "id=" + id +
                ", beschreibung='" + beschreibung + '\'' +
                ", betrag=" + betrag +
                ", datum='" + datum + '\'' +
                ", kategorie='" + kategorie + '\'' +
                ", typ='" + typ + '\'' +
                '}';
    }
}
