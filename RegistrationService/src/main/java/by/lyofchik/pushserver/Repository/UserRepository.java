package by.lyofchik.pushserver.Repository;

import by.lyofchik.pushserver.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public User findUserByLogin(String login);
}
