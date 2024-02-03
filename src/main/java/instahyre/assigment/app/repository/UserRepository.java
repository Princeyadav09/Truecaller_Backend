package instahyre.assigment.app.repository;

import instahyre.assigment.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

     User findByPhoneNumber(String phoneNumber);
}
