package JavaAPI_DUC_Company1.model.dto.cartItem;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class CartItemReadDTO {
//private Integer id;
//    private Integer cartId;
    private Integer productId;
    private String productName;
    private BigDecimal productPrice;
    private String productImageUrl;
    private Integer quantity;

    public CartItemReadDTO() {
    }

    public CartItemReadDTO( Integer productId,
                           String productName, BigDecimal productPrice,
                           String productImageUrl, Integer quantity) {

        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImageUrl = productImageUrl;
        this.quantity = quantity;
    }
}
