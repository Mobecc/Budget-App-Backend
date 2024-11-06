package de.htwberlin.budgetapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.htwberlin.budgetapp.model.BudgetItem;
import de.htwberlin.budgetapp.repository.BudgetItemRepository;
import java.util.List;
import java.util.Optional;


@Service
public class BudgetService {

    @Autowired
    private BudgetItemRepository repository;

    public List<BudgetItem> getAllTransactions() {
        return repository.findAll();
    }

    public BudgetItem addTransaction(BudgetItem transaction) {
        return repository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        repository.deleteById(id);
    }

    public double calculateTotalBudget() {
        return repository.findAll().stream().mapToDouble(BudgetItem::getBetrag).sum();
    }
}