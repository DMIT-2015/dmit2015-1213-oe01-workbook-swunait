package dmit2015.client;

import lombok.Data;

@Data
public class Todo {

    private String key;
    private String task;
    private Boolean important = false;
    private Boolean completed = false;
    // private LocalDate dueDate;
    // private LocalDateTime remindDateTime;

}