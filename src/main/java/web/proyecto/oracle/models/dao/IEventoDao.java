package web.proyecto.oracle.models.dao;


import org.springframework.data.repository.CrudRepository;


import web.proyecto.oracle.models.entity.Evento;

public interface IEventoDao extends CrudRepository<Evento, Long> {
	
	

}
