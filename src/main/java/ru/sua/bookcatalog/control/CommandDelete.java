package ru.sua.bookcatalog.control;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import lombok.Getter;
import ru.sua.bookcatalog.control.validation.LessThanNextCbn;
import ru.sua.bookcatalog.control.validation.PositiveLong;

import java.util.ArrayList;
import java.util.List;

@Getter
@Parameters(separators = "=", resourceBundle = "MessageBundle", commandDescriptionKey = "command.delete")
public class CommandDelete {

    public static final String COMMAND_NAME = "delete";

    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = {"-c", "--cbn"}, descriptionKey = "command.parameter.cbn", required = true, validateWith = {PositiveLong.class, LessThanNextCbn.class})
    private long cbn;

}
