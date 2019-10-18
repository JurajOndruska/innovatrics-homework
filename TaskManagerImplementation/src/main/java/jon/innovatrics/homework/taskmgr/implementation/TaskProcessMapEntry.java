/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr.implementation;

/**
 * An API object which provides access to the non thread safe interface
 * of the task process ({@linkplain MutableTaskProcess}) and also allows
 * the map ({@linkplain TaskProcessMap}) modifications on entry level.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
interface TaskProcessMapEntry {

    /**
     * An immutable (thread safe) part of the interface of the {@linkplain TaskProcess}
     * associated with the task id <code>taskId</code>.
     *
     * @return the immutable (thread safe) part of the interface of the {@linkplain TaskProcess}
     * associated with the task id <code>taskId</code>.
     *
     * @throws UnsupportedOperationException is thrown when <code>this.isPresent() == false</code>.
     */
    ImmutableTaskProcess asImmutableTaskProcess();

    /**
     * A mutable (not thread safe) part of the interface of the {@linkplain TaskProcess}
     * associated with the task id <code>taskId</code>.
     *
     * @return A mutable (not thread safe) part of the interface of the {@linkplain TaskProcess}
     * associated with the task id <code>taskId</code>.
     *
     * @throws UnsupportedOperationException is thrown when <code>this.isPresent() == false</code>.
     */
    MutableTaskProcess asMutableTaskProcess();

    /**
     * An identifier of the task entry.
     *
     * @return The identifier of the task entry.
     */
    String getTaskId();

    /**
     * A flag which indicates if the {@linkplain TaskProcessMap} has
     * an entry with the key equal tothe {@linkplain #getTaskId()}.
     *
     * @return the flag which indicates if the {@linkplain TaskProcessMap} has
     * an entry with the key equal to the {@linkplain #getTaskId()}.
     */
    boolean isPresent();

    /**
     * Removes the entry entry with key equal to the {@linkplain #getTaskId()}
     * or does nothing if there is no such entry.
     */
    void remove();

    /**
     * Replaces the task process associated with {@linkplain #getTaskId()}
     * or creates a new entry if there is no entry yet.
     *
     * @param taskProcess the new {@linkplain TaskProcess} to be associated
     * with the {@linkplain #getTaskId()}.
     *
     * @throws NullPointerException is thrown if the parameter <code>taskProcess</code> is the <code>null</code>.
     */
    void replace(TaskProcess taskProcess);
}
