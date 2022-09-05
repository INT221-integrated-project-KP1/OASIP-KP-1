package sit.int204.actionback.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import sit.int204.actionback.entities.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "insert into myuser (name, email, role, password) values (:#{#user.getName()}, :#{#user.getEmail()}, :#{#user.getRole()}, :#{#user.getPassword()})", nativeQuery = true)
    @Modifying
    @Transactional
    public void saveUser(@Param("user") User user);

    @Query(value = "UPDATE myuser SET name = :#{#user.getName()} , email = :#{#user.getEmail()} , role = :#{#user.getRole()} ,updatedOn =CURRENT_TIMESTAMP where id = :#{#user.getId()} ", nativeQuery = true)
    @Modifying
    @Transactional
    public void editUser(@Param("user") User user);

    Optional<User> findByEmail(String email);

}