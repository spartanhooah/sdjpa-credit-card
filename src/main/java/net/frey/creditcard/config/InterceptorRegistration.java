package net.frey.creditcard.config;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import net.frey.creditcard.interceptors.EncryptionInterceptor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;

// @Configuration
@RequiredArgsConstructor
public class InterceptorRegistration implements HibernatePropertiesCustomizer {
    private final EncryptionInterceptor interceptor;

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.session_factory.interceptor", interceptor);
    }
}
