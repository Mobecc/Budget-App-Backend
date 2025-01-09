package de.htwberlin.budgetapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.htwberlin.budgetapp.model.BudgetItem;
import de.htwberlin.budgetapp.repository.BudgetItemRepository;

import java.util.List;

@Service
public class BudgetService {

    private static final Logger logger = LoggerFactory.getLogger(BudgetService.class);

    @Autowired
    private BudgetItemRepository repository;

    /**
     * Gibt alle Transaktionen sortiert nach Datum (absteigend) zurück.
     *
     * @return Eine Liste der BudgetItem-Objekte, sortiert nach Datum.
     */
    public List<BudgetItem> getAllTransactions() {
        logger.info("Abrufen aller Transaktionen sortiert nach Datum gestartet.");
        List<BudgetItem> transactions = repository.findAllByOrderByDatumDesc();
        logger.info("Abrufen abgeschlossen: {} Transaktionen gefunden.", transactions.size());
        return transactions;
    }

    /**
     * Fügt eine neue Transaktion hinzu.
     *
     * @param transaction Die hinzuzufügende Transaktion.
     * @return Das gespeicherte BudgetItem-Objekt.
     */
    public BudgetItem addTransaction(BudgetItem transaction) {
        logger.info("Hinzufügen einer neuen Transaktion: {}", transaction);
        BudgetItem savedTransaction = repository.save(transaction);
        logger.info("Transaktion erfolgreich gespeichert: {}", savedTransaction);
        return savedTransaction;
    }

    /**
     * Aktualisiert eine bestehende Transaktion.
     *
     * @param id Die ID der zu aktualisierenden Transaktion.
     * @param updatedTransaction Die neuen Werte für die Transaktion.
     * @return Das aktualisierte BudgetItem-Objekt.
     */
    public BudgetItem updateTransaction(Long id, BudgetItem updatedTransaction) {
        logger.info("Aktualisierung der Transaktion mit ID {} gestartet.", id);

        BudgetItem existingTransaction = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction with ID " + id + " not found."));

        logger.debug("Vorherige Transaktion: {}", existingTransaction);

        // Aktualisiere nur gültige Werte
        if (updatedTransaction.getBeschreibung() != null && !updatedTransaction.getBeschreibung().isEmpty()) {
            existingTransaction.setBeschreibung(updatedTransaction.getBeschreibung());
        }
        if (updatedTransaction.getBetrag() > 0) {
            existingTransaction.setBetrag(updatedTransaction.getBetrag());
        }
        if (updatedTransaction.getKategorie() != null && !updatedTransaction.getKategorie().isEmpty()) {
            existingTransaction.setKategorie(updatedTransaction.getKategorie());
        }
        if (updatedTransaction.getTyp() != null && !updatedTransaction.getTyp().isEmpty()) {
            existingTransaction.setTyp(updatedTransaction.getTyp());
        }
        if (updatedTransaction.getDatum() != null) {
            existingTransaction.setDatum(updatedTransaction.getDatum());
        }

        BudgetItem savedTransaction = repository.save(existingTransaction);

        logger.info("Transaktion erfolgreich aktualisiert: {}", savedTransaction);
        return savedTransaction;
    }

    /**
     * Löscht eine Transaktion anhand der ID.
     *
     * @param id Die ID der zu löschenden Transaktion.
     */
    public void deleteTransaction(Long id) {
        logger.info("Löschen der Transaktion mit ID {} gestartet.", id);
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Transaction with ID " + id + " not found.");
        }
        repository.deleteById(id);
        logger.info("Transaktion mit ID {} erfolgreich gelöscht.", id);
    }

    /**
     * Berechnet das Gesamtbudget, indem alle Transaktionen summiert werden.
     *
     * @return Die Gesamtsumme der Beträge aller Transaktionen.
     */
    public double calculateTotalBudget() {
        logger.info("Berechnung des Gesamtbudgets gestartet.");
        double total = repository.findAll().stream()
                .mapToDouble(item -> item.getTyp().equalsIgnoreCase("Einnahme") ? item.getBetrag() : -item.getBetrag())
                .sum();
        logger.info("Gesamtbudget berechnet: {}", total);
        return total;
    }

    /**
     * Überprüft, ob eine Transaktion mit der angegebenen ID existiert.
     *
     * @param id Die ID der Transaktion.
     * @return true, wenn die Transaktion existiert, andernfalls false.
     */
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
