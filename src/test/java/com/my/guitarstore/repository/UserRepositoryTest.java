package com.my.guitarstore.repository;

import com.my.guitarstore.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // to test with real db
@Rollback(false)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

   // @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("alex@gmail.com");
        user.setPassword("alex2020");
        user.setFirstName("Alex");
        user.setLastName("Heb");

        User savedUser = userRepository.save(user);

        User existUser = entityManager.find(User.class, savedUser.getId());

        assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
    }

    //@Test
    public void testFindUserByEmail() {
        String email = "alex@gmail.com";
        User user = userRepository.findByEmail(email);
        assertThat(user).isNotNull();
    }
}
