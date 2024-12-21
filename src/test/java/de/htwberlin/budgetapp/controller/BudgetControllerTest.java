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

import java.text.SimpleDateFormat;
import java.util.Date;

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
        when(budgetService.getAllTransactions()).thenReturn(new ArrayList<>());

        ResponseEntity<List<BudgetItem>> response = budgetController.getAllTransactions();
        List<BudgetItem> result = response.getBody();

        assertNotNull(result, "Die zurückgegebene Liste sollte nicht null sein.");
        assertEquals(0, result.size(), "Die Liste sollte leer sein.");

        logger.info("Test für leere Liste bestanden. Ergebnis: {}", result);
    }

    // Test für das Zurückgeben einer Liste mit einem BudgetItem
    @Test
    public void testGetAllTransactions_SingleTransaction() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date testDate = sdf.parse("2024-12-10");

        BudgetItem item = new BudgetItem("Test", 100.0, "Einnahme", "Kategorie", testDate);
        List<BudgetItem> transactions = List.of(item);
        when(budgetService.getAllTransactions()).thenReturn(transactions);

        ResponseEntity<List<BudgetItem>> response = budgetController.getAllTransactions();
        List<BudgetItem> result = response.getBody();

        assertNotNull(result, "Die zurückgegebene Liste sollte nicht null sein.");
        assertEquals(1, result.size(), "Die Liste sollte eine Transaktion enthalten.");
        assertEquals("Test", result.get(0).getBeschreibung());
        assertEquals(100.0, result.get(0).getBetrag());
        assertEquals("Einnahme", result.get(0).getTyp());
        assertEquals(testDate, result.get(0).getDatum());

        logger.info("Test für eine Transaktion bestanden. Ergebnis: {}", result);
    }

    // Test für das Zurückgeben einer Liste mit mehreren BudgetItems
    @Test
    public void testGetAllTransactions_MultipleTransactions() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse("2024-12-10");
        Date date2 = sdf.parse("2024-12-01");

        BudgetItem item1 = new BudgetItem("Lebensmittel", 50.0, "Ausgabe", "Kategorie1", date1);
        BudgetItem item2 = new BudgetItem("Gehalt", 2000.0, "Einnahme", "Kategorie2", date2);
        List<BudgetItem> transactions = List.of(item1, item2);

        when(budgetService.getAllTransactions()).thenReturn(transactions);

        ResponseEntity<List<BudgetItem>> response = budgetController.getAllTransactions();
        List<BudgetItem> result = response.getBody();

        assertNotNull(result, "Die zurückgegebene Liste sollte nicht null sein.");
        assertEquals(2, result.size(), "Die Liste sollte zwei Transaktionen enthalten.");

        assertEquals("Lebensmittel", result.get(0).getBeschreibung());
        assertEquals(50.0, result.get(0).getBetrag());
        assertEquals("Ausgabe", result.get(0).getTyp());
        assertEquals(date1, result.get(0).getDatum());

        assertEquals("Gehalt", result.get(1).getBeschreibung());
        assertEquals(2000.0, result.get(1).getBetrag());
        assertEquals("Einnahme", result.get(1).getTyp());
        assertEquals(date2, result.get(1).getDatum());

        logger.info("Test für mehrere Transaktionen bestanden. Ergebnis: {}", result);
    }
}
