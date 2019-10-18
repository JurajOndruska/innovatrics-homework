/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr.implementation;

/**
 * A convenience shared object which generates new and globally
 * unique task identifiers.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
interface TaskIdGenerator {

    /**
     * Generates the new unique and not yet used task identifier.
     *
     * @return the new unique and not yet used task identifier.
     */
    String generateTaskId();
}
