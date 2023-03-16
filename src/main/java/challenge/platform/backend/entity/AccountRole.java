package challenge.platform.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.PersistenceCreator;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "Accounts_Roles")
public class AccountRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long roleId;


    @PersistenceCreator
    public AccountRole(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
