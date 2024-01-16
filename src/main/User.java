import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 30, nullable = false)
    @NotBlank(message = "O campo username é obrigatório!")
    private String userName;

    @Column(length = 30, nullable = false)
    @NotBlank(message = "O campo senha é obrigatório!")
    private String passoword;

    private boolean isEnabled;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Sale> sales;
}