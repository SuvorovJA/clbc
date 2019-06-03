package ru.sua.bookcatalog.control.validation;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import org.apache.commons.validator.routines.ISBNValidator;

import static ru.sua.bookcatalog.Tools.buildLocalizedMessage;
import static ru.sua.bookcatalog.Tools.makeBundleKey;

public class ISBN implements IParameterValidator {
    @Override
    public void validate(String name, String isbn) throws ParameterException {
        ISBNValidator isbnValidator = new ISBNValidator();
        if (!isbnValidator.isValid(isbn)) {
            throw new ParameterException(
                    buildLocalizedMessage(
                            makeBundleKey("parameter"),
                            name,
                            makeBundleKey("aboutisbn"),
                            isbn,
                            ")"
                    )
            );
        }
    }
}
