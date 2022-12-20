package sit.int204.actionback.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@Setter
@Table(name = "event_category_owner")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EventCategoryOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_category_owner_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER , optional = false)
    @JoinColumn(name = "event_category_id", nullable = false)
    private EventCategory eventCategory;

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}