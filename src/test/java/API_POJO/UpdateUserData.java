package API_POJO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateUserData {

    @JsonProperty("name")
    private String name  ;

    @JsonProperty("phone")
    private  String phone ;

    @JsonProperty("company")
    private String company;


}
