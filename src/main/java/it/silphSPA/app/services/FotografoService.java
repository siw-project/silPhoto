package it.silphSPA.app.services;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.silphSPA.app.model.Album;
import it.silphSPA.app.model.Fotografo;
import it.silphSPA.app.repository.FotografoRepository;

@Service
public class FotografoService {
	@Autowired
	private FotografoRepository fotografoRepository;
	@Autowired
	private AlbumService albumService;
	
	@Transactional
	public Fotografo addFotografo(Fotografo f) {
		return this.fotografoRepository.save(f);
	}
	@Transactional
	public List<Fotografo> getTutti(){
		return (List<Fotografo>)this.fotografoRepository.findAll();
	}
	@Transactional
	public Fotografo getPerId(Long id) {
		return this.fotografoRepository.findById(id).get();
	}
	@Transactional
	public List<Fotografo> getPerNome(String nome){
		return this.fotografoRepository.findByNome(nome);
	}
	@Transactional
	public List<Fotografo> getPerNomeAndCognome(String nome, String cognome){
		return this.fotografoRepository.findByNomeAndCognome(nome, cognome);
	}
	@Transactional
	public boolean existsPerId(Long id) {
		return this.fotografoRepository.existsById(id);
	}
	@Transactional
	public void addAlbum(Album a, Fotografo f) {
		this.albumService.addAlbum(a);
		//f.addAlbum(a);
		
	}
	@Transactional
	public List<Album> getAlbum(Fotografo f){
		return (List<Album>)f.getAlbum().values();
	}
	

}
