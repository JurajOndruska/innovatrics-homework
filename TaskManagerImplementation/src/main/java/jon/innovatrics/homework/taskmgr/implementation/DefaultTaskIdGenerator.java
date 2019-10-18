/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr.implementation;

import jon.innovatrics.homework.taskmgr.TaskManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A default implementation of the interface {@linkplain TaskManagerFactory}.
 *
 * <p>Note that this class is marked as spring component which is by default
 * a bean with singleton scope.</p>
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
@Component("jon.innovatrics.homework.taskmgr.implementation.DefaultTaskIdGenerator")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
final class DefaultTaskIdGenerator implements TaskIdGenerator {

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getAtomicLong()}.</p>
     */
    private final AtomicLong atomicLong;

    /**
     * A default constructor for production use.
     */
    @Autowired
    public DefaultTaskIdGenerator() {
        this(new AtomicLong());
    }

    /**
     * A package private constructor for unit test purposes.
     *
     * @param atomicLong the instance of atomic long which serves as seed value.
     */
    DefaultTaskIdGenerator(AtomicLong atomicLong) {
        super();
        this.atomicLong = Objects.requireNonNull(atomicLong);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public String generateTaskId() {
        return String.valueOf(getAtomicLong().incrementAndGet());
    }

    /**
     * An instance of atomic long which serves as seed value.
     *
     * @return the instance of atomic long which serves as seed value.
     */
    private AtomicLong getAtomicLong() {
        return atomicLong;
    }
}
