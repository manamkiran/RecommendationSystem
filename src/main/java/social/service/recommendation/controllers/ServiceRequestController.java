package social.service.recommendation.controllers;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.ResponseBody;

import social.service.recommendation.entity.ServiceRequest;
import social.service.recommendation.entity.ServiceResponse;
import social.service.recommendation.entity.User;
import social.service.recommendation.service.JDBCTemplateService;
import social.service.recommendation.service.ServiceRequestService;
import social.service.recommendation.service.ServiceResponseService;
import social.service.recommendation.service.UserService;
import social.service.recommendation.types.CategoryAndSubCategories;
import social.service.recommendation.types.Schedule;

@Controller
@RequestMapping("service")
public class ServiceRequestController {

	@Autowired
	private ServiceRequestService serviceRequestService;

	@Autowired
	private ServiceResponseService serviceResponseService;

	@Autowired
	private UserService userService;

	@Autowired
	private JDBCTemplateService jdbcTemplateService;

	@GetMapping("add")
	private String addForm(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("userName");
		model.addAttribute("serviceRequest", getServiceRequestWithUserDetails(userName));
		List<String> categories = jdbcTemplateService.getCategories();
		model.addAttribute("categories", categories);
		model.addAttribute("subCategories", jdbcTemplateService.getSubCategoryList(categories.get(0)));
		model.addAttribute("schedules", new ArrayList<Schedule>(Arrays.asList(Schedule.values())));
		return "serviceRequest/addServiceRequestForm";
	}

	private ServiceRequest getServiceRequestWithUserDetails(String userName) {
		ServiceRequest serviceRequest = new ServiceRequest();
		if (!StringUtils.isEmpty(userName)) {
			User user = userService.findUserByUserName(userName);
			if (user != null) {
				serviceRequest.setRequesterName(user.getUserName());
				serviceRequest.setRequesterNumber(user.getMobileNumber());
			}
		}
		return serviceRequest;
	}

	@PostMapping("create")
	private String createServiceRequest(Model model, @ModelAttribute("serviceRequest") ServiceRequest serviceRequest,
			BindingResult bindingResult, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("userName");
		if (StringUtils.isEmpty(userName)) {
			return "redirect:/";
		}
		serviceRequest.setCreator(new User(userName));
		serviceRequestService.createServiceRequest(serviceRequest);
		model.addAttribute("message", "Your Request is created successfully");
		return "pages/confirmation";
	}

	@GetMapping("{serviceRequestId}/serviceResponses")
	private String getServiceResponsesForGivenServiceId(Model model, HttpServletRequest request,
			@PathVariable(name = "serviceRequestId") String serviceRequestId) {
		String userName = (String) request.getSession().getAttribute("userName");
		if (StringUtils.isEmpty(userName)) {
			return "redirect:/";
		}
		List<ServiceResponse> serviceResponses = serviceResponseService
				.findServiceResponsesByServiceRequest(new ServiceRequest(Long.parseLong(serviceRequestId)));
		model.addAttribute("serviceResponses", serviceResponses);
		if (serviceResponses.size() > 0) {
			model.addAttribute("requestName", serviceResponses.get(0).getServiceRequest().getRequestName());
		}
		return "serviceRequest/serviceRequestResponses";
	}

	@PostMapping("{serviceRequestId}/donate")
	private String createResponseRequest(Model model, @PathVariable(name = "serviceRequestId") String serviceRequestId,
			HttpServletRequest request, @RequestParam Map<String, String> requestParam) {
		String userName = (String) request.getSession().getAttribute("userName");
		if (StringUtils.isEmpty(userName)) {
			return "redirect:/";
		}
		ServiceRequest serviceRequest = serviceRequestService.findById(Long.parseLong(serviceRequestId));
		Long quantityProvided = Long.parseLong(requestParam.get("quantityProvided"));
		ServiceResponse serviceResponse = new ServiceResponse();
		serviceResponse.setCategory(serviceRequest.getSubCategory());
		serviceResponse.setServiceRequest(serviceRequest);
		serviceResponse.setQuantityProvided(quantityProvided);
		serviceResponse.setUser(new User(userName));
		serviceResponseService.createServiceResponse(serviceResponse);
		serviceRequestService.updateQuantityAcquiredServiceRequest(serviceRequest.getId(),
				serviceRequest.getTotalQuantityAcquired() + quantityProvided);
		model.addAttribute("message", "Thank you for your Response.");
		return "pages/confirmation";
	}

	@GetMapping("/subCategory/{category}")
	private @ResponseBody List<String> getSubCategories(@PathVariable(name = "category") String category) {
		return jdbcTemplateService.getSubCategoryList(category);

	}

	@GetMapping("/list")
	private String getServiceRequestList(HttpServletRequest request, Model model,
			@ModelAttribute("filter") CategoryAndSubCategories filter) {
		String userName = (String) request.getSession().getAttribute("userName");
		if (StringUtils.isEmpty(userName)) {
			return "redirect:/";
		}
		if (filter == null) {
			filter = new CategoryAndSubCategories();
		}
		List<String> categories = new ArrayList<>();
		categories.add(CategoryAndSubCategories.ALL);
		categories.addAll(jdbcTemplateService.getCategories());
		model.addAttribute("categories", categories);
		String category = filter.getCategory() != null ? filter.getCategory() : categories.get(0);
		List<String> subCategories = new ArrayList<>();
		subCategories.add(CategoryAndSubCategories.ALL);
		if (!CategoryAndSubCategories.ALL.equals(category))
		{
			subCategories.addAll(jdbcTemplateService.getSubCategoryList(category));
		}
		model.addAttribute("subCategories", subCategories);
		List<ServiceRequest> serviceRequests = serviceRequestService.findByFilter(filter);
		model.addAttribute("serviceRequests", serviceRequests);
		model.addAttribute("filter", filter);
		return "serviceRequest/list";

	}

}
