package instahyre.assigment.app.service;

import instahyre.assigment.app.entity.PhoneNumber;

import java.util.List;

public interface SearchService {
    List<PhoneNumber> getNumberByNumber(String phoneNumber);
    List<PhoneNumber> getNumberByName(String name);

}
