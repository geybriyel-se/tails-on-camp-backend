package com.geybriyel.tailsoncamp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.geybriyel.tailsoncamp.dto.ApiResponse;
import com.geybriyel.tailsoncamp.enums.StatusCode;
import com.geybriyel.tailsoncamp.service.AnimalFactService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "Retrieve random dog fact",
            description = "Retrieves a random dog fact from a third-party API. " +
                    "The system filters the facts and returns only verified facts, as classified by the API itself."
    )
    @GetMapping("/dog")
    public ApiResponse<Object> getDogFact() throws JsonProcessingException {
        String fact = animalFactService.getDogFact();
        return new ApiResponse<>(StatusCode.SUCCESS, fact);
    }

    @Operation(
            summary = "Retrieve random cat fact",
            description = "Retrieves a random cat fact from a third-party API. " +
                    "The system filters the facts and returns only verified facts, as classified by the API itself."
    )
    @GetMapping("/cat")
    public ApiResponse<Object> getCatFact() throws JsonProcessingException {
        String fact = animalFactService.getCatFact();
        return new ApiResponse<>(StatusCode.SUCCESS, fact);
    }

    @Operation(
            summary = "Retrieve random cat or dog fact",
            description = "Retrieves a random cat or dog fact from a third-party API. " +
                    "The system filters the facts and returns only verified facts, as classified by the API itself."
    )
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
