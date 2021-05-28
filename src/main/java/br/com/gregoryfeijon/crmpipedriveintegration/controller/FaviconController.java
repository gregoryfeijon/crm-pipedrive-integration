package br.com.gregoryfeijon.crmpipedriveintegration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 27/05/2021 às 21:14:11
 * 
 * @author gregory.feijon
 */

@Controller
public class FaviconController {

	@GetMapping("favicon.ico")
	@ResponseBody
	void returnNoFavicon() {
	}
}
