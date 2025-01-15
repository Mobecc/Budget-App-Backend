package de.htwberlin.budgetapp.service;

import de.htwberlin.budgetapp.model.BudgetItem;
import de.htwberlin.budgetapp.repository.BudgetItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BudgetServiceTest {

    @Mock
    private BudgetItemRepository repository;

    @InjectMocks
    private BudgetService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddTransaction() {
        BudgetItem newItem = new BudgetItem("Test", 100.0, "Miete", "Einnahme", new Date());
        when(repository.save(newItem)).thenReturn(newItem);

        BudgetItem result = service.addTransaction(newItem);

        assertNotNull(result);
        assertEquals("Test", result.getBeschreibung());
        verify(repository, times(1)).save(newItem);
    }

    @Test
    public void testUpdateTransaction() {
        BudgetItem existingItem = new BudgetItem("Test", 100.0, "Miete", "Einnahme", new Date());
        existingItem.setId(1L);

        BudgetItem updatedItem = new BudgetItem("Updated", 200.0, "Lebensmittel", "Ausgabe", new Date());

        when(repository.findById(1L)).thenReturn(Optional.of(existingItem));
        when(repository.save(existingItem)).thenReturn(existingItem);

        BudgetItem result = service.updateTransaction(1L, updatedItem);

        assertNotNull(result);
        assertEquals("Updated", result.getBeschreibung());
        assertEquals(200.0, result.getBetrag());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(existingItem);
    }

    @Test
    public void testDeleteTransaction() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.deleteTransaction(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    public void testCalculateTotalBudget() {
        List<BudgetItem> items = Arrays.asList(
                new BudgetItem("Test1", 100.0, "Miete", "Einnahme", new Date()),
                new BudgetItem("Test2", 50.0, "Transport", "Ausgabe", new Date())
        );

        when(repository.findAll()).thenReturn(items);

        double totalBudget = service.calculateTotalBudget();

        assertEquals(150.0, totalBudget);
        verify(repository, times(1)).findAll();
    }
}