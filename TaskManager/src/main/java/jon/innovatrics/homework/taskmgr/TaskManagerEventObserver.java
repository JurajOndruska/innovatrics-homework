/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr;

/**
 * An object which notify about the changes in {@linkplain TaskManager}.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
public interface TaskManagerEventObserver {

    /**
     * This method is triggered when {@linkplain TaskManager} detects
     * that the external OS processes associated with task finished it's
     * execution.
     *
     * @param taskId the identifier of the task.
     * @param taskDetail the detail (start parameters) of the task.
     * @param taskResult the data object with execution results.
     */
    void onComplete(String taskId, TaskDetail taskDetail, TaskResult taskResult) throws Exception;

    /**
     * This method is triggered when {@linkplain TaskManager} restarts
     * already finished job (see {@linkplain TaskType#REPEATER}).
     *
     * @param taskId the identifier of the task.
     * @param taskDetail the detail (start parameters) of the task.
     */
    void onRestart(String taskId, TaskDetail taskDetail) throws Exception;
}
