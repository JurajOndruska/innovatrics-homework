/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.cmdexec.implementation;

import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.ExecuteException;

import java.util.Objects;

/**
 * An implementation of the {@linkplain ExtendedResultHandler} interface.
 *
 * <p>Note: This implementation is just a wrapper class for the inner instance
 * of {@linkplain DefaultExecuteResultHandler} class to which it delegates its
 * operations.</p>
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
final class DefaultExtendedResultHandler implements ExtendedResultHandler {

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getDelegate()}.</p>
     */
    private final DefaultExecuteResultHandler delegate;

    /**
     * A default constructor (for production use).
     */
    public DefaultExtendedResultHandler() {
        this(new DefaultExecuteResultHandler());
    }

    /**
     * A package private constructor for unit test purposes.
     *
     * @param delegate the value for the property {@linkplain #getDelegate()}.
     *
     * @throws NullPointerException is thrown if the parameter <code>delegate</code> is the <code>null</code>.
     */
    DefaultExtendedResultHandler(DefaultExecuteResultHandler delegate) {
        super();
        this.delegate = Objects.requireNonNull(delegate);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Note: This is just delegation to the method
     * {@linkplain DefaultExecuteResultHandler#getException()}.</p>
     *
     * @return {@inheritDoc}
     */
    @Override
    public ExecuteException getException() {
        return getDelegate().getException();
    }

    /**
     * {@inheritDoc}
     *
     * <p>Note: This is just delegation to the method
     * {@linkplain DefaultExecuteResultHandler#getExitValue()}.</p>
     *
     * @return {@inheritDoc}
     */
    @Override
    public int getExitValue() {
        return getDelegate().getExitValue();
    }

    /**
     * {@inheritDoc}
     *
     * <p>Note: This is just delegation to the method
     * {@linkplain DefaultExecuteResultHandler#hasResult()}.</p>
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean hasResult() {
        return getDelegate().hasResult();
    }

    /**
     * {@inheritDoc}
     *
     * <p>Note: This is just delegation to the method
     * {@linkplain DefaultExecuteResultHandler#onProcessComplete(int)}.</p>
     *
     * @param exitValue {@inheritDoc}
     */
    @Override
    public void onProcessComplete(int exitValue) {
        getDelegate().onProcessComplete(exitValue);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Note: This is just delegation to the method
     * {@linkplain DefaultExecuteResultHandler#onProcessFailed(ExecuteException)}.</p>
     *
     * @param e {@inheritDoc}
     */
    @Override
    public void onProcessFailed(ExecuteException e) {
        getDelegate().onProcessFailed(e);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Note: This is just delegation to the method
     * {@linkplain DefaultExecuteResultHandler#waitFor()}.</p>
     *
     * @throws InterruptedException {@inheritDoc}
     */
    @Override
    public void waitFor() throws InterruptedException {
        getDelegate().waitFor();
    }

    /**
     * {@inheritDoc}
     *
     * <p>Note: This is just delegation to the method
     * {@linkplain DefaultExecuteResultHandler#waitFor(long)}.</p>
     *
     * @param timeout {@inheritDoc}
     *
     * @throws InterruptedException {@inheritDoc}
     */
    @Override
    public void waitFor(long timeout) throws InterruptedException {
        getDelegate().waitFor(timeout);
    }

    /**
     * A dedicated instance of {@linkplain DefaultExecuteResultHandler} wrapped
     * by <code>this</code> object.
     *
     * @return the dedicated instance of {@linkplain DefaultExecuteResultHandler} wrapped
     * by <code>this</code> object.
     */
    private DefaultExecuteResultHandler getDelegate() {
        return delegate;
    }
}
