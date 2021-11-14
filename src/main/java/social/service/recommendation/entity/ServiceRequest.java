package social.service.recommendation.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import social.service.recommendation.types.Schedule;

@Entity
@Data
@Table(indexes = { @Index(name = "IX_servicerequest_category", columnList = "category"),
		@Index(name = "IX_servicerequest_subCategory", columnList = "subCategory"),
		@Index(name = "IX_servicerequest_createdDate", columnList = "createdDate") })
public class ServiceRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String category;
	
	private String subCategory;

	private String requestName;

	@Enumerated(EnumType.STRING)
	private Schedule schedule;

	@Lob
	private String description;

	private String requesterName;

	private String requesterNumber;

	private long totalQuantityAcquired;

	private long totalQuantityRequired;

	@EqualsAndHashCode.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creatorName", foreignKey = @ForeignKey(name = "FK_servicerequest_user"))
	private User creator;

	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "serviceRequest", fetch = FetchType.LAZY)
	@Cascade(CascadeType.PERSIST)
	private Set<ServiceResponse> serviceResponses;

	@CreationTimestamp
	private LocalDate createdDate;

	@UpdateTimestamp
	private LocalDate updatedDate;

	public ServiceRequest() {

	}

	public ServiceRequest(long id) {
		this.id = id;
	}

}
