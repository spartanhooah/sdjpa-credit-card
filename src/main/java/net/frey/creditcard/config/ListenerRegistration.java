package net.frey.creditcard.config;

import net.frey.creditcard.listeners.PostLoadListener;
import net.frey.creditcard.listeners.PreInsertListener;
import net.frey.creditcard.listeners.PreUpdateListener;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
public class ListenerRegistration implements BeanPostProcessor {
    @Autowired
    public void configureListeners(
            LocalContainerEntityManagerFactoryBean factoryBean,
            PostLoadListener postLoadListener,
            PreInsertListener preInsertListener,
            PreUpdateListener preUpdateListener) {
        var sessionFactory = (SessionFactoryImpl) factoryBean.getNativeEntityManagerFactory();
        var registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);

        registry.appendListeners(EventType.POST_LOAD, postLoadListener);
        registry.appendListeners(EventType.PRE_UPDATE, preUpdateListener);
        registry.appendListeners(EventType.PRE_INSERT, preInsertListener);
    }
}
