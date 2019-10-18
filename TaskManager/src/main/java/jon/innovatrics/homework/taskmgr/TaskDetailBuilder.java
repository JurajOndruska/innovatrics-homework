/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr;

/**
 * A mutable builder object which simplify the construction of the instances
 * of the {@linkplain TaskDetail} class.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
public final class TaskDetailBuilder {

    /**
     * A private final field with getter / setter.
     *
     * <p>For more details see the method: {@linkplain #getCommand()}
     * and {@linkplain #setCommand(CharSequence)}.</p>
     */
    private String command;

    /**
     * A private final field with getter / setter.
     *
     * <p>For more details see the method: {@linkplain #getDirectory()}
     * and {@linkplain #setDirectory(CharSequence)}.</p>
     */
    private String directory;

    /**
     * A private final field with getter / setter.
     *
     * <p>For more details see the method: {@linkplain #getDirectory()}
     * and {@linkplain #setName(CharSequence)}.</p>
     */
    private String name;

    /**
     * A private final field with getter / setter.
     *
     * <p>For more details see the method: {@linkplain #getTaskType()}
     * and {@linkplain #setTaskType(TaskType)}.</p>
     */
    private TaskType taskType;

    /**
     * A default public constructor.
     *
     * <ol>
     * <li>{@linkplain #getCommand()} is set to the <code>null</code>.</li>
     * <li>{@linkplain #getName()} is set to the <code>null</code>.</li>
     * <li>{@linkplain #getTaskType()} is set to the <code>null</code>.</li>
     * <li>{@linkplain #getDirectory()} is set to the <code>null</code>.</li>
     * </ol>
     */
    public TaskDetailBuilder() {
        super();
        setCommand(null).setDirectory(null).setName(null).setTaskType(null);
    }

    /**
     * A copy constructor.
     *
     * <p>The builder instance is initialized with the same data as
     * the <code>builder</code>.</p>
     *
     * @param builder the reference to the other builder instance.
     *
     * @throws NullPointerException is thrown if the parameter <code>builder</code> is the <code>null</code>.
     */
    public TaskDetailBuilder(TaskDetailBuilder builder) {
        super();
        setName(builder.getName())
            .setTaskType(builder.getTaskType())
            .setDirectory(builder.getDirectory())
            .setCommand(builder.getCommand());
    }

    /**
     * A constructor.
     *
     * <p>The builder instance is initialized with the same data as
     * the <code>detail</code>.</p>
     *
     * @param detail the reference to the detail instance.
     *
     * @throws NullPointerException is thrown if the parameter <code>detail</code> is the <code>null</code>.
     */
    public TaskDetailBuilder(TaskDetail detail) {
        super();
        setName(detail.getName())
            .setTaskType(detail.getTaskType())
            .setDirectory(detail.getDirectory())
            .setCommand(detail.getCommand());
    }

    /**
     * Creates a new instance of {@linkplain TaskDetail} initialized
     * according to the data currently stored in <code>this</code> builder.
     *
     * @return the new instance of {@linkplain TaskDetail}
     */
    public TaskDetail buildTaskDetail() {
        return new TaskDetail(getName(), getCommand(), getDirectory(), getTaskType());
    }

    /**
     * A value which will be used to initialize {@linkplain TaskDetail#getCommand()}.
     *
     * <p>Note: The value could be changed by the method {@linkplain #setCommand(CharSequence)}.</p>
     *
     * @return the value which will be used to initialize {@linkplain TaskDetail#getCommand()}.
     */
    public String getCommand() {
        return command;
    }

    /**
     * A value which will be used to initialize {@linkplain TaskDetail#getDirectory()}.
     *
     * <p>Note: The value could be changed by the method {@linkplain #setDirectory(CharSequence)}.</p>
     *
     * @return the value which will be used to initialize {@linkplain TaskDetail#getDirectory()}.
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * A value which will be used to initialize {@linkplain TaskDetail#getName()}.
     *
     * <p>Note: The value could be changed by the method {@linkplain #setName(CharSequence)}.</p>
     *
     * @return the value which will be used to initialize {@linkplain TaskDetail#getName()}.
     */
    public String getName() {
        return name;
    }

    /**
     * A value which will be used to initialize {@linkplain TaskDetail#getName()}.
     *
     * <p>Note: The value could be changed by the method {@linkplain #setName(CharSequence)}.</p>
     *
     * @return the value which will be used to initialize {@linkplain TaskDetail#getName()}.
     */
    public TaskType getTaskType() {
        return taskType;
    }

    /**
     * A builder style setter which updates the value of the property
     * {@linkplain #getCommand()}.
     *
     * <p>Note that this method allows to set given property to the <code>null</code>
     * but also note that in such case the operation {@linkplain #buildTaskDetail()}
     * would fail with an runtime exception.</p>
     *
     * @param command the new value. Note that the type of the value is not
     * the {@linkplain String} but an {@linkplain CharSequence}. If the parameter
     * is not the <code>null</code> the property is initialized by the expression
     * <code>metaMessage.toString()</code>.
     *
     * @return the reference to <code>this</code> builder object.
     */
    public TaskDetailBuilder setCommand(CharSequence command) {
        this.command = command != null ? command.toString() : null;
        return this;
    }

    /**
     * A builder style setter which updates the value of the property
     * {@linkplain #getDirectory()}.
     *
     * <p>Note that this method allows to set given property to the <code>null</code>
     * but also note that in such case the operation {@linkplain #buildTaskDetail()}
     * would fail with an runtime exception.</p>
     *
     * @param directory the new value. Note that the type of the value is not
     * the {@linkplain String} but an {@linkplain CharSequence}. If the parameter
     * is not the <code>null</code> the property is initialized by the expression
     * <code>metaMessage.toString()</code>.
     *
     * @return the reference to <code>this</code> builder object.
     */
    public TaskDetailBuilder setDirectory(CharSequence directory) {
        this.directory = directory != null ? directory.toString() : null;
        return this;
    }

    /**
     * A builder style setter which updates the value of the property
     * {@linkplain #getName()}.
     *
     * <p>Note that this method allows to set given property to the <code>null</code>
     * but also note that in such case the operation {@linkplain #buildTaskDetail()}
     * would fail with an runtime exception.</p>
     *
     * @param name the new value. Note that the type of the value is not
     * the {@linkplain String} but an {@linkplain CharSequence}. If the parameter
     * is not the <code>null</code> the property is initialized by the expression
     * <code>metaMessage.toString()</code>.
     *
     * @return the reference to <code>this</code> builder object.
     */
    public TaskDetailBuilder setName(CharSequence name) {
        this.name = name != null ? name.toString() : null;
        return this;
    }

    /**
     * A builder style setter which updates the value of the property
     * {@linkplain #getName()}.
     *
     * <p>Note that this method allows to set given property to the <code>null</code>
     * but also note that in such case the operation {@linkplain #buildTaskDetail()}
     * would fail with an runtime exception.</p>
     *
     * @param taskType the new value. Note that the type of the value is not
     * the {@linkplain String} but an {@linkplain CharSequence}. If the parameter
     * is not the <code>null</code> the property is initialized by the expression
     * <code>metaMessage.toString()</code>.
     *
     * @return the reference to <code>this</code> builder object.
     */
    public TaskDetailBuilder setTaskType(TaskType taskType) {
        this.taskType = taskType;
        return this;
    }
}
