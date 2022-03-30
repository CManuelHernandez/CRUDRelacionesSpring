package crud.CrudRelaciones.content.estudiante.domain;

import com.sun.istack.NotNull;
import crud.CrudRelaciones.content.enums.Rama;
import crud.CrudRelaciones.content.estudiante.infrastucture.controller.dto.input.EstudianteInputDTO;
import crud.CrudRelaciones.content.estudiante.infrastucture.controller.dto.output.EstudianteOutputDTO;
import crud.CrudRelaciones.content.estudiante.infrastucture.controller.dto.output.EstudiantePersonaOutputDTO;
import crud.CrudRelaciones.content.estudio.domain.Estudio;
import crud.CrudRelaciones.content.persona.domain.Persona;
import crud.CrudRelaciones.content.profesor.domain.Profesor;
import crud.CrudRelaciones.content.util.StringPrefixedSequenceIdGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estudiantes_seq")
    @GenericGenerator(
            name = "estudiantes_seq",
            strategy = "crud.CrudRelaciones.content.util.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "EST"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%08d")
            })
    private String id_estudiante;

    @OneToOne(optional = false)
    private Persona persona;

    @NotNull
    @Column(name = "horas_por_semana")
    private int num_hours_week;

    @Column(name = "comentarios")
    private String comentarios;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_profesor")
    private Profesor profesor;

    @Column(name = "rama")
    @Enumerated(value = EnumType.STRING)
    private Rama rama;

    @ManyToMany
    private List<Estudio> estudios;

    public Estudiante(EstudianteInputDTO estudianteInputDTO, Persona persona, Profesor profesor){

        this.comentarios = estudianteInputDTO.getComentarios();

        switch (estudianteInputDTO.getRama()) {

            case "BACK": this.rama = Rama.BACK;
                break;

            case "FRONT": this.rama = Rama.FRONT;
                break;

            case "FULLSTACK": this.rama = Rama.FULLSTACK;
                break;

            default: this.rama = null;

        }

        this.num_hours_week = estudianteInputDTO.getNum_hours_week();

        this.profesor = profesor;

        this.persona = persona;

        this.estudios = new ArrayList<>();

    }

    public EstudianteOutputDTO estudianteDTO(){

        EstudianteOutputDTO estudianteOutputDTO = new EstudianteOutputDTO();

        if(this.persona != null) estudianteOutputDTO.setPersona(this.persona.getId_persona());
        if(this.id_estudiante != null) estudianteOutputDTO.setId_estudiante(this.id_estudiante);
        if(this.comentarios != null) estudianteOutputDTO.setComentarios(this.comentarios);
        if(this.rama != null) estudianteOutputDTO.setRama(this.rama.name());
        if(this.num_hours_week != 0) estudianteOutputDTO.setNum_hours_week(this.num_hours_week);
        if(this.persona != null) estudianteOutputDTO.setPersona(this.persona.getId_persona());
        if(this.estudios != null) estudianteOutputDTO.setEstudios(this.estudios.stream().map(Estudio::getId_estudio).collect(Collectors.toList()));
        if(this.profesor != null) estudianteOutputDTO.setProfesor(this.profesor.getId_profesor());

        return estudianteOutputDTO;

    }

    public EstudiantePersonaOutputDTO estudiantePersonaDTO(){

        EstudiantePersonaOutputDTO estudiantePersonaOutputDTO = new EstudiantePersonaOutputDTO();

        if(this.id_estudiante != null) estudiantePersonaOutputDTO.setId_estudiante(this.id_estudiante);
        if(this.comentarios != null) estudiantePersonaOutputDTO.setComentarios(this.comentarios);
        if(this.rama != null) estudiantePersonaOutputDTO.setRama(this.rama.name());
        if(this.num_hours_week != 0) estudiantePersonaOutputDTO.setNum_hours_week(this.num_hours_week);
        if(this.profesor != null) estudiantePersonaOutputDTO.setProfesor(this.profesor.getId_profesor());
        if(this.estudios != null) estudiantePersonaOutputDTO.setEstudios(this.estudios.stream().map(Estudio::getId_estudio).collect(Collectors.toList()));



        if(this.persona.getId_persona() != 0) estudiantePersonaOutputDTO.setIdPersona(this.persona.getId_persona());
        if(this.persona.getActive() != null) estudiantePersonaOutputDTO.setActive(this.persona.getActive());
        if(this.persona.getPersonal_email() != null) estudiantePersonaOutputDTO.setPersonal_email(this.persona.getPersonal_email());
        if(this.persona.getCity() != null) estudiantePersonaOutputDTO.setCity(this.persona.getCity());
        if(this.persona.getCompany_email() != null) estudiantePersonaOutputDTO.setCompany_email(this.persona.getCompany_email());
        if(this.persona.getCreated_date() != null) estudiantePersonaOutputDTO.setCreated_date(this.persona.getCreated_date());
        if(this.persona.getUsuario() != null) estudiantePersonaOutputDTO.setUsuario(this.persona.getUsuario());
        if(this.persona.getPassword() != null) estudiantePersonaOutputDTO.setPassword(this.persona.getPassword());
        if(this.persona.getName() != null) estudiantePersonaOutputDTO.setName(this.persona.getName());
        if(this.persona.getSurname() != null) estudiantePersonaOutputDTO.setSurname(this.persona.getSurname());
        if(this.persona.getImagen_url() != null) estudiantePersonaOutputDTO.setImagen_url(this.persona.getImagen_url());
        if(this.persona.getTermination_date() != null) estudiantePersonaOutputDTO.setTermination_date(this.persona.getTermination_date());

        return estudiantePersonaOutputDTO;

    }

}