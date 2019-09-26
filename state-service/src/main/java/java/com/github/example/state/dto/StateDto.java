package java.com.github.example.state.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder(builderClassName = "StateBuilder")
@JsonDeserialize(builder = StateDto.StateBuilder.class)
public class StateDto {
    @Getter
    private Integer id;
    @Getter
    private String state;

    @JsonPOJOBuilder(withPrefix = "")
    public static class StateBuilder {
        // Lombok will add constructor, setters, build method
    }

}
