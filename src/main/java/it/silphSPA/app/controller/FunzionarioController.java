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
	@Autowired
	private RichiestaService richiestaService;

	@RequestMapping("/funzionario/home")
	public String homeFunzionario() {
		return "homeFunzionario";
	}
	@RequestMapping("/funzionario/richieste")
	public String visualizzaRichieste(Model model) {
		model.addAttribute("richieste", this.richiestaService.getTutte());
		return "listaRichieste";
	}
	@RequestMapping("/funzionario/listaFotografi")
	public String visualizzaAreaFotografi(Model model) {
		model.addAttribute("fotografi", this.fotografoService.getTutti());
		return "listaFotografi";
	}
	
	@RequestMapping("/funzionario/addFotografo")
	public String visualizzaFotografoForm(@Valid@ModelAttribute("fotografo")Fotografo fotografo) {
		return "fotografoForm";
	}

	@RequestMapping(value = "/funzionario/fotografo", method = {RequestMethod.POST, RequestMethod.GET})
	public String inserisciNuovoFotografo(@Valid@ModelAttribute("fotografo")Fotografo fotografo, 
			Model model, String nome, String cognome,BindingResult bindingResult) {
		this.fotografoValidator.validate(fotografo, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.fotografoService.addFotografo(fotografo);
			return "confermaInserimentoFotografo";
		}else {
			return "fotografoForm";

		}
	}




	@RequestMapping(value = "/funzionario/fotografo/{id}", method = RequestMethod.GET)
	public String visualizzaFotografo(@PathVariable("id")Long id, Model model){
		if(this.fotografoService.existsPerId(id)) {
			Fotografo f = this.fotografoService.getPerId(id);
			model.addAttribute("fotografo", f);
			model.addAttribute("listaAlbum", this.albumService.getAlbumPerFotografo(f));
			return "fotografoFunzionario";
		}else {
			model.addAttribute("fotografi", this.fotografoService.getTutti());
			return "listaFotografi";
		}
	}

	@RequestMapping("/funzionario/fotografo/{id}/addAlbum")
	public String visualizzaAlbumForm(@Valid@ModelAttribute("album")Album album,@PathVariable("id")Long id, 
			Model model) {
		Fotografo f = this.fotografoService.getPerId(id);
		model.addAttribute("fotografo", f);
		return "albumForm";
	}


	@RequestMapping(value = "/funzionario/fotografo/{id}/album", method = {RequestMethod.GET, RequestMethod.POST})
	public String inserisciNuovoAlbum(@Valid@ModelAttribute("album")Album album,
			@PathVariable("id")Long id,Model model, String titolo, BindingResult bindingResultAlbum ) {
		Fotografo f = this.fotografoService.getPerId(id);
		model.addAttribute("fotografo", f);
		this.albumValidator.validate(album, bindingResultAlbum);
		if(!bindingResultAlbum.hasErrors()) {
			this.albumService.setFotografo(album, f);
			this.fotografoService.addAlbum(album, f);
			model.addAttribute("confermaInserimentoAlbum", "Album Inserito Correttamente!");
			model.addAttribute("album", album);
			model.addAttribute("listaAlbum", this.albumService.getAlbumPerFotografo(f));
			return "fotografoFunzionario";

		}
		else {
			return "albumForm";
		}

	}
	@RequestMapping(value = "/funzionario/fotografo/{idF}/album/{idA}", method = RequestMethod.GET)
	public String visualizzaAlbum(@PathVariable("idF")Long idF,
			@PathVariable("idA")Long idA, Model model) {
		Fotografo f = this.fotografoService.getPerId(idF);
		Album a = this.albumService.getPerId(idA);
		model.addAttribute("fotografo", f);
		if(this.albumService.existsPerId(idA)) {
			model.addAttribute("fotografie", this.fotografiaService.getPerAlbum(a));
			model.addAttribute("album", a);
			return "albumFunzionario";
		}
		else {
			model.addAttribute("listaAlbum", this.albumService.getAlbumPerFotografo(f));
			return "fotografoFunzionario";
		}
	}
	@RequestMapping(value = "/funzionario/fotografo/{idF}/album/{idA}/addFotografia")
	public String visualizzaFotografiaForm(@Valid@ModelAttribute("fotografia")Fotografia fotografia,
			@PathVariable("idF")Long idF,@PathVariable("idA")Long idA,Model model) {
		model.addAttribute("fotografo", this.fotografoService.getPerId(idF));
		model.addAttribute("album", this.albumService.getPerId(idA));
		return "fotografiaForm";

	}
	@RequestMapping(value = "/funzionario/fotografo/{idF}/album/{idA}/confermaInserimentoFotografia", method = {RequestMethod.GET, RequestMethod.POST})
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
			return "albumFunzionario";
			}
		else {
			model.addAttribute("album", a);
			model.addAttribute("fotografo", f);
			return "fotografiaForm";

		}
	}

	@RequestMapping(value = "/funzionario/fotografo/{idF}/album/{idA}/fotografia/{idPh}", method = RequestMethod.GET)
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
			model.addAttribute("fotografie", this.fotografiaService.getPerAlbum(a));
			return "albumFunzionario";
		}

	}

}
