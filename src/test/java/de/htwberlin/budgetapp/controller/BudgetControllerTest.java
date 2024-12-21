package de.htwberlin.budgetapp.controller;

import de.htwberlin.budgetapp.model.BudgetItem;
import de.htwberlin.budgetapp.service.BudgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class BudgetControllerTest {

    @Mock
    private BudgetService budgetService;

    @InjectMocks
    private BudgetController budgetController;

    private SimpleDateFormat sdf;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sdf = new SimpleDateFormat("yyyy-MM-dd"); // Format für Datum
    }

    @Test
    public void testGetAllTransactions_SingleTransactionWithDate() throws Exception {
        // Arrange: Erstelle ein Datum und eine Transaktion
        Date testDate = sdf.parse("2024-12-10");
        BudgetItem item = new BudgetItem("Test", 100.0, "Kategorie", "Einnahme", testDate);
        List<BudgetItem> transactions = List.of(item);

        // Mock den Service
        when(budgetService.getAllTransactions()).thenReturn(transactions);

        // Act: Rufe die Methode im Controller auf
        ResponseEntity<List<BudgetItem>> response = budgetController.getAllTransactions();
        List<BudgetItem> result = response.getBody();

        // Assert: Überprüfen, ob die Transaktion korrekt zurückgegeben wird
        assertNotNull(result, "Die zurückgegebene Liste sollte nicht null sein.");
        assertEquals(1, result.size(), "Die Liste sollte genau eine Transaktion enthalten.");
        assertEquals("Test", result.get(0).getBeschreibung());
        assertEquals(100.0, result.get(0).getBetrag());
        assertEquals("Kategorie", result.get(0).getKategorie()); // Korrigiert
        assertEquals("Einnahme", result.get(0).getTyp()); // Korrigiert
        assertEquals(sdf.format(testDate), sdf.format(result.get(0).getDatum()), "Das Datum sollte übereinstimmen.");
    }

    @Test
    public void testGetAllTransactions_MultipleTransactionsWithDates() throws Exception {
        // Arrange: Erstelle mehrere Transaktionen mit Datum
        Date date1 = sdf.parse("2024-12-10");
        Date date2 = sdf.parse("2024-12-01");
        BudgetItem item1 = new BudgetItem("Lebensmittel", 50.0, "Kategorie1", "Ausgabe", date1);
        BudgetItem item2 = new BudgetItem("Gehalt", 2000.0, "Kategorie2", "Einnahme", date2);
        List<BudgetItem> transactions = List.of(item1, item2);

        // Mock den Service
        when(budgetService.getAllTransactions()).thenReturn(transactions);

        // Act: Rufe die Methode im Controller auf
        ResponseEntity<List<BudgetItem>> response = budgetController.getAllTransactions();
        List<BudgetItem> result = response.getBody();

        // Assert: Überprüfen, ob die Transaktionen korrekt zurückgegeben werden
        assertNotNull(result, "Die zurückgegebene Liste sollte nicht null sein.");
        assertEquals(2, result.size(), "Die Liste sollte zwei Transaktionen enthalten.");

        // Erste Transaktion
        assertEquals("Lebensmittel", result.get(0).getBeschreibung());
        assertEquals(50.0, result.get(0).getBetrag());
        assertEquals("Kategorie1", result.get(0).getKategorie()); // Korrigiert
        assertEquals("Ausgabe", result.get(0).getTyp()); // Korrigiert
        assertEquals(sdf.format(date1), sdf.format(result.get(0).getDatum()));

        // Zweite Transaktion
        assertEquals("Gehalt", result.get(1).getBeschreibung());
        assertEquals(2000.0, result.get(1).getBetrag());
        assertEquals("Kategorie2", result.get(1).getKategorie()); // Korrigiert
        assertEquals("Einnahme", result.get(1).getTyp()); // Korrigiert
        assertEquals(sdf.format(date2), sdf.format(result.get(1).getDatum()));
    }
}