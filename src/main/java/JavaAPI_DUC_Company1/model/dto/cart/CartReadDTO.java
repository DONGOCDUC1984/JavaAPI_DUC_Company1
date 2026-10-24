package JavaAPI_DUC_Company1.model.dto.cart;

import JavaAPI_DUC_Company1.model.dto.cartItem.CartItemReadDTO;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CartReadDTO {
    //private Integer id;
    //private Integer userId;
    private List<CartItemReadDTO> items;

    public CartReadDTO() {
    }
}

