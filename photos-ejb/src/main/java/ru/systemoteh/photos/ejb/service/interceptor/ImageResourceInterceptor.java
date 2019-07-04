package ru.systemoteh.photos.ejb.service.interceptor;

import ru.systemoteh.photos.model.ImageResource;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
public class ImageResourceInterceptor {

    @AroundInvoke
    public Object aroundProcessImageResource(InvocationContext ic) throws Exception {
        try(ImageResource imageResource = (ImageResource) ic.getParameters()[0]) {
            return ic.proceed();
        }
    }
}