/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr.implementation;

import java.util.Map;
import java.util.Objects;

/**
 * A default implementation of the interface {@linkplain TaskProcessMapEntry}.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
final class DefaultTaskProcessMapEntry implements TaskProcessMapEntry {

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getTaskId()}.</p>
     */
    private final String taskId;

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getTaskProcessMap()}</p>
     */
    private final Map<String, TaskProcess> taskProcessMap;

    /**
     * A constructor for production use.
     *
     * @param taskId the task identifier.
     * @param taskProcessMap the reference to the internal data map
     * of the {@linkplain TaskProcessMap} object.
     *
     * @throws NullPointerException is thrown if the parameter <code>taskId</code>
     * is the <code>null</code>.
     * @throws NullPointerException is thrown if the parameter <code>taskProcessMap</code>
     * is the <code>null</code>.
     */
    public DefaultTaskProcessMapEntry(String taskId, Map<String, TaskProcess> taskProcessMap) {
        super();
        this.taskId = Objects.requireNonNull(taskId);
        this.taskProcessMap = Objects.requireNonNull(taskProcessMap);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public ImmutableTaskProcess asImmutableTaskProcess() {
        if (isPresent()) {
            return getTaskProcessMap().get(getTaskId());
        }
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public MutableTaskProcess asMutableTaskProcess() {
        if (isPresent()) {
            return getTaskProcessMap().get(getTaskId());
        }
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public String getTaskId() {
        return taskId;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isPresent() {
        return getTaskProcessMap().containsKey(getTaskId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove() {
        getTaskProcessMap().remove(getTaskId());
    }

    /**
     * {@inheritDoc}
     *
     * @param taskProcess {@inheritDoc}
     */
    @Override
    public void replace(TaskProcess taskProcess) {
        Objects.requireNonNull(taskProcess);
        getTaskProcessMap().put(getTaskId(), taskProcess);
    }

    /**
     * A reference to the internal data map of the {@linkplain TaskProcessMap} object.
     *
     * @return th reference to the internal data map of the {@linkplain TaskProcessMap} object.
     */
    private Map<String, TaskProcess> getTaskProcessMap() {
        return taskProcessMap;
    }
}
