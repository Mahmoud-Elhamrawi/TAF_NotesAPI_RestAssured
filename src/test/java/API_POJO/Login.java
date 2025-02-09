package API_POJO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Login {

@JsonProperty("email")
private String email ;

@JsonProperty("password")
private String password ;



}
