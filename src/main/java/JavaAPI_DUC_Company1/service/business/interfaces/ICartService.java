package JavaAPI_DUC_Company1.service.business.interfaces;

import JavaAPI_DUC_Company1.model.Cart;
import JavaAPI_DUC_Company1.model.dto.cart.CartReadDTO;

public interface ICartService {
    CartReadDTO getCartReadDTOByUser();
    void addCartItem(Integer productId);
    void decreaseCartItem(Integer productId);
    void removeCartItem(Integer productId);
    Cart getCartByUser();
}
