package fr.elercia.redcloud.api.security;

import fr.elercia.redcloud.business.entity.UserType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireUserType {

    UserType[] value() default {UserType.USER};
}
