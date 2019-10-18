/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr.implementation;

import jon.innovatrics.homework.cmdexec.ExternalProcessFactory;
import jon.innovatrics.homework.taskmgr.TaskDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * A spring component which implements interface {@linkplain TaskProcessFactory}.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
@Component("jon.innovatrics.homework.taskmgr.implementation.DefaultTaskProcessFactory")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@ComponentScan(basePackageClasses = {ExternalProcessFactory.class})
final class DefaultTaskProcessFactory implements TaskProcessFactory {

    private final ExternalProcessFactory processFactory;

    @Autowired
    public DefaultTaskProcessFactory(ExternalProcessFactory processFactory) {
        this.processFactory = processFactory;
    }

    /**
     * {@inheritDoc}
     *
     * @param taskDetail {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public TaskProcess newTaskProcess(TaskDetail taskDetail) {

        Objects.requireNonNull(taskDetail);

        return new DefaultTaskProcess(getProcessFactory(), null, taskDetail);
    }

    private ExternalProcessFactory getProcessFactory() {
        return processFactory;
    }
}
