/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr.implementation;

import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * An action object which serves as input for the method
 * {@linkplain TaskProcessMap#executeForTaskId(String, TaskProcessMapAction)}.
 *
 * @param <R> the generic class parameter which specifies the type of the result
 * which is returned by this action.
 * @param <E> the generic class parameter of the exception which is being
 * thrown by this action.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
interface TaskProcessMapAction<R, E extends Throwable> {

    /**
     * The body of <code>this</code> action.
     *
     * @param mapView the un unmodifiable concurrent map view of task processes
     * currently stored within the {@linkplain TaskProcessMap} object i.e.
     * {@linkplain TaskProcessMap#asImmutableMap()}.
     * @param mapEntry the API object which provides access to the non
     * thread safe interface of the task process and also allows map
     * modifications on entry level.
     *
     * @return the result computed by <code>this</code> action execution.
     *
     * @throws E is thrown if the exception is thrown during action execution.
     * @throws TimeoutException is thrown if the acton did not execute in specified
     * time limit (system time limit).
     */
    R execute(Map<String, ImmutableTaskProcess> mapView, TaskProcessMapEntry mapEntry) throws E, TimeoutException;
}
