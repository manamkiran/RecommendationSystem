package social.service.recommendation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import social.service.recommendation.entity.UserAnalytics;

public interface UserAnalyticsRepository extends JpaRepository<UserAnalytics, String> {

}
