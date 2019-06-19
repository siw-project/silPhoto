package it.silphSPA.app.model;
import javax.persistence.*;
import java.util.*;
@Entity
public class Fotografia {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String titolo;
	@ManyToOne
	private Album album;
	//@ManyToMany(mappedBy = "album")
	//private List<Richiesta> richieste;
	public Fotografia() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public Album getAlbum() {
		return album;
	}
	public void setAlbum(Album album) {
		this.album = album;
	}
	
	
}
