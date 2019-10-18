/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr.implementation;

import jon.innovatrics.homework.taskmgr.TaskDetail;

/**
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
interface TaskProcessFactory {

    TaskProcess newTaskProcess(TaskDetail taskDetail);
}
