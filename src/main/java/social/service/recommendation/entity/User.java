package social.service.recommendation.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
public class User {

	@Id
	private String userName;
	
	private String firstName;
	
	private String lastName;
	
	private String emailId;
	
	private String mobileNumber;
	
	private String password;
	
	@Transient
	private String confirmPassword;
	
	@ElementCollection
	private List<String> interestedCategories;
	
	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "user" , fetch = FetchType.LAZY)
	private Set<ServiceResponse> myServiceResponses;
	
	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "creator" , fetch = FetchType.LAZY)
	@Cascade(CascadeType.PERSIST)
	private Set<ServiceRequest> myServiceRequests;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	
	public User(String userName) {
		this.userName = userName;
	}
	
	public User() {
	}
}
