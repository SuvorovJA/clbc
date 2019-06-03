package ru.sua.bookcatalog.control.validation;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import static ru.sua.bookcatalog.Tools.buildLocalizedMessage;
import static ru.sua.bookcatalog.Tools.makeBundleKey;

public class PositiveLong implements IParameterValidator {
    public void validate(String name, String value) throws ParameterException {
        long n = Long.parseLong(value);
        if (n <= 0) {
            throw new ParameterException(
                    buildLocalizedMessage(
                            makeBundleKey("parameter"),
                            name,
                            makeBundleKey("aboutpositive"),
                            value,
                            ")")
            );
        }
    }
}
