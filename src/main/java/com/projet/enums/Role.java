package com.projet.enums;

public enum Role {
    ADMIN,
    CLIENT,
    BOUTIQUIER;

    public static Role getValue(String roleName){
        for(Role role : Role.values()){
            if(role.name().equalsIgnoreCase(roleName)){
                return role;
            }
        }
        return null;
    }
}
