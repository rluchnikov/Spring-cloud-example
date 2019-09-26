package java.com.github.example.authservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder(builderClassName = "UserBuilder")
@JsonDeserialize(builder = UserDto.UserBuilder.class)
public class UserDto {

    private Integer id;

    private String username;

    private String password;

    private String firstname;

    private String lastname;

    private String middlename;

    private String birthday;

    private String appointment_date;

    private String mobile;

    @NotNull
    private String location;

    private Integer role;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserBuilder {
        // Lombok will add constructor, setters, build method
    }
}
