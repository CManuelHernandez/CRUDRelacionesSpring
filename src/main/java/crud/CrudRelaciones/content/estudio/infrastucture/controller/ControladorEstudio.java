package crud.CrudRelaciones.content.estudio.infrastucture.controller;


import crud.CrudRelaciones.content.estudio.applicacion.ServicioEstudio;
import crud.CrudRelaciones.content.estudio.domain.Estudio;
import crud.CrudRelaciones.content.estudio.infrastucture.controller.dto.input.EstudioInputDTO;
import crud.CrudRelaciones.content.estudio.infrastucture.controller.dto.output.EstudioOutputDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/estudios")
@Slf4j
public class ControladorEstudio {

    @Autowired
    ServicioEstudio servicioEstudio;

    @GetMapping("/{id}")
    ResponseEntity buscarEstudio(@PathVariable String id) {

        log.info("Buscando estudio con id: "+id);

        try {

            Estudio estudio = servicioEstudio.buscarEstudio(id);

            EstudioOutputDTO estudioOutputDTO = estudio.aEstudioDTO();

            return ResponseEntity.status(HttpStatus.OK).body(estudioOutputDTO);

        }catch (RuntimeException e){
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }

    }

    @GetMapping("")
    ResponseEntity devolverEstudios(){

        log.info("Buscando todos los estudios");
        List<Estudio> estudios = servicioEstudio.devolverEstudios();
        log.info("Cantidad de estudios: "+estudios.size());

        List<EstudioOutputDTO> estudioOutputDTOS = estudios.stream()
                .map(Estudio::aEstudioDTO).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(estudioOutputDTOS);

    }

    @PostMapping("")
    ResponseEntity crearEstudio(@RequestBody EstudioInputDTO estudioInputDTO){

        log.info("Creando estudio");
        try {

            Estudio estudio = servicioEstudio.crearEstudio(estudioInputDTO);

            EstudioOutputDTO estudioOutputDTO = estudio.aEstudioDTO();

            return ResponseEntity.status(HttpStatus.CREATED).body(estudioOutputDTO);

        } catch (TransactionSystemException e){
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La fecha inicio no puede ser nula");

        } catch (RuntimeException e){
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    ResponseEntity editarEstudio(@PathVariable String id, @RequestBody EstudioInputDTO estudioInputDTO){

        log.info("Editando estudio con id: "+id);

        try {

            Estudio estudio = servicioEstudio.editarEstudio(estudioInputDTO, id);

            EstudioOutputDTO estudioOutputDTO = estudio.aEstudioDTO();

            return ResponseEntity.status(HttpStatus.OK).body(estudioOutputDTO);

        }catch (RuntimeException e){
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }

    }

    @DeleteMapping("/{id}")
    ResponseEntity eliminarEstudio(@PathVariable String id) throws Exception{

        log.info("Eliminando estudio con id: "+id);

        try {

            servicioEstudio.eliminarEstudio(id);

            return ResponseEntity.status(HttpStatus.OK).body("Se ha eliminado el estudio con ID: "+id);

        }catch (RuntimeException e){
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }

    }

}

