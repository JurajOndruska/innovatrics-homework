/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.cmdexec.implementation;

import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;

/**
 * A dedicated package local extension of the {@linkplain ExecuteResultHandler}
 * interface.
 *
 * <p>This extension of {@linkplain ExecuteResultHandler} interface adds additional
 * convenience methods taken from the class {@linkplain DefaultExecuteResultHandler}.
 * Note that this interface only serves as wrapper for the instances of the
 * {@linkplain DefaultExecuteResultHandler} and is introduced only to lower the coupling
 * with the apache commons exec library (with this interface we don;t need to use
 * the {@linkplain DefaultExecuteResultHandler} directly).</p>
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
interface ExtendedResultHandler extends ExecuteResultHandler {

    /**
     * See {@linkplain DefaultExecuteResultHandler#getException()}
     *
     * @return see {@linkplain DefaultExecuteResultHandler#getException()}
     */
    ExecuteException getException();

    /**
     * See {@linkplain DefaultExecuteResultHandler#getException()}
     *
     * @return see {@linkplain DefaultExecuteResultHandler#getException()}
     */
    int getExitValue();

    /**
     * See {@linkplain DefaultExecuteResultHandler#getException()}
     *
     * @return see {@linkplain DefaultExecuteResultHandler#getException()}
     */
    boolean hasResult();

    /**
     * See {@linkplain DefaultExecuteResultHandler#getException()}
     *
     * @throws InterruptedException see {@linkplain DefaultExecuteResultHandler#getException()}
     */
    void waitFor() throws InterruptedException;

    /**
     * See {@linkplain DefaultExecuteResultHandler#getException()}
     *
     * @param timeout see {@linkplain DefaultExecuteResultHandler#getException()}
     *
     * @throws InterruptedException see {@linkplain DefaultExecuteResultHandler#getException()}
     */
    void waitFor(final long timeout) throws InterruptedException;
}
