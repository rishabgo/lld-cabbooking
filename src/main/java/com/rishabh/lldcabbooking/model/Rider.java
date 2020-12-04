package com.rishabh.lldcabbooking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@ToString
public class Rider {

    private String id;
    private String name;
    private String email;
    private String phoneNumber;
}
