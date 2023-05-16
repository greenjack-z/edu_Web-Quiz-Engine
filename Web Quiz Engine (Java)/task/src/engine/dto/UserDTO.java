package engine.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public record UserDTO (@Email(regexp = ".+@.+\\..+") String email, @Size(min = 5) String password) {
}
