package mezo.restmvc.spring_6_rest_mvc.mappers;


public interface MapperMain<T,D> {
    T dtoToObj(D objectDto);

    D objToDto(T object);
}
