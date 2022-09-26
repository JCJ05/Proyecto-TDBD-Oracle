package web.proyecto.oracle.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;




@Entity
@Table(name = "USUARIO" , schema = "SPRING")
@SequenceGenerator(
		
	    name="IDUSUARIO",
	    initialValue = 50000, 
	    allocationSize = 1
	  
	)
public class Usuario implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy =  GenerationType.SEQUENCE , generator = "IDUSUARIO")
	@Column(name = "idUsuario")
	private Long id;
	
	@NotEmpty(message = "Debe ingresar el nombre")
	@Column(name = "nombre")
	private String nombre;
	
	@NotEmpty(message = "Debe ingresar su apellido paterno")
	@Column(name = "apepatusu")
	private String apePat;
	
	@NotEmpty(message = "Debe ingresar su apellido materno")
	@Column(name = "apematusu")
	private String apemat;
	
	@NotNull(message = "Debe ingresar su fecha de nacimiento")
	@Column(name = "fechaNac")
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@NotEmpty(message = "Debe ingresar su documento de identidad")
	@Column(name = "dni")
	@Size(max = 8)
	private String identificacion;
	
	@NotEmpty(message = "Debe ingresar su correo por favor")
	@Column(name = "username")
	private String username;
	
	@NotEmpty(message = "Debe ingresar su telefono por favor")
	@Column(name = "telefono")
	private String telefono;
	
	@ManyToOne
	@JoinColumn(name = "ciudad_id" , referencedColumnName = "idCiudad")
	@NotNull(message = "Debe elegir una ciudad")
	private Ciudad ciudad;
	
	@Column(length = 60 , name = "password")
	private String password;
	
	private Boolean enabled;
	
	@OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	@JoinColumn(name = "usuario_id")
	private List<Rol> roles;
  
	

	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Usuario(Long id, @NotEmpty String nombre, @NotEmpty String apePat, @NotEmpty String apemat,
			@NotNull Date fecha, @NotEmpty @Size(max = 8) String identificacion, @NotEmpty String username,
			@NotEmpty String telefono, @NotNull(message = "Debe elegir una ciudad") Ciudad ciudad, List<Rol> roles) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apePat = apePat;
		this.apemat = apemat;
		this.fecha = fecha;
		this.identificacion = identificacion;
		this.username = username;
		this.telefono = telefono;
		this.ciudad = ciudad;
		this.roles = roles;
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


	public String getApePat() {
		return apePat;
	}


	public void setApePat(String apePat) {
		this.apePat = apePat;
	}


	public String getApemat() {
		return apemat;
	}


	public void setApemat(String apemat) {
		this.apemat = apemat;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public String getIdentificacion() {
		return identificacion;
	}


	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public Ciudad getCiudad() {
		return ciudad;
	}


	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}


	public List<Rol> getRoles() {
		return roles;
	}


	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}
    
	
	
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	


	public Boolean getEnabled() {
		return enabled;
	}


	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	

}
