package by.lyofchik.registrationservice.Repository;

import by.lyofchik.registrationservice.Model.Entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
