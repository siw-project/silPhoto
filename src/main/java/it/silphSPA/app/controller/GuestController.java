package it.silphSPA.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	private Richiesta richiestaCorrente;
	
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
	
	@RequestMapping("/areaFotografi")
	public String visualizzaAreaFotografi(Model model) {
		model.addAttribute("fotografi",this.fotografoService.getTutti());
		return "areaFotografi";
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
	public String visualizzaFotografia(@PathVariable("idF")Long idF, @PathVariable("idA")Long idA,
			@PathVariable("idPh")Long idPh, Model model) {
		Fotografo f = this.fotografoService.getPerId(idF);
		Album a = this.albumService.getPerId(idA);
		model.addAttribute("fotografo", f);
		model.addAttribute("album", a);
		model.addAttribute("fotografia", this.fotografiaService.getPerId(idPh));
		return "fotografiaGuest";
		
	}
	@RequestMapping(value = "/fotografo/{idF}/album/{idA}/fotografia/{idPh}/putInRichiesta", method = {RequestMethod.POST})
	public String aggiungiFotografiaInRichiesta(@PathVariable("idF")Long idF,@PathVariable("idA")Long idA,
			@PathVariable("idPh")Long idPh,Model model) {
		Fotografo f = this.fotografoService.getPerId(idF);
		Album a = this.albumService.getPerId(idA);
		Fotografia ph = this.fotografiaService.getPerId(idPh);
		if(richiestaCorrente==null) {
			this.richiestaCorrente = new Richiesta();
			}
		this.richiestaService.addFotografia(this.richiestaCorrente, ph);
		model.addAttribute("fotografo", f);
		model.addAttribute("album", a);
		model.addAttribute("fotografia", ph);
		model.addAttribute("richiesta", this.richiestaCorrente);
		return "confermaInserimentoFotografiaRichiesta";
	}
	
	@RequestMapping("/confermaRichiesta")
	public String confermaRichiesta(@PathVariable("idR")Long idR, Model model) {
		this.richiestaService.inserisciRichiesta(this.richiestaCorrente);
		model.addAttribute("richiesta", this.richiestaCorrente);
		model.addAttribute("listaFotografie", this.richiestaService.getFotografie(this.richiestaCorrente));
		return "confermaRichiesta";
	}
	@RequestMapping("/annullaRichiesta")
	public String annullaRichiesta(@PathVariable("idR")Long idR, Model model) {
		this.richiestaCorrente=null;
		return "homeGuest";
	}
		
	
	
}
