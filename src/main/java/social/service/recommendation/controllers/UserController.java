package social.service.recommendation.controllers;

import java.util.List;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import social.service.recommendation.entity.ServiceRequest;
import social.service.recommendation.entity.ServiceResponse;
import social.service.recommendation.entity.User;
import social.service.recommendation.service.JDBCTemplateService;
import social.service.recommendation.service.RecommendationService;
import social.service.recommendation.service.ServiceRequestService;
import social.service.recommendation.service.ServiceResponseService;
import social.service.recommendation.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ServiceResponseService serviceResponseService;

	@Autowired
	private ServiceRequestService serviceRequestService;

	@Autowired
	private RecommendationService recommendationService;

	@Autowired
	private JDBCTemplateService jdbcTemplateService;

	@GetMapping("signup")
	private String signup(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("categories", jdbcTemplateService.getCategories());
		return "user/signUp";
	}

	@GetMapping("{userName}/viewdetails")
	private String viewUserDetails(Model model, @PathVariable(name = "userName") String userName) {
		model.addAttribute("user", userService.findUserByUserName(userName));
		return "user/details";
	}

	@PostMapping("create")
	private String createUser(Model model, @ModelAttribute("user") User user, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "user/signUp";
		}
		User tempUser = userService.findUserByUserName(user.getUserName());
		if (tempUser != null) {
			model.addAttribute("user", user);
			model.addAttribute("categories", jdbcTemplateService.getCategories());
			model.addAttribute("message", "UserName Already exists please use a differentUserName");
			return "user/signUp";
		}
		userService.createUser(user);
		return "redirect:/";
	}

	@PostMapping("login")
	private RedirectView login(Model model, @RequestParam Map<String, String> requestParam, HttpServletRequest request,
			RedirectAttributes redir) {
		User loggedInUser = userService.findUserByUserNameAndPassword(requestParam.get("username"),
				requestParam.get("password"));
		if (loggedInUser != null) {
			request.getSession().setAttribute("userName", loggedInUser.getUserName());
			return new RedirectView("/user/home", true);
		}
		RedirectView view = new RedirectView("/", true);
		redir.addFlashAttribute("message", "UserName or Password is incorrect");
		return view;
	}

	@GetMapping("home")
	private String homePage(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("userName");
		if (StringUtils.isEmpty(userName)) {
			return "redirect:/";
		}
		List<ServiceRequest> serviceRequests = recommendationService.getRecommendedServiceRequests(userName);
		model.addAttribute("serviceRequests", serviceRequests);
		return "user/home";
	}

	@GetMapping("responseHistory")
	private String responseHistoryPage(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("userName");
		if (StringUtils.isEmpty(userName)) {
			return "redirect:/";
		}
		List<ServiceResponse> serviceResponses = serviceResponseService
				.findServiceResponsesByUserNameOrderByCreatedDateDesc(new User(userName));
		model.addAttribute("serviceResponses", serviceResponses);
		return "user/responseHistory";
	}

	@GetMapping("requestHistory")
	private String requestHistoryPage(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("userName");
		if (StringUtils.isEmpty(userName)) {
			return "redirect:/";
		}
		List<ServiceRequest> serviceRequests = serviceRequestService
				.findServiceRequestsByCreatorNameOrderByCreatedDateDesc(new User(userName));
		model.addAttribute("serviceRequests", serviceRequests);
		return "user/requestHistory";
	}

	@GetMapping("logout")
	private String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/";

	}
}
