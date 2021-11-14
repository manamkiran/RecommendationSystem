package social.service.recommendation.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import social.service.recommendation.service.DBPopulateServiceImpl;
import social.service.recommendation.service.SparkService;

@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	private DBPopulateServiceImpl dbPopulateService;
	
	@Autowired
	private SparkService sparkService;
	
	@GetMapping("generateDBData")
	private String generateDBData(HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("userName");
		if (StringUtils.isEmpty(userName)) {
			return "redirect:/";
		}
		if (userName.equalsIgnoreCase("vihaan"))
		{
			dbPopulateService.populateDBWithRandomData();
		}
		return "user/home";
	}
	
	@GetMapping("sparkJob")
	private String runSparkJob(HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("userName");
		if (StringUtils.isEmpty(userName)) {
			return "redirect:/";
		}
		if (userName.equalsIgnoreCase("vihaan"))
		{
			sparkService.runSparkJob();
		}
		return "user/home";
	}
	
}
