package crud.CrudRelaciones.content.estudio.infrastucture.controller.dto.input;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EstudioInputDTO {

    private String nombre;

    private String comentarios;

    private String profesor;

    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;


}
