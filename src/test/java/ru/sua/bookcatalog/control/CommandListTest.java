package ru.sua.bookcatalog.control;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.MissingCommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandListTest {

    private JCommander jCommander;
    private Args appArgs;
    private CommandList list;

    @BeforeEach
    void setUp() {
        appArgs = new Args();
        list = new CommandList();
        jCommander = JCommander.newBuilder()
                .addObject(appArgs)
                .addCommand(CommandList.COMMAND_NAME, list)
                .build();
    }

    @Test
    void getSelectionCorrectOptionAll() {
        String[] args = {"list", "-a"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandList.COMMAND_NAME);
        assertEquals(list.getSelection(), CommandList.SELECTED.ALL);
    }

    @Test
    void getSelectionCorrectOptionLend() {
        String[] args = {"list", "-l"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandList.COMMAND_NAME);
        assertEquals(list.getSelection(), CommandList.SELECTED.LEND);
    }

    @Test
    void getSelectionCorrectOptionPresent() {
        String[] args = {"list", "-p"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandList.COMMAND_NAME);
        assertEquals(list.getSelection(), CommandList.SELECTED.PRESENT);
    }

    @Test
    void getSelectionCorrectOptionAllLong() {
        String[] args = {"list", "--all"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandList.COMMAND_NAME);
        assertEquals(list.getSelection(), CommandList.SELECTED.ALL);
    }

    @Test
    void getSelectionCorrectOptionLendLong() {
        String[] args = {"list", "--lend"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandList.COMMAND_NAME);
        assertEquals(list.getSelection(), CommandList.SELECTED.LEND);
    }

    @Test
    void getSelectionCorrectOptionPresentLong() {
        String[] args = {"list", "--present"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandList.COMMAND_NAME);
        assertEquals(list.getSelection(), CommandList.SELECTED.PRESENT);
    }

    @Test
    void getSelectionIncorrectOptionsPresentLongAndAll() {
        String[] args = {"list", "--present", "-a"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandList.COMMAND_NAME);
        assertEquals(list.getSelection(), CommandList.SELECTED.FAIL);
    }

    @Test
    void getSelectionIncorrectOption() {
        String[] args = {"list", "--pppp"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandList.COMMAND_NAME);
        assertEquals(list.getSelection(), CommandList.SELECTED.FAIL);
    }

    @Test
    void getSelectionNoOption() {
        String[] args = {"list"};
        jCommander.parse(args);
        assertEquals(jCommander.getParsedCommand(), CommandList.COMMAND_NAME);
        assertEquals(list.getSelection(), CommandList.SELECTED.FAIL);
    }

    @Test
    void getSelectionIncorrectCommand() {
        String[] args = {"pppp"};
        Assertions.assertThrows(MissingCommandException.class, () -> jCommander.parse(args));
    }

    @Test
    void getSelectionNoCommand() {
        String[] args = {""};
        Assertions.assertThrows(MissingCommandException.class, () -> jCommander.parse(args));
    }
}