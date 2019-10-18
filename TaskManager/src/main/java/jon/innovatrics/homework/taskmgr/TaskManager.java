/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr;

import jon.innovatrics.homework.tools.lang.EnumResult;
import jon.innovatrics.homework.tools.lang.MetaResult;

import java.util.Set;

/**
 * A service object which manages tasks associated with external OS processes.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
public interface TaskManager {

    /**
     * Cancels the task identified by the <code>taskId</code> and removes it from
     * <code>this</code> manager.
     *
     * <p>The success (or failure) of the operation is indicated by the enum constant
     * which could be queried by the method {@linkplain EnumResult#getEnumValue()} and
     * which could have following values.</p>
     *
     * <ol>
     * <li>{@linkplain TaskActionType#SUCCESS} - indicates that the operation was successful.</li>
     * <li>{@linkplain TaskActionType#FAILURE} - indicates that the operation was not successful.
     * In such case the method {@linkplain EnumResult#getTextMessage()} provides the description
     * what went wrong.</li>
     * <li>{@linkplain TaskActionType#TIMEOUT} - indicates that the operation was
     * not able to finish in specified time limit.</li>
     * <li>{@linkplain TaskActionType#INVALID_ID} - indicates that  there is currently
     * no task with given identifier registered within <code>this</code> manager.</li>
     * <li>{@linkplain TaskActionType#INTERRUPTED} - indicates that the operation
     * was interrupted by another thread.</li>
     * </ol>
     *
     * @param taskId the unique id under which the task is registered withing
     * <code>this</code> manager.
     * @param timeout timeout the maximum time to wait in milliseconds.
     * In case the parameter is an negative number it's handled in same
     * way as the zero (i.e. no wait).
     *
     * @return the instance {@linkplain EnumResult} which describes the operation result.
     *
     * @throws NullPointerException is thrown if the parameter <code>taskId</code> is the <code>null</code>.
     */
    EnumResult<TaskActionType> cancelTask(String taskId, long timeout);

    /**
     * Cancels the task identified by the <code>taskId</code> and removes it from
     * <code>this</code> manager.
     *
     * <p>The success (or failure) of the operation is indicated by the enum constant
     * which could be queried by the method {@linkplain EnumResult#getEnumValue()} and
     * which could have following values.</p>
     *
     * <ol>
     * <li>{@linkplain TaskActionType#SUCCESS} - indicates that the operation was successful.</li>
     * <li>{@linkplain TaskActionType#FAILURE} - indicates that the operation was not successful.
     * In such case the method {@linkplain EnumResult#getTextMessage()} provides the description
     * what went wrong.</li>
     * <li>{@linkplain TaskActionType#TIMEOUT} - indicates that the operation was
     * not able to finish in specified time limit (set be the system).</li>
     * <li>{@linkplain TaskActionType#INVALID_ID} - indicates that  there is currently
     * no task with given identifier registered within <code>this</code> manager.</li>
     * <li>{@linkplain TaskActionType#INTERRUPTED} - indicates that the operation
     * was interrupted by another thread.</li>
     * </ol>
     *
     * @param taskId the unique id under which the task is registered withing
     * <code>this</code> manager.
     *
     * @return the instance {@linkplain EnumResult} which describes the operation result.
     *
     * @throws NullPointerException is thrown if the parameter <code>taskId</code> is the <code>null</code>.
     */
    EnumResult<TaskActionType> cancelTask(String taskId);

    /**
     * An unmodifiable set of identifiers of tasks registered
     * within <code>this</code> task manager.
     *
     * @return the unmodifiable set of identifiers of tasks registered
     * within <code>this</code> task manager.
     */
    Set<String> getAllTaskIds();

    /**
     * Retrieves the task detail (start parameters) of the task identified
     * by the <code>taskId</code>.
     *
     * <p>The success (or failure) of the operation is indicated by the enum constant
     * which could be queried by the method {@linkplain MetaResult#getMetaId()} and
     * which could have following values.</p>
     *
     * <ol>
     * <li>{@linkplain TaskActionType#SUCCESS} - indicates that the operation was successful.
     * In such case the method {@linkplain MetaResult#getResult()} provides the access
     * to the task detail (task parameters).</li>
     * <li>{@linkplain TaskActionType#FAILURE} - indicates that the operation was not successful.
     * In such case the result value ({@linkplain MetaResult#getResult()}) is not set
     * and the method {@linkplain MetaResult#getMetaMessage()} provides the description
     * what went wrong.</li>
     * <li>{@linkplain TaskActionType#INVALID_ID} - indicates that  there is currently
     * no task with given identifier registered within <code>this</code> manager. Note that
     * in such case the result value ({@linkplain MetaResult#getResult()}) is not set.</li>
     * </ol>
     *
     * @param taskId the unique id under which the task is registered withing
     * <code>this</code> manager.
     *
     * @return the instance {@linkplain MetaResult} which describes the operation result.
     *
     * @throws NullPointerException is thrown if the parameter <code>taskId</code> is the <code>null</code>.
     */
    MetaResult<TaskDetail, TaskQueryType> getTaskDetail(String taskId);

    /**
     * Cancels the task identified by the <code>taskId</code> and removes it from
     * <code>this</code> manager.
     *
     * <p>The success (or failure) of the operation is indicated by the enum constant
     * which could be queried by the method {@linkplain EnumResult#getEnumValue()} and
     * which could have following values.</p>
     *
     * <ol>
     * <li>{@linkplain TaskActionType#SUCCESS} - indicates that the operation was successful.</li>
     * <li>{@linkplain TaskActionType#FAILURE} - indicates that the operation was not successful.
     * In such case the method {@linkplain EnumResult#getTextMessage()} provides the description
     * what went wrong.</li>
     * <li>{@linkplain TaskActionType#TIMEOUT} - indicates that the operation was
     * not able to finish in specified time limit (set be the system).</li>
     * <li>{@linkplain TaskActionType#INVALID_ID} - indicates that  there is currently
     * no task with given identifier registered within <code>this</code> manager.</li>
     * <li>{@linkplain TaskActionType#INTERRUPTED} - indicates that the operation
     * was interrupted by another thread.</li>
     * </ol>
     *
     * @param taskId the unique id under which the task is registered withing
     * <code>this</code> manager.
     *
     * @return the instance {@linkplain EnumResult} which describes the operation result.
     *
     * @throws NullPointerException is thrown if the parameter <code>taskId</code> is the <code>null</code>.
     * @throws InterruptedException is thrown if the current thread was interrupted.
     */
    EnumResult<TaskActionType> loudCancelTask(String taskId) throws InterruptedException;

    /**
     * Cancels the task identified by the <code>taskId</code> and removes it from
     * <code>this</code> manager.
     *
     * <p>The success (or failure) of the operation is indicated by the enum constant
     * which could be queried by the method {@linkplain EnumResult#getEnumValue()} and
     * which could have following values.</p>
     *
     * <ol>
     * <li>{@linkplain TaskActionType#SUCCESS} - indicates that the operation was successful.</li>
     * <li>{@linkplain TaskActionType#FAILURE} - indicates that the operation was not successful.
     * In such case the method {@linkplain EnumResult#getTextMessage()} provides the description
     * what went wrong.</li>
     * <li>{@linkplain TaskActionType#TIMEOUT} - indicates that the operation was
     * not able to finish in specified time limit.</li>
     * <li>{@linkplain TaskActionType#INVALID_ID} - indicates that  there is currently
     * no task with given identifier registered within <code>this</code> manager.</li>
     * <li>{@linkplain TaskActionType#INTERRUPTED} - indicates that the operation
     * was interrupted by another thread. Note that this method will never return such
     * result because it throws {@linkplain InterruptedException} instead.</li>
     * </ol>
     *
     * @param taskId the unique id under which the task is registered withing
     * <code>this</code> manager.
     * @param timeout timeout the maximum time to wait in milliseconds.
     * In case the parameter is an negative number it's handled in same
     * way as the zero (i.e. no wait).
     *
     * @return the instance {@linkplain EnumResult} which describes the operation result.
     *
     * @throws NullPointerException is thrown if the parameter <code>taskId</code> is the <code>null</code>.
     * @throws InterruptedException is thrown if the current thread was interrupted.
     * @throws InterruptedException is thrown if the current thread was interrupted.
     */
    EnumResult<TaskActionType> loudCancelTask(String taskId, long timeout) throws InterruptedException;

    /**
     * Submit a new task with details specified by the parameter <code>taskDetail</code>.
     *
     * <p>The success (or failure) of the operation is indicated by the enum constant
     * which could be queried by the method {@linkplain MetaResult#getMetaId()} and
     * which could have following values.</p>
     *
     * <ol>
     * <li>{@linkplain TaskActionType#SUCCESS} - indicates that the operation was successful.
     * In such case the method {@linkplain MetaResult#getResult()} provides the access
     * to the unique id under which the task is registered withing <code>this</code>
     * manager.</li>
     * <li>{@linkplain TaskActionType#FAILURE} - indicates that the operation was not successful.
     * In such case the result value ({@linkplain MetaResult#getResult()}) is not set
     * and the method {@linkplain MetaResult#getMetaMessage()} provides the description
     * what went wrong.</li>
     * <li>{@linkplain TaskActionType#TIMEOUT} - indicates that the operation was
     * not able to finish in specified time limit. In such case the result value
     * ({@linkplain MetaResult#getResult()}) is not set and the method
     * {@linkplain MetaResult#getMetaMessage()} provides the description what
     * went wrong.</li>
     * <li>{@linkplain TaskActionType#INVALID_ID} - indicates that task manager failed
     * to generate unique task id (should never happen).In such case the result value
     * ({@linkplain MetaResult#getResult()}) is not set and the method
     * {@linkplain MetaResult#getMetaMessage()} provides the description what
     * went wrong.</li>
     * <li>{@linkplain TaskActionType#INTERRUPTED} - indicates that the operation
     * was interrupted by another thread. Note that this method will never return such
     * result because it throws {@linkplain InterruptedException} instead.</li>
     * </ol>
     *
     * @param taskDetail the detail (start parameters) of the task being submitted
     * @param timeout timeout the maximum time to wait in milliseconds.
     * In case the parameter is an negative number it's handled in same
     * way as the zero (i.e. no wait).
     *
     * @return the instance {@linkplain MetaResult} which describes the operation result.
     *
     * @throws NullPointerException is thrown if the parameter <code>taskDetail</code> is
     * the <code>null</code>.
     * @throws InterruptedException is thrown if the current thread was interrupted.
     */
    MetaResult<String, TaskActionType> loudSubmitTask(TaskDetail taskDetail, long timeout) throws InterruptedException;

    /**
     * Submit a new task with details specified by the parameter <code>taskDetail</code>.
     *
     * <p>The success (or failure) of the operation is indicated by the enum constant
     * which could be queried by the method {@linkplain MetaResult#getMetaId()} and
     * which could have following values.</p>
     *
     * <ol>
     * <li>{@linkplain TaskActionType#SUCCESS} - indicates that the operation was successful.
     * In such case the method {@linkplain MetaResult#getResult()} provides the access
     * to the unique id under which the task is registered withing <code>this</code>
     * manager.</li>
     * <li>{@linkplain TaskActionType#FAILURE} - indicates that the operation was not successful.
     * In such case the result value ({@linkplain MetaResult#getResult()}) is not set
     * and the method {@linkplain MetaResult#getMetaMessage()} provides the description
     * what went wrong.</li>
     * <li>{@linkplain TaskActionType#TIMEOUT} - indicates that the operation was
     * not able to finish in specified time limit (a default system limit). In such
     * case the result value ({@linkplain MetaResult#getResult()}) is not set and the method
     * {@linkplain MetaResult#getMetaMessage()} provides the description what
     * went wrong.</li>
     * <li>{@linkplain TaskActionType#INVALID_ID} - indicates that task manager failed
     * to generate unique task id (should never happen).In such case the result value
     * ({@linkplain MetaResult#getResult()}) is not set and the method
     * {@linkplain MetaResult#getMetaMessage()} provides the description what
     * went wrong.</li>
     * <li>{@linkplain TaskActionType#INTERRUPTED} - indicates that the operation
     * was interrupted by another thread. Note that this method will never return such
     * result because it throws {@linkplain InterruptedException} instead.</li>
     * </ol>
     *
     * @param taskDetail the detail (start parameters) of the task being submitted
     *
     * @return the instance {@linkplain MetaResult} which describes the operation result.
     *
     * @throws NullPointerException is thrown if the parameter <code>taskDetail</code> is
     * the <code>null</code>.
     * @throws InterruptedException is thrown if the current thread was interrupted.
     */
    MetaResult<String, TaskActionType> loudSubmitTask(TaskDetail taskDetail) throws InterruptedException;

    /**
     * Submit a new task with details specified by the parameter <code>taskDetail</code>.
     *
     * <p>The success (or failure) of the operation is indicated by the enum constant
     * which could be queried by the method {@linkplain MetaResult#getMetaId()} and
     * which could have following values.</p>
     *
     * <ol>
     * <li>{@linkplain TaskActionType#SUCCESS} - indicates that the operation was successful.
     * In such case the method {@linkplain MetaResult#getResult()} provides the access
     * to the unique id under which the task is registered withing <code>this</code>
     * manager.</li>
     * <li>{@linkplain TaskActionType#FAILURE} - indicates that the operation was not successful.
     * In such case the result value ({@linkplain MetaResult#getResult()}) is not set
     * and the method {@linkplain MetaResult#getMetaMessage()} provides the description
     * what went wrong.</li>
     * <li>{@linkplain TaskActionType#TIMEOUT} - indicates that the operation was
     * not able to finish in specified time limit. In such case the result value
     * ({@linkplain MetaResult#getResult()}) is not set and the method
     * {@linkplain MetaResult#getMetaMessage()} provides the description what
     * went wrong.</li>
     * <li>{@linkplain TaskActionType#INVALID_ID} - indicates that task manager failed
     * to generate unique task id (should never happen).In such case the result value
     * ({@linkplain MetaResult#getResult()}) is not set and the method
     * {@linkplain MetaResult#getMetaMessage()} provides the description what
     * went wrong.</li>
     * <li>{@linkplain TaskActionType#INTERRUPTED} - indicates that the operation
     * was interrupted by another thread.</li>
     * </ol>
     *
     * @param taskDetail the detail (start parameters) of the task being submitted
     * @param timeout timeout the maximum time to wait in milliseconds.
     * In case the parameter is an negative number it's handled in same
     * way as the zero (i.e. no wait).
     *
     * @return the instance {@linkplain MetaResult} which describes the operation result.
     *
     * @throws NullPointerException is thrown if the parameter <code>taskDetail</code> is
     * the <code>null</code>.
     */
    MetaResult<String, TaskActionType> submitTask(TaskDetail taskDetail, long timeout);

    /**
     * Submit a new task with details specified by the parameter <code>taskDetail</code>.
     *
     * <p>The success (or failure) of the operation is indicated by the enum constant
     * which could be queried by the method {@linkplain MetaResult#getMetaId()} and
     * which could have following values.</p>
     *
     * <ol>
     * <li>{@linkplain TaskActionType#SUCCESS} - indicates that the operation was successful.
     * In such case the method {@linkplain MetaResult#getResult()} provides the access
     * to the unique id under which the task is registered withing <code>this</code>
     * manager.</li>
     * <li>{@linkplain TaskActionType#FAILURE} - indicates that the operation was not successful.
     * In such case the result value ({@linkplain MetaResult#getResult()}) is not set
     * and the method {@linkplain MetaResult#getMetaMessage()} provides the description
     * what went wrong.</li>
     * <li>{@linkplain TaskActionType#TIMEOUT} - indicates that the operation was
     * not able to finish in specified time limit (a default system limit). In such
     * case the result value ({@linkplain MetaResult#getResult()}) is not set and the method
     * {@linkplain MetaResult#getMetaMessage()} provides the description what
     * went wrong.</li>
     * <li>{@linkplain TaskActionType#INVALID_ID} - indicates that task manager failed
     * to generate unique task id (should never happen).In such case the result value
     * ({@linkplain MetaResult#getResult()}) is not set and the method
     * {@linkplain MetaResult#getMetaMessage()} provides the description what
     * went wrong.</li>
     * <li>{@linkplain TaskActionType#INTERRUPTED} - indicates that the operation
     * was interrupted by another thread.</li>
     * </ol>
     *
     * @param taskDetail the detail (start parameters) of the task being submitted
     *
     * @return the instance {@linkplain MetaResult} which describes the operation result.
     *
     * @throws NullPointerException is thrown if the parameter <code>taskDetail</code> is
     * the <code>null</code>.
     */
    MetaResult<String, TaskActionType> submitTask(TaskDetail taskDetail);
}
