package com.ts.postmaster.dao.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(BaseEntity.class)
public class BaseEntity_ {

    public static volatile SingularAttribute<BaseEntity, Long> id;
    public static volatile SingularAttribute<BaseEntity, LocalDateTime> createdAt;
    public static volatile SingularAttribute<BaseEntity, LocalDateTime> updatedAt;

    // You can add static string variables for the attribute names, if needed.
    public static final String ID = "id";
    public static final String CREATED_AT = "createdAt";
    public static final String UPDATED_AT = "updatedAt";
}
