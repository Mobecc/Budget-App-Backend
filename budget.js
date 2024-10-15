import {Chart} from "chart.js";

function addTransaction(beschreibung, betrag, datum, kategorie) {
    let newTransaction = new BudgetItem(nextId, beschreibung, betrag, datum, kategorie);
    transactions.push(newTransaction);
    console.log("Transaktion hinzugefügt: ID:", nextId, newTransaction);
    nextId++; // Erhöhe die ID für die nächste Transaktion
    displayTransactions(); // Aktualisiere die Anzeige der Transaktionen
    visualizeBudget(); // Visualisiere das Budget
}

function displayTransactions() {
    const transactionsList = document.getElementById('transactionsList');
    transactionsList.innerHTML = ''; // Leere die Liste
    transactions.forEach(transaction => {
        const transactionDiv = document.createElement('div');
        transactionDiv.innerHTML = `ID: ${transaction.id}, Beschreibung: ${transaction.beschreibung}, Betrag: ${transaction.betrag}€, Datum: ${transaction.datum}, Kategorie: ${transaction.kategorie}`;
        transactionsList.appendChild(transactionDiv);
    });
}

function calculateTotalBudget() {
    let total = 0;
    transactions.forEach(transaction => {
        total += transaction.betrag; // Einnahmen sind positiv, Ausgaben negativ
    });
    return total;
}

function visualizeBudget() {
    const labels = categories; // Kategorien als Labels
    const data = {
        labels: labels,
        datasets: [{
            label: 'Einnahmen und Ausgaben',
            data: categories.map(cat => transactions
                .filter(t => t.kategorie === cat)
                .reduce((sum, t) => sum + t.betrag, 0)),
            backgroundColor: [
                'rgba(75, 192, 192, 0.2)',
                'rgba(255, 99, 132, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 159, 64, 0.2)',
                'rgba(153, 102, 255, 0.2)',
            ],
            borderColor: [
                'rgba(75, 192, 192, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 159, 64, 1)',
                'rgba(153, 102, 255, 1)',
            ],
            borderWidth: 1
        }]
    };

    const config = {
        type: 'bar', // Du kannst 'bar' oder 'pie' ändern
        data: data,
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    };

    const budgetChart = new Chart(
        document.getElementById('budgetChart'),
        config
    );
}

// Anmeldeformular-Logik
document.getElementById('loginForm').addEventListener('submit', function (event) {
    event.preventDefault(); // Verhindere das Neuladen der Seite

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Hier würdest du eine API-Anfrage an den Backend-Server senden
    fetch('/api/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                document.getElementById('message').innerText = 'Erfolgreich angemeldet!';
                document.getElementById('auth').style.display = 'none'; // Verstecke die Anmeldung
                document.getElementById('transactionForm').style.display = 'block'; // Zeige das Transaktionsformular
                document.getElementById('transactionsDisplay').style.display = 'block'; // Zeige die Transaktionen
                document.getElementById('settings').style.display = 'block'; // Zeige die Benutzereinstellungen
            } else {
                document.getElementById('message').innerText = 'Anmeldung fehlgeschlagen. Bitte versuche es erneut.';
            }
        })
        .catch(error => {
            console.error('Fehler:', error);
            document.getElementById('message').innerText = 'Ein Fehler ist aufgetreten.';
        });
});

// Transaktion hinzufügen
document.getElementById('addTransaction').addEventListener('click', function () {
    const beschreibung = document.getElementById('beschreibung').value;
    const betrag = parseFloat(document.getElementById('betrag').value);
    const datum = document.getElementById('datum').value;
    const kategorie = document.getElementById('kategorie').value;

    addTransaction(beschreibung, betrag, datum, kategorie);
});

// Benutzereinstellungen speichern
document.getElementById('settingsForm').addEventListener('submit', function (event) {
    event.preventDefault(); // Verhindere das Neuladen der Seite

    const newUsername = document.getElementById('newUsername').value;
    const newPassword = document.getElementById('newPassword').value;

    // Hier würdest du eine API-Anfrage senden, um die Benutzereinstellungen zu aktualisieren
    fetch('/api/updateSettings', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ newUsername, newPassword }),
    })
        .then(response => response.json())
        .then(data => {
            const settingsMessage = document.getElementById('settingsMessage');
            if (data.success) {
                settingsMessage.innerText = 'Einstellungen erfolgreich aktualisiert!';
            } else {
                settingsMessage.innerText = 'Fehler beim Aktualisieren der Einstellungen.';
            }
        })
        .catch(error => {
            console.error('Fehler:', error);
            document.getElementById('settingsMessage').innerText = 'Ein Fehler ist aufgetreten.';
        });
});
//Ende