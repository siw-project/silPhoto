package it.silphSPA.app.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Richiesta {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String indirizzo;
	private String nomeDestinatario;
	private String cognomeDestinatario;
	@ManyToMany(mappedBy="richieste")
	private List<Fotografia> fotografie;
	public Richiesta() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getNomeDestinatario() {
		return nomeDestinatario;
	}
	public void setNomeDestinatario(String nomeDestinatario) {
		this.nomeDestinatario = nomeDestinatario;
	}
	public String getCognomeDestinatario() {
		return cognomeDestinatario;
	}
	public void setCognomeDestinatario(String cognomeDestinatario) {
		this.cognomeDestinatario = cognomeDestinatario;
	}
}
