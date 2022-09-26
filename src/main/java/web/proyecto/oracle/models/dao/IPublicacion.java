package web.proyecto.oracle.models.dao;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import web.proyecto.oracle.models.entity.Publicacion;

public interface IPublicacion extends PagingAndSortingRepository<Publicacion,Long> {

	@Query(value = "SELECT * FROM SPRING.PUBLICACION P WHERE P.TIPO_PUBLICACION_ID = ?1" , nativeQuery = true)
	public Page<Publicacion> findByPublicacionIdFiltro(Long id , Pageable pageable);
	
	@Query(value = "SELECT * FROM SPRING.PUBLICACION P WHERE TO_CHAR(P.FECHA,'yyyy-MM-dd') = ?1" , nativeQuery = true)
	public Page<Publicacion> findByPublicacionByFecha(String fecha , Pageable pageable);
	
	@Query(value = "SELECT * FROM SPRING.PUBLICACION P WHERE P.TIPO_PUBLICACION_ID = ?1 AND TO_CHAR(P.FECHA,'yyyy-MM-dd') = ?2" , nativeQuery = true)
	public Page<Publicacion> findByPublicacionIdAndFecha(Long id , String fecha , Pageable pageable);
	
}
