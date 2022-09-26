package web.proyecto.oracle.models.entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "VOLUNTARIO" ,schema = "SPRING")
@SequenceGenerator(
		
	    name="IDVOLUNTARIO",
	    initialValue = 80000, 
	    allocationSize = 1
	  
	)
public class Voluntario implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "IDVOLUNTARIO")
	@Column(name = "idVoluntario")
	private Long id;
    
	@Column(name = "descripcion" , length = 2500)
	@NotEmpty
	private String descripcion;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id" , referencedColumnName = "idUsuario")
	private Usuario usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	
	
	
	
}
