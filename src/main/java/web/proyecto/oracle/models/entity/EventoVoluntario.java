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

@Entity
@Table(name = "EVENTO_VOLUNTARIO" , schema = "SPRING")
@SequenceGenerator(
		
	    name="IDEVENTOVOLUNTARIO",
	    initialValue = 7000, 
	    allocationSize = 1
	  
	)
public class EventoVoluntario implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy =  GenerationType.SEQUENCE , generator = "IDEVENTOVOLUNTARIO")
	@Column(name = "ID_EVE_VOL")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "voluntario_id" , referencedColumnName = "idVoluntario")
	private Voluntario voluntario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "evento_id")
	private Evento evento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Voluntario getVoluntario() {
		return voluntario;
	}

	public void setVoluntario(Voluntario voluntario) {
		this.voluntario = voluntario;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
