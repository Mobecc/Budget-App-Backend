package de.htwberlin.budgetapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "budget_item")
public class BudgetItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String beschreibung; // entspricht "character varying(255)"
    private double betrag;       // entspricht "double precision"
    private String kategorie;    // entspricht "character varying(255)"
    private String typ;          // entspricht "character varying(255)"
    private String datum;        // entspricht "character varying(255)"

    // Default-Konstruktor
    public BudgetItem() {}

    // Konstruktor mit allen Parametern
    public BudgetItem(String beschreibung, double betrag, String kategorie, String typ, String datum) {
        this.beschreibung = beschreibung;
        this.betrag = betrag;
        this.kategorie = kategorie;
        this.typ = typ;
        this.datum = datum;
    }

    // Konstruktor mit abweichender Reihenfolge (für Tests)
    public BudgetItem(String beschreibung, double betrag, String datum, String kategorie) {
        this.beschreibung = beschreibung;
        this.betrag = betrag;
        this.datum = datum;
        this.kategorie = kategorie;
        this.typ = null; // Standardwert für typ
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

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    @Override
    public String toString() {
        return "BudgetItem{" +
                "id=" + id +
                ", beschreibung='" + beschreibung + '\'' +
                ", betrag=" + betrag +
                ", kategorie='" + kategorie + '\'' +
                ", typ='" + typ + '\'' +
                ", datum='" + datum + '\'' +
                '}';
    }
}
