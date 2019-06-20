package it.silphSPA.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import it.silphSPA.app.model.Richiesta;
import it.silphSPA.app.services.AlbumService;
import it.silphSPA.app.services.FotografiaService;
import it.silphSPA.app.services.FotografoService;
import it.silphSPA.app.services.RichiestaService;
import it.silphSPA.app.services.RichiestaValidator;

@Controller
public class GuestController {
	@Autowired
	private FotografoService fotografoService;
	@Autowired
	private AlbumService albumService;
	@Autowired
	private FotografiaService fotografiaService;
	@Autowired
	private RichiestaService richiestaService;

	@Autowired
	private RichiestaValidator richiestaValidator;

	@RequestMapping(value = { "/admin" }, method = RequestMethod.GET)
	public String admin(Model model) {
		UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String role = details.getAuthorities().iterator().next().getAuthority();    // get first authority
		model.addAttribute("username", details.getUsername());
		model.addAttribute("role", role);

		return "admin";
	}

	@RequestMapping("/")
	public String homeGuest() {
		return "homeGuest";
	}
	@RequestMapping("/contatti")
	public String contatti() {
		return "contatti";
	}

	@RequestMapping("/areaFotografi")
	public String visualizzaAreaFotografi(Model model) {
		model.addAttribute("fotografi",this.fotografoService.getTutti());
		return "areaFotografi";
	}
	@RequestMapping("/iniziaRichiesta")
	public String iniziaRichiesta(Model model) {
		Richiesta r = new Richiesta();
		this.richiestaService.inserisciRichiesta(r);
		model.addAttribute("richiesta", r);
		return "iniziaRichiesta";
	}

	@RequestMapping("/gallery/{idR}")
	public String visualizzaGallery(@PathVariable("idR")Long idR, Model model) {
		model.addAttribute("richiesta", this.richiestaService.getPerId(idR));
		model.addAttribute("listaFotografie", this.fotografiaService.getTutti());
		return "gallery";

	}
	@RequestMapping("/fotografo/{id}")
	public String visuallizzaAlbum(@PathVariable("id")Long id, Model model) {
		Fotografo f = this.fotografoService.getPerId(id);
		model.addAttribute("fotografo", f);
		model.addAttribute("listaAlbum", this.albumService.getAlbumPerFotografo(f));
		return "fotografoGuest";
	}
	@RequestMapping("/fotografo/{idF}/album/{idA}")
	public String visualizzaFotografie(@PathVariable("idF")Long idF,
			@PathVariable("idA")Long idA,Model model){
		Fotografo f = this.fotografoService.getPerId(idF);
		Album a = this.albumService.getPerId(idA);
		model.addAttribute("fotografo", f);
		model.addAttribute("album", a);
		model.addAttribute("fotografie", this.fotografiaService.getPerAlbum(a));
		return "albumGuest";
	}
	@RequestMapping("/fotografo/{idF}/album/{idA}/fotografia/{idPh}")
	public String visualizzaFotografia(@Valid@ModelAttribute("fotografia")Fotografia fotografia,
			@PathVariable("idF")Long idF, @PathVariable("idA")Long idA,
			@PathVariable("idPh")Long idPh, Model model) {
		Fotografo f = this.fotografoService.getPerId(idF);
		Album a = this.albumService.getPerId(idA);
		model.addAttribute("fotografo", f);
		model.addAttribute("album", a);
		model.addAttribute("fotografia", this.fotografiaService.getPerId(idPh));
		return "fotografiaGuest";

	}
	@RequestMapping(value = "/gallery/{idR}/putInRichiesta/{idPh}", method = RequestMethod.GET)
	public String aggiungiFotografiaInRichiesta(@PathVariable("idR")Long idR,
			@PathVariable("idPh")Long idPh,
			Model model) {
		Richiesta r = this.richiestaService.getPerId(idR);
		Fotografia ph = this.fotografiaService.getPerId(idPh);
		this.richiestaService.addFotografia(r, ph);
		this.fotografiaService.addRichiesta(r, ph);
		model.addAttribute("listaFotografie", this.fotografiaService.getTutti());
		model.addAttribute("richiesta", r);
		return "gallery";
	}
	@RequestMapping("/riepilogo/richiesta/{idR}")
	public String visualizzaRiepilogoRichiesta(@PathVariable("idR")Long idR, Model model) {
		Richiesta r = this.richiestaService.getPerId(idR);
		model.addAttribute("listaFotografie", this.fotografiaService.getPerRichiesta(r));
		model.addAttribute("richiesta", r);
		return "confermaRichiesta";
	}
	@RequestMapping(value = "/conferma/richiesta/{idR}", method = RequestMethod.POST)
	public String confermaRichiesta( @PathVariable("idR")Long idR, @Valid@ModelAttribute("richiesta")Richiesta richiesta, String nomeDestinatario, String cognomeDestinatario,
			BindingResult bindingResult, Model model) {

		Richiesta r = this.richiestaService.getPerId(idR);
		this.richiestaValidator.validate(richiesta, bindingResult);
		if(!bindingResult.hasErrors()) {
			r.setNomeDestinatario(richiesta.getNomeDestinatario());
			r.setCognomeDestinatario(richiesta.getCognomeDestinatario());
			return "richiestaAccettata";
		}else {
			model.addAttribute("listaFotografie", this.fotografiaService.getPerRichiesta(r));
			model.addAttribute("richiesta", r);
			return "confermaRichiesta";
		}

	}
	@RequestMapping("/richiestaAccettata")
	public String richiestaAccettata( Model model) {

		return "/homeGuest";
	}
}