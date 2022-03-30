package crud.CrudRelaciones.content.profesor.infrastucture.controller.dto.output;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProfesorPersonaOutputDTO {

    private String id_profesor;
    private int persona;
    private String comentarios;
    private String rama;
    private List<String> alumnos;

    private int idPersona;
    private String usuario;
    private String password;
    private String name;
    private String surname;
    private String companyName;
    private String personalEmail;
    private String city;
    private Boolean active;
    private LocalDateTime createdDate;
    private String imagenUrl;
    private LocalDateTime terminationDate;

}
