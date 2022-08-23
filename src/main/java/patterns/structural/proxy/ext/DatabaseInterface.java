package patterns.structural.proxy.ext;

import java.util.List;

public interface DatabaseInterface {
    List<DatabaseRecord> getRecords();
    void addRecord(DatabaseRecord record);
    DatabaseRecord getById(long id);
}
