/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.cmdexec.implementation;

import org.apache.commons.exec.ExecuteWatchdog;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * A spring component which implements interface {@linkplain ExecutorFactory}.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
@Component("jon.innovatrics.homework.manager.implementation.DefaultExecuteWatchdogFactory")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
final class DefaultExecuteWatchdogFactory implements ExecuteWatchdogFactory {

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public ExecuteWatchdog newExecuteWatchdog() {
        return new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT);
    }
}
