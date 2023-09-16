package org.personal.User.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.personal.User.UserState;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @NonNull
    private String name;
    //private UserState state;
}
