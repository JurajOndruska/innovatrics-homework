/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr;

/**
 * An enum constants which describe the type of the jobs.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
public enum TaskType {

    /**
     * The task performs a one shot execution of the external OS process
     * and when it ends it's automatically discarded.
     *
     * <p>Note that the manager notifies about the fact that the task is finished via the
     * {@linkplain TaskManagerEventObserver#onComplete(String, TaskDetail, TaskResult)}.</p>
     */
    ONE_SHOT,

    /**
     * The task manager monitors given tasks and when it detects that task finished
     * it waits a specified delay and then it starts the task again.
     *
     * <p>Note that the manager notifies about the fact that the task is finished via the
     * observer ({@linkplain TaskManagerEventObserver#onComplete(String, TaskDetail, TaskResult)}).
     * And the fact that the job is being started again is notified also via the observer.
     * ({@linkplain TaskManagerEventObserver#onRestart(String, TaskDetail)}).</p>
     */
    REPEATER
}
