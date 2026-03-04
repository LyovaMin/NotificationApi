package by.lyofchik.webpushservice.Repository;

import by.lyofchik.webpushservice.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public User findUserByLogin(String login);
    public List<User> findUsersByCompany(int companyId);
}
