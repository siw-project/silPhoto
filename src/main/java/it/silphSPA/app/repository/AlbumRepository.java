package it.silphSPA.app.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.silphSPA.app.model.Album;
import it.silphSPA.app.model.Fotografo;

public interface AlbumRepository extends CrudRepository<Album,Long> {
	public Album findByTitolo(String Titolo);
	public List<Album> findByFotografo(Fotografo f);

}