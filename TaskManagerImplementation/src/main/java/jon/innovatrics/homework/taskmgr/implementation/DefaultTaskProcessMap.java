/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr.implementation;

import org.apache.commons.lang3.Validate;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A default implementation of the
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
final class DefaultTaskProcessMap implements TaskProcessMap {

    private final long defaultTimeout;

    private final Map<String, ReentrantLock> entriesLocks;

    private final ReentrantLock mapLock;

    private final Map<String, TaskProcess> taskProcessMapData;

    private final Map<String, TaskProcess> taskProcessMapView;

    public DefaultTaskProcessMap(long defaultTimeout) {
        this.mapLock = new ReentrantLock();
        this.entriesLocks = new ConcurrentHashMap<>();
        this.taskProcessMapData = new ConcurrentHashMap<>();
        this.taskProcessMapView = Collections.unmodifiableMap(this.taskProcessMapData);
        Validate.isTrue(defaultTimeout > 0);
        this.defaultTimeout = defaultTimeout;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public Map<String, ImmutableTaskProcess> asImmutableMap() {
        return castMap(getTaskProcessMapView());
    }

    /**
     * {@inheritDoc}
     *
     * @param taskId {@inheritDoc}
     * @param action {@inheritDoc}
     * @param <R> {@inheritDoc}
     * @param <E> {@inheritDoc}
     * @param <A> {@inheritDoc}
     *
     * @return {@inheritDoc}
     *
     * @throws E {@inheritDoc}
     * @throws InterruptedException {@inheritDoc}
     * @throws TimeoutException {@inheritDoc}
     */
    @Override
    public <R, E extends Throwable, A extends TaskProcessMapAction<R, E>> R executeForTaskId(String taskId, A action)
        throws E, InterruptedException, TimeoutException {

        Objects.requireNonNull(taskId);
        Objects.requireNonNull(action);

        /* Delegate functionality */
        return executeForTaskId(taskId, new TaskProcessMapTimeOutAction<R, E>() {
            @Override
            public R execute(Map<String, ImmutableTaskProcess> mapView,
                             TaskProcessMapEntry mapEntry, long timeLeft) throws E, TimeoutException {
                if (timeLeft <= 0) {
                    throw new TimeoutException();
                }
                return action.execute(mapView, mapEntry);
            }
        }, getDefaultTimeout());
    }

    /**
     * {@inheritDoc}
     *
     * @param taskId {@inheritDoc}
     * @param action {@inheritDoc}
     * @param timeout {@inheritDoc}
     * @param <R> {@inheritDoc}
     * @param <E> {@inheritDoc}
     * @param <A> {@inheritDoc}
     *
     * @return {@inheritDoc}
     *
     * @throws E {@inheritDoc}
     * @throws InterruptedException {@inheritDoc}
     * @throws TimeoutException {@inheritDoc}
     */
    @Override
    public <R, E extends Throwable, A extends TaskProcessMapTimeOutAction<R, E>> R executeForTaskId(String taskId,
                                                                                                    A action,
                                                                                                    long timeout)
        throws E, InterruptedException, TimeoutException {

        /* Local variables */
        boolean lockAcquired;
        long timeoutAt;
        long timeLeft;

        /* Check contract */
        Objects.requireNonNull(taskId);
        Objects.requireNonNull(action);

        /* Calculate timestamp when timeout should occur */
        timeoutAt = getCurrentMillis() + (timeout >= 0 ? timeout : 0);

        /* Execute action in try finally block to ensure that if lock is acquired
        and the action execution fails then the lock is released */
        lockAcquired = false;
        try {
            lockAcquired = lockTaskId(taskId, timeout);
            if (lockAcquired) {
                timeLeft = timeoutAt - getCurrentMillis();
                return action.execute(asImmutableMap(), createTaskProcessMapEntry(taskId), timeLeft > 0 ? timeLeft : 0);
            }
            throw new TimeoutException();
        } finally {
            if (lockAcquired) {
                unlockEntry(taskId);
            }
        }
    }

    /**
     * A convenience method which cast one map type to another map type.
     *
     * @param map the target map.
     * @param <K1> the type of key in target type.
     * @param <V1> the type of value in target type.
     * @param <K2> the type of key in source map.
     * @param <V2> the type of value in source map.
     *
     * @return the <code>map</code> casted to the required type.
     */
    @SuppressWarnings("unchecked")
    private <K1, V1, K2, V2> Map<K1, V1> castMap(Map<K2, V2> map) {
        return (Map<K1, V1>) map;
    }

    private TaskProcessMapEntry createTaskProcessMapEntry(String taskId) {
        return new DefaultTaskProcessMapEntry(taskId, getTaskProcessMapData());
    }

    private long getCurrentMillis() {
        return System.currentTimeMillis();
    }

    private long getDefaultTimeout() {
        return defaultTimeout;
    }

    private Map<String, ReentrantLock> getEntriesLocks() {
        return entriesLocks;
    }

    private ReentrantLock getMapLock() {
        return mapLock;
    }

    private Map<String, TaskProcess> getTaskProcessMapData() {
        return taskProcessMapData;
    }

    private Map<String, TaskProcess> getTaskProcessMapView() {
        return taskProcessMapView;
    }

    private boolean lockMap(long timeout) throws InterruptedException {
        if (timeout > 0) {
            return getMapLock().tryLock(timeout, TimeUnit.MILLISECONDS);
        }
        return getMapLock().tryLock();
    }

    private boolean lockTaskId(String key, long timeout) throws InterruptedException {

        /* Local variables */
        long lmTimeout;
        long ltTimeout;
        ReentrantLock entryLock;
        long timeoutAt;

        /* Calculate timeout expiration timestamp */
        timeoutAt = getCurrentMillis() + timeout;

        /* First try to lock whole map because the entry lock may not have yet
        exist in the "entriesLocks" so we need to protect it in case we will have
        to add a new one. */
        lmTimeout = timeout;
        if (not(lockMap(lmTimeout))) {
            return false;
        }

        /* Now try to lock entry with given key */
        try {

            /* First check if the entry lock even exists, if not create one */
            entryLock = getEntriesLocks().computeIfAbsent(key, k -> new ReentrantLock());

            /* Now if we have some remaining time try to lock entry in good
            old time-out fashion */
            ltTimeout = timeoutAt - getCurrentMillis();
            if (timeout > 0 && ltTimeout > 0) {
                return entryLock.tryLock(ltTimeout, TimeUnit.MILLISECONDS);
            }

            /* If we do not have remaining time then just try locking without waiting */
            return entryLock.tryLock();
        } finally {

            /* We may have or not may have acquired lock for entry but now we
            must unlock the map so other threads could acquire locks for other
            entries */
            unlockMap();
        }
    }

    private boolean not(boolean booleanValue) {
        return !booleanValue;
    }

    private void unlockEntry(String key) {
        getEntriesLocks().get(key).unlock();
        getMapLock().lock();
        try {
            if (getEntriesLocks().containsKey(key)) {
                if (not(getEntriesLocks().get(key).isLocked()) && not(getTaskProcessMapData().containsKey(key))) {
                    getEntriesLocks().remove(key);
                }
            }
        } finally {
            getMapLock().unlock();
        }
    }

    private void unlockMap() {
        getMapLock().unlock();
    }

}
