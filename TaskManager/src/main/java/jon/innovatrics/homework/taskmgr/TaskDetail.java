/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr;

import org.apache.commons.lang3.Validate;

import java.util.Objects;

/**
 * An immutable data object which contains the details (start parameters)
 * of the task handled by the {@linkplain TaskManager}.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
public final class TaskDetail {

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getCommand()}.</p>
     */
    private final String command;

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getDirectory()}.</p>
     */
    private final String directory;

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getDirectory()}.</p>
     */
    private final String name;

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getTaskType()}.</p>
     */
    private final TaskType taskType;

    /**
     * A constructor for production use.
     *
     * @param name the value for the property {@linkplain #getName()}.
     * @param command the value for the property {@linkplain #getCommand()}.
     * @param directory the value for the property {@linkplain #getDirectory()}.
     * @param taskType the value for the property {@linkplain #getTaskType()} .
     *
     * @throws NullPointerException is thrown if the parameter <code>name</code> is the <code>null</code>.
     * @throws IllegalArgumentException is thrown if the parameter <code>name</code> is blank.
     * @throws NullPointerException is thrown if the parameter <code>command</code> is the <code>null</code>.
     * @throws IllegalArgumentException is thrown if the parameter <code>command</code> is blank.
     * @throws NullPointerException is thrown if the parameter <code>directory</code> is the <code>null</code>.
     * @throws IllegalArgumentException is thrown if the parameter <code>directory</code> is blank.
     */
    public TaskDetail(String name, String command, String directory, TaskType taskType) {
        super();

        Objects.requireNonNull(name);
        Validate.notBlank(name);
        Objects.requireNonNull(command);
        Validate.notBlank(command);
        Objects.requireNonNull(directory);
        Validate.notBlank(directory);

        this.name = name;
        this.command = command;
        this.directory = directory;
        this.taskType = Objects.requireNonNull(taskType);
    }

    /**
     * A string which contains command which starts external OS process.
     *
     * @return the string which contains command which starts external OS process.
     */
    public String getCommand() {
        return command;
    }

    /**
     * A string which contains the working directory of the external OS process.
     *
     * @return the string which contains the working directory of the external OS process.
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * A name of the external OS process.
     *
     * @return the name of the external OS process.
     */
    public String getName() {
        return name;
    }

    /**
     * A type of the task which also serves as the hint for task manager
     * how to handle the task.
     *
     * @return type of the task which also serves as the hint for task manager
     * how to handle the task.
     */
    public TaskType getTaskType() {
        return taskType;
    }
}
