package JavaAPI_DUC_Company1.model.auth;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Use the database's AUTO_INCREMENT
    private Integer id;
    @Column(nullable = false, unique = true)
    private String name;

    public Role() {

    }
    public Role(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
