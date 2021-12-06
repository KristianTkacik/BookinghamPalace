package cz.muni.fi.pv217.DTO;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

public class AuthorCreateDTO {
    @NotEmpty
    public String name;
    public LocalDate dateOfBirth;
}
