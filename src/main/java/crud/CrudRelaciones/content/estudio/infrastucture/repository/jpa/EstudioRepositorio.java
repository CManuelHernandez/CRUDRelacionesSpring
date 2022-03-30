package crud.CrudRelaciones.content.estudio.infrastucture.repository.jpa;

import crud.CrudRelaciones.content.estudio.domain.Estudio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudioRepositorio extends JpaRepository<Estudio, String> {
}
