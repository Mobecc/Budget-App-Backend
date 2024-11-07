package de.htwberlin.budgetapp.controller;

import de.htwberlin.budgetapp.model.BudgetItem;
import de.htwberlin.budgetapp.service.BudgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BudgetControllerTest {

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
        List<BudgetItem> result = budgetController.getAllTransactions();

        // Assert: Überprüfen, ob die zurückgegebene Liste leer ist
        assertEquals(0, result.size(), "Die Liste sollte leer sein.");
    }

    // Test für das Zurückgeben einer Liste mit einem BudgetItem
    @Test
    public void testGetAllTransactions_SingleTransaction() {
        // Arrange: Erstelle eine Beispiel-Transaktion und mocke den Service
        BudgetItem item = new BudgetItem("Test", 100.0, "Einnahme", "Sonstiges");
        List<BudgetItem> transactions = List.of(item);
        when(budgetService.getAllTransactions()).thenReturn(transactions);

        // Act: Rufe die Methode im Controller auf
        List<BudgetItem> result = budgetController.getAllTransactions();

        // Assert: Überprüfen, ob die Liste eine Transaktion enthält und die Daten korrekt sind
        assertEquals(1, result.size(), "Die Liste sollte eine Transaktion enthalten.");
        assertEquals("Test", result.get(0).getBeschreibung(), "Die Beschreibung sollte 'Test' sein.");
        assertEquals(100.0, result.get(0).getBetrag(), "Der Betrag sollte 100.0 sein.");
    }

    // Test für das Zurückgeben einer Liste mit mehreren BudgetItems
    @Test
    public void testGetAllTransactions_MultipleTransactions() {
        // Arrange: Erstelle mehrere Transaktionen und mocke den Service
        BudgetItem item1 = new BudgetItem("Lebensmittel", 50.0, "Ausgabe", "Lebensmittel");
        BudgetItem item2 = new BudgetItem("Gehalt", 2000.0, "Einnahme", "Arbeit");
        List<BudgetItem> transactions = List.of(item1, item2);
        when(budgetService.getAllTransactions()).thenReturn(transactions);

        // Act: Rufe die Methode im Controller auf
        List<BudgetItem> result = budgetController.getAllTransactions();

        // Assert: Überprüfen, ob die Liste beide Transaktionen enthält und die Daten korrekt sind
        assertEquals(2, result.size(), "Die Liste sollte zwei Transaktionen enthalten.");
        assertEquals("Lebensmittel", result.get(0).getBeschreibung(), "Die erste Transaktion sollte 'Lebensmittel' sein.");
        assertEquals(50.0, result.get(0).getBetrag(), "Der Betrag der ersten Transaktion sollte 50.0 sein.");
        assertEquals("Gehalt", result.get(1).getBeschreibung(), "Die zweite Transaktion sollte 'Gehalt' sein.");
        assertEquals(2000.0, result.get(1).getBetrag(), "Der Betrag der zweiten Transaktion sollte 2000.0 sein.");
    }
}