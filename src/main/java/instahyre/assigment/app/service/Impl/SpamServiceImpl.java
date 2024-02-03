package instahyre.assigment.app.service.Impl;

import instahyre.assigment.app.entity.PhoneNumber;
import instahyre.assigment.app.repository.PhoneNumberRepository;
import instahyre.assigment.app.service.SpamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpamServiceImpl implements SpamService {

    @Autowired
    PhoneNumberRepository phoneNumberRepository;

    @Override
    public void markSpam(String phoneNumber) {
        PhoneNumber phoneNumber1 = phoneNumberRepository.findByPhoneNumber(phoneNumber);
        phoneNumber1.setSpamCount(phoneNumber1.getSpamCount()+1L);
        phoneNumberRepository.save(phoneNumber1);
    }
}
