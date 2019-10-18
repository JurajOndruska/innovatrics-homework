/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.cmdexec;

/**
 * An object which represents a single execution of an external OS process
 * started by this JVM.
 *
 * <p>In general <code>this</code> external OS process could be in two
 * states.</p>
 *
 * <ol>
 * <li>The external OS process is still running.</li>
 * <li>The external OS process is terminated (it exited).</li>
 * </ol>
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
public interface ExternalProcess {

    /**
     * Destroys the <code>this</code> external OS process and forces
     * it to exit.
     *
     * <p>Note that in case this method is called on already finished process
     * this method does nothing.</p>
     */
    void destroy();

    /**
     * An exit code returned by <code>this</code> external OS process
     * upon its exit (or termination).
     *
     * <p>Note that the exit code is platform dependent i.e. different
     * exit codes may have different interpretations (depending the process
     * or OS internals). Note also that this method could be invoked only
     * for finished processes (<code>this.isFinished() == true</code>). In
     * case this method is called on still running process it will throw
     * {@linkplain IllegalStateException}.</p>
     *
     * @return the exit code returned by <code>this</code> external OS process
     * upon its exit (or termination).
     *
     * @throws IllegalStateException is thrown if this method is called for still
     * running process <code>this.isRunning() == true</code>.
     */
    int getExitCode();

    /**
     * An accompanying text message for process exit code returned by
     * <code>this</code> external OS process upon its exit (or termination).
     *
     * <p>In most cases this method returns an empty string. The accompanying
     * message is usually available in case the exit of the process was not
     * correct (process was terminated, its execution failed etc.). Note also
     * that this method could be invoked only for finished processes
     * (<code>this.isFinished() == true</code>). In case this method is called on
     * still running process it will throw {@linkplain IllegalStateException}.</p>
     *
     * @return the accompanying text message for process exit code returned by
     * <code>this</code> external OS process upon its exit (or termination). Note
     * that the method result is never the <code>null</code> (there is no need for
     * the <code>null</code> handling)
     *
     * @throws IllegalStateException is thrown if this method is called for still
     * running process <code>this.isRunning() == true</code>.
     */
    String getExitMessage();

    /**
     * A boolean flag which indicates if <code>this</code> external
     * OS process is still running or not.
     *
     * @return the <code>true</code> if <code>this</code> external
     * OS process is still running; the <code>false</code> otherwise.
     */
    boolean isFinished();

    /**
     * A boolean flag which indicates if <code>this</code> external
     * OS process is still running or not.
     *
     * @return the <code>false</code> if <code>this</code> external
     * OS process is still running; the <code>true</code> otherwise.
     */
    boolean isRunning();

    /**
     * Causes the current thread to wait, if necessary, until the
     * process has terminated.
     *
     * <p>This method returns immediately if <code>this</code> the process
     * has already terminated. If <code>this</code> process has not yet terminated,
     * the calling thread will be blocked until the process exits.</p>
     *
     * @param timeout timeout the maximum time to wait in milliseconds.
     * In case the parameter is an negative number it's handled in same
     * way as the zero (i.e. no wait).
     *
     * @throws InterruptedException is thrown if the current thread was interrupted.
     */
    void loudWaitFor(long timeout) throws InterruptedException;

    /**
     * Causes the current thread to wait until the process has terminated
     * (or the internal time out limit was exceeded).
     *
     * <p>This method returns immediately if <code>this</code> the process
     * has already terminated. If <code>this</code> process has not yet terminated,
     * the calling thread will be blocked until the process exits.</p>
     *
     * @throws InterruptedException is thrown if the current thread was interrupted.
     */
    void loudWaitFor() throws InterruptedException;
}
