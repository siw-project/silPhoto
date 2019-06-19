package it.silphSPA.app.services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.silphSPA.app.model.Album;
import it.silphSPA.app.model.Fotografia;
import it.silphSPA.app.model.Fotografo;
import it.silphSPA.app.repository.AlbumRepository;
import it.silphSPA.app.repository.FotografiaRepository;

@Service
public class AlbumService {
	@Autowired
	private AlbumRepository albumRepository;
	@Autowired
	private FotografiaService fotografiaService;
	
	@Transactional
	public Album addAlbum(Album a) {
		return this.albumRepository.save(a);
	}
	@Transactional
	public List<Album> getTutti(){
		return (List<Album>)this.albumRepository.findAll();
	}
	@Transactional
	public Album getPerId(Long id) {
		return this.albumRepository.findById(id).get();
	}
	@Transactional
	public Album getPerTitolo(String titolo){
		return this.albumRepository.findByTitolo(titolo);
	}
	@Transactional
	public boolean existsPerId(Long id) {
		return this.albumRepository.existsById(id);
	}
	@Transactional
	public void setFotografo(Album a, Fotografo f) {
		a.setFotografo(f);
	}
	@Transactional
	public void addFotgrafia(Fotografia f, Album a) {
		this.fotografiaService.addFotografia(f);
		a.addFotografia(f);
	}
	@Transactional
	public List<Album> getAlbumPerFotografo(Fotografo f){
		return this.albumRepository.findByFotografo(f);
	}
	@Transactional
	public List<Fotografia> getFotografie(Album a){
		return (List<Fotografia>)a.getFotografie().values();
	}
	
	

}

