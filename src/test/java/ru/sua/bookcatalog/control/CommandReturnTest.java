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
class CommandReturnTest {

    @Mock
    private CatalogDAO dao;

    private JCommander jCommander;
    private Args appArgs;
    private CommandReturn commandReturn;

    @BeforeEach
    void setUp() {

        Tools.createCbnProvider(dao);

        Mockito.lenient().when(dao.getMaxCbn()).thenReturn(2L);

        appArgs = new Args();
        commandReturn = new CommandReturn();
        jCommander = JCommander.newBuilder()
                .addObject(appArgs)
                .addCommand(CommandReturn.COMMAND_NAME, commandReturn)
                .build();
    }


    @Test
    void commandReturnCorrectOption() {
        String[] args = {"return", "-c", "1"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandReturn.COMMAND_NAME);
        assertEquals(1L, commandReturn.getCbn());
    }

    @Test
    void commandReturnCorrectOptionLong() {
        String[] args = {"return", "--cbn", "1"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandReturn.COMMAND_NAME);
        assertEquals(1L, commandReturn.getCbn());
    }

    // detailed cbn test in CommandDeleteTest

    @Test
    void commandReturnIncorrectOption() {
        String[] args = {"return", "--pppp"};
        Assertions.assertThrows(ParameterException.class, () -> jCommander.parse(args));
    }

    @Test
    void commandReturnNoOption() {
        String[] args = {"return"};
        Assertions.assertThrows(ParameterException.class, () -> jCommander.parse(args));
    }


}