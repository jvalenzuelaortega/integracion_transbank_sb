package cl.app.springboot_transbank.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import cl.app.springboot_transbank.model.Purchase;

@Controller
public class MainController {

	private static final Logger LOG = LogManager.getLogger(MainController.class);

	@GetMapping("/")
	public ModelAndView getIndex() {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("purchase", new Purchase());
		LOG.debug("Debugging log in our greeting method");
		LOG.info("Info log in our greeting method");
		LOG.warn("Warning log in our greeting method");
		LOG.error("Error in our greeting method");
		LOG.fatal("Damn! Fatal error. Please fix me.");
		return mav;
	}
}
