package JavaAPI_DUC_Company1.model;

import JavaAPI_DUC_Company1.model.auth.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales_orders")
@Getter
@Setter
public class SalesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    //private List<SalesOrderItem> salesOrderItems;
    //private Instant createdDate = Instant.now();
    //  The following line can be replaced by the above line.
    private LocalDateTime createdDate=LocalDateTime.now();

    @NotBlank(message = "User's telephone number is required")
    private String userTel;

    @NotBlank(message = "User's address is required")
    private String userAddress;

    @Column(precision = 12, scale = 2)
    private BigDecimal totalCost;
//   @Enumerated(EnumType.STRING): the database contains: Pending,Delivered,Cancelled
    @Enumerated(EnumType.STRING)
    private SalesStatus status = SalesStatus.Pending;

    public SalesOrder() {
    }

    public SalesOrder(User user,String userTel, String userAddress,
                      BigDecimal totalCost )
    {
        this.user = user;
        this.userTel = userTel;
        this.userAddress = userAddress;
        this.totalCost = totalCost;
    }
}
