package mezo.restmvc.spring_6_rest_mvc.mappers;

import mezo.restmvc.spring_6_rest_mvc.entities.Customer;
import mezo.restmvc.spring_6_rest_mvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper extends MapperMain<Customer,CustomerDTO> {

}
