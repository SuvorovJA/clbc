package ru.sua.bookcatalog.control.validation;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import static ru.sua.bookcatalog.Tools.*;

public class LessThanNextCbn implements IParameterValidator {

    public void validate(String name, String value) throws ParameterException {
        long n = Long.parseLong(value);
        long nextCbn = getCbnProvider().getNextCbn();
        if (n >= nextCbn) {
            throw new ParameterException(
                    buildLocalizedMessage(
                            makeBundleKey("parameter"),
                            name,
                            makeBundleKey("aboutcbn1"),
                            nextCbn,
                            makeBundleKey("aboutcbn2"),
                            value,
                            ")"
                    )
            );
        }
    }
}
