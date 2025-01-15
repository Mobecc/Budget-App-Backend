package de.htwberlin.budgetapp.controller;

import de.htwberlin.budgetapp.model.BudgetItem;
import de.htwberlin.budgetapp.service.BudgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BudgetControllerTest {

    @Mock
    private BudgetService service;

    @InjectMocks
    private BudgetController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTransactions() {
        List<BudgetItem> items = Arrays.asList(
                new BudgetItem("Test1", 100.0, "Miete", "Einnahme", new Date()),
                new BudgetItem("Test2", 50.0, "Transport", "Ausgabe", new Date())
        );

        when(service.getAllTransactions()).thenReturn(items);

        ResponseEntity<List<BudgetItem>> response = controller.getAllTransactions();

        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service, times(1)).getAllTransactions();
    }

    @Test
    public void testAddTransaction() {
        BudgetItem newItem = new BudgetItem("Test", 100.0, "Miete", "Einnahme", new Date());
        when(service.addTransaction(newItem)).thenReturn(newItem);

        ResponseEntity<BudgetItem> response = controller.addTransaction(newItem);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Test", response.getBody().getBeschreibung());
        verify(service, times(1)).addTransaction(newItem);
    }

    @Test
    public void testUpdateTransaction() {
        BudgetItem updatedItem = new BudgetItem("Updated", 200.0, "Lebensmittel", "Ausgabe", new Date());
        when(service.updateTransaction(1L, updatedItem)).thenReturn(updatedItem);

        ResponseEntity<BudgetItem> response = controller.updateTransaction(1L, updatedItem);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated", response.getBody().getBeschreibung());
        verify(service, times(1)).updateTransaction(1L, updatedItem);
    }

    @Test
    public void testDeleteTransaction() {
        doNothing().when(service).deleteTransaction(1L);

        ResponseEntity<Void> response = controller.deleteTransaction(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(service, times(1)).deleteTransaction(1L);
    }
}