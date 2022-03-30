package crud.CrudRelaciones.content.estudio.infrastucture.controller.dto.output;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EstudioOutputDTO {

    private String id_estudio;

    private List<String> estudiantes;

    private String nombre;

    private String comentarios;

    private String profesor;

    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

}
