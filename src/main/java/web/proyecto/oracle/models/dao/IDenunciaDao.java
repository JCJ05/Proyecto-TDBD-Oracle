package web.proyecto.oracle.models.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import web.proyecto.oracle.models.entity.Denuncia;

public interface IDenunciaDao extends PagingAndSortingRepository <Denuncia,Long> {
       
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.TIPO_DENUNCIA_ID = ?1" , nativeQuery = true)
	  public Page<Denuncia> findByidDenunciaFiltro(Long id , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE TO_CHAR(D.FECHA_DENUNCIA,'yyyy-MM-dd') = ?1" , nativeQuery = true)
	  public Page<Denuncia> findByFechaDenunciaFiltro(String fecha , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.LEIDA != ?1 " , nativeQuery = true)
	  public Page<Denuncia> findByEstadoDenunciaFiltro1(String valor , Pageable pageable);
	
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.LEIDA = ?1 " , nativeQuery = true)
	  public Page<Denuncia> findByEstadoDenunciaFiltro2(String valor , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.PROCESADA = ?1 " , nativeQuery = true)
	  public Page<Denuncia> findByEstadoDenunciaFiltro3(String valor , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.CULMINADA = ?1 " , nativeQuery = true)
	  public Page<Denuncia> findByEstadoDenunciaFiltro4(String valor , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.CIUDAD_ID = ?1" , nativeQuery = true)
	  public Page<Denuncia> findByCiudadDenunciaFiltro(Long id , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.TIPO_DENUNCIA_ID = ?1 AND TO_CHAR(D.FECHA_DENUNCIA,'yyyy-MM-dd') = ?2" , nativeQuery = true)
	  public Page<Denuncia> findByidDenunciaAndFecha(Long id , String fecha , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.TIPO_DENUNCIA_ID = ?1 AND D.LEIDA != ?2" , nativeQuery = true)
	  public Page<Denuncia> findByIdDenunciaAndEstado1(Long id , String valor , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.TIPO_DENUNCIA_ID = ?1 AND D.LEIDA = ?2" , nativeQuery = true)
	  public Page<Denuncia> findByIdDenunciaAndEstado2(Long id , String valor , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.TIPO_DENUNCIA_ID = ?1 AND D.PROCESADA = ?2" , nativeQuery = true)
	  public Page<Denuncia> findByIdDenunciaAndEstado3(Long id , String valor , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.TIPO_DENUNCIA_ID = ?1 AND D.CULMINADA = ?2" , nativeQuery = true)
	  public Page<Denuncia> findByIdDenunciaAndEstado4(Long id , String valor , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.TIPO_DENUNCIA_ID = ?1 AND D.CIUDAD_ID = ?2" , nativeQuery = true)
	  public Page<Denuncia> findByIdDenunciaAndCiudad(Long id , Long idCiudad , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE TO_CHAR(D.FECHA_DENUNCIA , 'yyyy-MM-dd') = ?1 AND D.LEIDA != ?2" , nativeQuery = true)
	  public Page<Denuncia> findByFechaAndEstado1(String fecha , String estado , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE TO_CHAR(D.FECHA_DENUNCIA , 'yyyy-MM-dd') = ?1 AND D.LEIDA = ?2" , nativeQuery = true)
	  public Page<Denuncia> findByFechaAndEstado2(String fecha , String estado , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE TO_CHAR(D.FECHA_DENUNCIA , 'yyyy-MM-dd') = ?1 AND D.PROCESADA = ?2" , nativeQuery = true)
	  public Page<Denuncia> findByFechaAndEstado3(String fecha , String estado , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE TO_CHAR(D.FECHA_DENUNCIA , 'yyyy-MM-dd') = ?1 AND D.CULMINADA = ?2" , nativeQuery = true)
	  public Page<Denuncia> findByFechaAndEstado4(String fecha , String estado , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE TO_CHAR(D.FECHA_DENUNCIA , 'yyyy-MM-dd') = ?1 AND D.CIUDAD_ID = ?2" , nativeQuery = true)
	  public Page<Denuncia> findByFechaAndCiudad(String fecha , Long idCiudad , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.LEIDA != ?1 AND D.CIUDAD_ID = ?2" , nativeQuery = true)
	  public Page<Denuncia> findByEstadoAndCiudadDenuncia1(String estado , Long idCiudad , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.LEIDA = ?1 AND D.CIUDAD_ID = ?2" , nativeQuery = true)
	  public Page<Denuncia> findByEstadoAndCiudadDenuncia2(String estado , Long idCiudad , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.PROCESADA = ?1 AND D.CIUDAD_ID = ?2" , nativeQuery = true)
	  public Page<Denuncia> findByEstadoAndCiudadDenuncia3(String estado , Long idCiudad , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.CULMINADA = ?1 AND D.CIUDAD_ID = ?2" , nativeQuery = true)
	  public Page<Denuncia> findByEstadoAndCiudadDenuncia4(String estado , Long idCiudad , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.TIPO_DENUNCIA_ID = ?1 AND TO_CHAR(D.FECHA_DENUNCIA ,'yyyy-MM-dd') = ?2 AND D.LEIDA != ?3" , nativeQuery = true)
	  public Page<Denuncia> findByTipoAndFechaAndEstado1(Long id , String fecha , String estado , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.TIPO_DENUNCIA_ID = ?1 AND TO_CHAR(D.FECHA_DENUNCIA ,'yyyy-MM-dd') = ?2 AND D.LEIDA = ?3" , nativeQuery = true)
	  public Page<Denuncia> findByTipoAndFechaAndEstado2(Long id , String fecha , String estado , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.TIPO_DENUNCIA_ID = ?1 AND TO_CHAR(D.FECHA_DENUNCIA ,'yyyy-MM-dd') = ?2 AND D.PROCESADA = ?3" , nativeQuery = true)
	  public Page<Denuncia> findByTipoAndFechaAndEstado3(Long id , String fecha , String estado , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.TIPO_DENUNCIA_ID = ?1 AND TO_CHAR(D.FECHA_DENUNCIA ,'yyyy-MM-dd') = ?2 AND D.CULMINADA = ?3" , nativeQuery = true)
	  public Page<Denuncia> findByTipoAndFechaAndEstado4(Long id , String fecha , String estado , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.CIUDAD_ID = ?1 AND TO_CHAR(D.FECHA_DENUNCIA ,'yyyy-MM-dd') = ?2 AND D.LEIDA != ?3" , nativeQuery = true)
	  public Page<Denuncia> findByCiudadAndFechaAndEstado1(Long idCiudad , String fecha , String estado , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.CIUDAD_ID = ?1 AND TO_CHAR(D.FECHA_DENUNCIA ,'yyyy-MM-dd') = ?2 AND D.LEIDA = ?3" , nativeQuery = true)
	  public Page<Denuncia> findByCiudadAndFechaAndEstado2(Long idCiudad , String fecha , String estado , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.CIUDAD_ID = ?1 AND TO_CHAR(D.FECHA_DENUNCIA ,'yyyy-MM-dd') = ?2 AND D.PROCESADA = ?3" , nativeQuery = true)
	  public Page<Denuncia> findByCiudadAndFechaAndEstado3(Long idCiudad , String fecha , String estado , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.CIUDAD_ID = ?1 AND TO_CHAR(D.FECHA_DENUNCIA ,'yyyy-MM-dd') = ?2 AND D.CULMINADA = ?3" , nativeQuery = true)
	  public Page<Denuncia> findByCiudadAndFechaAndEstado4(Long idCiudad , String fecha , String estado , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.CIUDAD_ID = ?1 AND D.TIPO_DENUNCIA_ID = ?2 AND D.LEIDA != ?3" , nativeQuery = true)
	  public Page<Denuncia> findByCiudadAndTipoAndEstado1(Long idCiudad , Long id , String estado , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.CIUDAD_ID = ?1 AND D.TIPO_DENUNCIA_ID = ?2 AND D.LEIDA = ?3" , nativeQuery = true)
	  public Page<Denuncia> findByCiudadAndTipoAndEstado2(Long idCiudad , Long id , String estado , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.CIUDAD_ID = ?1 AND D.TIPO_DENUNCIA_ID = ?2 AND D.PROCESADA = ?3" , nativeQuery = true)
	  public Page<Denuncia> findByCiudadAndTipoAndEstado3(Long idCiudad , Long id , String estado , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.CIUDAD_ID = ?1 AND D.TIPO_DENUNCIA_ID = ?2 AND D.CULMINADA = ?3" , nativeQuery = true)
	  public Page<Denuncia> findByCiudadAndTipoAndEstado4(Long idCiudad , Long id , String estado , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.TIPO_DENUNCIA_ID = ?1 AND D.CIUDAD_ID = ?2 AND TO_CHAR(D.FECHA_DENUNCIA,'yyyy-MM-dd') = ?3" , nativeQuery = true)
	  public Page<Denuncia> findByTipoAndFechaAndCiudad(Long id , Long idCiudad , String fecha , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.TIPO_DENUNCIA_ID = ?1 AND D.CIUDAD_ID = ?2 AND D.LEIDA != ?3 AND TO_CHAR(D.FECHA_DENUNCIA,'yyyy-MM-dd') = ?4" , nativeQuery = true)
	  public Page<Denuncia> findByAllFilters1(Long id , Long idCiudad , String estado , String fecha , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.TIPO_DENUNCIA_ID = ?1 AND D.CIUDAD_ID = ?2 AND D.LEIDA = ?3 AND TO_CHAR(D.FECHA_DENUNCIA,'yyyy-MM-dd') = ?4" , nativeQuery = true)
	  public Page<Denuncia> findByAllFilters2(Long id , Long idCiudad , String estado , String fecha , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.TIPO_DENUNCIA_ID = ?1 AND D.CIUDAD_ID = ?2 AND D.PROCESADA = ?3 AND TO_CHAR(D.FECHA_DENUNCIA,'yyyy-MM-dd') = ?4" , nativeQuery = true)
	  public Page<Denuncia> findByAllFilters3(Long id , Long idCiudad , String estado , String fecha , Pageable pageable);
	  
	  @Query(value = "SELECT * FROM SPRING.DENUNCIA D WHERE D.TIPO_DENUNCIA_ID = ?1 AND D.CIUDAD_ID = ?2 AND D.CULMINADA = ?3 AND TO_CHAR(D.FECHA_DENUNCIA,'yyyy-MM-dd') = ?4" , nativeQuery = true)
	  public Page<Denuncia> findByAllFilters4(Long id , Long idCiudad , String estado , String fecha , Pageable pageable);
	  
	  
}
