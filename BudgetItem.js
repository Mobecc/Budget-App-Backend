class BudgetItem {
    constructor(id, beschreibung, betrag, datum, kategorie) {
        this.id = id;
        this.beschreibung = beschreibung;
        this.betrag = betrag;
        this.datum = datum;  // Datum im deutschen Format "TT.MM.JJJJ"
        this.kategorie = kategorie;
    }
}

let transactions = [];
const categories = ["Einkommen", "Miete", "Lebensmittel", "Freizeit", "Transport", "Sonstiges"];
let nextId = 1; // Startwert für die ID

const RESET = "\x1b[0m"; // Zurücksetzen auf die Standardfarbe
const RED = "\x1b[31m";   // Rot
const GREEN = "\x1b[32m"; // Grün
const YELLOW = "\x1b[33m"; // Gelb
const BLUE = "\x1b[34m";   // Blau
const MAGENTA = "\x1b[35m"; // Magenta
const LIGHT_GREEN = "\x1b[92m"; // Hellgrün

function addTransaction(beschreibung, betrag, datum, kategorie) {
    if (!categories.includes(kategorie)) {
        console.log("Ungültige Kategorie. Wähle aus den verfügbaren Kategorien.");
        return;
    }
    let newTransaction = new BudgetItem(nextId, beschreibung, betrag, datum, kategorie);
    transactions.push(newTransaction);
    console.log(GREEN + "Transaktion hinzugefügt: " + `ID: ${nextId}` + RESET, newTransaction);
    nextId++; // Erhöhe die ID für die nächste Transaktion
}

function editTransaction(id, newBeschreibung, newBetrag, newDatum, newKategorie) {
    const transaction = transactions.find(t => t.id === id);

    if (!transaction) {
        console.log(RED + "Transaktion mit ID " + id + " nicht gefunden." + RESET);
        return;
    }

    // Ändere die Werte
    transaction.beschreibung = newBeschreibung;
    transaction.betrag = newBetrag;
    transaction.datum = newDatum;
    transaction.kategorie = newKategorie;

    console.log(BLUE + "Transaktion bearbeitet: " + RESET, transaction);
}

function deleteTransaction(id) {
    const index = transactions.findIndex(t => t.id === id);

    if (index === -1) {
        console.log(RED + "Transaktion mit ID " + id + " nicht gefunden." + RESET);
        return;
    }

    const deletedTransaction = transactions.splice(index, 1);
    console.log(MAGENTA + "Transaktion gelöscht: " + RESET, deletedTransaction[0]);
}

function calculateTotalBudget() {
    let total = 0;
    transactions.forEach(transaction => {
        total += transaction.betrag; // Einnahmen sind positiv, Ausgaben negativ
    });
    return total;
}

function displayBudget() {
    console.log("****************************************");
    console.log(YELLOW + "✨ Aktuelles Budget: " + calculateTotalBudget() + "€ ✨" + RESET);
    console.log("****************************************");
}

function displayTransactions() {
    console.log("Alle Transaktionen:");
    transactions.forEach(transaction => {
        const color = transaction.betrag > 0 ? GREEN : RED; // Grün für Einnahmen, Rot für Ausgaben
        console.log(`ID: ${transaction.id}, ${color}Beschreibung: ${transaction.beschreibung}, Betrag: ${transaction.betrag}€, Datum: ${transaction.datum}, Kategorie: ${transaction.kategorie}` + RESET);
        console.log("----------------------------------------"); // Visuelle Trennung
    });
}

// Beispiel für das Hinzufügen von Transaktionen
addTransaction("Gehalt", 2500, "01.10.2024", "Einkommen");
addTransaction("Miete", -800, "05.10.2024", "Miete");
addTransaction("Lebensmittel", -150, "06.10.2024", "Lebensmittel");

// Alle Transaktionen ausgeben
displayTransactions();

// Beispiel für das Bearbeiten und Löschen von Transaktionen
editTransaction(2, "Miete für Oktober", -850, "05.10.2024", "Miete");
deleteTransaction(3);

// Budget ganz unten anzeigen
displayBudget();
//fertig