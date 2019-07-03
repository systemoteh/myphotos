package ru.systemoteh.photos.ejb.repository.mock;

import ru.systemoteh.photos.common.annotation.cdi.Factory;
import ru.systemoteh.photos.ejb.repository.AccessTokenRepository;
import ru.systemoteh.photos.ejb.repository.PhotoRepository;
import ru.systemoteh.photos.ejb.repository.ProfileRepository;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Dependent
public class InMemoryRepositoryFactory {

    @Inject
    private ProfileRepositoryInvocationHandler profileRepositoryInvocationHandler;

    @Inject
    private PhotoRepositoryInvocationHandler photoRepositoryInvocationHandler;

    @Produces @Factory
    public ProfileRepository getProfileRepository() {
        return (ProfileRepository) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{ProfileRepository.class}, profileRepositoryInvocationHandler);
    }

    @Produces @Factory
    public PhotoRepository getPhotoRepository() {
        return (PhotoRepository) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{PhotoRepository.class}, photoRepositoryInvocationHandler);
    }

    @Produces @Factory
    public AccessTokenRepository getAccessTokenRepository() {
        return (AccessTokenRepository) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{AccessTokenRepository.class}, (Object proxy, Method method, Object[] args) -> {
                    throw new UnsupportedOperationException("Not implemented yet.");
                });
    }
}
