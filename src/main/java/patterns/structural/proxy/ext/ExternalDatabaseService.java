package patterns.structural.proxy.ext;

import java.util.ArrayList;
import java.util.List;

public class ExternalDatabaseService implements DatabaseInterface {
    private final List<DatabaseRecord> records = new ArrayList<>();

    public ExternalDatabaseService() {

        records.add(new DatabaseRecord(1, "Record 1", 1000));
        records.add(new DatabaseRecord(2, "Record 2", 1512));
        records.add(new DatabaseRecord(3, "Record 3", 463));
        records.add(new DatabaseRecord(4, "Record 4", 42678));
        records.add(new DatabaseRecord(5, "Record 5", 854678));
    }


    @Override
    public List<DatabaseRecord> getRecords() {
        return new ArrayList<>(records);
    }

    @Override
    public void addRecord(DatabaseRecord record) {
        this.records.add(record);
    }

    @Override
    public DatabaseRecord getById(long id) {
        return records.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
    }
}
