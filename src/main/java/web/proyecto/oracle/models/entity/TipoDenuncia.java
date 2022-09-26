package web.proyecto.oracle.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TIPODENUNCIA" , schema = "SPRING")
@SequenceGenerator(
		
	    name="IDTIPO",
	    initialValue = 6000, 
	    allocationSize = 1
	  
	)
public class TipoDenuncia implements Serializable {

	
	private static final long serialVersionUID = 1L;
    
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "IDTIPO")
	@Column(name = "idTipo")
	private Long id;
	
	@Column(name = "tipo")
	private String nomDenuncia;
	
	
	

	public TipoDenuncia() {
		
		
	}
	
	
	public TipoDenuncia(Long id, String nomDenuncia) {
	
		this.id = id;
		this.nomDenuncia = nomDenuncia;
	}




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomDenuncia() {
		return nomDenuncia;
	}

	public void setNomDenuncia(String nomDenuncia) {
		this.nomDenuncia = nomDenuncia;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
