package com.geybriyel.tailsoncamp.dto;

import com.geybriyel.tailsoncamp.entity.Shelter;
import com.geybriyel.tailsoncamp.entity.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PetDetailsRequestDTO {

    private Long id;

    private String name;

    private String type;

    private String breed;

    private Integer age;

    private String gender;

    private String size;

    private String description;

    private String imageUrl;

    private Integer availability;

    private Long shelterId;

}
