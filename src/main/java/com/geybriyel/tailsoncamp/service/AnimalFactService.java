package com.geybriyel.tailsoncamp.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface AnimalFactService {

    String getDogFact() throws JsonProcessingException;

    String getCatFact() throws JsonProcessingException;

//    String getFactByAnimal(String animal) throws JsonProcessingException;

    String getRandomFact() throws JsonProcessingException;

}
