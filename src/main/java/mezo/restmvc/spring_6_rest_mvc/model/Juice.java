package mezo.restmvc.spring_6_rest_mvc.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Juice {
    private UUID id;
    private Integer version;
    private String juiceName;
    private JuiceStyle juiceStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private  LocalDateTime updateDate;
}
