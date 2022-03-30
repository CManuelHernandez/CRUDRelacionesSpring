package crud.CrudRelaciones.content.estudio.domain;

import com.sun.istack.NotNull;
import crud.CrudRelaciones.content.estudiante.domain.Estudiante;
import crud.CrudRelaciones.content.estudio.infrastucture.controller.dto.input.EstudioInputDTO;
import crud.CrudRelaciones.content.estudio.infrastucture.controller.dto.output.EstudioOutputDTO;
import crud.CrudRelaciones.content.profesor.domain.Profesor;
import crud.CrudRelaciones.content.util.StringPrefixedSequenceIdGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
public class Estudio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estudios_seq")
    @GenericGenerator(
            name = "estudios_seq",
            strategy = "crud.CrudRelaciones.content.util.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "MAT"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%08d")
            })
    private String id_estudio;

    @ManyToMany(mappedBy = "estudios")
    private List<Estudiante> estudiantes;

    private String nombre;

    private String comentarios;

    @ManyToOne(fetch = FetchType.LAZY)
    private Profesor profesor;

    @NotNull
    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

    public Estudio(EstudioInputDTO estudioInputDTO, Profesor profesor){

        this.comentarios = estudioInputDTO.getComentarios();
        this.nombre = estudioInputDTO.getNombre();
        this.profesor = profesor;
        this.fechaInicio = estudioInputDTO.getFechaInicio();
        this.fechaFin = estudioInputDTO.getFechaFin();

    }

    public EstudioOutputDTO aEstudioDTO(){

        EstudioOutputDTO estudioOutputDTO = new EstudioOutputDTO();

        if(this.id_estudio != null) estudioOutputDTO.setId_estudio(this.id_estudio);
        if(this.nombre != null) estudioOutputDTO.setNombre(this.nombre);
        if(this.comentarios != null) estudioOutputDTO.setComentarios(this.comentarios);
        if(this.fechaInicio != null) estudioOutputDTO.setFechaInicio(this.fechaInicio);
        if(this.fechaFin != null) estudioOutputDTO.setFechaFin(this.fechaFin);
        if(this.profesor != null) estudioOutputDTO.setProfesor(this.profesor.getId_profesor());
        if(this.estudiantes != null) estudioOutputDTO.setEstudiantes(this.estudiantes.stream().map(Estudiante::getId_estudiante).collect(Collectors.toList()));

        return estudioOutputDTO;

    }

}
