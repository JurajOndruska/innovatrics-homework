/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr.implementation;

import jon.innovatrics.homework.taskmgr.TaskDetail;

/**
 * An immutable (thread safe) part of the interface of the {@linkplain TaskProcess}.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
interface ImmutableTaskProcess {

    /**
     * An immutable data object which contains the details (start parameters)
     * of <code>this</code> task process.
     *
     * @return the immutable data object which contains the details (start parameters)
     * of <code>this</code> task process.
     */
    TaskDetail getTaskDetail();
}
