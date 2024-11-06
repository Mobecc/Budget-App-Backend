package de.htwberlin.budgetapp.repository;


import de.htwberlin.budgetapp.model.BudgetItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetItemRepository extends JpaRepository<BudgetItem, Long> {
}