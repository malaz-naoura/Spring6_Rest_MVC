package mezo.restmvc.spring_6_rest_mvc.mappers;

import mezo.restmvc.spring_6_rest_mvc.entities.Juice;
import mezo.restmvc.spring_6_rest_mvc.model.JuiceDTO;
import org.mapstruct.Mapper;

@Mapper
public interface JuiceMapper  extends MapperMain<Juice, JuiceDTO> {
}
