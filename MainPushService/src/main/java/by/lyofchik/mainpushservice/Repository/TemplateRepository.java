package by.lyofchik.mainpushservice.Repository;

import by.lyofchik.mainpushservice.Model.Entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateRepository extends JpaRepository<Template, Integer> {
    List<Template> findAllByCompany(int companyId);
    Template findByTemplateId(int templateId);
}
