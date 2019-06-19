package it.silphSPA.app.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.silphSPA.app.model.*;
public interface FotografoRepository extends CrudRepository<Fotografo,Long> {
	public List<Fotografo> findByNome(String nome);
	public List<Fotografo> findByNomeAndCognome(String nome, String cognome);

}
