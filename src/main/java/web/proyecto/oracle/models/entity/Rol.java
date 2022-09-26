package web.proyecto.oracle.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ROL" , schema = "SPRING" , uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id","authoritie"})})
@SequenceGenerator(
		
	    name="IDROL",
	    initialValue = 4000, 
	    allocationSize = 1
	  
	)
public class Rol implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "IDROL")
	@Column(name = "idRol")
	private Long id;
	
	
	private String authoritie;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getAuthoritie() {
		return authoritie;
	}


	public void setAuthoritie(String authoritie) {
		this.authoritie = authoritie;
	}
	
	

}
