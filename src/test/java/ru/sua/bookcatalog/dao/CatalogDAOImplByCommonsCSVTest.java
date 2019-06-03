package ru.sua.bookcatalog.dao;

import org.junit.jupiter.api.*;
import ru.sua.bookcatalog.Tools;
import ru.sua.bookcatalog.domain.Book;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CatalogDAOImplByCommonsCSVTest {

    static File temp;

    CatalogDAO dao;
    Book b1, b2, b3;

    @BeforeAll
    static void setInit() throws IOException {
        temp = File.createTempFile("test-catalog", ".csv");
        System.out.println("Use temp file: " + temp.getAbsolutePath());
    }

    @AfterAll
    static void setFinal() {
//        temp.delete();
    }

    @BeforeEach
    void setUp() throws IOException {
        initReferenceBooks();
        dao = new CatalogDAOImplByCommonsCSV(temp.getAbsolutePath());
        Tools.createCbnProvider(dao);
        System.out.println(dao.findAll());
    }

    @AfterEach
    void setDown() throws IOException {
        System.out.println(dao.findAll());
        ((Closeable) dao).close();
    }


    @Test
    @Order(1)
    void add() {
        System.out.println("add 3 book");
        dao.add(b1);
        dao.add(b2);
        dao.add(b3);
        assertEquals(3, dao.findAll().size());
        assertEquals(0, dao.findAllLend().size());
        assertEquals(3, dao.findAllPresent().size());


    }


    @Test
    @Order(3)
    void lendBook() {
        System.out.println("lent 1 book");

        assertFalse(dao.isLent(2));
        dao.lendBook(2, "Baeldung Mkyong", Tools.addDaysToDate(new Date(), 10));
        assertTrue(dao.isLent(2));
        assertEquals(1, dao.findAllLend().size());
        assertEquals(2, dao.findAllPresent().size());
        assertEquals(3, dao.findAll().size());

    }

    @Test
    @Order(4)
    void returnBook() {
        System.out.println("return lent book");

        assertTrue(dao.isLent(2));
        dao.returnBook(2);
        assertFalse(dao.isLent(2));
        assertEquals(0, dao.findAllLend().size());
        assertEquals(3, dao.findAllPresent().size());
        assertEquals(3, dao.findAll().size());

    }

    @Test
    @Order(7)
    void delete() {
        System.out.println("delete book");

        dao.delete(2);
        assertEquals(0, dao.findAllLend().size());
        assertEquals(2, dao.findAllPresent().size());
        assertEquals(2, dao.findAll().size());
    }

    @Test
    @Order(6)
    void edit() {
        System.out.println("edit book");

        b3.setCbn(3);
        b3.setYearOfPub(2010);
        b3.setIsbn("978-5-7502-0064-2");
        b3.setCreated(dao.findbyId(3).getCreated());
        dao.edit(b3);
        Book t = dao.findbyId(3);
        assertNotNull(t.getModified());
        assertNotEquals(t.getCreated(), t.getModified());
        assertEquals("978-5-7502-0064-2", t.getIsbn());
        assertEquals(2010, t.getYearOfPub());
    }

    @Test
    @Order(2)
    void isExist() {
        System.out.println("exist book");

        assertTrue(dao.isExist(3));
    }

    @Test
    @Order(5)
    void findbyId() {
        System.out.println("find book by cbn");

        Book t = dao.findbyId(1);
        assertEquals(b1.getAuthor(), t.getAuthor());
        assertEquals(b1.getTitle(), t.getTitle());
        assertEquals(b1.getYearOfPub(), t.getYearOfPub());
        assertEquals(b1.getIsbn(), t.getIsbn());
    }


    private void initReferenceBooks() {
        b1 = new Book();
        b2 = new Book();
        b3 = new Book();

        b1.setAuthor("Bloch Joshua");
        b1.setTitle("Effective java, 3rd ed");
        b1.setIsbn("978-0-13-468599-1");
        b1.setYearOfPub(2018);

        b2.setYearOfPub(2019);
        b2.setIsbn("9785604139448");
        b2.setAuthor("Джошуа Блох");
        b2.setTitle("Java эффективное программирование, 3 ред");

        b3.setAuthor("Макконнелл");
        b3.setTitle("Совершенный");

    }
}