package crud.CrudRelaciones.content.estudiante.applicacion;

import crud.CrudRelaciones.content.estudiante.domain.Estudiante;
import crud.CrudRelaciones.content.estudiante.infrastucture.controller.dto.input.EstudianteInputDTO;
import crud.CrudRelaciones.content.estudiante.infrastucture.repository.jpa.EstudianteRepositorio;
import crud.CrudRelaciones.content.estudio.applicacion.ServicioEstudio;
import crud.CrudRelaciones.content.estudio.domain.Estudio;
import crud.CrudRelaciones.content.persona.application.ServicioPersona;
import crud.CrudRelaciones.content.persona.domain.Persona;
import crud.CrudRelaciones.content.profesor.applicacion.ServicioProfesor;
import crud.CrudRelaciones.content.profesor.domain.Profesor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ServicioEstudiante {

    @Autowired
    EstudianteRepositorio estudianteRepositorio;

    @Autowired
    ServicioPersona servicioPersona;

    @Autowired
    ServicioProfesor servicioProfesor;

    @Autowired
    ServicioEstudio servicioEstudio;

    public Estudiante buscarEstudiante(String id_estudiante){

        return estudianteRepositorio.findById(id_estudiante).orElseThrow(() -> new RuntimeException("No existe ningún estudiante con ese Id"));

    }

    public Estudiante crearEstudiante(EstudianteInputDTO estudianteInputDTO) throws Exception{

        Persona persona = servicioPersona.buscarPorId(estudianteInputDTO.getPersona());

        if(estudianteRepositorio.findByPersona(persona).isPresent()){

            throw new RuntimeException("La persona ya existe");

        }

        Profesor profesor = servicioProfesor.buscarProfesor(estudianteInputDTO.getProfesor());

        Estudiante estudiante = new Estudiante(estudianteInputDTO, persona, profesor);

        Estudiante estudianteguardado = estudianteRepositorio.save(estudiante);

         return this.asignarAsignaturasAEstudiante(estudianteguardado.getId_estudiante(),estudianteInputDTO);


//        return estudiante;

    }

    public List<Estudiante> devolverEstudiantes(){

        return estudianteRepositorio.findAll();
    }

    public Estudiante asignarAsignaturasAEstudiante(String idEstudiante, EstudianteInputDTO estudianteInputDTO){

        Estudiante estudiante = estudianteRepositorio.findById(idEstudiante).orElseThrow(() -> new RuntimeException("No se ha encontrado al estudiante: "+idEstudiante));

        List<Estudio> estudios = estudianteInputDTO.getEstudios().stream().map(estudio -> servicioEstudio.buscarEstudio(estudio)).collect(Collectors.toList());

        estudios.stream().forEach(estudio -> {
            if(estudiante.getEstudios().contains(estudio))
                throw new RuntimeException("El estudiante ya tiene el estudio con ID: "+estudio.getId_estudio());
            estudiante.getEstudios().add(estudio);
        });

        Estudiante estudianteGuardado = estudianteRepositorio.save(estudiante);

        return estudianteGuardado ;

    }

    public Estudiante desasignarAsignaturasAEstudiante(String idEstudiante, EstudianteInputDTO estudianteInputDTO){

        Estudiante estudiante = estudianteRepositorio.findById(idEstudiante).orElseThrow(() -> new RuntimeException("No se ha encontrado al estudiante: "+idEstudiante));

        List<Estudio> estudios = estudianteInputDTO.getEstudios().stream().map(estudio -> servicioEstudio.buscarEstudio(estudio)).collect(Collectors.toList());

        estudios.stream().forEach(estudio -> {
            if(!estudiante.getEstudios().contains(estudio))
                throw new RuntimeException("El estudiante no tiene el estudio con ID: "+estudio.getId_estudio());
            estudiante.getEstudios().remove(estudio);
        });

        Estudiante estudianteGuardado = estudianteRepositorio.save(estudiante);

        return estudianteGuardado ;

    }

}
