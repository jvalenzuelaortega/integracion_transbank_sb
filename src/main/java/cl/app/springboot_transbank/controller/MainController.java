package cl.app.springboot_transbank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import cl.app.springboot_transbank.model.Purchase;

@Controller
public class MainController {

	@GetMapping("/")
	public ModelAndView getIndex() {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("purchase", new Purchase());
		return mav;
	}
}
