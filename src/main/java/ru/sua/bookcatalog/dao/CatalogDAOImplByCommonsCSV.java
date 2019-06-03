package ru.sua.bookcatalog.dao;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import ru.sua.bookcatalog.domain.Book;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static ru.sua.bookcatalog.Tools.getCbnProvider;

@Slf4j
public class CatalogDAOImplByCommonsCSV implements CatalogDAO, Closeable {

    private Map<Long, Book> catalog;
    private File catalogFile;
    private long maxLoadedCbn;

    public CatalogDAOImplByCommonsCSV(String catalogFileName) {
        this.catalogFile = new File(catalogFileName);
        this.catalog = new HashMap<>();
        if (!this.catalogFile.isFile()) createEmptyCatalog();
        loadCatalog();
    }

    private void loadCatalog() {
        try (Reader in = new FileReader(catalogFile)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader(Book.CSV_HEADERS).withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                Book book = new Book(
                        Long.parseLong(record.get("cbn")),
                        record.get("author"),
                        record.get("title"),
                        Integer.parseInt(record.get("yearOfPub")),
                        record.get("isbn"),
                        dateOrNull(record.get("created")),
                        dateOrNull(record.get("modified")),
                        record.get("lendWhom"),
                        dateOrNull(record.get("lendBefore"))
                );
                catalog.put(book.getCbn(), book);
                updateMaxCbn(book.getCbn());
            }
        } catch (NumberFormatException e) {
            log.error(e.getLocalizedMessage());
        } catch (FileNotFoundException e) {
            log.error(e.getLocalizedMessage());
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    private void updateMaxCbn(long cbn) {
        if (cbn > maxLoadedCbn) maxLoadedCbn = cbn;
    }

    private Date dateOrNull(String s) {
        if (isNonValue(s)) return null;
        try {
            return Book.getDateFormatter().parse(s);
        } catch (ParseException e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    private boolean isNonValue(String s) {
        return (s == null || s.isBlank() || s.isEmpty());
    }

    private void createEmptyCatalog() {
        try (FileWriter out = new FileWriter(catalogFile)) {
            CSVFormat.DEFAULT.withHeader(Book.CSV_HEADERS).print(out);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(catalog.values());
    }

    @Override
    public List<Book> findAllLend() {
        return catalog.values().stream().filter(b -> !isNonValue(b.getLendWhom())).collect(Collectors.toList());
    }

    @Override
    public List<Book> findAllPresent() {
        return catalog.values().stream().filter(b -> isNonValue(b.getLendWhom())).collect(Collectors.toList());
    }

    @Override
    public void add(Book book) {
        book.setCbn(getCbnProvider().getNextCbn());
        book.setCreated(new Date());
        book.setModified(new Date());
        catalog.put(book.getCbn(), book);
        updateMaxCbn(book.getCbn());
    }

    @Override
    public void delete(long cbn) {
        catalog.remove(cbn);
        if (maxLoadedCbn == cbn)
            maxLoadedCbn = catalog.keySet().stream().max(Comparator.comparing(Long::valueOf)).get();
    }

    @Override
    public void edit(Book book) {
        book.setModified(new Date());
        catalog.put(book.getCbn(), book);
        updateMaxCbn(book.getCbn());
    }

    @Override
    public void lendBook(long cbn, String whom, Date before) {
        Book book = catalog.get(cbn);
        book.setLendWhom(whom);
        book.setLendBefore(before);
        edit(book);
    }

    @Override
    public void returnBook(long cbn) {
        Book book = catalog.get(cbn);
        book.setLendWhom(null);
        book.setLendBefore(null);
        edit(book);
    }

    @Override
    public boolean isLent(long cbn) {
        return (catalog.get(cbn).getLendBefore() != null ||
                (catalog.get(cbn).getLendBefore() != null &&
                        (!catalog.get(cbn).getLendWhom().isEmpty() ||
                                !catalog.get(cbn).getLendWhom().isBlank())));
    }

    @Override
    public boolean isExist(long cbn) {
        return catalog.containsKey(cbn);
    }

    @Override
    public Book findbyId(long cbn) {
        return catalog.get(cbn);
    }

    @Override
    public long getMaxCbn() {
        return maxLoadedCbn;
    }

    @Override
    public void close() {
        //TODO create bak file
        try (FileWriter out = new FileWriter(catalogFile)) {
            CSVPrinter printer = CSVFormat.DEFAULT.withHeader(Book.CSV_HEADERS).print(out);
            for (Book b : catalog.values()) printer.printRecord(b.asArray());
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
