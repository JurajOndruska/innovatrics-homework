/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.cmdexec.implementation;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * A spring component which implements interface {@linkplain ExecutorFactory}.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
@Component("jon.innovatrics.homework.manager.implementation.DefaultExtendedResultHandlerFactory")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
final class DefaultExtendedResultHandlerFactory implements ExtendedResultHandlerFactory {

    /**
     * {@inheritDoc}
     *
     * <p>Note: The newly created instance is an instance of the
     * {@linkplain DefaultExtendedResultHandler} class.</p>
     *
     * @return {@inheritDoc}
     */
    @Override
    public ExtendedResultHandler newExtendedResultHandler() {
        return new DefaultExtendedResultHandler();
    }
}
