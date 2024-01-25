package mezo.restmvc.spring_6_rest_mvc.controllers;


import lombok.AllArgsConstructor;
import mezo.restmvc.spring_6_rest_mvc.model.JuiceDTO;
import mezo.restmvc.spring_6_rest_mvc.service.JuiceService;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/juice")
public class JuiceController {
    public static final String JUICE_PATH = "/api/v1/juice";
    private static final String JUICE_ID_VAR = "/{juiceId}";
    public static final String JUICE_PATH_ID = JUICE_PATH + JUICE_ID_VAR;

    private final JuiceService juiceService;

    @GetMapping
    Page<JuiceDTO> listJuice(@RequestParam(required = false) String juiceName,
                             @RequestParam(required = false) String juiceStyle,
                             @RequestParam(required = false) Boolean showInventory,
                             @RequestParam(required = false) Integer pageNumber,
                             @RequestParam(required = false) Integer pageSize) {
        return juiceService.listJuices(juiceName, juiceStyle, showInventory, pageNumber, pageSize);
    }

    @GetMapping(JUICE_ID_VAR)
    JuiceDTO getById(@PathVariable("juiceId") UUID juiceId) {
        return juiceService.getJuiceById(juiceId)
                           .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    ResponseEntity saveNewJuice(@Validated @RequestBody JuiceDTO juiceDTO) {
        JuiceDTO savedJuiceDTO = juiceService.addJuice(juiceDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/" + savedJuiceDTO.getId());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping(JUICE_ID_VAR)
    ResponseEntity updateOrCreateJuice(@PathVariable("juiceId") UUID id, @RequestBody JuiceDTO juiceDTO) {
        juiceService.updateOrCreate(id, juiceDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(JUICE_ID_VAR)
    ResponseEntity deleteById(@PathVariable("juiceId") UUID id) {
        Boolean isExistAndRemoved = juiceService.removeById(id);
        if (!isExistAndRemoved) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(JUICE_ID_VAR)
    ResponseEntity updateJuice(@PathVariable("juiceId") UUID id, @RequestBody JuiceDTO juiceDTO) {
        Optional<JuiceDTO> updatedJuice = juiceService.update(id, juiceDTO);
        if (updatedJuice.isEmpty()) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }
}
