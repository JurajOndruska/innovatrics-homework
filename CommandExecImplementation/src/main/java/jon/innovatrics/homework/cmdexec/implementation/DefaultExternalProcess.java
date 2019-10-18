/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.cmdexec.implementation;

import jon.innovatrics.homework.cmdexec.ExternalProcess;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * An implementation of the {@linkplain ExternalProcess} interface.
 *
 * <p>Note that this implementation uses Apache Commons Exec library.
 * The required operations are delegated to the functionality which
 * is provided by the internals which were provided during instance
 * construction. Note that the instances of this class should be created
 * only by the dedicated factory {@linkplain DefaultExternalProcessFactory}
 * which ensures that the internals used to construct the instance are
 * in correct states.</p>
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
final class DefaultExternalProcess implements ExternalProcess {

    /**
     * A static reference to the logger object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExternalProcess.class);

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getResultHandler()}.</p>
     */
    private final ExtendedResultHandler resultHandler;

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getWatchdog()}.</p>
     */
    private final ExecuteWatchdog watchdog;

    /**
     * A public constructor for production use.
     *
     * @param watchdog the value for the property {@linkplain #getWatchdog()}.
     * @param resultHandler the value for the property {@linkplain #getResultHandler()}.
     *
     * @throws NullPointerException is thrown if the parameter <code>watchdog</code> is the <code>null</code>.
     * @throws NullPointerException is thrown if the parameter <code>resultHandler</code> is the <code>null</code>.
     */
    public DefaultExternalProcess(ExecuteWatchdog watchdog, ExtendedResultHandler resultHandler) {
        super();
        this.watchdog = Objects.requireNonNull(watchdog);
        this.resultHandler = Objects.requireNonNull(resultHandler);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {

        /* First before we use watch we need to check if the process is running.
        This is because the process may have failed to start at all (for example
        when executing command) and the watchdog therefore also may not have been stared
        and the destroy operation will be blocked forever. */
        if (isRunning()) {
            getWatchdog().destroyProcess();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int getExitCode() {
        return getResultHandler().getExitValue();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public String getExitMessage() {
        if (Objects.nonNull(getResultHandler().getException())) {
            return String.valueOf(getResultHandler().getException().getMessage());
        }
        return StringUtils.EMPTY;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isFinished() {
        return getResultHandler().hasResult();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isRunning() {
        return BooleanUtils.isFalse(isFinished());
    }

    /**
     * {@inheritDoc}
     *
     * @param timeout {@inheritDoc}
     *
     * @throws InterruptedException
     */
    @Override
    public void loudWaitFor(long timeout) throws InterruptedException {
        if (isRunning()) {
            getResultHandler().waitFor(timeout > 0 ? timeout : 1);
        }
    }

    @Override
    public void loudWaitFor() throws InterruptedException {
        if (isRunning()) {
            getResultHandler().waitFor();
        }
    }

    /**
     * A reference to the dedicated {@linkplain ExtendedResultHandler} object
     * which is associated with the external OS process which is represented
     * by <code>this</code> instance.
     *
     * @return the reference to the dedicated {@linkplain ExtendedResultHandler} object
     * which is associated with the external OS process which is represented
     * by <code>this</code> instance.
     */
    private ExtendedResultHandler getResultHandler() {
        return resultHandler;
    }

    /**
     * A reference to the dedicated {@linkplain ExecuteWatchdog} object
     * which is associated with the external OS process which is represented
     * by <code>this</code> instance.
     *
     * @return the reference to the dedicated {@linkplain ExecuteWatchdog} object
     * which is associated with the external OS process which is represented
     * by <code>this</code> instance.
     */
    private ExecuteWatchdog getWatchdog() {
        return watchdog;
    }
}
