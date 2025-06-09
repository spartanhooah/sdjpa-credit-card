package net.frey.creditcard.interceptors;

import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

@Component
public class EncryptionInterceptor implements Interceptor {
    @Override
    public boolean onLoad(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types)
            throws CallbackException {
        System.out.println("I'm in onLoad");

        return Interceptor.super.onLoad(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(
            Object entity,
            Object id,
            Object[] currentState,
            Object[] previousState,
            String[] propertyNames,
            Type[] types)
            throws CallbackException {
        System.out.println("I'm in onFlushDirty");

        return Interceptor.super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }

    @Override
    public boolean onPersist(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types)
            throws CallbackException {
        System.out.println("I'm in onPersist");

        return Interceptor.super.onPersist(entity, id, state, propertyNames, types);
    }
}
