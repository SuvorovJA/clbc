package ru.sua.bookcatalog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    public static final String[] CSV_HEADERS = new String[]{"cbn", "author", "title", "yearOfPub", "isbn",
            "created", "modified",
            "lendWhom", "lendBefore"};

    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";

    public static SimpleDateFormat getDateFormatter() {
        return new SimpleDateFormat(DATE_FORMAT);
    }

    private long cbn;
    private String author;
    private String title;
    private int yearOfPub;
    private String isbn;
    private Date created;
    private Date modified;
    private String lendWhom;
    private Date lendBefore;

    @Override
    public String toString() {
        return "Book(" +
                cbn + ";" +
                Objects.toString(author, "") + ";" +
                Objects.toString(title, "") + ";" +
                yearOfPub + ";" +
                Objects.toString(isbn, "") + ";" +
                Objects.toString(created, "") + ";" +
                Objects.toString(modified, "") + ";" +
                Objects.toString(lendWhom, "") + ";" +
                Objects.toString(lendBefore, "") + ")";
    }

    public Object[] asArray() {
        DateFormat dateFormat = getDateFormatter();
        Object[] a = new Object[]{cbn, author, title, yearOfPub, isbn,
                created == null ? null : dateFormat.format(created),
                modified == null ? null : dateFormat.format(modified),
                lendWhom,
                lendBefore == null ? null : dateFormat.format(lendBefore)};
        return a;
    }

}
