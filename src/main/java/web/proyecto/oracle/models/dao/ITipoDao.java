package web.proyecto.oracle.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import web.proyecto.oracle.models.entity.TipoPublicacion;

public interface ITipoDao extends CrudRepository<TipoPublicacion,Long>{
	
	@Query(value = "SELECT * FROM SPRING.TIPO_PUBLI T WHERE T.ID_TIPO != ?1" , nativeQuery = true)
	public List<TipoPublicacion> findByIdTipoPublicacionNotId(Long id);

}
