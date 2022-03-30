package crud.CrudRelaciones.content.estudio.applicacion;


import crud.CrudRelaciones.content.estudio.domain.Estudio;
import crud.CrudRelaciones.content.estudio.infrastucture.controller.dto.input.EstudioInputDTO;
import crud.CrudRelaciones.content.estudio.infrastucture.repository.jpa.EstudioRepositorio;
import crud.CrudRelaciones.content.persona.application.ServicioPersona;
import crud.CrudRelaciones.content.profesor.applicacion.ServicioProfesor;
import crud.CrudRelaciones.content.profesor.domain.Profesor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioEstudio {

    @Autowired
    EstudioRepositorio estudioRepositorio;

    @Autowired
    ServicioPersona servicioPersona;

    @Autowired
    ServicioProfesor servicioProfesor;

    public Estudio buscarEstudio(String id){

        return estudioRepositorio.findById(id).orElseThrow(() -> new RuntimeException("No existe ningún estudio con ese Id"));

    }

    public Estudio crearEstudio(EstudioInputDTO estudioInputDTO) {

        Profesor profesor = servicioProfesor.buscarProfesor(estudioInputDTO.getProfesor());

        Estudio estudio = new Estudio(estudioInputDTO, profesor);

        estudioRepositorio.save(estudio);

        Estudio estudioGuardado = estudioRepositorio.getById(estudio.getId_estudio());

        return estudioGuardado;

    }

    public Estudio editarEstudio(EstudioInputDTO estudioInputDTO, String id){

        Estudio estudio = estudioRepositorio.findById(id).orElseThrow(() -> new RuntimeException("No existe ningún estudio con ese Id"));

        if(estudioInputDTO.getFechaInicio()!=null) estudio.setFechaInicio(estudioInputDTO.getFechaInicio());
        if(estudioInputDTO.getFechaFin()!=null) estudio.setFechaFin(estudioInputDTO.getFechaFin());
        if(estudioInputDTO.getComentarios()!=null) estudio.setComentarios(estudioInputDTO.getComentarios());
        if(estudioInputDTO.getNombre()!=null) estudio.setNombre(estudioInputDTO.getNombre());
        if(estudioInputDTO.getProfesor()!=null){

            Profesor profesor = servicioProfesor.buscarProfesor(estudioInputDTO.getProfesor());
            estudio.setProfesor(profesor);

        }

        estudioRepositorio.save(estudio);

        return estudio;

    }

    public void eliminarEstudio(String idEstudio) throws Exception{

        estudioRepositorio.findById(idEstudio).orElseThrow(() -> new RuntimeException("El estudio con ID "+idEstudio+" no se ha encontrado"));

        estudioRepositorio.deleteById(idEstudio);

    }

    public List<Estudio> devolverEstudios(){

        return estudioRepositorio.findAll();

    }

}
