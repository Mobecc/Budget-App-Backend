package de.htwberlin.budgetapp.controller;

import de.htwberlin.budgetapp.model.BudgetItem;
import de.htwberlin.budgetapp.service.BudgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class BudgetControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(BudgetControllerTest.class);

    @Mock
    private BudgetService budgetService;

    @InjectMocks
    private BudgetController budgetController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test für das Zurückgeben einer leeren Liste
    @Test
    public void testGetAllTransactions_EmptyList() {
        // Arrange: Mock den Service, um eine leere Liste zurückzugeben
        when(budgetService.getAllTransactions()).thenReturn(new ArrayList<>());

        // Act: Rufe die Methode im Controller auf
        ResponseEntity<List<BudgetItem>> response = budgetController.getAllTransactions();
        List<BudgetItem> result = response.getBody(); // Extrahiere die Liste aus ResponseEntity

        // Assert: Überprüfen, ob die zurückgegebene Liste leer ist
        assertNotNull(result, "Die zurückgegebene Liste sollte nicht null sein.");
        assertEquals(0, result.size(), "Die Liste sollte leer sein.");

        // Logging
        logger.info("Test für leere Liste bestanden. Ergebnis: {}", result);
    }

    // Test für das Zurückgeben einer Liste mit einem BudgetItem
    @Test
    public void testGetAllTransactions_SingleTransaction() {
        // Arrange: Erstelle eine Beispiel-Transaktion und mocke den Service
        BudgetItem item = new BudgetItem("Test", 100.0, "2024-12-10", "Einnahme");
        List<BudgetItem> transactions = List.of(item);
        when(budgetService.getAllTransactions()).thenReturn(transactions);

        // Act: Rufe die Methode im Controller auf
        ResponseEntity<List<BudgetItem>> response = budgetController.getAllTransactions();
        List<BudgetItem> result = response.getBody(); // Extrahiere die Liste aus ResponseEntity

        // Assert: Überprüfen, ob die Liste eine Transaktion enthält und die Daten korrekt sind
        assertNotNull(result, "Die zurückgegebene Liste sollte nicht null sein.");
        assertEquals(1, result.size(), "Die Liste sollte eine Transaktion enthalten.");
        assertEquals("Test", result.get(0).getBeschreibung(), "Die Beschreibung sollte 'Test' sein.");
        assertEquals(100.0, result.get(0).getBetrag(), "Der Betrag sollte 100.0 sein.");
        assertEquals("Einnahme", result.get(0).getKategorie(), "Die Kategorie sollte 'Einnahme' sein.");

        // Logging
        logger.info("Test für eine Transaktion bestanden. Ergebnis: {}", result);
    }

    // Test für das Zurückgeben einer Liste mit mehreren BudgetItems
    @Test
    public void testGetAllTransactions_MultipleTransactions() {
        // Arrange: Erstelle mehrere Transaktionen und mocke den Service
        BudgetItem item1 = new BudgetItem("Lebensmittel", 50.0, "2024-12-10", "Ausgabe");
        BudgetItem item2 = new BudgetItem("Gehalt", 2000.0, "2024-12-01", "Einnahme");
        List<BudgetItem> transactions = List.of(item1, item2);
        when(budgetService.getAllTransactions()).thenReturn(transactions);

        // Act: Rufe die Methode im Controller auf
        ResponseEntity<List<BudgetItem>> response = budgetController.getAllTransactions();
        List<BudgetItem> result = response.getBody(); // Extrahiere die Liste aus ResponseEntity

        // Assert: Überprüfen, ob die Liste beide Transaktionen enthält und die Daten korrekt sind
        assertNotNull(result, "Die zurückgegebene Liste sollte nicht null sein.");
        assertEquals(2, result.size(), "Die Liste sollte zwei Transaktionen enthalten.");

        // Erste Transaktion
        assertEquals("Lebensmittel", result.get(0).getBeschreibung(), "Die erste Transaktion sollte 'Lebensmittel' sein.");
        assertEquals(50.0, result.get(0).getBetrag(), "Der Betrag der ersten Transaktion sollte 50.0 sein.");
        assertEquals("Ausgabe", result.get(0).getKategorie(), "Die Kategorie der ersten Transaktion sollte 'Ausgabe' sein.");

        // Zweite Transaktion
        assertEquals("Gehalt", result.get(1).getBeschreibung(), "Die zweite Transaktion sollte 'Gehalt' sein.");
        assertEquals(2000.0, result.get(1).getBetrag(), "Der Betrag der zweiten Transaktion sollte 2000.0 sein.");
        assertEquals("Einnahme", result.get(1).getKategorie(), "Die Kategorie der zweiten Transaktion sollte 'Einnahme' sein.");

        // Logging
        logger.info("Test für mehrere Transaktionen bestanden. Ergebnis: {}", result);
    }
}
