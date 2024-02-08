package instahyre.assigment.app.service.Impl;

import instahyre.assigment.app.entity.PhoneNumber;
import instahyre.assigment.app.exception.BusinessException;
import instahyre.assigment.app.repository.PhoneNumberRepository;
import instahyre.assigment.app.service.SpamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class SpamServiceImpl implements SpamService {

    @Autowired
    PhoneNumberRepository phoneNumberRepository;

    @Override
    public void markSpam(String phoneNumber) {

        validate(phoneNumber);

        PhoneNumber phoneNumber1 = phoneNumberRepository.findByPhoneNumber(phoneNumber);
        if(phoneNumber1==null){
            phoneNumber1 = new PhoneNumber();
            phoneNumber1.setPhoneNumber(phoneNumber);
            phoneNumber1.setSpamCount(1L);
        }
        phoneNumber1.setSpamCount(phoneNumber1.getSpamCount()+1L);
        phoneNumberRepository.save(phoneNumber1);
    }

    public void validate(String phoneNumber){
        if(phoneNumber.length() != 10){
            throw  new BusinessException("Phone Number is not valid !", HttpStatus.BAD_REQUEST);
        }
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(phoneNumber).matches()) {
            throw new BusinessException("Phone Number must contain only digits!", HttpStatus.BAD_REQUEST);
        }

    }
}
