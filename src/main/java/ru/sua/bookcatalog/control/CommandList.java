package ru.sua.bookcatalog.control;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static ru.sua.bookcatalog.Tools.*;

@Getter
@Parameters(separators = "=", resourceBundle = "MessageBundle", commandDescriptionKey = "command.list")
public class CommandList {

    public static final String COMMAND_NAME = "list";

    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = {"-a", "--all"}, descriptionKey = "command.list.all")
    private boolean listAll = false;

    @Parameter(names = {"-l", "--lend"}, descriptionKey = "command.list.lend")
    private boolean listLend = false;

    @Parameter(names = {"-p", "--present"}, descriptionKey = "command.list.present")
    private boolean listPresent = false;


    public SELECTED getSelection() {
        if (isNotMutuallyExclusive(listAll, listLend, listPresent)) return SELECTED.FAIL;
        if (listAll) return SELECTED.ALL;
        if (listLend) return SELECTED.LEND;
        if (listPresent) return SELECTED.PRESENT;
        throw new ParameterException(buildLocalizedMessage("\"list\"", makeBundleKey("commandhaveunknownerror")));
    }

    public enum SELECTED {
        ALL, LEND, PRESENT, FAIL
    }
}
