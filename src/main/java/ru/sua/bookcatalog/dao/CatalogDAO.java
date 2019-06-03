package ru.sua.bookcatalog.dao;

import ru.sua.bookcatalog.domain.Book;

import java.util.Date;
import java.util.List;

/**
 * @param cbn is 'catalog number of the book'
 *            <p>
 *            this class violate 'interface segregation'
 */
public interface CatalogDAO {

    List<Book> findAll();

    List<Book> findAllLend();

    List<Book> findAllPresent();

    void add(Book book);

    void delete(long cbn);

    void edit(Book book);

    void lendBook(long cbn, String whom, Date before);

    void returnBook(long cbn);

    boolean isLent(long cbn);

    boolean isExist(long cbn);

    Book findbyId(long cbn);

    long getMaxCbn();

}
