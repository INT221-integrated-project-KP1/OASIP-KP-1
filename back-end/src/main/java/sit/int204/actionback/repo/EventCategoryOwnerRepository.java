package sit.int204.actionback.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.entities.EventCategoryOwner;
import sit.int204.actionback.entities.User;

import java.util.List;

public interface EventCategoryOwnerRepository extends JpaRepository<EventCategoryOwner, Integer> {
    @Query(value = "DELETE FROM event_category_owner WHERE user_id = :#{#user_id}", nativeQuery = true)
    @Modifying
    @Transactional
    public void deleteOwner(Integer user_id);

    @Query(value = "select u.id from myuser u , event_category_owner eco where eco.user_id = u.id and eco.event_category_id = :id", nativeQuery = true)
    List<Integer> getOwnersId(@Param("id") Integer id);


    @Query(value = "insert into event_category_owner (event_category_id , user_id) values (:eventCate_id,:user_id)", nativeQuery = true)
    @Transactional
    @Modifying
    void addEventCategoryOwner(@Param("eventCate_id") Integer eventCate_id, @Param("user_id") Integer user_id);

    List<EventCategoryOwner> findEventCategoryOwnerByEventCategory(EventCategory ec);

    List<EventCategoryOwner> findAll();

    @Query(value = "select eco.* from event_category_owner eco", nativeQuery = true)
    List<EventCategoryOwner> getAll();

}
