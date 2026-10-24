package JavaAPI_DUC_Company1.model;

import JavaAPI_DUC_Company1.model.auth.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "carts")
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @ManyToOne
//    @JoinColumn(nullable = false)
    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private User user;


    //private List<CartItem> cartItems;

    public Cart() {
    }

    public Cart(User user) {
        this.user = user;
    }
}

