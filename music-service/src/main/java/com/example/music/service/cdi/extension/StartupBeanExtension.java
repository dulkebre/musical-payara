package com.example.music.service.cdi.extension;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.ProcessBean;

public class StartupBeanExtension implements javax.enterprise.inject.spi.Extension {

	private static final Logger LOGGER = Logger.getLogger(StartupBeanExtension.class.getName());

	private final Set<Bean<?>> startupBeans = new LinkedHashSet<>();

	<X> void processBean(@Observes ProcessBean<X> event) {
		Annotated annotated = event.getAnnotated();
		if (isStartUpBean(annotated)) {
			LOGGER.info("New StartUp class found: " + event.getBean());
			startupBeans.add(event.getBean());
		}
	}

	private boolean isStartUpBean(Annotated annotated) {
		return annotated.isAnnotationPresent(StartUp.class) && annotated.isAnnotationPresent(ApplicationScoped.class);
	}

	void afterDeploymentValidation(@Observes AfterDeploymentValidation event, BeanManager manager) {
		LOGGER.info("Number of StartUp classes: " + startupBeans.size());
		for (Bean<?> bean : startupBeans) {
			// the call to toString() is a cheat to force the bean to be initialized
			manager.getReference(bean, bean.getBeanClass(), manager.createCreationalContext(bean)).toString();
		}
	}
}