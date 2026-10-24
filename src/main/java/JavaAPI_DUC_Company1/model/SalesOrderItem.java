package JavaAPI_DUC_Company1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sales_order_items")
@Getter
@Setter
public class SalesOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private SalesOrder salesOrder;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private SalesStatus status = SalesStatus.Pending;

    public SalesOrderItem() {
    }

    public SalesOrderItem( SalesOrder salesOrder, Product product,
                          Integer quantity) {
        this.salesOrder = salesOrder;
        this.product = product;
        this.quantity = quantity;
    }
}
