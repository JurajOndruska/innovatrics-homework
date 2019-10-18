/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr.implementation;

import jon.innovatrics.homework.taskmgr.TaskActionType;
import jon.innovatrics.homework.taskmgr.TaskDetail;
import jon.innovatrics.homework.taskmgr.TaskManager;
import jon.innovatrics.homework.taskmgr.TaskManagerEventObserver;
import jon.innovatrics.homework.taskmgr.TaskQueryType;
import jon.innovatrics.homework.taskmgr.TaskResult;
import jon.innovatrics.homework.taskmgr.TaskType;
import jon.innovatrics.homework.tools.lang.EnumResult;
import jon.innovatrics.homework.tools.lang.EnumResultBuilder;
import jon.innovatrics.homework.tools.lang.ExitType;
import jon.innovatrics.homework.tools.lang.MetaResult;
import jon.innovatrics.homework.tools.lang.MetaResultBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

/**
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
final class DefaultTaskManager implements TaskManager {

    /**
     * A static reference to the logger object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTaskManager.class);

    private final long defaultTimeOut;

    private final TaskManagerEventObserver observer;

    private final TaskIdGenerator taskIdGenerator;

    private final TaskProcessFactory taskProcessFactory;

    private final TaskProcessMap taskProcessMap;

    private final TaskScheduler taskScheduler;

    public DefaultTaskManager(TaskProcessMap taskProcessMap,
                              TaskIdGenerator taskIdGenerator,
                              TaskProcessFactory taskProcessFactory,
                              TaskScheduler taskScheduler,
                              TaskManagerEventObserver observer,
                              long defaultTimeout) {
        super();
        Validate.isTrue(defaultTimeout > 0);
        this.defaultTimeOut = defaultTimeout;
        this.taskProcessMap = Objects.requireNonNull(taskProcessMap);
        this.taskIdGenerator = Objects.requireNonNull(taskIdGenerator);
        this.taskProcessFactory = Objects.requireNonNull(taskProcessFactory);
        this.taskScheduler = Objects.requireNonNull(taskScheduler);
        this.observer = Objects.requireNonNull(observer);
    }

    /**
     * {@inheritDoc}
     *
     * @param taskId {@inheritDoc}
     * @param timeout {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public EnumResult<TaskActionType> cancelTask(String taskId, long timeout) {

        /* Delegate functionality to the inner method which propagates
        unchecked exceptions.  */
        try {
            return loudCancelTask(taskId, timeout);
        } catch (InterruptedException e) {

            /* Handle thread interruption. The interruption is logged and
            special result notifying about this is returned as result. */
            LOGGER.error("cancelTask(" + taskId + "," + timeout + ")", e);
            return newEnumResultBuilder(TaskActionType.class)
                .setEnumValue(TaskActionType.INTERRUPTED)
                .setTextMessage(String.valueOf(e.getMessage()))
                .buildEnumResult();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param taskId {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public EnumResult<TaskActionType> cancelTask(String taskId) {
        return cancelTask(taskId, getDefaultTimeOut());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public Set<String> getAllTaskIds() {
        return getTaskProcessMap().asImmutableMap().keySet();
    }

    /**
     * {@inheritDoc}
     *
     * @param taskId {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public MetaResult<TaskDetail, TaskQueryType> getTaskDetail(String taskId) {

        MetaResultBuilder<TaskDetail, TaskQueryType> rb;
        ImmutableTaskProcess taskProcess;

        Objects.requireNonNull(taskId);

        rb = new MetaResultBuilder<>();
        taskProcess = getTaskProcessMap().asImmutableMap().get(taskId);

        if (Objects.isNull(taskProcess)) {
            return rb.clearResult()
                .setMetaId(TaskQueryType.INVALID_ID)
                .setMetaMessage("There is no task associated with the " + taskId + "!")
                .buildMetaResult();
        }

        return rb.setResult(taskProcess.getTaskDetail())
            .setMetaId(TaskQueryType.INVALID_ID)
            .setMetaMessage("There is no task associated with the " + taskId + "!")
            .buildMetaResult();
    }

    /**
     * {@inheritDoc}
     *
     * @param taskId {@inheritDoc}
     * @param timeout {@inheritDoc}
     *
     * @return {@inheritDoc}
     *
     * @throws InterruptedException {@inheritDoc}
     */
    @Override
    public EnumResult<TaskActionType> loudCancelTask(String taskId, long timeout) throws InterruptedException {

        Objects.requireNonNull(taskId);

        /* First quick check for the existence of the task process with given task id */
        if (not(getTaskProcessMap().asImmutableMap().containsKey(taskId))) {
            return newEnumResult(TaskActionType.class, TaskActionType.INVALID_ID);
        }

        /* Handle the cancellation of the task */
        try {

            return getTaskProcessMap().executeForTaskId(taskId, new TaskProcessMapTimeOutAction<EnumResult<TaskActionType>, Exception>() {
                @Override
                public EnumResult<TaskActionType> execute(Map<String, ImmutableTaskProcess> mapView,
                                                          TaskProcessMapEntry mapEntry, long timeLeft) throws Exception {

                    /* Local variables */
                    MutableTaskProcess process;

                    /* First safe check if the task is still present in the map */
                    if (not(mapEntry.isPresent())) {
                        return newEnumResult(TaskActionType.class, TaskActionType.INVALID_ID);
                    }

                    /* Destroy task process  */
                    process = mapEntry.asMutableTaskProcess();
                    process.destroy();
                    process.loudWaitFor(timeLeft);

                    /* If process is still running then we run of time (time out) */
                    if (not(process.isFinished())) {
                        return newEnumResult(TaskActionType.class, TaskActionType.TIMEOUT);
                    }

                    /* Cleanup task from the map and exit successfully */
                    mapEntry.remove();
                    return newEnumResult(TaskActionType.class, TaskActionType.SUCCESS);
                }
            }, getDefaultTimeOut());
        } catch (InterruptedException e) {

            /* Interruption is just rethrown */
            throw e;
        } catch (TimeoutException e) {

            /* Handle time out */
            LOGGER.error("loudCancelTask(TaskDetail taskDetail, long timeout)", e);
            return newEnumResult(TaskActionType.class, TaskActionType.TIMEOUT, String.valueOf(e.getMessage()));
        } catch (Exception e) {

            /* Handle unspecified errors */
            LOGGER.error("loudCancelTask(TaskDetail taskDetail, long timeout)", e);
            return newEnumResult(TaskActionType.class, TaskActionType.FAILURE, String.valueOf(e.getMessage()));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param taskId {@inheritDoc}
     *
     * @return {@inheritDoc}
     *
     * @throws {@inheritDoc}
     */
    @Override
    public EnumResult<TaskActionType> loudCancelTask(String taskId) throws InterruptedException {
        return loudCancelTask(taskId, getDefaultTimeOut());
    }

    /**
     * {@inheritDoc}
     *
     * @param taskDetail {@inheritDoc}
     * @param timeout {@inheritDoc}
     *
     * @return {@inheritDoc}
     *
     * @throws InterruptedException {@inheritDoc}
     */
    @Override
    public MetaResult<String, TaskActionType> loudSubmitTask(TaskDetail taskDetail, long timeout) throws InterruptedException {

        /* Local variables */
        String taskId;

        Objects.requireNonNull(taskDetail);

        /* Generate new task id */
        taskId = getTaskIdGenerator().generateTaskId();

        /* Handle the startup of the task */
        try {

            return getTaskProcessMap().executeForTaskId(taskId, new TaskProcessMapTimeOutAction<MetaResult<String, TaskActionType>, Exception>() {
                @Override
                public MetaResult<String, TaskActionType> execute(Map<String, ImmutableTaskProcess> mapView,
                                                                  TaskProcessMapEntry mapEntry, long timeLeft) throws Exception {

                    /* Local variables */
                    TaskProcess process;
                    EnumResult<ExitType> pr;

                    /* First safe check if the task is already still present in the map */
                    if (mapEntry.isPresent()) {
                        return newMetaResult(String.class, TaskActionType.class, TaskActionType.INVALID_ID,
                                             "Task with task id " + taskId + " is already present!");
                    }

                    /* Create task process and start it  */
                    process = getTaskProcessFactory().newTaskProcess(taskDetail);
                    pr = process.restart();

                    /* If process failed to start return error */
                    if (not(ExitType.SUCCESS.equals(pr.getEnumValue()))) {
                        return newMetaResult(String.class, TaskActionType.class, TaskActionType.FAILURE, pr.getTextMessage());
                    }

                    /* Add process to the map and return success */
                    mapEntry.replace(process);
                    return newMetaResult(String.class, TaskActionType.class, taskId, TaskActionType.SUCCESS, pr.getTextMessage());
                }
            }, getDefaultTimeOut());
        } catch (InterruptedException e) {

            /* Interruption is just rethrown */
            throw e;
        } catch (TimeoutException e) {
            LOGGER.error("loudSubmitTask(TaskDetail taskDetail, long timeout)", e);

            /* Handle time out */
            return newMetaResult(String.class, TaskActionType.class, TaskActionType.TIMEOUT, String.valueOf(e.getMessage()));
        } catch (Exception e) {
            LOGGER.error("loudSubmitTask(TaskDetail taskDetail, long timeout)", e);

            /* Handle unspecified errors */
            return newMetaResult(String.class, TaskActionType.class, TaskActionType.FAILURE, String.valueOf(e.getMessage()));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param taskDetail {@inheritDoc}
     *
     * @return {@inheritDoc}
     *
     * @throws InterruptedException {@inheritDoc}
     */
    @Override
    public MetaResult<String, TaskActionType> loudSubmitTask(TaskDetail taskDetail) throws InterruptedException {
        return loudSubmitTask(taskDetail, getDefaultTimeOut());
    }

    public void startWatchDog() {
        getTaskScheduler().scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Set<String> toTraverse;
                Set<String> toRestart;

                try {
                    toTraverse = ConcurrentHashMap.newKeySet();
                    toTraverse.addAll(getTaskProcessMap().asImmutableMap().keySet());
                    toRestart = ConcurrentHashMap.newKeySet();
                    for (String taskId : toTraverse) {
                        if (needRestart(taskId)) {
                            toRestart.add(taskId);
                        }
                    }
                    Thread.sleep(1000);
                    for (String taskId : toRestart) {
                        restart(taskId);
                    }
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
            }
        }, 1000);
    }

    /**
     * {@inheritDoc}
     *
     * @param taskDetail {@inheritDoc}
     * @param timeout {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public MetaResult<String, TaskActionType> submitTask(TaskDetail taskDetail, long timeout) {

        /* Delegate functionality to the inner method which propagates
        unchecked exceptions.  */
        try {
            return loudSubmitTask(taskDetail, timeout);
        } catch (InterruptedException e) {

            /* Handle thread interruption. The interruption is logged and
            special result notifying about this is returned as result. */
            LOGGER.error("submitTask(taskDetail," + timeout + ")", e);
            return newMetaResultBuilder(String.class, TaskActionType.class)
                .setMetaId(TaskActionType.INTERRUPTED)
                .setMetaMessage(String.valueOf(e.getMessage()))
                .buildMetaResult();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param taskDetail {@inheritDoc}
     *
     * @return {@inheritDoc}
     */

    @Override
    public MetaResult<String, TaskActionType> submitTask(TaskDetail taskDetail) {
        return submitTask(taskDetail, getDefaultTimeOut());
    }

    @Scheduled(fixedDelay = 1000)
    public void watchdog() {
        int x = 0;
        x++;
    }

    private TaskManagerEventObserver createRobustObserver() {
        return new TaskManagerEventObserver() {
            @Override
            public void onComplete(String taskId, TaskDetail taskDetail, TaskResult taskResult) throws Exception {
                try {
                    getObserver().onComplete(taskId, taskDetail, taskResult);
                } catch (Exception e) {
                    LOGGER.error("onComplete(String taskId, TaskDetail taskDetail, TaskResult taskResult)", e);
                }
            }

            @Override
            public void onRestart(String taskId, TaskDetail taskDetail) throws Exception {
                try {
                    getObserver().onRestart(taskId, taskDetail);
                } catch (Exception e) {
                    LOGGER.error("onRestart(String taskId, TaskDetail taskDetail)", e);
                }
            }
        };
    }

    private long getDefaultTimeOut() {
        return defaultTimeOut;
    }

    private TaskManagerEventObserver getObserver() {
        return observer;
    }

    private TaskIdGenerator getTaskIdGenerator() {
        return taskIdGenerator;
    }

    private TaskProcessFactory getTaskProcessFactory() {
        return taskProcessFactory;
    }

    private TaskProcessMap getTaskProcessMap() {
        return taskProcessMap;
    }

    private TaskScheduler getTaskScheduler() {
        return taskScheduler;
    }

    private boolean needRestart(String taskId) throws Exception {
        return getTaskProcessMap().executeForTaskId(taskId, new TaskProcessMapAction<Boolean, Exception>() {
            @Override
            public Boolean execute(Map<String, ImmutableTaskProcess> mapView, TaskProcessMapEntry mapEntry) throws Exception {
                MutableTaskProcess mtp;
                ImmutableTaskProcess itp;

                if (not(mapEntry.isPresent())) {
                    return false;
                } else if (not(mapEntry.asMutableTaskProcess().isFinished())) {
                    return false;
                }
                mtp = mapEntry.asMutableTaskProcess();
                itp = mapEntry.asImmutableTaskProcess();
                createRobustObserver().onComplete(taskId, itp.getTaskDetail(), new TaskResult(mtp.getExitCode(), mtp.getExitMessage()));
                if (TaskType.REPEATER.equals(itp.getTaskDetail().getTaskType())) {
                    return true;
                }
                mapEntry.remove();
                return false;
            }
        });
    }

    private <T extends Enum<T>> EnumResult<T> newEnumResult(Class<T> enumClass, T enumValue) {
        return newEnumResult(enumClass, enumValue, StringUtils.EMPTY);
    }

    private <T extends Enum<T>> EnumResult<T> newEnumResult(Class<T> enumClass, T enumValue, String message) {
        return newEnumResultBuilder(enumClass)
            .setEnumValue(enumValue)
            .setTextMessage(message)
            .buildEnumResult();
    }

    private <E extends Enum<E>> EnumResultBuilder<E> newEnumResultBuilder(Class<E> enumClass) {
        return new EnumResultBuilder<>();
    }

    private <R, T extends Enum<T>> MetaResult<R, T> newMetaResult(Class<R> resultClass, Class<T> enumClass, R result, T enumValue, String message) {
        return newMetaResultBuilder(resultClass, enumClass)
            .setResult(result)
            .setMetaId(enumValue)
            .setMetaMessage(message)
            .buildMetaResult();
    }

    private <R, T extends Enum<T>> MetaResult<R, T> newMetaResult(Class<R> resultClass, Class<T> enumClass, T enumValue, String message) {
        return newMetaResultBuilder(resultClass, enumClass)
            .clearResult()
            .setMetaId(enumValue)
            .setMetaMessage(message)
            .buildMetaResult();
    }

    private <R, E extends Enum<E>> MetaResultBuilder<R, E> newMetaResultBuilder(Class<R> resultClass, Class<E> enumClass) {
        return new MetaResultBuilder<>();
    }

    private boolean not(boolean booleanValue) {
        return !booleanValue;
    }

    private boolean restart(String taskId) throws Exception {
        return getTaskProcessMap().executeForTaskId(taskId, new TaskProcessMapAction<Boolean, Exception>() {
            @Override
            public Boolean execute(Map<String, ImmutableTaskProcess> mapView, TaskProcessMapEntry mapEntry) throws Exception {
                MutableTaskProcess mtp;
                ImmutableTaskProcess itp;

                if (not(mapEntry.isPresent())) {
                    return false;
                } else if (not(mapEntry.asMutableTaskProcess().isFinished())) {
                    return false;
                }
                itp = mapEntry.asImmutableTaskProcess();
                mapEntry.asMutableTaskProcess().restart();
                createRobustObserver().onRestart(taskId, itp.getTaskDetail());
                return false;
            }
        });
    }
}
