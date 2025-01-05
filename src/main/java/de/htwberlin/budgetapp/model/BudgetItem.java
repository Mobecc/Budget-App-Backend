package de.htwberlin.budgetapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

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

    @Temporal(TemporalType.DATE)
    private Date datum;          // neues Datumsfeld

    // Default-Konstruktor
    public BudgetItem() {}

    // Konstruktor mit allen Parametern
    public BudgetItem(String beschreibung, double betrag, String kategorie, String typ, Date datum) {
        this.beschreibung = beschreibung;
        this.betrag = betrag;
        this.kategorie = kategorie;
        this.typ = typ;
        this.datum = datum;
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

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
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
                ", datum=" + datum +
                '}';
    }
}
