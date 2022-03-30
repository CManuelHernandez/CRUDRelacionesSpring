package crud.CrudRelaciones.content.profesor.infrastucture.controller.dto.output;

import lombok.Data;

import java.util.List;

@Data
public class ProfesorOutputDTO {

    private String id_profesor;

    private int persona;

    private String comentarios;

    private String rama;

    private List<String> alumnos;

}
