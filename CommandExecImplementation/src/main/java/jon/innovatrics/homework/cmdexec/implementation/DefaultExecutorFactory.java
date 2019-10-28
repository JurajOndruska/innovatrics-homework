/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.cmdexec.implementation;

import org.apache.commons.exec.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * A spring component which implements interface {@linkplain ExecutorFactory}.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
@Component("jon.innovatrics.homework.manager.implementation.DefaultExecutorFactory")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
final class DefaultExecutorFactory implements ExecutorFactory {

    /**
     * {@inheritDoc}
     *
     * <p>This method creates a new customized instance of the {@linkplain DefaultExecutor}
     * class. The customization ensures that standard output and standard error output
     * is silently consumed.</p>
     *
     * @return {@inheritDoc}
     */
    @Override
    public Executor newExecutor() {

        /* Local variables */
        Executor executor;

        /* The process output is redirected by using an instance of
        PumpStreamHandler which has set it's std-out, err-out streams
        to the null. In windows this works, but not sure about linux.
        On linux Probably dedicated ExecuteStreamHandler implementation
        would be needed which would consume the streams otherwise the
        processes may hang. */
        executor = new DefaultExecutor();
        executor.setStreamHandler(new FixedPumpStreamHandler(new LogOutputStreamConsumer()));
        return executor;
    }
}
