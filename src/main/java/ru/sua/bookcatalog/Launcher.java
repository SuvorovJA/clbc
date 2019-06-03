package ru.sua.bookcatalog;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.MissingCommandException;
import com.beust.jcommander.ParameterException;
import lombok.extern.slf4j.Slf4j;
import ru.sua.bookcatalog.control.*;
import ru.sua.bookcatalog.dao.CatalogDAO;
import ru.sua.bookcatalog.dao.CatalogDAOImplByCommonsCSV;
import ru.sua.bookcatalog.domain.Book;

import java.io.Closeable;
import java.io.IOException;
import java.util.Date;

import static ru.sua.bookcatalog.Tools.*;

@Slf4j
public class Launcher {

    private static final String CATALOG = "catalog.csv";
    private static CatalogDAO dao = new CatalogDAOImplByCommonsCSV(CATALOG);

    public static void main(String[] args) {

        createCbnProvider(dao);

        Args appArgs = new Args();
        CommandList commandList = new CommandList();
        CommandDelete commandDelete = new CommandDelete();
        CommandLend commandLend = new CommandLend();
        CommandReturn commandReturn = new CommandReturn();
        CommandAdd commandAdd = new CommandAdd();
        CommandEdit commandEdit = new CommandEdit();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(appArgs)
                .addCommand(CommandList.COMMAND_NAME, commandList)
                .addCommand(CommandDelete.COMMAND_NAME, commandDelete)
                .addCommand(CommandLend.COMMAND_NAME, commandLend)
                .addCommand(CommandReturn.COMMAND_NAME, commandReturn)
                .addCommand(CommandAdd.COMMAND_NAME, commandAdd)
                .addCommand(CommandEdit.COMMAND_NAME, commandEdit)
                .build();

        if (args.length == 0) {
            jCommander.usage();
            System.exit(0);
        }

        try {
            jCommander.parse(args);

            if (appArgs.isHelp()) {
                jCommander.usage();
                System.exit(0);
            }

            switch (jCommander.getParsedCommand()) {
                case CommandList.COMMAND_NAME:
                    doListCommand(dao, commandList, jCommander);
                    break;
                case CommandDelete.COMMAND_NAME:
                    doDeleteCommand(dao, commandDelete);
                    saveCatalog(dao);
                    break;
                case CommandLend.COMMAND_NAME:
                    doLendCommand(dao, commandLend);
                    saveCatalog(dao);
                    break;
                case CommandReturn.COMMAND_NAME:
                    doReturnCommand(dao, commandReturn);
                    saveCatalog(dao);
                    break;
                case CommandAdd.COMMAND_NAME:
                    doAddCommand(dao, commandAdd);
                    saveCatalog(dao);
                    break;
                case CommandEdit.COMMAND_NAME:
                    doEditCommand(dao, commandEdit);
                    saveCatalog(dao);
                    break;
                default:
                    log.error(BUNDLE.getString("unknowncommand"));
                    jCommander.usage();
            }
        } catch (MissingCommandException e) {
            log.error(BUNDLE.getString("nocommand"));
        } catch (ParameterException e) {
            log.error(e.getLocalizedMessage());
            log.error(BUNDLE.getString("unknownoption"));
        }

    }

    private static void saveCatalog(CatalogDAO dao) {
        try {
            ((Closeable) dao).close();
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    private static void doEditCommand(CatalogDAO dao, CommandEdit cmd) {
        if (cmd.getSelection() == CommandEdit.SELECTED.YES) {
            if (dao.isExist(cmd.getCbn())) {
                Book book = dao.findbyId(cmd.getCbn());
                if (cmd.getAuthor() != null) book.setAuthor(cmd.getAuthor());
                if (cmd.getTitle() != null) book.setTitle(cmd.getTitle());
                if (cmd.getYearOfPub() != 0) book.setYearOfPub(cmd.getYearOfPub());
                if (cmd.getIsbn() != null) book.setIsbn(cmd.getIsbn());
                dao.edit(book);
            } else {
                log.warn(BUNDLE.getString("unexistentcbn"));
            }
        } else {
            log.error(BUNDLE.getString("unknownoption"));
        }
    }

    private static void doAddCommand(CatalogDAO dao, CommandAdd cmd) {
        Book book = new Book();
        book.setAuthor(cmd.getAuthor());
        book.setTitle(cmd.getTitle());
        book.setYearOfPub(cmd.getYearOfPub());
        book.setIsbn(cmd.getIsbn());
        dao.add(book);
    }

    private static void doReturnCommand(CatalogDAO dao, CommandReturn cmd) {
        if (dao.isExist(cmd.getCbn())) {
            dao.returnBook(cmd.getCbn());
        } else {
            log.warn(BUNDLE.getString("unexistentcbn"));
        }
    }

    private static void doLendCommand(CatalogDAO dao, CommandLend cmd) {
        if (dao.isExist(cmd.getCbn())) {
            if (dao.isLent(cmd.getCbn())) {
                log.warn(BUNDLE.getString("alreadylent"));
            } else {
                dao.lendBook(cmd.getCbn(), cmd.getWhom(), addDaysToDate(new Date(), cmd.getFordays()));
            }
        } else {
            log.warn(BUNDLE.getString("unexistentcbn"));
        }
    }

    private static void doDeleteCommand(CatalogDAO dao, CommandDelete cmd) {
        if (dao.isExist(cmd.getCbn())) {
            dao.delete(cmd.getCbn());
        } else {
            log.warn(BUNDLE.getString("unexistentcbn"));
        }
    }


    private static void doListCommand(CatalogDAO dao, CommandList commandList, JCommander jCommander) {
        switch (commandList.getSelection()) {
            case ALL:
                prettyPrint(dao.findAll());
                break;
            case LEND:
                prettyPrint(dao.findAllLend());
                break;
            case PRESENT:
                prettyPrint(dao.findAllPresent());
                break;
            case FAIL:
                log.error(BUNDLE.getString("incorrectcombination"));
                jCommander.usage();
                break;
        }
        return;
    }
}
