package web.proyecto.oracle.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PUBLICACION" , schema = "SPRING")
@SequenceGenerator(
		
	    name="IDPUBLICACION",
	    initialValue = 4000, 
	    allocationSize = 1
	  
	)
public class Publicacion implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "IDPUBLICACION")
	@Column(name = "id_Publi")
	private Long id;
	
	@NotEmpty(message = "Debe ingresar el titulo de la publicacion")
	@Column(name = "tiulo" , length = 100)
	@Size(max = 100)
	private String titulo;
	
    @NotEmpty(message = "Debe ingresar la descripcion de la publicacion")
    @Column(name = "descripcion" , length = 2500)
    @Size(max = 2500)
	private String descripcion;
    
    @NotEmpty(message = "Debe ingresar un texto impactante para la publicacion")
    @Column(name = "resaltante" , length = 150)
    @Size(max = 150)
    public String resaltante;
    
    @Column(name = "subdes" , length = 100)
    @Size(max = 100)
    private String subdescripcion;
    
    private String foto;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipoPublicacion_id" ,referencedColumnName = "idTipo")
    @NotNull(message = "Debe seleciionar un tipo de publicacion")
    private TipoPublicacion tipoPublicacion;
    
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    public Date fecha;
    
   

	public Publicacion() {
		
		
	}

	public Publicacion(Long id, @NotEmpty @Size(max = 20) String titulo, @NotEmpty @Size(max = 350) String descripcion,
			String foto, TipoPublicacion tipoPublicacion) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.foto = foto;
		this.tipoPublicacion = tipoPublicacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public TipoPublicacion getTipoPublicacion() {
		return tipoPublicacion;
	}

	public void setTipoPublicacion(TipoPublicacion tipoPublicacion) {
		this.tipoPublicacion = tipoPublicacion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSubdescripcion() {
		return subdescripcion;
	}

	public void setSubdescripcion(String subdescripcion) {
		this.subdescripcion = subdescripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getResaltante() {
		return resaltante;
	}

	public void setResaltante(String resaltante) {
		this.resaltante = resaltante;
	}

	
	
	
	
	
	
	
    
    
    
    
}
