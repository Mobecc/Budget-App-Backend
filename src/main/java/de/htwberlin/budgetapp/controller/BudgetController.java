package de.htwberlin.budgetapp.controller;

import de.htwberlin.budgetapp.model.BudgetItem;
import de.htwberlin.budgetapp.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BudgetController {

    @Autowired
    private BudgetService service;

    @GetMapping("/transactions")
    public List<BudgetItem> getAllTransactions() {
        return service.getAllTransactions();
    }

    @PostMapping("/transactions")
    public BudgetItem addTransaction(@RequestBody BudgetItem transaction) {
        return service.addTransaction(transaction);
    }

    @DeleteMapping("/transactions/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        service.deleteTransaction(id);
    }

    @GetMapping("/totalBudget")
    public double getTotalBudget() {
        return service.calculateTotalBudget();
    }
}