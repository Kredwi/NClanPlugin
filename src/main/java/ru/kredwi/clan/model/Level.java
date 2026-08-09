package ru.kredwi.clan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Level {

    private String name;
    private int exp;
    private int members;

}
