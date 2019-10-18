/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.cmdexec.implementation;

import org.apache.commons.exec.ProcessDestroyer;
import org.apache.commons.exec.ShutdownHookProcessDestroyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * A wrapper implementation of the {@linkplain LocalProcessDestroyer} interface.
 *
 * <p>This implementation delegates all its functionality to the inner delegate
 * which in production is an instance of the {@linkplain ShutdownHookProcessDestroyer}
 * class. This is to provide means how to inject the {@linkplain ShutdownHookProcessDestroyer}
 * instance into the spring's application context as singleton bean (via component scan).</p>
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
@Component("jon.innovatrics.homework.manager.implementation.DefaultProcessDestroyer")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
final class DefaultLocalProcessDestroyer implements LocalProcessDestroyer {

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getDelegate()}.</p>
     */
    private final ProcessDestroyer delegate;

    /**
     * A default public constructor.
     */
    @Autowired
    public DefaultLocalProcessDestroyer() {
        this(new ShutdownHookProcessDestroyer());
    }

    /**
     * Package private constructor for unit test purposes.
     *
     * @param delegate the reference to the delegate.
     *
     * @throws NullPointerException is thrown if the parameter <code>delegate</code> is the <code>null</code>.
     */
    DefaultLocalProcessDestroyer(ProcessDestroyer delegate) {
        super();
        this.delegate = Objects.requireNonNull(delegate);
    }

    /**
     * {@inheritDoc}
     *
     * @param process {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean add(Process process) {
        return getDelegate().add(process);
    }

    /**
     * {@inheritDoc}
     *
     * @param process {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean remove(Process process) {
        return getDelegate().remove(process);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int size() {
        return getDelegate().size();
    }

    /**
     * A reference to {@linkplain ProcessDestroyer} object to which
     * <code>this</code> wrapper object delegates it's functionality.
     *
     * <p>Note: In production the returned object is an instance of the
     * {@linkplain ShutdownHookProcessDestroyer} class.</p>
     *
     * @return the reference to {@linkplain ProcessDestroyer} object to which
     * <code>this</code> wrapper object delegates it's functionality.
     */
    private ProcessDestroyer getDelegate() {
        return delegate;
    }
}
