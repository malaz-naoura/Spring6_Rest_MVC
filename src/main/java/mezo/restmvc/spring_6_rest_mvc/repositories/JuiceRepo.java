package mezo.restmvc.spring_6_rest_mvc.repositories;

import mezo.restmvc.spring_6_rest_mvc.entities.Juice;
import mezo.restmvc.spring_6_rest_mvc.model.JuiceStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JuiceRepo extends JpaRepository<Juice, UUID> {

    Page<Juice> findAllByJuiceName(String JuiceName, Pageable pageable);

    Page<Juice> findAllByJuiceStyle(JuiceStyle juiceStyle, Pageable pageable);

    Page<Juice> findAllByJuiceNameAndJuiceStyle(String JuiceName, JuiceStyle juiceStyle, Pageable pageable);
}
