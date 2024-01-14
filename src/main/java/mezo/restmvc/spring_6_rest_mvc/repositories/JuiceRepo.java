package mezo.restmvc.spring_6_rest_mvc.repositories;

import mezo.restmvc.spring_6_rest_mvc.entities.Juice;
import mezo.restmvc.spring_6_rest_mvc.model.JuiceStyle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JuiceRepo extends JpaRepository<Juice, UUID> {
    Optional<Juice> findJuiceByJuiceName(String JuiceName);

    List<Juice> findAllByJuiceName(String JuiceName);

    List<Juice> findAllByJuiceStyle(JuiceStyle juiceStyle);

    List<Juice> findAllByJuiceNameAndJuiceStyle(String JuiceName,JuiceStyle juiceStyle);
}
