package social.service.recommendation.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryAndSubCategories {
	
	public static String ALL = "ALL";
	
	String category;
	
	String subCategory;

}
