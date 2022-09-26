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
@Table(name = "DENUNCIA" , schema = "SPRING")
@SequenceGenerator(
		
	    name="IDENUNCIA",
	    initialValue = 6000, 
	    allocationSize = 1
	  
	)
public class Denuncia implements Serializable {

	
	private static final long serialVersionUID = 1L;
    
	@Id
	@GeneratedValue(strategy =  GenerationType.SEQUENCE , generator = "IDENUNCIA")
	@Column(name = "idDenuncia")
	private Long id;
	
	
	@NotEmpty
	@Size(max = 40)
	private String direccion;
	
	
	@NotEmpty
	@Size(max = 2500)
	private String descripcion;
	
	
	@NotNull
	@Column(name = "fecha_denuncia")
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	
	private String foto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipoDenuncia_id" , referencedColumnName = "idTipo")
	@NotNull(message = "Debes elegir un tipo de denuncia")
	private TipoDenuncia tipoDenuncia;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ciudad_id" , referencedColumnName = "idCiudad")
	@NotNull(message = "Debe seleccionar la ciudad donde se visualizo este hecho")
	private Ciudad ciudad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id" , referencedColumnName = "idUsuario")
	private Usuario usuario;
    
	@Column(name = "leida")
	private Boolean leida;
	    
	@Column(name = "procesada")
	private Boolean procesada;
	    
	@Column(name = "culminada")
	private Boolean culminada;
	
	public Denuncia() {
		
		
	}

	public Denuncia(Long id, @NotEmpty @Size(max = 40) String direccion, @NotEmpty @Size(max = 2500) String descripcion,
			@NotNull Date fecha, String foto,
			@NotNull(message = "Debes elegir un tipo de denuncia") TipoDenuncia tipoDenuncia,
			@NotNull(message = "Debe seleccionar la ciudad donde visualizo este hecho") Ciudad ciudad,
			Usuario usuario) {
		super();
		this.id = id;
		this.direccion = direccion;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.foto = foto;
		this.tipoDenuncia = tipoDenuncia;
		this.ciudad = ciudad;
		this.usuario = usuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public TipoDenuncia getTipoDenuncia() {
		return tipoDenuncia;
	}

	public void setTipoDenuncia(TipoDenuncia tipoDenuncia) {
		this.tipoDenuncia = tipoDenuncia;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Boolean getLeida() {
		return leida;
	}

	public void setLeida(Boolean leida) {
		this.leida = leida;
	}

	public Boolean getProcesada() {
		return procesada;
	}

	public void setProcesada(Boolean procesada) {
		this.procesada = procesada;
	}

	public Boolean getCulminada() {
		return culminada;
	}

	public void setCulminada(Boolean culminada) {
		this.culminada = culminada;
	}
	
	
	
	
	
	
}
