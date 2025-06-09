package net.frey.creditcard.interceptors;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import net.frey.creditcard.services.EncryptionService;
import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;
import org.springframework.util.StringUtils;

// @Component
@RequiredArgsConstructor
public class EncryptionInterceptor implements Interceptor {
    private final EncryptionService encryptionService;

    @Override
    public boolean onLoad(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types)
            throws CallbackException {
        System.out.println("I'm in onLoad");

        var newState = processFields(entity, state, propertyNames, "onLoad");

        return Interceptor.super.onLoad(entity, id, newState, propertyNames, types);
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

        var newState = processFields(entity, currentState, propertyNames, "onFlushDirty");

        return Interceptor.super.onFlushDirty(entity, id, newState, previousState, propertyNames, types);
    }

    @Override
    public boolean onPersist(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types)
            throws CallbackException {
        System.out.println("I'm in onPersist");

        var newState = processFields(entity, state, propertyNames, "onPersist");

        return Interceptor.super.onPersist(entity, id, newState, propertyNames, types);
    }

    private Object[] processFields(Object entity, Object[] state, String[] propertyNames, String type) {
        List<String> annotationFields = getAnnotationFields(entity);

        for (var field : annotationFields) {
            for (int i = 0; i < propertyNames.length; i++) {
                if (propertyNames[i].equals(field) && StringUtils.hasText(state[i].toString())) {
                    if ("onPersist".equals(type) || "onFlushDirty".equals(type)) {
                        state[i] = encryptionService.encrypt(state[i].toString());
                    } else if ("onLoad".equals(type)) {
                        state[i] = encryptionService.decrypt(state[i].toString());
                    }
                }
            }
        }

        return state;
    }

    private List<String> getAnnotationFields(Object entity) {
        List<String> annotatedFields = new ArrayList<>();

        for (var field : entity.getClass().getDeclaredFields()) {
            if (!Objects.isNull(field.getAnnotation(EncryptedString.class))) {
                annotatedFields.add(field.getName());
            }
        }

        return annotatedFields;
    }
}
