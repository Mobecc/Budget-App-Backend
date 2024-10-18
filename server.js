// Datei: server.js

const express = require('express');
const bodyParser = require('body-parser');
const path = require('path');
const mysql = require('mysql');

const app = express();
const PORT = 3000;

// Konfiguration für die Verbindung zur Datenbank
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'Fkmb2003',
    database: 'budget_verwaltung'
});

// Middleware für Body-Parsing und statische Dateien
app.use(bodyParser.json());
app.use(express.static(path.join(__dirname, '..', 'public')));

app.post('/api/transactions', (req, res) => {
    const { description, amount, category, transactionType } = req.body;

    if (!description || isNaN(amount) || !category || !transactionType) {
        return res.status(400).json({ success: false, message: 'Bitte füllen Sie alle Felder aus.' });
    }

    const query = 'INSERT INTO transactions (description, amount, category, type) VALUES (?, ?, ?, ?)';
    connection.query(query, [description, amount, category, transactionType], (err, results) => {
        if (err) {
            return res.status(500).json({ success: false, message: 'Fehler beim Hinzufügen der Transaktion.' });
        }
        res.json({ success: true, transactionId: results.insertId });
    });
});

app.get('/api/transactions', (req, res) => {
    const query = 'SELECT * FROM transactions';
    connection.query(query, (err, results) => {
        if (err) {
            return res.status(500).json({ error: 'Fehler beim Abrufen der Transaktionen.' });
        }
        res.json(results);
    });
});

app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, '..', 'public', 'index.html'));
});

// API zum Aktualisieren einer Transaktion
app.put('/api/transactions/:id', (req, res) => {
    const transactionId = req.params.id;
    const { description, amount, category, transactionType } = req.body;

    if (!description || isNaN(amount) || !category || !transactionType) {
        return res.status(400).json({ success: false, message: 'Bitte füllen Sie alle Felder aus.' });
    }

    const query = 'UPDATE transactions SET description = ?, amount = ?, category = ?, type = ? WHERE id = ?';
    connection.query(query, [description, amount, category, transactionType, transactionId], (err, results) => {
        if (err) {
            return res.status(500).json({ success: false, message: 'Fehler beim Aktualisieren der Transaktion.' });
        }
        res.json({ success: true });
    });
});

// API zum Löschen einer Transaktion
app.delete('/api/transactions/:id', (req, res) => {
    const transactionId = req.params.id;

    const query = 'DELETE FROM transactions WHERE id = ?';
    connection.query(query, [transactionId], (err, results) => {
        if (err) {
            return res.status(500).json({ success: false, message: 'Fehler beim Löschen der Transaktion.' });
        }
        res.json({ success: true });
    });
});

app.listen(PORT, () => console.log(`Server läuft auf http://localhost:${PORT}`));
