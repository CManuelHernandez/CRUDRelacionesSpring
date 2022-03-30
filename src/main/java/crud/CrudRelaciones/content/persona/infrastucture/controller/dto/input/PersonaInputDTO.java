package crud.CrudRelaciones.content.persona.infrastucture.controller.dto.input;

import crud.CrudRelaciones.content.persona.domain.Persona;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PersonaInputDTO {

    //int id_persona;
    String usuario;
    String password;
    String name;
    String surname;
    String personal_email;
    String company_email;
    String city;
    Boolean active;
    LocalDateTime created_date;
    String imagen_url;
    LocalDateTime termination_date;

    public Persona persona() {
        Persona persona = new Persona();
        //persona.setId_persona(this.getId_persona());
        persona.setUsuario(this.getUsuario());
        persona.setPassword(this.getPassword());
        persona.setName(this.getName());
        persona.setSurname(this.getSurname());
        persona.setCompany_email(this.getCompany_email());
        persona.setPersonal_email(this.getPersonal_email());
        persona.setCity(this.getCity());
        persona.setActive(this.getActive());
        persona.setCreated_date(this.getCreated_date());
        persona.setImagen_url(this.getImagen_url());
        persona.setTermination_date(this.getTermination_date());
        return persona;
    }
}
