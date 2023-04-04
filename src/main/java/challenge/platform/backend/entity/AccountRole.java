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
@Table(name = "accounts_roles")
public class AccountRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // needed to start, but what does it mean?
    @Column(name = "account_id", insertable = true, updatable = false)
    private Long accountId;
    @Column(name = "role_id", insertable = true, updatable = false)
    private Long roleId;


    @PersistenceCreator
    public AccountRole(Long accountId, Long roleId) {
        this.accountId = accountId;
        this.roleId = roleId;
    }
}
