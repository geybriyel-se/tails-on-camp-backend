package com.geybriyel.tailsoncamp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.geybriyel.tailsoncamp.dto.ApiResponse;
import com.geybriyel.tailsoncamp.enums.StatusCode;
import com.geybriyel.tailsoncamp.service.AnimalFactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/facts")
@RequiredArgsConstructor
public class FactsController {

    private final AnimalFactService animalFactService;

    @GetMapping("/dog")
    public ApiResponse<Object> getDogFact() throws JsonProcessingException {
        String fact = animalFactService.getDogFact();
        return new ApiResponse<>(StatusCode.SUCCESS, fact);
    }

    @GetMapping("/cat")
    public ApiResponse<Object> getCatFact() throws JsonProcessingException {
        String fact = animalFactService.getCatFact();
        return new ApiResponse<>(StatusCode.SUCCESS, fact);
    }

    @GetMapping("/random")
    public ApiResponse<Object> getRandomFact() throws JsonProcessingException {
        String fact = animalFactService.getRandomFact();
        return new ApiResponse<>(StatusCode.SUCCESS, fact);
    }

/*    @GetMapping("/animal")
    public ApiResponse<Object> getFactByAnimal(@RequestBody String animal) throws JsonProcessingException {
        String fact = animalFactService.getFactByAnimal(animal);
        return new ApiResponse<>(StatusCode.SUCCESS, fact);
    }*/
}
