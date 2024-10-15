const mysql = require('mysql');

// Konfiguration für die Verbindung zur Datenbank
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'Fkmb2003',
    database: 'budget_verwaltung'
});
// Verbindung zur Datenbank herstellen
connection.connect((err) => {
    if (err) {
        console.error('Fehler beim Verbinden zur Datenbank:', err);
        return;
    }
    console.log('Verbunden mit der Datenbank.');
});

// Funktion zum Hinzufügen einer Transaktion
const addTransaction = (description, amount, category, transactionType, callback) => {
    const query = 'INSERT INTO transactions (description, amount, category, transactionType) VALUES (?, ?, ?, ?)';
    connection.query(query, [description, amount, category, transactionType], (err, results) => {
        if (err) {
            console.error('Fehler beim Hinzufügen der Transaktion:', err);
            callback(err);  // Callback für Fehlerbehandlung
            return;
        }
        console.log('Transaktion hinzugefügt:', results.insertId);
        callback(null, results.insertId);  // Rückgabe der Insert-ID
    });
};

// Exporte
module.exports = {
    addTransaction
};


