package ru.sua.bookcatalog.control;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandAddTest {

    private JCommander jCommander;
    private Args appArgs;
    private CommandAdd commandAdd;

    @BeforeEach
    void setUp() {
        appArgs = new Args();
        commandAdd = new CommandAdd();
        jCommander = JCommander.newBuilder()
                .addObject(appArgs)
                .addCommand(CommandAdd.COMMAND_NAME, commandAdd)
                .build();
    }

    @Test
    void commandAddCorrectOptionAllISBN13() {
        String[] args = {"add", "-a", "john doe", "-t", "best book title", "-y", "2019", "-i", "978-5-93878-705-6"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandAdd.COMMAND_NAME);
        assertEquals("john doe", commandAdd.getAuthor());
        assertEquals("best book title", commandAdd.getTitle());
        assertEquals(2019, commandAdd.getYearOfPub());
        assertEquals("978-5-93878-705-6", commandAdd.getIsbn());
    }

    @Test
    void commandAddCorrectOptionAllISBN10() {
        String[] args = {"add", "-a", "john doe", "-t", "best book title", "-y", "2019", "-i", "0-19-852663-6"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandAdd.COMMAND_NAME);
        assertEquals("john doe", commandAdd.getAuthor());
        assertEquals("best book title", commandAdd.getTitle());
        assertEquals(2019, commandAdd.getYearOfPub());
        assertEquals("0-19-852663-6", commandAdd.getIsbn());
    }

    @Test
    void commandAddIncorrectOptionISBN13() {
        String[] args = {"add", "-a", "john doe", "-t", "best book title", "-y", "2019", "-i", "0-19-85266-6"};
        Assertions.assertThrows(ParameterException.class, () -> jCommander.parse(args));
    }

    @Test
    void commandAddIncorrectOptionYear() {
        String[] args = {"add", "-a", "john doe", "-t", "best book title", "-y", "20019", "-i", "0-19-85266-6"};
        Assertions.assertThrows(ParameterException.class, () -> jCommander.parse(args));
    }

    @Test
    void commandAddIncorrectOptionAuthor() {
        String[] args = {"add", "john doe", "-t", "best book title", "-y", "20019", "-i", "0-19-85266-6"};
        Assertions.assertThrows(ParameterException.class, () -> jCommander.parse(args));
    }

    @Test
    void commandAddIncorrectOptionTitle() {
        String[] args = {"add", "-a", "john doe", "best book title", "-y", "20019", "-i", "0-19-85266-6"};
        Assertions.assertThrows(ParameterException.class, () -> jCommander.parse(args));
    }

}