package instahyre.assigment.app.repository;

import instahyre.assigment.app.entity.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber,Long> {
       PhoneNumber findByPhoneNumber(String phoneNumber);
       List<PhoneNumber> findByPhoneNumberContaining(String PhoneNumber);
       List<PhoneNumber> findByNamesContaining(String names);
}
