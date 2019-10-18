/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr;

/**
 * A data object which contains results of task execution.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
public final class TaskResult {

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getExitCode()}.</p>
     */
    private final int exitCode;

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getExitMessage()}.</p>
     */
    private final String exitMessage;

    /**
     * Constructor for production use.
     *
     * @param exitCode the exit code of external OS process.
     * @param exitMessage the detail message usually accompanying the result
     * mostly for case the process ended with an error.
     *
     * @throws NullPointerException is thrown if the parameter <code>exitMessage</code> is the <code>null</code>.
     */
    public TaskResult(int exitCode, String exitMessage) {
        super();
        this.exitCode = exitCode;
        this.exitMessage = exitMessage;
    }

    /**
     * An exit code of external OS process.
     *
     * @return the exit code of external OS process.
     */
    public int getExitCode() {
        return exitCode;
    }

    /**
     * A detail message usually accompanying the result
     * mostly for case the process ended with an error.
     *
     * @return the detail message usually accompanying the result
     * mostly for case the process ended with an error.
     */
    public String getExitMessage() {
        return exitMessage;
    }
}
