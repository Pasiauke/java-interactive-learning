package patterns.structural.proxy.ext;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class DatabaseRecord {

    private long id;
    private String recordName;
    private Integer recordValue;

}
