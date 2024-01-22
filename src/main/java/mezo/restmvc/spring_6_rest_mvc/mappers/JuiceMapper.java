package mezo.restmvc.spring_6_rest_mvc.mappers;

import mezo.restmvc.spring_6_rest_mvc.entities.Juice;
import mezo.restmvc.spring_6_rest_mvc.model.JuiceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface JuiceMapper extends MapperMain<Juice, JuiceDTO> {
//    @Override
//    @Mapping(target = "name", source = "objectDto.juiceName")
//    Juice dtoToObj(JuiceDTO objectDto);
//
//    @Override
//    @Mapping(target = "juiceName", source = "object.name")
//    JuiceDTO objToDto(Juice object);
}
