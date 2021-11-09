package my.company.cdi.demo.extension;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

public class StartupBeanExtension implements javax.enterprise.inject.spi.Extension {

	private static final Logger LOGGER = Logger.getLogger(StartupBeanExtension.class.getName());

	// Will store all the Bean<?> annotated with @StartUp
	private final Set<Bean<?>> startupBeans = new LinkedHashSet<>();

	// Will store all the Bean<?> intercepted during the ProcessBean event, part of
	// the the CDI Lifecycle.
	private final Set<Bean<?>> allProcessedBeans = new LinkedHashSet<>();

	private void processBean(@Observes ProcessBean event) {
		Annotated annotated = event.getAnnotated();

		allProcessedBeans.add(event.getBean());
		if (isStartUpBean(annotated)) {
			LOGGER.info("===> New bean with @StartUp found: " + event.getBean());

			/**
			 * TODO: Obtain the bean from the event object, and add it to the startupBeans
			 * list.
			 */
			startupBeans.add(event.getBean());
		}
	}

	private boolean isStartUpBean(Annotated annotated) {
		return annotated.isAnnotationPresent(StartUp.class) && annotated.isAnnotationPresent(ApplicationScoped.class);
	}

	/**
	 * This method goal is to eagerly initialize the bean. In this way, whenever the
	 * instance is injected it is already created and part of the CDI context.
	 */
	void afterDeploymentValidation(@Observes AfterDeploymentValidation event, BeanManager manager) {
		LOGGER.info("===> Number of classes with @StartUp: " + startupBeans.size()
				+ ", Total number of intercepted beans at ProcessBean phase: " + allProcessedBeans.size());

		// Initialize all beans annotated with @StartUp
		for (Bean<?> bean : startupBeans) {
			// Use to toString() "Workaround" as a way to initialize every reference of the
			// beans annotated with @StartUp
			manager.getReference(bean, bean.getBeanClass(), manager.createCreationalContext(bean)).toString();
		}
	}
}