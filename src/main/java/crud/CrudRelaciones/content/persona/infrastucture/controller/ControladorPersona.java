package crud.CrudRelaciones.content.persona.infrastucture.controller;

import crud.CrudRelaciones.content.persona.application.ServicioPersona;
import crud.CrudRelaciones.content.persona.domain.Persona;
import crud.CrudRelaciones.content.persona.infrastucture.controller.dto.input.PersonaInputDTO;
import crud.CrudRelaciones.content.persona.infrastucture.controller.dto.output.PersonaOutputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ControladorPersona {


    @Autowired
    ServicioPersona servicio;

    //POST
    //Añade personas a la BBDD
    /*localhost:8080/persona
{
    "usuario": "Persona1",
    "password": "seguro",
    "name": "Carlos",
    "surname": "Hernández",
    "company_email": "CH@email.com",
    "personal_email": "CHpersonal@email.com",
    "city": "Madrid",
    "active": true,
    "created_date": "2022-02-03T12:50:16.0178888",
    "imagen_url": "url",
    "termination_date": "2022-02-03T12:50:16.0178888"
}
     */
    @PostMapping("/persona")
    public ResponseEntity crearPersona(@RequestBody PersonaInputDTO personaInputDTO){
        Persona persona = servicio.crearPersona(personaInputDTO.persona());
        PersonaOutputDTO personaOutputDTO = new PersonaOutputDTO(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(personaOutputDTO);
    }

    //GET
    //Busca personas por ID
    //localhost:8080/persona/id/1
    @GetMapping("/persona/id/{id_persona}")
    public ResponseEntity buscarPorId(@PathVariable int id_persona) throws Exception{
        Persona persona = servicio.buscarPorId(id_persona);
        return ResponseEntity.status(HttpStatus.OK).body(persona);
    }

    //GET
    //Busca personas por Usuario
    //localhost:8080/persona/usuario/Usuario1
    @GetMapping("/persona/usuario/{usuario}")
    public ResponseEntity getByUser(@PathVariable String usuario) {
        Persona persona = servicio.findByUsuario(usuario);
        return ResponseEntity.status(HttpStatus.OK).body(persona);
    }

    //GET
    //Mostrar todas las personas
    //localhost:8080/persona
    @GetMapping("/persona")
    ResponseEntity devolverPersonas(){
        List<Persona> personas = servicio.devolverPersonas();
        return ResponseEntity.status(HttpStatus.OK).body(personas);
    }

    //DELETE
    //localhost:8080/persona/borrar/1
    //Borrar un usuario por ID
    @DeleteMapping("/persona/borrar/{id_persona}")
    public ResponseEntity borrarPersona(@PathVariable int id_persona){
        servicio.borrarUsuario(id_persona);
        return ResponseEntity.status(HttpStatus.OK).body("Borrada Persona con Id" + id_persona);
    }

    @PutMapping("/personas/{id_persona}")
    public ResponseEntity updatePerson(@PathVariable int id_persona,@RequestBody PersonaInputDTO personaInputDTO){
        Persona persona = servicio.actualizarPersona(id_persona, personaInputDTO);
        PersonaOutputDTO personaOutputDTO = new PersonaOutputDTO(persona);
        return ResponseEntity.status(HttpStatus.OK).body(personaOutputDTO);
    }
}

