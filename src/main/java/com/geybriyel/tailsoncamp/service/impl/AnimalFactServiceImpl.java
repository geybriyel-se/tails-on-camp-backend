package com.geybriyel.tailsoncamp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geybriyel.tailsoncamp.service.AnimalFactService;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AnimalFactServiceImpl implements AnimalFactService {

    @Value("${api.cookie}")
    private String apiCookie;

    @Override
    public String getDogFact() throws JsonProcessingException {
        boolean isVerified = false;
        JsonNode response = null;

        while (!isVerified) {
            response = getApiResponse("dog");
            isVerified = response.get("status").get("verified").asBoolean();
        }

        return response.get("text").asText();
    }

    @Override
    public String getCatFact() throws JsonProcessingException {
        boolean isVerified = false;
        JsonNode response = null;

        while (!isVerified) {
            response = getApiResponse("cat");
            isVerified = response.get("status").get("verified").asBoolean();
        }

        return response.get("text").asText();
    }

/*    @Override
    public String getFactByAnimal(String animal) throws JsonProcessingException {
        boolean isVerified = false;
        JsonNode response = null;

        while (!isVerified) {
            response = getApiResponse(animal);
            isVerified = response.get("status").get("verified").asBoolean();
        }

        if (response == null) {
            return StatusCode.BAD_REQUEST.getMessage();
        } else {
            return response.get("text").asText();
        }

    }*/

    @Override
    public String getRandomFact() throws JsonProcessingException {
        Random random = new Random();
        int i = random.nextInt(2);
        if (i == 0) {
            return getCatFact();
        } else {
            return getDogFact();
        }
    }

    public JsonNode getApiResponse(String animal) throws JsonProcessingException {
        HttpResponse<String> response = Unirest.get("https://cat-fact.herokuapp.com/facts/random?animal_type=" + animal + "&amount=1")
                .header("Cookie", apiCookie)
                .asString();
        String body = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(body);
    }
}
