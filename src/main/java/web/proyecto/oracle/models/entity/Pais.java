package web.proyecto.oracle.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "PAIS" , schema = "SPRING")
@SequenceGenerator(
		
	    name="IDPAISES",
	    initialValue = 10001, 
	    allocationSize = 1
	  
	)
public class Pais implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "IDPAISES")
	@Column(name = "idPais")
	private Long id;
	
	
	@NotEmpty
	@Column(name = "nombre" , length = 40)
	private String nombre;


	public Pais() {
		
		
	}


	public Pais(Long id, @NotEmpty String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	
	
	
	
	
	
}
