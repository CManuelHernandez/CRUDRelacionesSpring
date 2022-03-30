package crud.CrudRelaciones.content.estudiante.infrastucture.controller.dto.output;

import lombok.Data;

import java.util.List;

@Data
public class EstudianteOutputDTO {

    private String id_estudiante;

    private int persona;

    private int num_hours_week;

    private String comentarios;

    private String profesor;

    private String rama;

    private List<String> estudios;

}
