package by.lyofchik.mainpushservice.Repository;

import by.lyofchik.mainpushservice.Model.Entity.User;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findUserByLogin(String login);
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Stream<User> findUsersByCompany(int companyId);
}
