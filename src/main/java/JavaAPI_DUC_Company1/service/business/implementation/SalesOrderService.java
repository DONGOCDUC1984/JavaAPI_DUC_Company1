package JavaAPI_DUC_Company1.service.business.implementation;

import JavaAPI_DUC_Company1.mapper.SalesOrderItemMapper;
import JavaAPI_DUC_Company1.mapper.SalesOrderMapper;
import JavaAPI_DUC_Company1.model.*;
import JavaAPI_DUC_Company1.model.auth.User;
import JavaAPI_DUC_Company1.model.dto.salesOrder.SalesOrderAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.salesOrder.SalesOrderReadDTO;
import JavaAPI_DUC_Company1.repository.*;
import JavaAPI_DUC_Company1.security.interfaces.IAuthService;
import JavaAPI_DUC_Company1.service.business.interfaces.ICartService;
import JavaAPI_DUC_Company1.service.business.interfaces.ISalesOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;


@Service
@Transactional
public class SalesOrderService implements ISalesOrderService {
    private final SalesOrderRepository _repo;
    private final CartRepository _cartRepo;
    private final CartItemRepository _cartItemRepo;
    private final SalesOrderItemRepository _salesOrderItemRepo;
    private final ProductRepository _productRepo;
    private final ICartService _cartService;
    private final IAuthService _authService;
    private final SalesOrderMapper _mapper;
    private final SalesOrderItemMapper _salesOrderItemMapper;
    public  SalesOrderService(SalesOrderRepository repo, CartRepository cartRepo,
                              CartItemRepository cartItemRepo, SalesOrderItemRepository salesOrderItemItemRepo, ProductRepository productRepo,
                              ICartService cartService, IAuthService authService, SalesOrderMapper mapper, SalesOrderItemMapper salesOrderItemMapper){
        _repo=repo;
        _cartRepo = cartRepo;
        _cartItemRepo = cartItemRepo;
        _salesOrderItemRepo = salesOrderItemItemRepo;
        _productRepo = productRepo;
        _cartService = cartService;
        _authService = authService;
        _mapper = mapper;
        _salesOrderItemMapper = salesOrderItemMapper;
    }

    @Override
    public List<SalesOrderReadDTO> getAll() {
        List<SalesOrder> salesOrders=_repo.findAll();
        List<SalesOrderReadDTO> listReadDTO=_mapper.toReadDTOList(salesOrders);
        for (SalesOrderReadDTO dto: listReadDTO) {
            List<SalesOrderItem> salesOrderItems
                    =_salesOrderItemRepo.findBySalesOrderId(dto.getId())
                    .orElse(null);
            dto.setItems(_salesOrderItemMapper.toReadDTOList(salesOrderItems));
        }
        return listReadDTO;
    }

    @Override
    public List<SalesOrderReadDTO> getByUserId() {
        User user=_authService.getCurrentUser();
        List<SalesOrder> salesOrders=_repo
                .findByUserIdAndStatus(user.getId(), SalesStatus.Pending)
                .orElse(null) ;
        List<SalesOrderReadDTO> listReadDTO=_mapper.toReadDTOList(salesOrders);
        for (SalesOrderReadDTO dto: listReadDTO) {
            List<SalesOrderItem> salesOrderItems
                    =_salesOrderItemRepo.findBySalesOrderId(dto.getId())
                    .orElse(null);
            dto.setItems(_salesOrderItemMapper.toReadDTOList(salesOrderItems));
        }
        return  listReadDTO;
    }

    @Override
    public SalesOrder getById(Integer id) {
        return _repo.findById(id)
                .orElseThrow(() -> new RuntimeException("SalesOrder not found with id " + id));
    }

    @Override
    public void add(SalesOrderAddUpdateDTO modelDTO) {
        Cart cart=_cartService.getCartByUser();
        if (cart==null ||
                modelDTO.getTotalCost().compareTo(BigDecimal.ZERO) ==  0) {
            throw new RuntimeException("Cart does not exist or cart is empty");
        }
//        SalesOrder salesOrder=new SalesOrder();
//        salesOrder.setUser(_authService.getCurrentUser());
//        salesOrder.setUserTel(modelDTO.getUserTel());
//        salesOrder.setUserAddress(modelDTO.getUserAddress());
//        salesOrder.setTotalCost(modelDTO.getTotalCost());
//        The 5 above lines can be replaced by the 5 following line;
        SalesOrder salesOrder=new SalesOrder(
                _authService.getCurrentUser(),
                modelDTO.getUserTel(),
                modelDTO.getUserAddress(),
                modelDTO.getTotalCost()
        );
        _repo.save(salesOrder);
        List<CartItem> cartItems = _cartItemRepo.findByCartId(cart.getId())
                .orElseThrow(() -> new RuntimeException("CartItems not found."));
        for (CartItem cartItem : cartItems) {
            SalesOrderItem salesOrderItem=new SalesOrderItem();
            salesOrderItem.setSalesOrder(salesOrder);
            salesOrderItem.setProduct(cartItem.getProduct());
            salesOrderItem.setQuantity(cartItem.getQuantity());
            _salesOrderItemRepo.save(salesOrderItem);
        }
        _cartItemRepo.deleteAll(cartItems);
        _cartRepo.delete(cart);
    }

// I should not have this delete method since :
// _A sales order is usually business history.
// If I delete it,I lose the historical record.
// _I can replace it by markCancelled method.
// If I want to delete a SalesOrder, I will have to delete its SalesOrderItem first.
// Just check in MySQL Workbench .
//    @Override
//    public void delete(Integer id) {
//        _repo.deleteById(id);
//    }

    @Override
    public void markDelivered(Integer id) {
        // Load order
        SalesOrder salesOrder=getById(id);
        if (salesOrder.getStatus() == SalesStatus.Delivered) {
            return;
        }
        // Load all related items + products in one query
        List<SalesOrderItem> salesOrderItems
                =_salesOrderItemRepo.findBySalesOrderId(id)
                .orElse(null);
        // Update order
        salesOrder.setStatus(SalesStatus.Delivered);
        // Update each item + adjust stock
        for (SalesOrderItem item : salesOrderItems) {
            item.setStatus(SalesStatus.Delivered);
            Product product =item.getProduct();
            if (product.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product with id="
                        + product.getId());
            }
            product.setStockQuantity(product.getStockQuantity()
                    -item.getQuantity());
        }
    }

    @Override
    public void markCancelled(Integer id) {
        // Load order
        SalesOrder salesOrder=getById(id);
        if (salesOrder.getStatus() == SalesStatus.Cancelled) {
            return;
        }
        // Load all related items + products in one query
        List<SalesOrderItem> salesOrderItems
                =_salesOrderItemRepo.findBySalesOrderId(id)
                .orElse(null);
        // Update order
        salesOrder.setStatus(SalesStatus.Cancelled);
        // Update each item
        for (SalesOrderItem item : salesOrderItems) {
            item.setStatus(SalesStatus.Cancelled);
        }
    }
}

