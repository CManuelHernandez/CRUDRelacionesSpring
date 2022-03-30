package crud.CrudRelaciones.content.estudiante.infrastucture.repository.jpa;

import crud.CrudRelaciones.content.estudiante.domain.Estudiante;
import crud.CrudRelaciones.content.persona.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstudianteRepositorio extends JpaRepository<Estudiante, String> {

    public Optional<Estudiante> findByPersona(Persona persona);
}
