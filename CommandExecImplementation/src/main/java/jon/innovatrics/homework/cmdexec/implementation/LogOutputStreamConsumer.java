/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.cmdexec.implementation;

import org.apache.commons.exec.LogOutputStream;

/**
 * A helper class for consuming standard output and error output
 * from external processes.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
final class LogOutputStreamConsumer extends LogOutputStream {

    /**
     * {@inheritDoc}
     *
     * @param line {@inheritDoc}
     * @param logLevel {@inheritDoc}
     */
    @Override
    protected void processLine(String line, int logLevel) {
        /* Do nothing */
    }
}
