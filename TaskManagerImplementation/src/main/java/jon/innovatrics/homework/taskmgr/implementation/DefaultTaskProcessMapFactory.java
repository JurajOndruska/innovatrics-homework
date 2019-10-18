/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr.implementation;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * A spring component which implements interface {@linkplain TaskProcessMapFactory}.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
@Component("jon.innovatrics.homework.taskmgr.implementation.DefaultTaskProcessMapFactory")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
final class DefaultTaskProcessMapFactory implements TaskProcessMapFactory {

    /**
     * {@inheritDoc}
     *
     * @param defaultTimeout {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    public TaskProcessMap newTaskProcessMap(long defaultTimeout) {
        return new DefaultTaskProcessMap(defaultTimeout);
    }
}
