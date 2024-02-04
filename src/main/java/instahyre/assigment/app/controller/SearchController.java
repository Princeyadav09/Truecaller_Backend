package instahyre.assigment.app.controller;

import instahyre.assigment.app.entity.PhoneNumber;
import instahyre.assigment.app.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("app/v1/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @GetMapping("/by/phoneNumber")
    public ResponseEntity<List<PhoneNumber>> getNumberByNumber(@RequestParam String phoneNumber){
        return new ResponseEntity<>(searchService.getNumberByNumber(phoneNumber), HttpStatus.OK);
    }

    @GetMapping("/by/names")
    public ResponseEntity<List<PhoneNumber>> getNumberByName(@RequestParam String name){
        return new ResponseEntity<>(searchService.getNumberByName(name),HttpStatus.OK);
    }

}
