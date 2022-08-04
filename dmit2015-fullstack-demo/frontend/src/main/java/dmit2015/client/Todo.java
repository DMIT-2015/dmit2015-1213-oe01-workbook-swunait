package dmit2015.client;

import lombok.Data;

@Data
public class Todo {

    private Long id;
    private String name;
    private boolean completed;
    private Integer version;

}
