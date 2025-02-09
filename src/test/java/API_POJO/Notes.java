package API_POJO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Notes {

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
private String  description  ;


    @JsonProperty("category")
    private String category ;

    @JsonProperty("completed")
    private boolean completed ;


}
