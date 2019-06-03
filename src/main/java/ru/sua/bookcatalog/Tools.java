package ru.sua.bookcatalog;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.sua.bookcatalog.dao.CatalogDAO;
import ru.sua.bookcatalog.dao.CbnProvider;
import ru.sua.bookcatalog.dao.CbnProviderImpl;
import ru.sua.bookcatalog.domain.Book;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Tools {

    public static final ResourceBundle BUNDLE = ResourceBundle.getBundle("MessageBundle");
    private static CbnProvider cbnProvider;

    public static boolean isMutuallyExclusive(Boolean... parameters) {
        long trues = Stream.of(parameters).filter(Predicate.isEqual(Boolean.TRUE)).count();
        return (trues == 1L);
    }

    public static boolean isNotMutuallyExclusive(Boolean... parameters) {
        return !isMutuallyExclusive(parameters);
    }

    public static void prettyPrint(List<Book> bookList) {
        bookList.forEach(System.out::println); //TODO make pretty text table OR|AND add option for selection table look
    }

    public static void createCbnProvider(CatalogDAO dao) {
        cbnProvider = new CbnProviderImpl(dao);
    }

    public static CbnProvider getCbnProvider() {
        return cbnProvider;
    }

    public static Date addDaysToDate(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public static String buildLocalizedMessage(Object... args) {
        StringBuilder sb = new StringBuilder();
        for (Object o : args) {
            sb.append(" ");
            if (o instanceof BundleKey) {
                BundleKey bundleKey = (BundleKey) o;
                String key = bundleKey.getKey();
                sb.append(BUNDLE.getString(key));
                continue;
            }
            sb.append(o);
        }
        return sb.toString();
    }

    public static BundleKey makeBundleKey(String s) {
        return new BundleKey(s);
    }

    @Data
    @AllArgsConstructor
    static class BundleKey {
        private String key;
    }
}
