package sit.int204.actionback.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import sit.int204.actionback.entities.EventCategoryOwner;
import sit.int204.actionback.entities.User;

public interface EventCategoryOwnerRepository extends JpaRepository<EventCategoryOwner, Integer> {
    @Query(value = "DELETE FROM EventCategoryOwner WHERE user_id = :#{#user_id}", nativeQuery = true)
    @Modifying
    @Transactional
    public void deleteOwner(Integer user_id);
}
