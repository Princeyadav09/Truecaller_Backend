package instahyre.assigment.app.controller;

import instahyre.assigment.app.service.SpamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/v1/spam")
public class SpamController {

    @Autowired
    SpamService spamService;

    @PutMapping("/mark")
    public ResponseEntity<Void> markSpam(@RequestParam String phoneNumber){
        spamService.markSpam(phoneNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
