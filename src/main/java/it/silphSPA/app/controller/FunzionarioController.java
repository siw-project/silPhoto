package it.silphSPA.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.silphSPA.app.model.Album;
import it.silphSPA.app.model.Fotografia;
import it.silphSPA.app.model.Fotografo;
import it.silphSPA.app.services.*;

@Controller
public class FunzionarioController {
	@Autowired
	private FotografoService fotografoService;
	@Autowired
	private FotografoValidator fotografoValidator;
	@Autowired
	private AlbumService albumService;
	@Autowired
	private AlbumValidator albumValidator;
	@Autowired
	private FotografiaService fotografiaService;
	@Autowired
	private FotografiaValidator fotografiaValidator;

	@RequestMapping("/homeFunzionario")
	public String homeFunzionario() {
		return "homeFunzionario";
	}
	
	@RequestMapping("/addFotografo")
	public String visualizzaFotografoForm(@Valid@ModelAttribute("fotografo")Fotografo fotografo) {
		return "fotografoForm";
	}

	@RequestMapping(value = "/fotografo", method = {RequestMethod.POST, RequestMethod.GET})
	public String inserisciNuovoFotografo(@Valid@ModelAttribute("fotografo")Fotografo fotografo, 
			Model model, String nome, String cognome,BindingResult bindingResult) {
		this.fotografoValidator.validate(fotografo, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.fotografoService.addFotografo(fotografo);
			model.addAttribute("fotografi", this.fotografoService.getTutti());
			return "fotografi";
		}else {
			return "fotografoForm";

		}
	}




	@RequestMapping(value = "/fotografo/{id}", method = RequestMethod.GET)
	public String visualizzaFotografo(@PathVariable("id")Long id, Model model){
		if(this.fotografoService.existsPerId(id)) {
			model.addAttribute("fotografo", this.fotografoService.getPerId(id));
			return "fotografo";
		}else {
			model.addAttribute("fotografi", this.fotografoService.getTutti());
			return "fotografi";
		}
	}
	@RequestMapping("/fotografo/{id}/addAlbum")
	public String visualizzaAlbumForm(@Valid@ModelAttribute("album")Album album,@PathVariable("id")Long id, 
			Model model) {
		Fotografo f = this.fotografoService.getPerId(id);
		model.addAttribute("fotografo", f);
		return "albumForm";
	}


	@RequestMapping(value = "/fotografo/{id}/album", method = RequestMethod.POST)
	public String inserisciNuovoAlbum(@Valid@ModelAttribute("album")Album album,
			@PathVariable("id")Long id,Model model, String titolo, BindingResult bindingResultAlbum ) {
		Fotografo f = this.fotografoService.getPerId(id);
		model.addAttribute("fotografo", f);
		this.albumValidator.validate(album, bindingResultAlbum);
		if(!bindingResultAlbum.hasErrors()) {
			this.albumService.setFotografo(album, f);
			this.fotografoService.addAlbum(album, f);
			model.addAttribute("album", album);
			model.addAttribute("albums", this.albumService.getAlbumPerFotografo(f));
			return "albums";

		}
		else {
			return "albumForm";
		}

	}
	@RequestMapping(value = "/fotografo/{idF}/album/{idA}", method = RequestMethod.GET)
	public String visualizzaAlbum(@Valid@ModelAttribute("fotografia") Fotografia ph,
			@PathVariable("idF")Long idF,
			@PathVariable("idA")Long idA, Model model) {
		Fotografo f = this.fotografoService.getPerId(idF);
		model.addAttribute("fotografo", f);
		if(this.albumService.existsPerId(idA)) {
			model.addAttribute("album", this.albumService.getPerId(idA));
			return "album";
		}
		else {
			model.addAttribute("albums", this.fotografoService.getAlbum(f));
			return "albums";
		}
	}
	@RequestMapping(value = "/fotografo/{idF}/album/{idA}/addFotografia")
	public String visualizzaFotografiaForm(@Valid@ModelAttribute("fotografia")Fotografia fotografia,
			@PathVariable("idF")Long idF,@PathVariable("idA")Long idA,Model model) {
		model.addAttribute("fotografo", this.fotografoService.getPerId(idF));
		model.addAttribute("album", this.albumService.getPerId(idA));
		return "fotografiaForm";

	}
	@RequestMapping(value = "/fotografo/{idF}/album/{idA}/aggiornato", method = RequestMethod.POST)
	public String inserisciNuovaFotografia(@Valid@ModelAttribute("fotografia")Fotografia fotografia,
			@PathVariable("idF")Long idF, @PathVariable("idA")Long idA, Model model,
			BindingResult bindingResult, String titolo) {
		Fotografo f = this.fotografoService.getPerId(idF);
		Album a = this.albumService.getPerId(idA);
		this.fotografiaValidator.validate(fotografia, bindingResult);
		if(!bindingResult.hasErrors()) {			
			this.albumService.addFotgrafia(fotografia, a);
			this.fotografiaService.setAlbum(a, fotografia);
			model.addAttribute("fotografie", this.fotografiaService.getPerAlbum(a));
			model.addAttribute("album", a);
			model.addAttribute("fotografo", f);
			model.addAttribute("confermaInserimentoFotografia","Fotografia inserita correttamente!");
			return "album";}
		else {
			model.addAttribute("album", a);
			model.addAttribute("fotografo", f);
			return "fotografiaForm";

		}
	}

	@RequestMapping(value = "/fotografo/{idF}/album/{idA}/fotografia/{idPh}", method = RequestMethod.GET)
	public String visualizzaFotografia(@Valid@ModelAttribute("fotografia")Fotografia fotografia,
			@PathVariable("idF")Long idF, @PathVariable("idA")Long idA,
			@PathVariable("idPh")Long idPh,  Model model) {
		Fotografo f = this.fotografoService.getPerId(idF);
		Album a = this.albumService.getPerId(idA);
		model.addAttribute("fotografo", f);
		model.addAttribute("album", a);
		if(this.fotografiaService.existsPerId(idPh)) {
			model.addAttribute("fotografia", this.fotografiaService.getPerId(idPh));
			return "fotografia";
		}
		else {
			model.addAttribute("fotografie", this.albumService.getFotografie(a));
			return "album";
		}

	}

}
