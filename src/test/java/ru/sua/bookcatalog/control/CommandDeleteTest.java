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
class CommandDeleteTest {

    @Mock
    private CatalogDAO dao;

    private JCommander jCommander;
    private Args appArgs;
    private CommandDelete delete;

    @BeforeEach
    void setUp() {

        Tools.createCbnProvider(dao);

        Mockito.lenient().when(dao.getMaxCbn()).thenReturn(2L);

        appArgs = new Args();
        delete = new CommandDelete();
        jCommander = JCommander.newBuilder()
                .addObject(appArgs)
                .addCommand(CommandDelete.COMMAND_NAME, delete)
                .build();
    }


    @Test
    void commandDeleteCorrectOption() {
        String[] args = {"delete", "-c", "1"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandDelete.COMMAND_NAME);
        assertEquals(1L, delete.getCbn());
    }

    @Test
    void commandDeleteCorrectOptionLong() {
        String[] args = {"delete", "--cbn", "1"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandDelete.COMMAND_NAME);
        assertEquals(1L, delete.getCbn());
    }

    @Test
    void commandDeleteInorrectOptionGreaterThanMax() {
        String[] args = {"delete", "-c", "2"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandDelete.COMMAND_NAME);
        Assertions.assertThrows(ParameterException.class, () -> jCommander.parse(args));
    }

    @Test
    void commandDeleteInorrectOptionZero() {
        String[] args = {"delete", "-c", "0"};
        Assertions.assertThrows(ParameterException.class, () -> jCommander.parse(args));
    }

    @Test
    void commandDeleteInorrectOptionNegate() {
        String[] args = {"delete", "-c", "-1"};
        Assertions.assertThrows(ParameterException.class, () -> jCommander.parse(args));
    }

    @Test
    void commandDeleteIncorrectOption() {
        String[] args = {"delete", "--pppp"};
        Assertions.assertThrows(ParameterException.class, () -> jCommander.parse(args));
    }

    @Test
    void commandDeleteNoOption() {
        String[] args = {"delete"};
        Assertions.assertThrows(ParameterException.class, () -> jCommander.parse(args));
    }


}