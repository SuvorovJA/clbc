package ru.sua.bookcatalog.control;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sua.bookcatalog.Tools;
import ru.sua.bookcatalog.dao.CatalogDAO;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class CommandEditTest {

    @Mock
    private CatalogDAO dao;

    private JCommander jCommander;
    private Args appArgs;
    private CommandEdit commandEdit;

    @BeforeEach
    void setUp() {

        Tools.createCbnProvider(dao);

        Mockito.lenient().when(dao.getMaxCbn()).thenReturn(2L);

        appArgs = new Args();
        commandEdit = new CommandEdit();
        jCommander = JCommander.newBuilder()
                .addObject(appArgs)
                .addCommand(CommandEdit.COMMAND_NAME, commandEdit)
                .build();
    }

    @Test
    void commandEditCorrectOptionAllISBN13() {
        String[] args = {"edit", "-c", "1", "-a", "john doe", "-t", "best book title", "-y", "2019", "-i", "978-5-93878-705-6"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandEdit.COMMAND_NAME);
        assertEquals(1L, commandEdit.getCbn());
        assertEquals("john doe", commandEdit.getAuthor());
        assertEquals("best book title", commandEdit.getTitle());
        assertEquals(2019, commandEdit.getYearOfPub());
        assertEquals("978-5-93878-705-6", commandEdit.getIsbn());
    }

    @Test
    void commandEditCorrectOptionAllISBN10() {
        String[] args = {"edit", "-c", "1", "-a", "john doe", "-t", "best book title", "-y", "2019", "-i", "0-19-852663-6"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandEdit.COMMAND_NAME);
        assertEquals(1L, commandEdit.getCbn());
        assertEquals("john doe", commandEdit.getAuthor());
        assertEquals("best book title", commandEdit.getTitle());
        assertEquals(2019, commandEdit.getYearOfPub());
        assertEquals("0-19-852663-6", commandEdit.getIsbn());
    }

    @Test
    void commandEditCorrectOneOptionISBN10() {
        String[] args = {"edit", "-c", "1", "-i", "0-19-852663-6"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandEdit.COMMAND_NAME);
        assertEquals(1L, commandEdit.getCbn());
        assertEquals("0-19-852663-6", commandEdit.getIsbn());
    }

    @Test
    void commandEditCorrectOneOptionYear() {
        String[] args = {"edit", "-c", "1", "-y", "2019"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandEdit.COMMAND_NAME);
        assertEquals(1L, commandEdit.getCbn());
        assertEquals(2019, commandEdit.getYearOfPub());
    }

    @Test
    void commandEditCorrectOneOptionTitle() {
        String[] args = {"edit", "-c", "1", "-t", "best book title"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandEdit.COMMAND_NAME);
        assertEquals(1L, commandEdit.getCbn());
        assertEquals("best book title", commandEdit.getTitle());
    }

    @Test
    void commandEditCorrectOneOptionAuthor() {
        String[] args = {"edit", "-c", "1", "-a", "john doe"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandEdit.COMMAND_NAME);
        assertEquals(1L, commandEdit.getCbn());
        assertEquals("john doe", commandEdit.getAuthor());
    }

    @Test
    void commandEditIncorrectOptionISBN13() {
        String[] args = {"edit", "-c", "1", "-a", "john doe", "-t", "best book title", "-y", "2019", "-i", "0-19-85266-6"};
        Assertions.assertThrows(ParameterException.class, () -> jCommander.parse(args));
    }

    @Test
    void commandEditIncorrectOptionYear() {
        String[] args = {"edit", "-c", "1", "-a", "john doe", "-t", "best book title", "-y", "20019", "-i", "0-19-85266-6"};
        Assertions.assertThrows(ParameterException.class, () -> jCommander.parse(args));
    }


}