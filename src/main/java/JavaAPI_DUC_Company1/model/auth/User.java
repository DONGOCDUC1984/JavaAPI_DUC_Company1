package JavaAPI_DUC_Company1.model.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Use the database's AUTO_INCREMENT
    private Integer id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    //  @ManyToOne : many users can have the same role like Admin, User.
    // fetch = FetchType.EAGER: Eager loading.
    // In this case:Whenever a user is loaded, load its role immediately.
    @ManyToOne(fetch = FetchType.EAGER)
    // The foreign key column is named role_id.
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(unique = true)
    private String googleId;

    @Column(unique = true)
    private String email;

    public User() {
    }


}
