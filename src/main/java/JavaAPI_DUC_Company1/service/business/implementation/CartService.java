package JavaAPI_DUC_Company1.service.business.implementation;

import JavaAPI_DUC_Company1.model.Cart;
import JavaAPI_DUC_Company1.model.CartItem;
import JavaAPI_DUC_Company1.model.auth.User;
import JavaAPI_DUC_Company1.model.dto.cart.CartReadDTO;
import JavaAPI_DUC_Company1.model.dto.cartItem.CartItemReadDTO;
import JavaAPI_DUC_Company1.repository.CartItemRepository;
import JavaAPI_DUC_Company1.repository.CartRepository;
import JavaAPI_DUC_Company1.repository.ProductRepository;
import JavaAPI_DUC_Company1.security.interfaces.IAuthService;
import JavaAPI_DUC_Company1.service.business.interfaces.ICartService;
import JavaAPI_DUC_Company1.service.business.interfaces.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CartService implements ICartService {
    private final CartRepository _repo;
    private final CartItemRepository _cartItemRepo;
    private final IProductService _productService;
    private final IAuthService _authService;
    public CartService(CartRepository repo, CartItemRepository cartItemRepo, ProductRepository productRepo,
                       IProductService productService, IAuthService authService) {
        _repo = repo;
        _cartItemRepo = cartItemRepo;
        _productService = productService;
        _authService = authService;
    }

    @Override
//    @Transactional(readOnly = true)
    public CartReadDTO getCartReadDTOByUser() {
        Cart cart=getCartByUser();
        List<CartItem> cartItems = _cartItemRepo.findByCartId(cart.getId())
                .orElseThrow(() -> new RuntimeException("CartItems not found."));
        //If ArrayList is replaced by List in the next line, there will be an error.
        ArrayList<CartItemReadDTO> cartItemReadDTOList=new ArrayList<CartItemReadDTO>() ;
        for (int i = 0; i < cartItems.size(); i++) {
            CartItemReadDTO dto=new CartItemReadDTO();
            dto.setProductId(cartItems.get(i).getProduct().getId());
            dto.setProductName(cartItems.get(i).getProduct().getName());
            dto.setProductPrice(cartItems.get(i).getProduct().getPrice());
            dto.setProductImageUrl(cartItems.get(i).getProduct().getImageUrl());
            dto.setQuantity(cartItems.get(i).getQuantity());
            cartItemReadDTOList.add(dto);
        }

        CartReadDTO cartReadDTO=new CartReadDTO();
        cartReadDTO.setItems(cartItemReadDTOList);
        return  cartReadDTO;
    }

    @Override
    public void addCartItem(Integer productId) {
       try {
           Cart cart=getCartByUser();
           // If the cart is empty
           if (cart == null) {
               cart=new Cart(_authService.getCurrentUser()) ;
               _repo.save(cart);
               CartItem cartItem = new CartItem();
               cartItem.setCart(cart);
               cartItem.setProduct(_productService.getById(productId));
               cartItem.setQuantity(1);
               _cartItemRepo.save(cartItem);
           }
           // If the cart is not empty
           else {
               CartItem cartItem = _cartItemRepo
                       .findByCartIdAndProductId(cart.getId(),productId )
                       .orElse(null);
               if (cartItem != null) {
                   cartItem.setQuantity(cartItem.getQuantity()+1) ;
               }
               else {
                   CartItem cartItem1 = new CartItem();
                   cartItem1.setCart(cart);
                   cartItem1.setProduct(_productService.getById(productId));
                   cartItem1.setQuantity(1);
                   _cartItemRepo.save(cartItem1);
               }
           }
       } catch (RuntimeException e) {
           throw new RuntimeException(e);
       }
    }

    @Override
    public void decreaseCartItem(Integer productId) {
        try {
            Cart cart=getCartByUser();
            // If the cart is empty
            if (cart == null){
                throw new RuntimeException("Cart does not exist");
            }
            // If a cart is not empty
            else {
                CartItem cartItem = _cartItemRepo
                        .findByCartIdAndProductId(cart.getId(),productId )
                        .orElse(null);
                if (cartItem != null) {
                    if (cartItem.getQuantity() >1 ) {
                        cartItem.setQuantity(cartItem.getQuantity()-1);
                    }
                    else{
                        _cartItemRepo.deleteById(cartItem.getId());
                    }

                }
                else {
                    throw new RuntimeException("CartItem does not exist");
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeCartItem(Integer productId) {
        try {
            Cart cart=getCartByUser();
            // If the cart is empty
            if (cart == null){
                throw new RuntimeException("Cart does not exist");
            }
            // If a cart is not empty
            else{
                CartItem cartItem = _cartItemRepo
                        .findByCartIdAndProductId(cart.getId(),productId )
                        .orElse(null);
                if (cartItem != null) {
                    _cartItemRepo.deleteById(cartItem.getId());
                }
                else {
                    throw new RuntimeException("CartItem does not exist");
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cart getCartByUser() {
        User user=_authService.getCurrentUser();
        Cart cart=_repo
                .findByUserId(user.getId())
                .orElse(null);
        return cart;
    }
}
