package de.ait.pool.models;

import de.ait.pool.models.cart.Cart;
import de.ait.pool.models.order.Order;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    public User(Long userId, String testName, String testLastName, String testMail, String testPassword, String testPhoneNumber, Role testRole, State TestState, Cart testCart) {
        this.id = userId;
        this.firstName = testName;
        this.lastName = testLastName;
        this.email = testMail;
        this.hashPassword = testPassword;
        this.phoneNumber = testPhoneNumber;
        this.role = testRole;
        this.state = TestState;
        this.cart = testCart;
    }

    public enum Role {
        ADMIN, USER
    }

    public enum State {
        NOT_CONFIRMED, CONFIRMED, DELETED, BANNED
    }

    // TODO проверить колонки
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (nullable = false)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    // проверка пароля - второе поле подтвердить пароль
    @Column
    private String hashPassword;


    @Column(nullable = false)
    private String phoneNumber;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column
    @Enumerated(value = EnumType.STRING)
    private State state;

    // Один пользователь может иметь только одну корзину
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    // Один пользователь может иметь множество заказов
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();

    //TODO заказы Orders
    // Многие пользователи могут иметь множество продуктов




    @OneToMany(mappedBy = "user")
    private Set<ConfirmationCode> codes;


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
