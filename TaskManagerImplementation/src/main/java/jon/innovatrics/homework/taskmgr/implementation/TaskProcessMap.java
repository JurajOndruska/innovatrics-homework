/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr.implementation;

import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * An object which provides thread safe interface to the map
 * of {@linkplain TaskProcess} instances.
 *
 * <p>The map keys are strings (task identifiers) and values are process
 * instances. Note that {@linkplain TaskProcess} has partitioned its API
 * into with two separate interfaces ({@linkplain ImmutableTaskProcess},
 * {@linkplain MutableTaskProcess}). One provides access to the operations
 * which are thread safe the other which are not.</p>
 *
 * <p>The thread safe interfaces are exposed as an unmodifiable concurrent
 * map view {@linkplain #asImmutableMap()}. This map serves as fast access
 * for read operations and there is no need to take extra care with it.</p>
 *
 * <p>To modify the map content and to access the non thread safe API
 * of the task processes stored within <code>this</code> object. Following
 * methods available.</p>
 *
 * <ol>
 * <li>{@linkplain #executeForTaskId(String, TaskProcessMapAction)}</li>
 * <li>{@linkplain #executeForTaskId(String, TaskProcessMapTimeOutAction, long)}.</li>
 * </ol>
 *
 * <p>These methods accepts action objects ({@linkplain TaskProcessMapAction},
 * {@linkplain TaskProcessMapTimeOutAction} which are being executed in a thread
 * safe context on a map entry level.</p>
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
interface TaskProcessMap {

    /**
     * An unmodifiable concurrent map view of task processes currently
     * stored within <code>this</code> object.
     *
     * <p>Note that keys are task identifiers and values are immutable
     * interfaces of the associated task processes.</p>
     *
     * @return the unmodifiable concurrent map view of task processes currently
     * stored within <code>this</code> object.
     */
    Map<String, ImmutableTaskProcess> asImmutableMap();

    /**
     * An thread safe executor for the instances of {@linkplain TaskProcessMapAction}.
     *
     * @param taskId the identifier of a task process targeted by the <code>action</code>.
     * Note that there is not necessarily an existing entry with given key. But the thread
     * safe context will provide an exclusive lock for given key.
     * @param action the action to be executed.
     * @param <R> the generic method parameter which specifies the type of the action result.
     * @param <E> the generic method parameter which specifies the type of the exception
     * being thrown by the action.
     * @param <A> the generic method parameter which specifies the type of the action.
     *
     * @return the result computed by the action execution.
     *
     * @throws E is thrown if the exception is thrown during action execution.
     * @throws InterruptedException is thrown if the current thread was interrupted.
     * @throws TimeoutException is thrown if the acton did not execute in specified
     * time limit (system time limit).
     * @throws NullPointerException is thrown if the parameter <code>taskId</code> is the <code>null</code>.
     * @throws NullPointerException is thrown if the parameter <code>action</code> is the <code>null</code>.
     */
    <R, E extends Throwable, A extends TaskProcessMapAction<R, E>> R executeForTaskId(String taskId, A action)
        throws E, InterruptedException, TimeoutException;

    /**
     * An thread safe executor for the instances of {@linkplain TaskProcessMapTimeOutAction}.
     *
     * @param taskId the identifier of a task process targeted by the <code>action</code>.
     * Note that there is not necessarily an existing entry with given key. But the thread
     * safe context will provide an exclusive lock for given key.
     * @param action the action to be executed.
     * @param timeout timeout the maximum time to wait in milliseconds.
     * In case the parameter is an negative number it's handled in same
     * way as the zero (i.e. no wait).
     * @param <R> the generic method parameter which specifies the type of the action result.
     * @param <E> the generic method parameter which specifies the type of the exception
     * being thrown by the action.
     * @param <A> the generic method parameter which specifies the type of the action.
     *
     * @throws E is thrown if the exception is thrown during action execution.
     * @throws InterruptedException is thrown if the current thread was interrupted.
     * @throws TimeoutException is thrown if the acton did not execute in specified
     * time limit.
     * @throws NullPointerException is thrown if the parameter <code>taskId</code> is the <code>null</code>.
     * @throws NullPointerException is thrown if the parameter <code>action</code> is the <code>null</code>.
     */
    <R, E extends Throwable, A extends TaskProcessMapTimeOutAction<R, E>> R executeForTaskId(String taskId, A action,
                                                                                             long timeout)
        throws E, InterruptedException, TimeoutException;
}
