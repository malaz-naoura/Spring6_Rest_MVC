package mezo.restmvc.spring_6_rest_mvc.controllers;


import lombok.AllArgsConstructor;
import mezo.restmvc.spring_6_rest_mvc.model.Juice;
import mezo.restmvc.spring_6_rest_mvc.service.JuiceService;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/juices")
public class JuiceController {
    private final JuiceService juiceService;

    @GetMapping
    List<Juice> listJuice(){
        return juiceService.listJuices();
    }

    @GetMapping("/{juideId}")
    Juice getById(@PathVariable("juideId") UUID juiceId){
        return juiceService.getJuiceById(juiceId);
    }

    @PostMapping
    ResponseEntity saveNewJuice(@RequestBody Juice juice){
        Juice savedJuice=juiceService.addJuice(juice);
        HttpHeaders headers=new HttpHeaders();
        headers.add("Location","api/v1/"+savedJuice.getId());
        return new ResponseEntity(headers,HttpStatus.CREATED);
    }

    @PutMapping("{juiceId}")
    ResponseEntity updateOrCreateJuice(@PathVariable("juiceId") UUID id,@RequestBody Juice juice){
         juiceService.updateOrCreate(id,juice);
         return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{juiceId}")
    ResponseEntity deleteById(@PathVariable("juiceId") UUID id){
        juiceService.removeById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{juiceId}")
    ResponseEntity updateJuice(@PathVariable("juiceId") UUID id,@RequestBody Juice juice){
        juiceService.update(id,juice);
        return new ResponseEntity(HttpStatus.NO_CONTENT);


    }


}
