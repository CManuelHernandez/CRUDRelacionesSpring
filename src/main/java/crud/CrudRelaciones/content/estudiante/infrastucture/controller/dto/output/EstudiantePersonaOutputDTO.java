package crud.CrudRelaciones.content.estudiante.infrastucture.controller.dto.output;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EstudiantePersonaOutputDTO {

    private String id_estudiante;
    private int num_hours_week;
    private String comentarios;
    private String rama;
    private String profesor;
    private List<String> estudios;

    private int idPersona;
    private String usuario;
    private String password;
    private String name;
    private String surname;
    private String company_email;
    private String personal_email;
    private String city;
    private Boolean active;
    private LocalDateTime created_date;
    private String imagen_url;
    private LocalDateTime termination_date;

}
