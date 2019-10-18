/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr.implementation;

import jon.innovatrics.homework.cmdexec.ExternalProcess;
import jon.innovatrics.homework.tools.lang.EnumResult;
import jon.innovatrics.homework.tools.lang.ExitType;

/**
 * A mutable (not thread safe) part of the interface of the {@linkplain TaskProcess}.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
interface MutableTaskProcess extends ExternalProcess {

    /**
     * Destroys the <code>this</code> external OS process and forces
     * it to exit and then start a new external OS process with same
     * parameters.
     *
     * @return the {@linkplain ExitType#SUCCESS} if the operation was successful, the
     * {@linkplain ExitType#FAILURE} otherwise.
     *
     * @throws InterruptedException is thrown if the current thread was interrupted.
     */
    EnumResult<ExitType> restart() throws InterruptedException;
}
