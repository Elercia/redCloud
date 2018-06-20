package redcloud.api.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import redcloud.logging.LoggerWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

public abstract class GenericAnnotationInterceptor<T extends Annotation> extends HandlerInterceptorAdapter {

    protected Class<T> annotationToUse;

    protected GenericAnnotationInterceptor(Class<T> annotationToUse) {

        this.annotationToUse = annotationToUse;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        T annotationPresent = handlerMethod.getMethod().getAnnotation(annotationToUse);

        if (annotationPresent == null) {
            return true;
        }

        doPreHandle(request, response, handler, annotationPresent);

        return super.preHandle(request, response, handler);
    }

    public abstract void doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler, T annotation) throws Exception;
}
