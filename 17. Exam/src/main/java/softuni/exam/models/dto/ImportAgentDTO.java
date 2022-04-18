package softuni.exam.models.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class ImportAgentDTO {

    @Size(min = 2)
    private String firstName;

    @Size(min = 2)
    private String lastName;

    @Size(min = 2)
    private String town;

    @Email
    private String email;

    public ImportAgentDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTown() {
        return town;
    }

    public String getEmail() {
        return email;
    }
}
