/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.cmdexec.implementation;

import org.apache.commons.exec.PumpStreamHandler;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * A custom override of the {@linkplain PumpStreamHandler}.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
final class FixedPumpStreamHandler extends PumpStreamHandler {

    /**
     * {@inheritDoc}
     *
     * @param outAndErr {@inheritDoc}
     */
    public FixedPumpStreamHandler(OutputStream outAndErr) {
        super(outAndErr);
    }

    /**
     * {@inheritDoc}
     *
     * @param is {@inheritDoc}
     * @param os {@inheritDoc}
     * @param closeWhenExhausted {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    protected Thread createPump(InputStream is, OutputStream os, boolean closeWhenExhausted) {
        final Thread result = new Thread(new FixedStreamPumper(is, os, closeWhenExhausted), "Exec Stream Pumper");
        result.setDaemon(true);
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param thread {@inheritDoc}
     * @param timeout {@inheritDoc}
     */
    @Override
    protected void stopThread(Thread thread, long timeout) {
        if (Objects.nonNull(thread)) {
            thread.interrupt();
        }
        super.stopThread(thread, timeout);
    }
}
