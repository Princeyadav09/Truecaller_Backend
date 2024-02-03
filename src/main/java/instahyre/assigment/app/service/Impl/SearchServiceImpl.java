package instahyre.assigment.app.service.Impl;

import instahyre.assigment.app.entity.PhoneNumber;
import instahyre.assigment.app.exception.BusinessException;
import instahyre.assigment.app.repository.PhoneNumberRepository;
import instahyre.assigment.app.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    PhoneNumberRepository phoneNumberRepository;

    @Override
    public List<PhoneNumber> getNumberByNumber(String phoneNumber) {
        try {
            return phoneNumberRepository.findByPhoneNumberContaining(phoneNumber);
        } catch (Exception ex){
            throw new BusinessException(ex);
        }
    }

    @Override
    public List<PhoneNumber> getNumberByName(String name) {
        try {
            return phoneNumberRepository.findByNamesContaining(name);
        } catch (Exception ex){
            throw new BusinessException(ex);
        }
    }
}
