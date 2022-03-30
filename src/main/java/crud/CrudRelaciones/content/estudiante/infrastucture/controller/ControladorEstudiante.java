package crud.CrudRelaciones.content.estudiante.infrastucture.controller;

import crud.CrudRelaciones.content.estudiante.applicacion.ServicioEstudiante;
import crud.CrudRelaciones.content.estudiante.domain.Estudiante;
import crud.CrudRelaciones.content.estudiante.infrastucture.controller.dto.input.EstudianteInputDTO;
import crud.CrudRelaciones.content.estudiante.infrastucture.controller.dto.output.EstudianteOutputDTO;
import crud.CrudRelaciones.content.estudiante.infrastucture.controller.dto.output.EstudiantePersonaOutputDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/estudiantes")
@Slf4j
public class ControladorEstudiante {

    @Autowired
    ServicioEstudiante servicioEstudiante;

    @GetMapping("/{id}")
    ResponseEntity buscarEstudiante(@PathVariable String id, @RequestParam(defaultValue = "simple") String outputType) {

        log.info("Buscando estudiante con id: " + id);

        try {

            switch (outputType.toLowerCase()) {

                case "simple": {

                    Estudiante estudiante = servicioEstudiante.buscarEstudiante(id);

                    estudiante.getProfesor().getId_profesor();

                    EstudianteOutputDTO estudianteOutputDTO = estudiante.estudianteDTO();

                    return ResponseEntity.status(HttpStatus.OK).body(estudianteOutputDTO);
                }
                case "full": {

                    Estudiante estudiante = servicioEstudiante.buscarEstudiante(id);

                    estudiante.getPersona();

                    EstudiantePersonaOutputDTO estudiantePersonaOutputDTO = estudiante.estudiantePersonaDTO();

                    return ResponseEntity.status(HttpStatus.OK).body(estudiantePersonaOutputDTO);
                }

                default:
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

            }

        } catch (RuntimeException e) {
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }

    }

    @GetMapping("")
    ResponseEntity devolverEstudiantes() {

        log.info("Buscando todos los estudiantes");
        List<Estudiante> estudiantes = servicioEstudiante.devolverEstudiantes();
        log.info("Cantidad de estudiantes: " + estudiantes.size());

        List<EstudianteOutputDTO> estudianteOutputDTOS = estudiantes.stream()
                .map(Estudiante::estudianteDTO).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(estudianteOutputDTOS);

    }

    //POST
    //Añade estudiante a la BBDD asignandole un profesor
    /*localhost:8080/
    {
        "persona": 15,
        "horas_por_semana": 40,
        "comentarios": "Solo Teletrabajo",
        "rama": "FULLSTACK",
        "profesor": "PRO00000001",
        "estudios": ["MAT00000003"]
    }
     */
    @PostMapping("")
    ResponseEntity crearEstudiante(@RequestBody EstudianteInputDTO estudianteInputDTO) throws Exception{

        log.info("Creando estudiante");
        try {

            Estudiante estudiante = servicioEstudiante.crearEstudiante(estudianteInputDTO);

            EstudianteOutputDTO estudianteOutputDTO = estudiante.estudianteDTO();

            return ResponseEntity.status(HttpStatus.CREATED).body(estudianteOutputDTO);
        } catch (RuntimeException e) {
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }
}