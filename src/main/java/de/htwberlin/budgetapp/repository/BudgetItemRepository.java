package de.htwberlin.budgetapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import de.htwberlin.budgetapp.model.BudgetItem;

import java.util.List;

@Repository
public interface BudgetItemRepository extends JpaRepository<BudgetItem, Long> {

    /**
     * Benutzerdefinierte Query: Sortiere die Transaktionen nach Datum absteigend.
     */
    @Query("SELECT b FROM BudgetItem b ORDER BY b.datum DESC")
    List<BudgetItem> findAllSortedByDate();

    /**
     * Automatisch generierte Methode: Sortiere die Transaktionen nach Datum absteigend.
     * Funktioniert nur, wenn 'datum' korrekt als DATE oder TIMESTAMP in der Datenbank definiert ist.
     */
    List<BudgetItem> findAllByOrderByDatumDesc();
}
