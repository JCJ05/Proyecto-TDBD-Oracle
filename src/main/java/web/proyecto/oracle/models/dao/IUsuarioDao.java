package web.proyecto.oracle.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import web.proyecto.oracle.models.entity.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario,Long>{
    
	@Query(value = "SELECT * FROM SPRING.USUARIO WHERE username = ?1" , nativeQuery = true)
	public Usuario findByCorreo(String username);
	
}
