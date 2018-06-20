package redcloud.api.interceptor;

import redcloud.business.entity.PrivilegeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePrivilege {

    PrivilegeType[] value();
}
