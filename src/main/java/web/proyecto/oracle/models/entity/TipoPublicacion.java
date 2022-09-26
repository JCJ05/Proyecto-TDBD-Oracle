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
@Table(name = "TIPO_PUBLI" , schema = "SPRING")
@SequenceGenerator(
		
	    name="IDTIPOPUBLICACION",
	    initialValue = 3000, 
	    allocationSize = 1
	  
	)
public class TipoPublicacion implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "IDTIPOPUBLICACION")
	@Column(name = "idTipo")
    private Long id;
	
	
	@NotEmpty
	@Column(name = "tipo" , length = 40)
	private String tipo;


	
	
	public TipoPublicacion() {
	
		
	}
	
	


	public TipoPublicacion(Long id, @NotEmpty String tipo) {
		super();
		this.id = id;
		this.tipo = tipo;
	}




	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
}
