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
class CommandLendTest {

    @Mock
    private CatalogDAO dao;

    private JCommander jCommander;
    private Args appArgs;
    private CommandLend lend;

    @BeforeEach
    void setUp() {

        Tools.createCbnProvider(dao);

        Mockito.lenient().when(dao.getMaxCbn()).thenReturn(2L);

        appArgs = new Args();
        lend = new CommandLend();
        jCommander = JCommander.newBuilder()
                .addObject(appArgs)
                .addCommand(CommandLend.COMMAND_NAME, lend)
                .build();
    }


    @Test
    void commandLendCorrectOption() {
        String[] args = {"lend", "-c", "1", "-w", "ivanov i i", "-d", "5"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandLend.COMMAND_NAME);
        assertEquals(1L, lend.getCbn());
        assertEquals("ivanov i i", lend.getWhom());
        assertEquals(5, lend.getFordays());
    }

    @Test
    void commandLendCorrectOptionLong() {
        String[] args = {"lend", "--cbn", "1", "--whom", "ivanov i i", "--for-days", "5"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandLend.COMMAND_NAME);
        assertEquals(1L, lend.getCbn());
        assertEquals("ivanov i i", lend.getWhom());
        assertEquals(5, lend.getFordays());
    }

    // detailed cbn test in CommandDeleteTest

    @Test
    void commandLendIncorrectOptionWhom() {
        String[] args = {"lend", "--whom"};
        Assertions.assertThrows(ParameterException.class, () -> jCommander.parse(args));
    }

    @Test
    void commandLendIncorrectOptionFordays() {
        String[] args = {"lend", "--for-days", "-1"};
        Assertions.assertThrows(ParameterException.class, () -> jCommander.parse(args));
    }

    @Test
    void commandLendIncorrectOption() {
        String[] args = {"lend", "--pppp"};
        Assertions.assertThrows(ParameterException.class, () -> jCommander.parse(args));
    }

    @Test
    void commandLendNoOption() {
        String[] args = {"lend"};
        Assertions.assertThrows(ParameterException.class, () -> jCommander.parse(args));
    }


}