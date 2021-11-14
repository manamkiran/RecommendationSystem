package social.service.recommendation.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import social.service.recommendation.entity.ServiceResponse;
import social.service.recommendation.service.ServiceResponseService;

@Controller
@RequestMapping("serviceResponse")
public class ServiceResponseController {

	@Autowired
	private ServiceResponseService serviceResponseService;

	@PostMapping("create")
	private String createServiceResponse(Model model, @ModelAttribute("serviceResponse") ServiceResponse serviceResponse,
			BindingResult bindingResult, HttpServletRequest request) {
		serviceResponseService.createServiceResponse(serviceResponse);
		return "redirect:/ngo/home";
	}

	@GetMapping("{serviceResponseId}/rating")
	private String getRatingPage(Model model,@PathVariable(name = "serviceResponseId") String serviceResponseId,
			HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("userName");
		if (StringUtils.isEmpty(userName)) {
			return "redirect:/";
		}
		ServiceResponse serviceResponse=serviceResponseService.findById(Long.parseLong(serviceResponseId));
		if(!userName.equals(serviceResponse.getUser().getUserName())){
			return "redirect:/";
		}
		model.addAttribute("serviceResponse", serviceResponse);
		return "serviceResponse/addRatingForm";
	}
	
	@PostMapping("addRating")
	private String addRating(Model model, @ModelAttribute("serviceResponse") ServiceResponse serviceResponse,
			BindingResult bindingResult, HttpServletRequest request) {
		serviceResponseService.updateRatingAndCommentOfServiceResponse(serviceResponse.getId(), serviceResponse.getRating(), serviceResponse.getComment());
		model.addAttribute("message","Thank you for your Rating.");
		return "pages/confirmation";
	}
}
