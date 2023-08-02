package com.ts.postmaster.dao.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(PMUser.class)
public class PMUser_ extends BaseEntity_ {

    public static volatile SingularAttribute<PMUser, String> username;
    public static volatile SingularAttribute<PMUser, String> email;
    public static volatile SingularAttribute<PMUser, String> password;

    // You can add static string variables for the attribute names, if needed.
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
}
