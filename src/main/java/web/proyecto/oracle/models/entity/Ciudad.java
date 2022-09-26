package web.proyecto.oracle.models.entity;

import java.io.Serializable;

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
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "CIUDAD" , schema = "SPRING")
@SequenceGenerator(
		
	    name="IDCIUDADES",
	    initialValue = 1000, 
	    allocationSize = 1
	  
	)
public class Ciudad implements Serializable{

	private static final long serialVersionUID = 1L;
    
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "IDCIUDADES")
	@Column(name = "idCiudad")
	private Long id;
	
	
	@NotEmpty
	@Column(name = "nombre")
	private String ciudad;
	
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "pais_id" , referencedColumnName = "idPais")
	private Pais pais;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCiudad() {
		return ciudad;
	}


	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}


	public Pais getPais() {
		return pais;
	}


	public void setPais(Pais pais) {
		this.pais = pais;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
}
