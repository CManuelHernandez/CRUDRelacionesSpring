package crud.CrudRelaciones.content.profesor.domain;

import com.sun.istack.NotNull;
import crud.CrudRelaciones.content.enums.Rama;
import crud.CrudRelaciones.content.estudiante.domain.Estudiante;
import crud.CrudRelaciones.content.persona.domain.Persona;
import crud.CrudRelaciones.content.profesor.infrastucture.controller.dto.input.ProfesorInputDTO;
import crud.CrudRelaciones.content.profesor.infrastucture.controller.dto.output.ProfesorOutputDTO;
import crud.CrudRelaciones.content.profesor.infrastucture.controller.dto.output.ProfesorPersonaOutputDTO;
import crud.CrudRelaciones.content.util.StringPrefixedSequenceIdGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profesores_seq")
    @GenericGenerator(
            name = "profesores_seq",
            strategy = "crud.CrudRelaciones.content.util.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "PRO"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%08d")
            })
    private String id_profesor;

    @OneToOne(optional = false)
    private Persona persona;

    @Column(name = "comentarios")
    private String comentarios;

    @NotNull
    @Column(name = "rama")
    @Enumerated(value = EnumType.STRING)
    private Rama rama;

    @OneToMany
    private List<Estudiante> alumnos;

    public Profesor(ProfesorInputDTO profesorInputDTO, Persona persona){

        this.comentarios = profesorInputDTO.getComentarios();

        switch (profesorInputDTO.getRama()) {

            case "BACK": this.rama = Rama.BACK;
                break;

            case "FRONT": this.rama = Rama.FRONT;
                break;

            case "FULLSTACK": this.rama = Rama.FULLSTACK;
                break;

            default: this.rama = null;

        }

        this.persona = persona;

    }

    public ProfesorOutputDTO profesorDTO(){

        ProfesorOutputDTO profesorOutputDTO = new ProfesorOutputDTO();

        if(this.id_profesor != null) profesorOutputDTO.setId_profesor(this.id_profesor);
        if(this.comentarios != null) profesorOutputDTO.setComentarios(this.comentarios);
        if(this.persona != null) profesorOutputDTO.setPersona(this.persona.getId_persona());
        if(this.rama != null) profesorOutputDTO.setRama(this.rama.name());
        if(this.getAlumnos() != null) profesorOutputDTO.setAlumnos(this.alumnos.stream().map(Estudiante::getId_estudiante).collect(Collectors.toList()));

        return profesorOutputDTO;

    }

    public ProfesorPersonaOutputDTO profesorPersonaDTO(){

        ProfesorPersonaOutputDTO profesorPersonaOutputDTO = new ProfesorPersonaOutputDTO();

        if(this.id_profesor != null) profesorPersonaOutputDTO.setId_profesor(this.id_profesor);
        if(this.comentarios != null) profesorPersonaOutputDTO.setComentarios(this.comentarios);
        if(this.persona != null) profesorPersonaOutputDTO.setPersona(this.persona.getId_persona());
        if(this.rama != null) profesorPersonaOutputDTO.setRama(this.rama.name());
        if(this.getAlumnos() != null) profesorPersonaOutputDTO.setAlumnos(this.alumnos.stream().map(Estudiante::getId_estudiante).collect(Collectors.toList()));

        if(this.persona.getId_persona() != 0) profesorPersonaOutputDTO.setIdPersona(this.persona.getId_persona());
        if(this.persona.getActive() != null) profesorPersonaOutputDTO.setActive(this.persona.getActive());
        if(this.persona.getPersonal_email() != null) profesorPersonaOutputDTO.setPersonalEmail(this.persona.getPersonal_email());
        if(this.persona.getCity() != null) profesorPersonaOutputDTO.setCity(this.persona.getCity());
        if(this.persona.getCompany_email() != null) profesorPersonaOutputDTO.setCompanyName(this.persona.getCompany_email());
        if(this.persona.getCreated_date() != null) profesorPersonaOutputDTO.setCreatedDate(this.persona.getCreated_date());
        if(this.persona.getUsuario() != null) profesorPersonaOutputDTO.setUsuario(this.persona.getUsuario());
        if(this.persona.getPassword() != null) profesorPersonaOutputDTO.setPassword(this.persona.getPassword());
        if(this.persona.getName() != null) profesorPersonaOutputDTO.setName(this.persona.getName());
        if(this.persona.getSurname() != null) profesorPersonaOutputDTO.setSurname(this.persona.getSurname());
        if(this.persona.getImagen_url() != null) profesorPersonaOutputDTO.setImagenUrl(this.persona.getImagen_url());
        if(this.persona.getTermination_date() != null) profesorPersonaOutputDTO.setTerminationDate(this.persona.getTermination_date());

        return profesorPersonaOutputDTO;

    }


}