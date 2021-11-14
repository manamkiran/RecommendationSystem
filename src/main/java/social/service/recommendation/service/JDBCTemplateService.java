package social.service.recommendation.service;

import java.util.List;

public interface JDBCTemplateService {
	
	List<String> getCategories();
	
	List<String> getSubCategoryList(String category);

}