package web.proyecto.oracle.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.proyecto.oracle.models.dao.ICiudadDao;
import web.proyecto.oracle.models.dao.IDenunciaDao;
import web.proyecto.oracle.models.dao.IEventoDao;
import web.proyecto.oracle.models.dao.IEventoVoluntario;
import web.proyecto.oracle.models.dao.IPaisDao;
import web.proyecto.oracle.models.dao.IPublicacion;
import web.proyecto.oracle.models.dao.ITipoDao;
import web.proyecto.oracle.models.dao.ITipoDenuncia;
import web.proyecto.oracle.models.dao.IUsuarioDao;
import web.proyecto.oracle.models.dao.IVoluntarioDao;
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

@Service
public class ServiceGeneral implements IServiceGeneral {
     
	@Autowired
	private IEventoDao eventoDao;
	
	@Autowired
	private IPaisDao paisDao;
	
	@Autowired
	private ICiudadDao ciudadDao;
	
	@Autowired
	private ITipoDao tipoDao;
	
	@Autowired
	private IPublicacion publicacionDao;
	
	@Autowired
	private IDenunciaDao denunciaDao;
	
	@Autowired
	private ITipoDenuncia tipoDenunciaDao;
	
	@Autowired
	private IVoluntarioDao voluntarioDao;
	
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	
	@Autowired
	private IEventoVoluntario eventoVoluntarioDao;
	
	@Override
	@Transactional
	public void save(Evento evento) {
		
		eventoDao.save(evento);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Evento> findAll() {
	
		return (List<Evento>) eventoDao.findAll();
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		eventoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Evento findById(Long id) {
		
		return eventoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Pais> findAllPais() {
		
		return (List<Pais>) paisDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Ciudad> findAllCiudad() {
		
		return (List<Ciudad>) ciudadDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Ciudad findByIdCiudad(Long id) {
		
		return ciudadDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public TipoPublicacion findByIdTipoPublicacion(Long id) {
		
		return tipoDao.findById(id).orElse(null);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoPublicacion> findAllTipo() {
		
		return (List<TipoPublicacion>) tipoDao.findAll();
		
	}

	@Override
	@Transactional
	public void save(Publicacion publicacion) {
		
		publicacionDao.save(publicacion);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Publicacion> findAllPublicacion() {
		
		return (List<Publicacion>) publicacionDao.findAll() ;
		
	}

	@Override
	@Transactional(readOnly = true)
	public Publicacion findByIdPublicacion(Long id) {
		
		return publicacionDao.findById(id).orElse(null);
		
	}

	@Override
	@Transactional
	public void deletePublicacion(Long id) {
		
		publicacionDao.deleteById(id);
		
	}

	@Override
	@Transactional
	public void saveDenuncia(Denuncia denuncia) {
		
		denunciaDao.save(denuncia);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Denuncia> findAllDenuncia() {
		
		return (List<Denuncia>) denunciaDao.findAll();
		
	}

	@Override
	@Transactional(readOnly = true)
	public Denuncia findByIdDenuncia(Long id) {
		
		return denunciaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public TipoDenuncia findByTipoDenuncia(Long id) {
		
		return tipoDenunciaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoDenuncia> findAllTipoDenuncia() {
		
		return (List<TipoDenuncia>) tipoDenunciaDao.findAll();
		
	}

	@Override
	@Transactional
	public void saveUsuario(Usuario usuario) {
		
		usuarioDao.save(usuario);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAllUsuario() {
		
		return (List<Usuario>) usuarioDao.findAll();
		
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findByIdUsuario(Long id) {
		
		return usuarioDao.findById(id).orElse(null);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Publicacion> findAll(Pageable pageable) {
		
		return publicacionDao.findAll(pageable);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Denuncia> findAllDenuncia(Pageable pageable) {
		
		return denunciaDao.findAll(pageable);
		
	}

	@Override
	@Transactional
	public void deleleDenuncia(Long id) {
		
		denunciaDao.deleteById(id);
		
	}

	@Override
	@Transactional
	public void saveEventoVoluntario(EventoVoluntario eventoVoluntario) {
		
		eventoVoluntarioDao.save(eventoVoluntario);
		
	}

	@Override
	@Transactional
	public void saveVoluntario(Voluntario voluntario) {
		
		voluntarioDao.save(voluntario);
		
	}

	
	
}
