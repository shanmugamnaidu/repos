package masterSpringMvc.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TwitterController {

	@Autowired
	private Twitter twitter;
	
	@RequestMapping(value="/")
	public String searchHome() {
		return "searchHome";
	}
	
	@RequestMapping(value="/twitter")
	public String twitter(@RequestParam(defaultValue="masterSpringMvc4") String search, Model model) {
		SearchResults results = twitter.searchOperations().search(search);
		List<String> tweets = results.getTweets()
									 .stream()
									 .map(Tweet::getText)
									 .collect(Collectors.toList());
		model.addAttribute("tweets", tweets);
		return "tweetPage";
	}
	
	@RequestMapping(value="/tweets")
	public  String tweets(@RequestParam(defaultValue="masterSpringMvc4") String search, Model model) {
		SearchResults results = twitter.searchOperations().search(search);
		model.addAttribute("tweets", results.getTweets());
		model.addAttribute("search", search);
		return "tweetsPage";
	}
	
	@RequestMapping(value="/postSearch")
	public String postSearch(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String search = request.getParameter("search");
		if ("struts".equalsIgnoreCase(search == null ? "" : search.trim())) {
			redirectAttributes.addFlashAttribute("error", "Try using spring instead!");
			
			return "redirect:/";
		}
			
		redirectAttributes.addAttribute("search", search);
		
		return "redirect:tweets";
	}
	
	
	/*@RequestMapping(value="/postSearch")
	public String postSearch(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String search = request.getParameter("search");
		redirectAttributes.addAttribute("search", search);
		
		return "redirect:tweets";
	}*/
}
