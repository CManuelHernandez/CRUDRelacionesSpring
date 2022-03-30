package crud.CrudRelaciones.content.profesor.infrastucture.controller;


import crud.CrudRelaciones.content.profesor.applicacion.ServicioProfesor;
import crud.CrudRelaciones.content.profesor.domain.Profesor;
import crud.CrudRelaciones.content.profesor.infrastucture.controller.dto.input.ProfesorInputDTO;
import crud.CrudRelaciones.content.profesor.infrastucture.controller.dto.output.ProfesorOutputDTO;
import crud.CrudRelaciones.content.profesor.infrastucture.controller.dto.output.ProfesorPersonaOutputDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/profesores")
@Slf4j
public class ControladorProfesor {

    @Autowired
    ServicioProfesor servicioProfesor;

    @GetMapping("/{id}")
    ResponseEntity buscarProfesor(@PathVariable String id, @RequestParam(defaultValue = "simple") String outputType){

        log.info("Buscando profesor con id: "+id);

        try {

            switch (outputType.toLowerCase()) {

                case "simple": {

                    Profesor profesor = servicioProfesor.buscarProfesor(id);

                    ProfesorOutputDTO profesorOutputDTO = profesor.profesorDTO();

                    return ResponseEntity.status(HttpStatus.OK).body(profesorOutputDTO);
                }
                case "full": {

                    Profesor profesor = servicioProfesor.buscarProfesor(id);

                    profesor.getPersona();

                    ProfesorPersonaOutputDTO profesorPersonaOutputDTO = profesor.profesorPersonaDTO();

                    return ResponseEntity.status(HttpStatus.OK).body(profesorPersonaOutputDTO);
                }

                default: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

            }

        }catch (RuntimeException e){
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }

    }

    @GetMapping("")
    ResponseEntity devolverProfesores(){

        log.info("Buscando todos los profesores");
        List<Profesor> profesores = servicioProfesor.devolverProfesores();
        log.info("Cantidad de profesores: "+profesores.size());

        List<ProfesorOutputDTO> profesorOutputDTOS = profesores.stream()
                .map(Profesor::profesorDTO).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(profesorOutputDTOS);

    }

    //POST
    //Añade profesor a la BBDD
    /*localhost:8080/
    {
        "persona": 5,
        "horas_por_semana": 40,
        "comentarios": "Tutor",
        "rama": "FULLSTACK"
    }
     */
    @PostMapping("")
    ResponseEntity crearProfesor(@RequestBody ProfesorInputDTO profesorInputDTO) throws Exception{

        log.info("Creando profesor");
        try {

            Profesor profesor = servicioProfesor.crearProfesor(profesorInputDTO);

            ProfesorOutputDTO profesorOutputDTO = profesor.profesorDTO();

            return ResponseEntity.status(HttpStatus.CREATED).body(profesorOutputDTO);
        }catch (RuntimeException e){
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }

    @PutMapping("/{id}")
    ResponseEntity editaProfesor(@PathVariable String id, @RequestBody ProfesorInputDTO profesorInputDTO) throws Exception{

        log.info("Editando profesor con id: "+id);

        try {

            Profesor profesor = servicioProfesor.editarProfesor(profesorInputDTO, id);

            ProfesorOutputDTO profesorOutputDTO = profesor.profesorDTO();

            return ResponseEntity.status(HttpStatus.OK).body(profesorOutputDTO);

        }catch (RuntimeException e){
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }

    }

    @DeleteMapping("/{id}")
    ResponseEntity eliminarProfesor(@PathVariable String id) throws Exception{

        log.info("Eliminando profesor con id: "+id);

        try {

            servicioProfesor.eliminarProfesor(id);

            return ResponseEntity.status(HttpStatus.OK).body("Se ha eliminado el profesor con ID: "+id);

        }catch (RuntimeException e){
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }

    }

}

