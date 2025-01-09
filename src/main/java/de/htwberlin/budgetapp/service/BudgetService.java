package de.htwberlin.budgetapp.service;

import de.htwberlin.budgetapp.model.BudgetItem;
import de.htwberlin.budgetapp.repository.BudgetItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    private static final Logger logger = LoggerFactory.getLogger(BudgetService.class);

    @Autowired
    private BudgetItemRepository repository;

    public List<BudgetItem> getAllTransactions() {
        return repository.findAllByOrderByDatumDesc();
    }

    public BudgetItem addTransaction(BudgetItem transaction) {
        if (transaction.getBeschreibung() == null || transaction.getBeschreibung().isEmpty()) {
            throw new IllegalArgumentException("Beschreibung darf nicht leer sein.");
        }
        if (transaction.getBetrag() <= 0) {
            throw new IllegalArgumentException("Betrag muss größer als 0 sein.");
        }
        if (transaction.getKategorie() == null || transaction.getKategorie().isEmpty()) {
            throw new IllegalArgumentException("Kategorie darf nicht leer sein.");
        }
        if (transaction.getTyp() == null || transaction.getTyp().isEmpty()) {
            throw new IllegalArgumentException("Typ darf nicht leer sein.");
        }
        return repository.save(transaction);
    }

    public BudgetItem updateTransaction(Long id, BudgetItem updatedTransaction) {
        BudgetItem existingTransaction = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction with ID " + id + " not found."));

        logger.debug("Update initiated: Before: {}", existingTransaction);
        logger.debug("Update with: {}", updatedTransaction);

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

        logger.info("Update completed: Saved to DB: {}", savedTransaction);

        return savedTransaction;
    }

    public void deleteTransaction(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Transaction with ID " + id + " not found.");
        }
        repository.deleteById(id);
    }

    public double calculateTotalBudget() {
        return repository.findAll().stream()
                .mapToDouble(item -> "Einnahme".equalsIgnoreCase(item.getTyp()) ? item.getBetrag() : -item.getBetrag())
                .sum();
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
