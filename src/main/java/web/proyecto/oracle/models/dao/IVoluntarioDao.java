package web.proyecto.oracle.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import web.proyecto.oracle.models.entity.Voluntario;

public interface IVoluntarioDao extends CrudRepository<Voluntario,Long>{

	
	@Query(value = "SELECT * FROM SPRING.VOLUNTARIO WHERE USUARIO_ID = ?1" , nativeQuery = true)
	public Voluntario findByIdUsuarioXId(Long id);
}
