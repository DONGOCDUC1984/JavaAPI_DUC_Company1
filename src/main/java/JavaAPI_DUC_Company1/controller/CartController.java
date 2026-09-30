package JavaAPI_DUC_Company1.controller;

import JavaAPI_DUC_Company1.model.dto.cart.CartReadDTO;
import JavaAPI_DUC_Company1.service.business.interfaces.ICartService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cart")
@CrossOrigin
public class CartController {
    private final ICartService _service;
    public CartController(ICartService service)
    {
        _service=service;
    }

    @GetMapping
    public CartReadDTO getCartReadDTOByUser() {
        return _service.getCartReadDTOByUser();
    }

    @GetMapping("addCartItem/{productId}")
    public CartReadDTO addCartItem(@PathVariable Integer productId)
    {
        _service.addCartItem(productId);
        return _service.getCartReadDTOByUser();
    }

    @GetMapping("decreaseCartItem/{productId}")
    public void decreaseCartItem(@PathVariable Integer productId)
    {
        _service.decreaseCartItem(productId);
    }

    @DeleteMapping("removeCartItem/{productId}")
    public void removeCartItem(@PathVariable Integer productId) {
        _service.removeCartItem(productId);
    }


}


