package it.silphSPA.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.silphSPA.app.model.Album;
import it.silphSPA.app.model.Fotografo;
import it.silphSPA.app.services.AlbumService;
import it.silphSPA.app.services.FotografiaService;
import it.silphSPA.app.services.FotografoService;

@Controller
public class GuestController {
	@Autowired
	private FotografoService fotografoService;
	@Autowired
	private AlbumService albumService;
	@Autowired
	private FotografiaService fotografiaService;
	
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
		model.addAttribute("albums", this.albumService.getAlbumPerFotografo(f));
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
	
}
