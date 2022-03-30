package crud.CrudRelaciones.content.profesor.infrastucture.repository.jpa;


import crud.CrudRelaciones.content.persona.domain.Persona;
import crud.CrudRelaciones.content.profesor.domain.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfesorRepositorio extends JpaRepository<Profesor, String> {

        public Optional<Profesor> findByPersona(Persona persona);
}
