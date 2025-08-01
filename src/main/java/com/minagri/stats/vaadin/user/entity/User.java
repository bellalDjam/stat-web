package com.minagri.stats.vaadin.user.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String displayName;
    private String name;
    private String email;

    private Set<String> roles = new HashSet<>();
    private Integer fid;
    private List<Integer> allowedFids = new ArrayList<>();


}
