package social.service.recommendation.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Value;
import social.service.recommendation.entity.ServiceRequest;
import social.service.recommendation.entity.ServiceResponse;
import social.service.recommendation.entity.User;
import social.service.recommendation.repository.UserRepository;
import social.service.recommendation.types.Schedule;

@Service
public class DBPopulateServiceImpl {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JDBCTemplateService jdbcTemplateService;

	private List<ServiceRequestData> requestNameandDescriptionStrings;
	private List<String> categories;
	private Schedule schedules[];
	private List<User> users;

	/* fields for random generation */
	Random rand = new Random();
	private static final int maxRequestsPerUser = 100;
	private static final int maxRequiredItems = 50;
	private static final int maxRequiredAmount = 1000000;
	private static final int maxResponsesPerRequest = 100;

	@Transactional
	public void populateDBWithRandomData() {
		populateRequiredData();
		for (User user : users) {
			populateDBDataForUser(user);
		}
	}

	@Transactional(value = TxType.REQUIRES_NEW)
	private void populateDBDataForUser(User user) {
		populateInterestedCategoriesForUser(user);
		createServiceRequestsForUsers(user);
	}

	private void populateRequiredData() {
		populateServiceRequestData();
		categories = jdbcTemplateService.getCategories();
		schedules = Schedule.values();
		users = userRepository.findAll();
	}

	private void createServiceRequestsForUsers(User user) {
		int random = rand.nextInt(maxRequestsPerUser);
		Set<ServiceRequest> serviceRequests = new HashSet<>();
		for (int i = 0; i < random; i++) {
			serviceRequests.add(createServiceRequest(user));
		}
		user.setMyServiceRequests(serviceRequests);
	}

	private ServiceRequest createServiceRequest(User user) {
		ServiceRequest req = new ServiceRequest();
		req.setCreator(user);
		req.setRequesterName(user.getUserName());
		req.setRequesterNumber(user.getMobileNumber());
		req.setCreatedDate(LocalDate.now().minusDays(rand.nextInt(30)));
		req.setSchedule(schedules[rand.nextInt(schedules.length)]);
		req.setTotalQuantityRequired(rand.nextInt(maxRequiredItems));
		populateRequestData(req);
		populateRequestResponses(req);
		return req;
	}

	private void populateRequestData(ServiceRequest req) {
		ServiceRequestData requestData = requestNameandDescriptionStrings
				.get(rand.nextInt(requestNameandDescriptionStrings.size()));
		if ("Monetary".equalsIgnoreCase(requestData.getCategory())) {
			req.setTotalQuantityRequired(rand.nextInt(maxRequiredAmount));
		}
		req.setCategory(requestData.getCategory());
		req.setSubCategory(requestData.getSubCategory());
		req.setRequestName(requestData.getRequestName());
		req.setDescription(requestData.getRequestDescription());
	}

	private void populateRequestResponses(ServiceRequest req) {
		int random = rand.nextInt(maxResponsesPerRequest);
		long totalQuantityProvided = 0;
		Set<ServiceResponse> responses = new HashSet<>();
		for (int i = 0; i < random; i++) {
			ServiceResponse serviceResponse = new ServiceResponse();
			serviceResponse.setCategory(req.getSubCategory());
			serviceResponse.setServiceRequest(req);
			int quantityProvided = 1;
			if ("Monetary".equalsIgnoreCase(req.getCategory())) {
				quantityProvided = rand.nextInt(((int) req.getTotalQuantityRequired()) / 100);
			}
			serviceResponse.setQuantityProvided(quantityProvided);
			User user = users.get(rand.nextInt(users.size()));
			while (req.getCreator() == user) {
				user = users.get(rand.nextInt(users.size()));
			}
			serviceResponse.setUser(user);
			serviceResponse.setRating(rand.nextInt(5));
			serviceResponse.setCreatedDate(LocalDate.now().minusDays(rand.nextInt(30)));
			responses.add(serviceResponse);
			totalQuantityProvided+=quantityProvided;
		}
		req.setServiceResponses(responses);
		req.setTotalQuantityAcquired(totalQuantityProvided);
	}

	private void populateInterestedCategoriesForUser(User user) {
		int random = rand.nextInt(categories.size());
		List<String> interestCategories = new ArrayList<>();
		for (int i = 0; i < random; i++) {
			interestCategories.add(categories.get(rand.nextInt(categories.size())));
		}
		user.setInterestedCategories(interestCategories);
	}

	private void populateServiceRequestData() {
		requestNameandDescriptionStrings = new ArrayList<>();
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Abacus", "Teacher Required",
				"Need a teacher to teach to students"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Computer", "Teacher Required",
				"Need a teacher to teach to students"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Drawing", "Teacher Required",
				"Need a teacher to teach to students"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "English", "Teacher Required",
				"Need a teacher to teach to students"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Languages", "Teacher Required",
				"Need a teacher to teach to students"));
		requestNameandDescriptionStrings.add(
				new ServiceRequestData("Teaching", "Maths", "Teacher Required", "Need a teacher to teach to students"));
		requestNameandDescriptionStrings.add(
				new ServiceRequestData("Teaching", "Music", "Teacher Required", "Need a teacher to teach to students"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Physicial Education",
				"Teacher Required", "Need a teacher to teach to students"));
		requestNameandDescriptionStrings.add(
				new ServiceRequestData("Teaching", "Languages", "Tutor Required", "We need a tutor to teach to hindi"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Teaching - Others", "Teacher Required",
				"We are looking for a teacher to teach social science to students"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Teaching - Others", "Teacher Required",
				"Need a teacher to teach all the subjects for 1 to 10th standard students"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Teaching - Others",
				"Tution teacher Required", "Need a tution teacher to teach to students"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Computer", "Computer teacher Required",
				"Need a teacher to teach computer basics for our children"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Teaching - Others",
				"Female Teacher Required", "Need a teacher to teach our girls from 1 to 10th standard"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Teaching - Others",
				"Physical trainer Required", "We are searching for a teacher to teach sports to children"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Teaching - Others", "Teacher Required",
				"Need a teacher to teach kids"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Maths", "Maths teacher Required",
				"Need a maths teacher to teach to students"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Teaching - Others",
				"volunteers Required", "Need a volunteers who can teach to students"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Music", "Music Teacher Required",
				"Need a teacher to teach music for kids"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Teaching - Others",
				"Dance Teacher Required", "We are looking for a dance teacher to teach dance to our children"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Teaching - Others",
				"Chess teacher Required", "Need a teacher to teach chess to students"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Languages", "Telugu teacher Required",
				"Need a teacher to teach telugu to students"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Languages", "English teacher Required",
				"We are searching for a english teacher to teach to english for our students"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Abacus", "Abacus Teacher Required",
				"Need a teacher to teach abacus to students"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Drawing", "Drawing Teacher Required",
				"Need a teacher to teach drawing to students"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Teaching", "Music", " Music Teacher Required",
				"Need a teacher to teach instruments to students"));

		requestNameandDescriptionStrings.add(new ServiceRequestData("Monetary", "Donations", "Donation Required",
				"Need Volunteers to who can donate money to help our children needs"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Monetary", "Monetary - Others", "Money Required",
				"We need money to buy things for the new borns"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Monetary", "Donations", "Monetary help Required",
				"Need urgent monetary help to repair our old age home"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Monetary", "Medical Expenses", "Help me Required",
				"Need money to help one of our children to recover from health issue"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Monetary", "Medical Expenses",
				"Medicinal need Required", "Need money to buy medicines for our orphanage"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Monetary", "Sponsors", "sponser Required",
				"Need sponser to one of our students for his education"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Monetary", "Donations", "Help us ",
				"Help us in constructing new rooms for our old age home"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Monetary", "Donations", "Donation Required",
				"Need money to complete our temple work"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Monetary", "Orphanage", "Money Required",
				"We want to buy fans for our orphanage"));
		requestNameandDescriptionStrings.add(
				new ServiceRequestData("Monetary", "Orphanage", "Help Required", "Need money to help our children"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Monetary", "Medical Expenses", "Please donate ",
				"Please donate us to get medicines for our old age home"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Monetary", "Monetary - Others",
				"General help Required", "Need money to help our organisation"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Monetary", "Donations", "money Required",
				"Need money to help the needy of the floods"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Monetary", "Medical Expenses",
				"Urgent help Required", "Need urgent help for the surgery of one of our old age home member"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Monetary", "Donations", "Money Required",
				"Need money to construsct new bathrooms for our girl children"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Monetary", "Sponsors", "Help us",
				"Help us by providing money to our organisation"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Monetary", "Monetary - Others", "Donate please",
				"Donate us in conducting heath check ups for our old age home"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Monetary", "Medical Expenses",
				"Urgent help Required", "Need money for medical assistance for our orphanage member."));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Monetary", "Donations", "Help our organisation",
				"Need your kind help in repairing our water tank in our organisation"));

		requestNameandDescriptionStrings
				.add(new ServiceRequestData("Events", "PlantingTrees", "Events", "Tree Planting events"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Events", "Marathon", "marathon",
				"We are conducting marathon in the city,so we need participants"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Events", "Volunteers", "Volunteers Required",
				"Need Volunteers for our cycling event"));
		requestNameandDescriptionStrings.add(
				new ServiceRequestData("Events", "Volunteers", "Need Volunteers", "Need Volunteers for our events"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Events", "Volunteers", "Volunteers for events",
				"Need Volunteers for awareness programs"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Events", "Cycling", "cycling ",
				"We are conducting cycling in the city,so we need participants"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Events", "Marathon", "Events Required",
				"Need a teacher to teach to students for a marathon"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Events", "PlantingTrees", "Events Required",
				"Need a teacher to teach to students for a plant tree event"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Events", "Awareness", "Pollution awareness",
				"Need volunteers to participate in the pollution awareness event"));
		requestNameandDescriptionStrings
				.add(new ServiceRequestData("Events", "Awareness", "awareness about mental health Required",
						"Need volunteers to participate in the mental health awareness event"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Events", "PlantingTrees",
				"Planting trees Required", "Need volunteers for Planting trees"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Events", "Awareness",
				"vaccination awareness Required", "Need volunteers for conducting vaccination awareness"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Events", "Awareness", "Women empowerment",
				"Need volunteers for conducting Women empowerment awareness"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Events", "Awareness", "road safety",
				"We are conducting road safety awareness in the city,so we need participants"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Events", "Events - Others", "campaign Required",
				"We are conducting eye check up campaign in the city,so we need participants"));
		requestNameandDescriptionStrings
				.add(new ServiceRequestData("Events", "Awareness", "women health awareness Required",
						"We are conducting women health awareness in the city,so we need participants"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Events", "Awareness", "Malnutrition awareness",
				"We are conducting Malnutrition awareness in the city,so we need participants"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Events", "Volunteers", "Child labor",
				"Need volunteers for conducting campaign aboutchild labor"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Events", "Volunteers", "Girl child education",
				"Need volunteers for conducting campaign about Girl child education"));

		requestNameandDescriptionStrings.add(new ServiceRequestData("In Kind", "Donations", "Donation Required",
				"Need Volunteers to who can donate notebooks for our children"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("In Kind", "Provisionals", "Rice Required",
				"We need rice for the people staying in the flood campaign"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("In Kind", "Provisionals", "vegetables Required",
				"Need vegetables for cooking for the needy in the campaign"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("In Kind", "Medicines", "Help me Required",
				"Need medicines to help one of our children to recover from health issue"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("In Kind", "Medicines", "Medicinal need Required",
				"Need medicines for our orphanage"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("In Kind", "In Kind - Others", "sponser Required",
				"Need sponser to donate stationeries for our students"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("In Kind", "In Kind - Others", "Help us ",
				"Help us in providing bricks and sand for constructing new rooms for our old age home"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("In Kind", "In Kind - Others", "Donation Required",
				"Need cements and rods to complete our temple work"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("In Kind", "In Kind - Others", "Help Required",
				"We want fans for our orphanage"));
		requestNameandDescriptionStrings
				.add(new ServiceRequestData("In Kind", "Clothing", "Help Required", "Need clothes for our children"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("In Kind", "Medicines", "Please donate ",
				"Please donate us to get medicines for our old age home"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("In Kind", "Clothing", "General help Required",
				"Need some bed covers or blankets for our organisation"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("In Kind", "Donations", "Help Required",
				"Need  help for the needy of the floods"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("In Kind", "In Kind - Others", "help Required",
				"Need water tanker for our old age home"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("In Kind", "In Kind - Others", "Help us",
				"Need raw materials to construsct new bathrooms for our girl children"));
		requestNameandDescriptionStrings.add(
				new ServiceRequestData("In Kind", "Food", "Help us", "Help us by providing food to our organisation"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("In Kind", "Donations", "Donate please",
				"Donate us in conducting heath check ups for our old age home"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("In Kind", "Donations", "Urgent help Required",
				"Need medical assistance for our orphanage member."));
		requestNameandDescriptionStrings.add(new ServiceRequestData("In Kind", "In Kind - Others",
				"Help our organisation", "Need your kind help in repairing our water tank in our organisation"));

		requestNameandDescriptionStrings
				.add(new ServiceRequestData("Others", "Others", "others", "Need participants for health campaign"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Others", "Others", "others",
				"Need care taker for the infants in our orphanage"));
		requestNameandDescriptionStrings
				.add(new ServiceRequestData("Others", "Others", "others", "Need care taker for our old age homes"));
		requestNameandDescriptionStrings
				.add(new ServiceRequestData("Others", "Others", "others", "Need help in bulding a orphanage"));
		requestNameandDescriptionStrings
				.add(new ServiceRequestData("Others", "Others", "others", "Need organiser for our trust"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Others", "Others", "others",
				"Need volunteers to take care of our organisation"));
		requestNameandDescriptionStrings.add(
				new ServiceRequestData("Others", "Others", "others", "Need volunteers in starting educational trust"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Others", "Others", "others",
				"Need NCC volunteers for cleaning our old age homes"));
		requestNameandDescriptionStrings
				.add(new ServiceRequestData("Others", "Others", "others", "Need blood donors "));
		requestNameandDescriptionStrings
				.add(new ServiceRequestData("Others", "Others", "others", "Need hair donors for the cancer patients"));
		requestNameandDescriptionStrings
				.add(new ServiceRequestData("Others", "Others", "others", "motivation speakers for our children"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Others", "Others", "others",
				"Need help in conducting cultural events in our organisation"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Others", "Others", "others",
				"Please help in conducting ganesha pooja in our old age home"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Others", "Others", "others",
				"Need volunteers who can arrange vehicles to take our old age people to temples"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Others", "Others", "others",
				"Need to conduct healthy food carnival for nutrition awareness"));
		requestNameandDescriptionStrings
				.add(new ServiceRequestData("Others", "Others", "others", "Help in collecting clothes for the flood"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Others", "Others", "others",
				"Need help to conduct one day educational trip our children"));
		requestNameandDescriptionStrings.add(
				new ServiceRequestData("Others", "Others", "others", "Need job oppurtunity for our ashramam women"));
		requestNameandDescriptionStrings.add(new ServiceRequestData("Others", "Others", "others",
				"Need counselling for our home women who had bad marrital life"));

	}

}

@Value
class ServiceRequestData {

	private String category;
	private String subCategory;
	private String requestName;
	private String requestDescription;
}