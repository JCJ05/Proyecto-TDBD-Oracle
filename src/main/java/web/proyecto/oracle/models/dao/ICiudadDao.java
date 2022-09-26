package web.proyecto.oracle.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import web.proyecto.oracle.models.entity.Ciudad;

public interface ICiudadDao extends CrudRepository<Ciudad,Long> {
    
	@Query(value = "SELECT * FROM CIUDAD WHERE pais_id = ?1" ,nativeQuery = true)
	public List<Ciudad>finById(Long id);
	
	@Query(value = "SELECT * FROM SPRING.CIUDAD C WHERE C.ID_CIUDAD != ?1" , nativeQuery = true)
	public List<Ciudad>findByDiferentIdCiudad(Long id);
}
