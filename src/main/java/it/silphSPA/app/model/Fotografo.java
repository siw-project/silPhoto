package it.silphSPA.app.model;
import java.util.*;

import javax.persistence.*;
@Entity
public class Fotografo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String cognome;
	@OneToMany(mappedBy = "fotografo")
	private Map<Long,Album> album;
	
	public Fotografo() {
		/*this.nome = nome;
		this.cognome = cognome;*/
		this.album = new HashMap<Long,Album>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Map<Long, Album> getAlbum() {
		return album;
	}

	public void setAlbum(Map<Long, Album> album) {
		this.album = album;
	}
	public void addAlbum(Album a) {
		this.album.put(a.getId(), a);
	}
	

}
