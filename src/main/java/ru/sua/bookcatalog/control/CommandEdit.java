package ru.sua.bookcatalog.control;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import lombok.Getter;
import ru.sua.bookcatalog.control.validation.ISBN;
import ru.sua.bookcatalog.control.validation.LessThanNextCbn;
import ru.sua.bookcatalog.control.validation.PositiveLong;
import ru.sua.bookcatalog.control.validation.YearOfPublication;

import java.util.ArrayList;
import java.util.List;

@Getter
@Parameters(separators = "=", resourceBundle = "MessageBundle", commandDescriptionKey = "command.edit")
public class CommandEdit {

    public static final String COMMAND_NAME = "edit";

    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = {"-c", "--cbn"}, descriptionKey = "command.parameter.cbn", required = true, validateWith = {PositiveLong.class, LessThanNextCbn.class})
    private long cbn;

    @Parameter(names = {"-a", "--author"}, descriptionKey = "command.add.author")
    private String author;

    @Parameter(names = {"-t", "--title"}, descriptionKey = "command.add.title")
    private String title;

    @Parameter(names = {"-y", "--year"}, descriptionKey = "command.add.year", validateWith = {YearOfPublication.class})
    private int yearOfPub;

    @Parameter(names = {"-i", "--isbn"}, descriptionKey = "command.add.isbn", validateWith = {ISBN.class})
    private String isbn;

    public SELECTED getSelection() {
        if (author == null && title == null && yearOfPub == 0 && isbn == null) {
            return SELECTED.NONE;
        } else {
            return SELECTED.YES;
        }
    }


    public enum SELECTED {
        YES, NONE
    }

}
