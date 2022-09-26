package web.proyecto.oracle.models.entity;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

@Entity
@Table(name = "EVENTO" ,schema = "SPRING")
@SequenceGenerator(
		
	    name="IDEVENTOS",
	    initialValue = 2000, 
	    allocationSize = 1
	  
	)
public class Evento implements Serializable {


	private static final long serialVersionUID = 1L;

   
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "IDEVENTOS")
	private Long id;
	
	@NotEmpty
	@Column(name = "nomEvento" , length = 40)
	private String nombre;
	
	@NotEmpty
	@Column(name = "direccion" , length = 40)
	private String direccion;
	
	
	@NotNull
	@Column(name = "fecha")
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	
	@NotEmpty
	@Column(name = "descripcion" ,length = 350)
	private String descripcion;
	
	
	@Column(name = "fotos" ,length = 400)
	private String foto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ciudad_id" , referencedColumnName = "idCiudad")
	@NotNull(message = "Debe seleccionar una ciudad")
	private Ciudad ciudad;
    
	@Column(name = "hora")
	private String hora;
	
	@Column(name = "hora_voluntario")
	private String horaVoluntario;
	
	@NotNull
	@Column(name = "fecha_voluntario")
	@Temporal(TemporalType.DATE)
	private Date fechaVoluntario;

	public Evento() {
		
		
	}


	public Evento(Long id, @NotEmpty String nombre, @NotEmpty String direccion, @NotNull Date fecha,
			@NotEmpty String descripcion, Ciudad ciudad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.ciudad = ciudad;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Ciudad getCiudad() {
		return ciudad;
	}


	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getFoto() {
		return foto;
	}


	public void setFoto(String foto) {
		this.foto = foto;
	}


	public String getHora() {
		return hora;
	}


	public void setHora(String hora) {
		this.hora = hora;
	}


	public String getHoraVoluntario() {
		return horaVoluntario;
	}


	public void setHoraVoluntario(String horaVoluntario) {
		this.horaVoluntario = horaVoluntario;
	}


	public Date getFechaVoluntario() {
		return fechaVoluntario;
	}


	public void setFechaVoluntario(Date fechaVoluntario) {
		this.fechaVoluntario = fechaVoluntario;
	}
	
	
	
	
	
	
	
	
	
}
