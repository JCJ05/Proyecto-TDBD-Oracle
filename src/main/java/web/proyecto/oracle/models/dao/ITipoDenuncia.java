package web.proyecto.oracle.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import web.proyecto.oracle.models.entity.TipoDenuncia;

public interface ITipoDenuncia extends CrudRepository<TipoDenuncia,Long> {
	
	@Query(value = "SELECT * FROM SPRING.TIPODENUNCIA WHERE ID_TIPO != ?1" , nativeQuery = true)
	public List<TipoDenuncia> findAllTipoDenunciaNotId(Long id);

}
