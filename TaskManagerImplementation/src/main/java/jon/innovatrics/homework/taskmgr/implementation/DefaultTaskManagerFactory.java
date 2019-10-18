/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr.implementation;

import jon.innovatrics.homework.taskmgr.TaskDetail;
import jon.innovatrics.homework.taskmgr.TaskManager;
import jon.innovatrics.homework.taskmgr.TaskManagerEventObserver;
import jon.innovatrics.homework.taskmgr.TaskManagerFactory;
import jon.innovatrics.homework.taskmgr.TaskResult;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * A default implementation of the interface {@linkplain TaskManagerFactory}.
 *
 * <p>Note that this class is marked as spring component which is by default
 * a bean with singleton scope.</p>
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
@Component("jon.innovatrics.homework.taskmgr.implementation.DefaultTaskManagerFactory")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@EnableScheduling
final class DefaultTaskManagerFactory implements TaskManagerFactory {

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getDefaultTimeout()}.</p>
     */
    private final long defaultTimeout;

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getTaskIdGenerator()}.</p>
     */
    private final TaskIdGenerator taskIdGenerator;

    private final TaskProcessFactory taskProcessFactory;

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getTaskProcessMapFactory()}.</p>
     */
    private final TaskProcessMapFactory taskProcessMapFactory;

    private final TaskScheduler taskScheduler;

    /**
     * Constructor for production use.
     *
     * @param taskProcessMapFactory the value for the property {@linkplain #getTaskProcessMapFactory()}.
     * @param taskIdGenerator the value for the property {@linkplain #getTaskIdGenerator()}.
     * @param defaultTimeout the default system timeout limit.
     */
    @Autowired
    public DefaultTaskManagerFactory(TaskProcessMapFactory taskProcessMapFactory,
                                     TaskIdGenerator taskIdGenerator,
                                     TaskProcessFactory taskProcessFactory,
                                     TaskScheduler taskScheduler,
                                     @Value("3600000") long defaultTimeout) {
        super();
        this.taskProcessMapFactory = Objects.requireNonNull(taskProcessMapFactory);
        this.taskIdGenerator = Objects.requireNonNull(taskIdGenerator);
        Validate.isTrue(defaultTimeout > 0);
        this.defaultTimeout = defaultTimeout;
        this.taskProcessFactory = Objects.requireNonNull(taskProcessFactory);
        this.taskScheduler = Objects.requireNonNull(taskScheduler);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public TaskManager newTaskManager() {
        return newTaskManager(new TaskManagerEventObserver() {
            @Override
            public void onComplete(String taskId, TaskDetail taskDetail, TaskResult taskResult) throws Exception {

            }

            @Override
            public void onRestart(String taskId, TaskDetail taskDetail) throws Exception {

            }
        });
    }

    /**
     * {@inheritDoc}
     *
     * @param observer {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public TaskManager newTaskManager(TaskManagerEventObserver observer) {
        TaskProcessMap taskProcessMap;
        DefaultTaskManager taskManager;

        Objects.requireNonNull(observer);
        taskProcessMap = getTaskProcessMapFactory().newTaskProcessMap(getDefaultTimeout());
        taskManager = new DefaultTaskManager(taskProcessMap, getTaskIdGenerator(), getTaskProcessFactory(),
                                             getTaskScheduler(), observer, getDefaultTimeout());
        taskManager.startWatchDog();
        return taskManager;
    }

    private long getDefaultTimeout() {
        return defaultTimeout;
    }

    private TaskIdGenerator getTaskIdGenerator() {
        return taskIdGenerator;
    }

    private TaskProcessFactory getTaskProcessFactory() {
        return taskProcessFactory;
    }

    private TaskProcessMapFactory getTaskProcessMapFactory() {
        return taskProcessMapFactory;
    }

    private TaskScheduler getTaskScheduler() {
        return taskScheduler;
    }
}
