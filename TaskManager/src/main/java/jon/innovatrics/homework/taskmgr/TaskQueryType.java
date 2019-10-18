/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr;

import jon.innovatrics.homework.tools.lang.EnumResult;
import jon.innovatrics.homework.tools.lang.MetaResult;

/**
 * An dedicated enum which provides enum constants which indicates
 * the success or the failure of the task manager query operations which
 * returns the {@linkplain MetaResult} or {@linkplain EnumResult} objects.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
public enum TaskQueryType {

    /**
     * An constant which indicates that the operation was success.
     */
    SUCCESS,

    /**
     * An constant which indicates that the operation was failure.
     */
    FAILURE,

    /**
     * An constant which indicates that the operation targeted invalid task identifier.
     */
    INVALID_ID
}
