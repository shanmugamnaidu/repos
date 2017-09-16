package masterSpringMvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

	@RequestMapping("/hello")
	public String hello(@RequestParam(value = "name", required = false) String name, Model model) {
		model.addAttribute("message", "Hello, " + (name == null || "".equals(name.trim()) ? "World!" : name));
		
		return "resultPage";
	}
}
