/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.cmdexec.implementation;

import org.apache.commons.exec.StreamPumper;
import org.apache.commons.exec.util.DebugUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A custom version of {@linkplain StreamPumper} which handles thread interruption.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
final class FixedStreamPumper implements Runnable {

    /**
     * the default size of the internal buffer for copying the streams
     */
    private static final int DEFAULT_SIZE = 1024;

    /**
     * the input stream to pump from
     */
    private final InputStream is;

    /**
     * the output stream to pmp into
     */
    private final OutputStream os;

    /**
     * the size of the internal buffer for copying the streams
     */
    private final int size;

    /**
     * was the end of the stream reached
     */
    private boolean finished;

    /**
     * close the output stream when exhausted
     */
    private final boolean closeWhenExhausted;

    /**
     * Create a new stream pumper.
     *
     * @param is                 input stream to read data from
     * @param os                 output stream to write data to.
     * @param closeWhenExhausted if true, the output stream will be closed when the input is exhausted.
     */
    public FixedStreamPumper(final InputStream is, final OutputStream os,
                             final boolean closeWhenExhausted) {
        this.is = is;
        this.os = os;
        this.size = DEFAULT_SIZE;
        this.closeWhenExhausted = closeWhenExhausted;
    }

    /**
     * Create a new stream pumper.
     *
     * @param is                 input stream to read data from
     * @param os                 output stream to write data to.
     * @param closeWhenExhausted if true, the output stream will be closed when the input is exhausted.
     * @param size               the size of the internal buffer for copying the streams
     */
    public FixedStreamPumper(final InputStream is, final OutputStream os,
                             final boolean closeWhenExhausted, final int size) {
        this.is = is;
        this.os = os;
        this.size = size > 0 ? size : DEFAULT_SIZE;
        this.closeWhenExhausted = closeWhenExhausted;
    }

    /**
     * Create a new stream pumper.
     *
     * @param is input stream to read data from
     * @param os output stream to write data to.
     */
    public FixedStreamPumper(final InputStream is, final OutputStream os) {
        this(is, os, false);
    }

    /**
     * Copies data from the input stream to the output stream. Terminates as
     * soon as the input stream is closed or an error occurs.
     */
    public void run() {
        synchronized (this) {
            // Just in case this object is reused in the future
            finished = false;
        }

        final byte[] buf = new byte[this.size];

        int length;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                while (!Thread.currentThread().isInterrupted() && is.available() > 0 && (length = is.read(buf)) > 0) {
                    os.write(buf, 0, length);
                }
                Thread.sleep(1);
            }
        } catch (final Exception e) {
            // nothing to do - happens quite often with watchdog
        } finally {
            if (closeWhenExhausted) {
                try {
                    os.close();
                } catch (final IOException e) {
                    final String msg = "Got exception while closing exhausted output stream";
                    DebugUtils.handleException(msg, e);
                }
            }
            synchronized (this) {
                finished = true;
                notifyAll();
            }
        }
    }

    /**
     * Tells whether the end of the stream has been reached.
     *
     * @return true is the stream has been exhausted.
     */
    public synchronized boolean isFinished() {
        return finished;
    }

    /**
     * This method blocks until the stream pumper finishes.
     *
     * @throws InterruptedException if any thread interrupted the current thread before or while the current thread was waiting for a
     *                              notification.
     * @see #isFinished()
     */
    public synchronized void waitFor() throws InterruptedException {
        while (!isFinished()) {
            wait();
        }
    }
}
