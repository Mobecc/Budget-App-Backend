package de.htwberlin.budgetapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.htwberlin.budgetapp.model.BudgetItem;
import de.htwberlin.budgetapp.repository.BudgetItemRepository;

import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetItemRepository repository;

    /**
     * Gibt alle Transaktionen sortiert nach Datum (absteigend) zurück.
     *
     * @return Eine Liste der BudgetItem-Objekte, sortiert nach Datum.
     */
    public List<BudgetItem> getAllTransactions() {
        return repository.findAllByOrderByDatumDesc();
    }

    /**
     * Fügt eine neue Transaktion hinzu.
     *
     * @param transaction Die hinzuzufügende Transaktion.
     * @return Das gespeicherte BudgetItem-Objekt.
     */
    public BudgetItem addTransaction(BudgetItem transaction) {
        return repository.save(transaction);
    }

    /**
     * Löscht eine Transaktion anhand der ID.
     *
     * @param id Die ID der zu löschenden Transaktion.
     * @throws IllegalArgumentException, wenn die Transaktion nicht existiert.
     */
    public void deleteTransaction(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Transaction with ID " + id + " not found.");
        }
        repository.deleteById(id);
    }

    /**
     * Berechnet das Gesamtbudget, indem alle Transaktionen summiert werden.
     *
     * @return Die Gesamtsumme der Beträge aller Transaktionen.
     */
    public double calculateTotalBudget() {
        return repository.findAll().stream()
                .mapToDouble(BudgetItem::getBetrag)
                .sum();
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
