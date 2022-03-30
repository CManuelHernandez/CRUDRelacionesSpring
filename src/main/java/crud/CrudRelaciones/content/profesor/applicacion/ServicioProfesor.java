package crud.CrudRelaciones.content.profesor.applicacion;


import crud.CrudRelaciones.content.enums.Rama;
import crud.CrudRelaciones.content.estudiante.domain.Estudiante;
import crud.CrudRelaciones.content.persona.application.ServicioPersona;
import crud.CrudRelaciones.content.persona.domain.Persona;
import crud.CrudRelaciones.content.profesor.domain.Profesor;
import crud.CrudRelaciones.content.profesor.infrastucture.controller.dto.input.ProfesorInputDTO;
import crud.CrudRelaciones.content.profesor.infrastucture.repository.jpa.ProfesorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioProfesor {

    @Autowired
    ProfesorRepositorio profesorRepositorio;

    @Autowired
    ServicioPersona servicioPersona;

    public Profesor buscarProfesor(String id_profesor) {

        return profesorRepositorio.findById(id_profesor).orElseThrow(() -> new RuntimeException("No existe ningún profesor con ese Id"));

    }

    public Profesor crearProfesor(ProfesorInputDTO profesorInputDTO) throws Exception{

        Persona persona = servicioPersona.buscarPorId(profesorInputDTO.getPersona());

        if(profesorRepositorio.findByPersona(persona).isPresent()){

            throw new RuntimeException("La persona ya existe");

        }

        Profesor profesor = new Profesor(profesorInputDTO, persona);

        profesorRepositorio.save(profesor);

        return profesor;

    }

    public Profesor editarProfesor(ProfesorInputDTO profesorInputDTO, String id) throws Exception{

        Profesor profesor = profesorRepositorio.findById(id).orElseThrow(() -> new RuntimeException("No existe ningún profesor con ese Id"));

        if(profesorInputDTO.getRama()!=null) profesor.setRama(Rama.valueOf(profesorInputDTO.getRama()));
        if(profesorInputDTO.getComentarios()!=null) profesor.setComentarios(profesorInputDTO.getComentarios());
        if(profesorInputDTO.getPersona()!=0){

            Persona persona = servicioPersona.buscarPorId(profesorInputDTO.getPersona()) ;

            if(profesorRepositorio.findByPersona(persona).isPresent()){

                throw new RuntimeException("La persona ya existe");

            }

            profesor.setPersona(persona);

        }

        profesorRepositorio.save(profesor);

        return profesor;

    }

    public void eliminarProfesor(String idProfesor) throws Exception{

        profesorRepositorio.findById(idProfesor).orElseThrow(() -> new RuntimeException("El profesor con ID "+idProfesor+" no se ha encontrado"));

        profesorRepositorio.deleteById(idProfesor);

    }

    public List<Profesor> devolverProfesores(){

        return profesorRepositorio.findAll();

    }

    public void añadirAlumno(Profesor profesor, Estudiante alumno){

        profesor.getAlumnos().add(alumno);

        profesorRepositorio.save(profesor);

    }

}

