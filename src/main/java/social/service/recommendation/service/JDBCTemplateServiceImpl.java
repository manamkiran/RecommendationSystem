package social.service.recommendation.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import social.service.recommendation.types.CategoryAndSubCategories;

@Service
public class JDBCTemplateServiceImpl implements JDBCTemplateService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private List<String> categories;

	private Map<String, List<String>> categoryMap;

	@Transactional
	public List<String> getCategories() {
		if (categories == null || categories.isEmpty()) {
			if (categoryMap == null || categoryMap.isEmpty()) {
				populateMap();
			}
			categories = new ArrayList<>(categoryMap.keySet());
			Collections.sort(categories);
		}
		return categories;

	}

	private void populateMap() {
		if (categoryMap == null || categoryMap.isEmpty()) {
			List<CategoryAndSubCategories> categoriesList = jdbcTemplate
					.query("SELECT CATEGORY,SUBCATEGORY FROM CATEGORY", new RowMapper<CategoryAndSubCategories>() {

						@Override
						public CategoryAndSubCategories mapRow(ResultSet rs, int rowNum) throws SQLException {
							return new CategoryAndSubCategories(rs.getString("CATEGORY"), rs.getString("SUBCATEGORY"));
						}

					});
			Map<String, List<String>> tempcategoryMap = new HashMap<>();
			for (CategoryAndSubCategories categoryAndSubCategories : categoriesList) {
				List<String> subCategoriesList = tempcategoryMap.getOrDefault(categoryAndSubCategories.getCategory(),
						new ArrayList<>());
				subCategoriesList.add(categoryAndSubCategories.getSubCategory());
				tempcategoryMap.put(categoryAndSubCategories.getCategory(), subCategoriesList);
			}
			categoryMap = tempcategoryMap;
		}
	}

	public Map<String, List<String>> getCategoryMap() {
		if (categoryMap == null || categoryMap.isEmpty()) {
			populateMap();
		}
		return categoryMap;
	}
	
	@Override
	public  List<String> getSubCategoryList(String category) {
		if (categoryMap == null || categoryMap.isEmpty()) {
			populateMap();
		}
		return categoryMap.get(category);
	}
}
