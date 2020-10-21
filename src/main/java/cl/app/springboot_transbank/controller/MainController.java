package cl.app.springboot_transbank.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import cl.app.springboot_transbank.model.Purchase;

/**
 * La clase MainController.
 */
@Controller
public class MainController {

	/** Constante para LOGS*/
	private static final Logger LOG = LogManager.getLogger(MainController.class);

	/**
	 * Obtiene la vista index.html.
	 *
	 * @return la vista que inicia la compra con transbank
	 */
	@GetMapping("/")
	public ModelAndView getIndex() {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("purchase", new Purchase());
		LOG.info("Cargando valores iniciales para transaccion");
		return mav;
	}
}
