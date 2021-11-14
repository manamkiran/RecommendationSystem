package social.service.recommendation.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(indexes = { @Index(name = "IX_serviceresponse_rating", columnList = "rating"),
		@Index(name = "IX_servicerespone_category", columnList = "category") })
public class ServiceResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@EqualsAndHashCode.Exclude
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "serviceRequestId", foreignKey = @ForeignKey(name = "FK_servicereponse_servicerequest"))
	private ServiceRequest serviceRequest;

	@EqualsAndHashCode.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "FK_servicereponse_user"))
	private User user;

	private long quantityProvided;

	private String category;

	@CreationTimestamp
	private LocalDate createdDate;

	private int rating;

	@Lob
	private String comment;

}
