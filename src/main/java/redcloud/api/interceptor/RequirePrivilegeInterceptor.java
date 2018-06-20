package redcloud.api.interceptor;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redcloud.business.entity.PrivilegeType;
import redcloud.business.entity.User;
import redcloud.exceptions.UnauthorizedRestCall;
import redcloud.logging.LoggerWrapper;
import redcloud.business.service.AuthorizationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class RequirePrivilegeInterceptor extends GenericAnnotationInterceptor<RequirePrivilege> {

    private static final LoggerWrapper LOG = new LoggerWrapper(RequirePrivilegeInterceptor.class);

    private AuthorizationService authorizationService;

    @Autowired
    public RequirePrivilegeInterceptor(AuthorizationService authorizationService) {
        super(RequirePrivilege.class);
        this.authorizationService = authorizationService;
    }

    @Override
    public void doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler, RequirePrivilege annotation) throws Exception {

        String token = request.getHeader("token");
        LOG.info("Handle RequirePrivilege annotation", "token", token);

        if(token == null) {
            throw new UnauthorizedRestCall();
        }

        User user = authorizationService.getUserToken(token);

        if(user == null) {
            throw new UnauthorizedRestCall();
        }

        PrivilegeType[] requiredPrivileges = annotation.value();

        if(!user.getPrivilegesTypes().containsAll(Lists.newArrayList(requiredPrivileges))) {
            throw new UnauthorizedRestCall();
        }
    }
}
