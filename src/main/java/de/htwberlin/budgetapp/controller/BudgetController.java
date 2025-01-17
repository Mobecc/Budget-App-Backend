package de.htwberlin.budgetapp.controller;

import de.htwberlin.budgetapp.model.BudgetItem;
import de.htwberlin.budgetapp.service.BudgetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BudgetController {

    private static final Logger logger = LoggerFactory.getLogger(BudgetController.class);

    @Autowired
    private BudgetService service;

    @GetMapping("/transactions")
    public ResponseEntity<List<BudgetItem>> getAllTransactions() {
        logger.info("GET /transactions - Abrufen aller Transaktionen gestartet.");
        try {
            List<BudgetItem> transactions = service.getAllTransactions();
            logger.info("GET /transactions - {} Transaktionen erfolgreich abgerufen.", transactions.size());
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("GET /transactions - Fehler beim Abrufen der Transaktionen.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/transactions")
    public ResponseEntity<BudgetItem> addTransaction(@RequestBody BudgetItem transaction) {
        logger.info("POST /transactions - Neue Transaktion wird verarbeitet: {}", transaction);
        try {
            BudgetItem savedTransaction = service.addTransaction(transaction);
            logger.info("POST /transactions - Transaktion erfolgreich gespeichert: {}", savedTransaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
        } catch (IllegalArgumentException e) {
            logger.warn("POST /transactions - Ungültige Transaktionsdaten: {}", transaction, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.error("POST /transactions - Fehler beim Hinzufügen der Transaktion.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<BudgetItem> updateTransaction(
            @PathVariable Long id, @RequestBody BudgetItem updatedTransaction) {
        logger.info("PUT /transactions/{} - Vollständige Aktualisierung gestartet: {}", id, updatedTransaction);

        try {
            // Existenzprüfung über den Service
            if (!service.existsById(id)) {
                logger.warn("PUT /transactions/{} - Transaktion nicht gefunden.", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Aktualisierung der Transaktion über den Service
            BudgetItem savedTransaction = service.updateTransaction(id, updatedTransaction);
            logger.info("PUT /transactions/{} - Erfolgreich aktualisiert: {}", id, savedTransaction);

            return ResponseEntity.ok(savedTransaction);

        } catch (IllegalArgumentException e) {
            logger.warn("PUT /transactions/{} - Ungültige Daten: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error("PUT /transactions/{} - Fehler beim Aktualisieren.", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        logger.info("DELETE /transactions/{} - Löschen der Transaktion gestartet.", id);
        try {
            if (!service.existsById(id)) {
                logger.warn("DELETE /transactions/{} - Transaktion nicht gefunden.", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            service.deleteTransaction(id);
            logger.info("DELETE /transactions/{} - Transaktion erfolgreich gelöscht.", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("DELETE /transactions/{} - Fehler beim Löschen der Transaktion.", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
