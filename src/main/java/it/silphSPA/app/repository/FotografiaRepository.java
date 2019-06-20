package it.silphSPA.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.silphSPA.app.model.Album;
import it.silphSPA.app.model.Fotografia;
import it.silphSPA.app.model.Richiesta;

public interface FotografiaRepository extends CrudRepository<Fotografia,Long> {
	public Fotografia findByTitolo(String Titolo);
	public List<Fotografia> findByAlbum(Album a);
	public List<Fotografia> findByRichieste(Richiesta r);

}