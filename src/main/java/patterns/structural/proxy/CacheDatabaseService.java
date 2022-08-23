package patterns.structural.proxy;

import patterns.structural.proxy.ext.DatabaseInterface;
import patterns.structural.proxy.ext.DatabaseRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheDatabaseService implements DatabaseInterface {
    private final DatabaseInterface externalDatabaseService;
    private final Map<Long, DatabaseRecord> cachedRecords = new HashMap<>();
    private List<DatabaseRecord> allRecordsCached = new ArrayList<>();

    private boolean isRefreshNeeded = false;

    public CacheDatabaseService(DatabaseInterface externalDatabaseService) {
        this.externalDatabaseService = externalDatabaseService;
    }

    @Override
    public List<DatabaseRecord> getRecords() {
        if (allRecordsCached.isEmpty() || isRefreshNeeded) {
            System.out.println("Caching records...");
            allRecordsCached = this.externalDatabaseService.getRecords();
            isRefreshNeeded = false;
        }
        return this.allRecordsCached;
    }

    @Override
    public void addRecord(DatabaseRecord record) {
        this.externalDatabaseService.addRecord(record);
    }

    @Override
    public DatabaseRecord getById(long id) {
        if (cachedRecords.get(id) == null || isRefreshNeeded) {
            cachedRecords.put(id, this.externalDatabaseService.getById(id));
            isRefreshNeeded = false;
        }
        return cachedRecords.get(id);
    }

    public void flush() {
        System.out.println("Flushing...");
        this.cachedRecords.clear();
        this.allRecordsCached.clear();
        isRefreshNeeded = true;
    }
}
