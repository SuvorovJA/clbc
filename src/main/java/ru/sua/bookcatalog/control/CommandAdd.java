package ru.sua.bookcatalog.control;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import lombok.Getter;
import ru.sua.bookcatalog.control.validation.ISBN;
import ru.sua.bookcatalog.control.validation.YearOfPublication;

import java.util.ArrayList;
import java.util.List;

@Getter
@Parameters(separators = "=", resourceBundle = "MessageBundle", commandDescriptionKey = "command.add")
public class CommandAdd {

    public static final String COMMAND_NAME = "add";

    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = {"-a", "--author"}, descriptionKey = "command.add.author", required = true)
    private String author;

    @Parameter(names = {"-t", "--title"}, descriptionKey = "command.add.title", required = true)
    private String title;

    @Parameter(names = {"-y", "--year"}, descriptionKey = "command.add.year", validateWith = {YearOfPublication.class})
    private int yearOfPub;

    @Parameter(names = {"-i", "--isbn"}, descriptionKey = "command.add.isbn", validateWith = {ISBN.class})
    private String isbn = "";

}
