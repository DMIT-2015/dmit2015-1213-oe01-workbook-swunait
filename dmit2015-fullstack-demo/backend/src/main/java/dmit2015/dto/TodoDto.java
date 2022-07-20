package dmit2015.dto;

import lombok.Data;

@Data
public class TodoDto {

    private Long id;
    private String name;
    private boolean completed;
    private Integer version;

}
