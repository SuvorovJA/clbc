package ru.sua.bookcatalog.dao;

public class CbnProviderImpl implements CbnProvider {

    private CatalogDAO dao;

    public CbnProviderImpl(CatalogDAO dao) {
        this.dao = dao;
    }

    @Override
    public long getNextCbn() {
        return dao.getMaxCbn() + 1;
    }
}
