package by.lyofchik.pushserver.Repository;

import by.lyofchik.pushserver.Model.Entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
