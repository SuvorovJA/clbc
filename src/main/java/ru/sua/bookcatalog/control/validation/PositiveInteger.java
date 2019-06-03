package ru.sua.bookcatalog.control.validation;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import static ru.sua.bookcatalog.Tools.buildLocalizedMessage;
import static ru.sua.bookcatalog.Tools.makeBundleKey;

public class PositiveInteger implements IParameterValidator {
    public void validate(String name, String value) throws ParameterException {
        int n = Integer.parseInt(value);
        if (n <= 0) {
            throw new ParameterException(buildLocalizedMessage(
                    makeBundleKey("parameter"),
                    name,
                    makeBundleKey("aboutpositive"),
                    value,
                    ")"));
        }
    }
}
