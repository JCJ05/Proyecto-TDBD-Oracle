package web.proyecto.oracle.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import web.proyecto.oracle.models.entity.Ciudad;
import web.proyecto.oracle.models.entity.Denuncia;
import web.proyecto.oracle.models.entity.Evento;
import web.proyecto.oracle.models.entity.EventoVoluntario;
import web.proyecto.oracle.models.entity.Pais;
import web.proyecto.oracle.models.entity.Publicacion;
import web.proyecto.oracle.models.entity.TipoDenuncia;
import web.proyecto.oracle.models.entity.TipoPublicacion;
import web.proyecto.oracle.models.entity.Usuario;
import web.proyecto.oracle.models.entity.Voluntario;

public interface IServiceGeneral {
	
	public void save(Evento evento);
	
	public List<Evento> findAll();
	
	public void delete(Long id);
	
	public Evento findById(Long id);
	
	public List<Pais> findAllPais();
	
	public List<Ciudad> findAllCiudad();
	
	public Ciudad findByIdCiudad(Long id);
	
	public TipoPublicacion findByIdTipoPublicacion(Long id);
	
	public List<TipoPublicacion> findAllTipo();
	
	public void save(Publicacion publicacion);
	
	public List<Publicacion> findAllPublicacion();
	
	public Publicacion findByIdPublicacion(Long id);
	
	public void deletePublicacion(Long id);
	
	public void saveDenuncia(Denuncia denuncia);

	public List<Denuncia> findAllDenuncia();
	
	public Denuncia findByIdDenuncia(Long id);
	
	public TipoDenuncia findByTipoDenuncia(Long id);
	
	public List<TipoDenuncia> findAllTipoDenuncia();
	
	public void saveUsuario(Usuario usuario);
	
	public List<Usuario> findAllUsuario();
	
	public Usuario findByIdUsuario(Long id);
	
	public Page<Publicacion> findAll(Pageable pageable);
	
	public Page<Denuncia> findAllDenuncia(Pageable pageable);
	
	public void deleleDenuncia(Long id);
	
	public void saveEventoVoluntario(EventoVoluntario eventoVoluntario);
	
	public void saveVoluntario(Voluntario voluntario);
	
}
