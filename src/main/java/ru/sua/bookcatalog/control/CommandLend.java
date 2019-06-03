package ru.sua.bookcatalog.control;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.validators.PositiveInteger;
import lombok.Getter;
import ru.sua.bookcatalog.control.validation.LessThanNextCbn;
import ru.sua.bookcatalog.control.validation.PositiveLong;

import java.util.ArrayList;
import java.util.List;

@Getter
@Parameters(separators = "=", resourceBundle = "MessageBundle", commandDescriptionKey = "command.lend")
public class CommandLend {

    public static final String COMMAND_NAME = "lend";

    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = {"-c", "--cbn"}, descriptionKey = "command.parameter.cbn", required = true, validateWith = {PositiveLong.class, LessThanNextCbn.class})
    private long cbn;

    @Parameter(names = {"-w", "--whom"}, descriptionKey = "command.lend.whom", required = true)
    private String whom;

    @Parameter(names = {"-d", "--for-days"}, descriptionKey = "command.lend.fordays", required = true, validateWith = {PositiveInteger.class})
    private int fordays;

}
