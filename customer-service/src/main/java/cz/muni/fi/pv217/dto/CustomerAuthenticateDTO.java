package cz.muni.fi.pv217.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class CustomerAuthenticateDTO {

    @Email
    public String email;

    @NotEmpty
    public String password;
}
