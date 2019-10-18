/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.cmdexec.implementation;

/**
 * A dedicated package local factory object which creates
 * the new instances of {@linkplain ExtendedResultHandler} interface.
 *
 * <p>This package local interface is intended to have only a one dedicated
 * implementation which is a spring component {@linkplain DefaultExtendedResultHandlerFactory}.
 * Given component has an unique name (full classname) so this interface could be
 * used to auto-wire instances of given component via spring's component scan
 * without worries of possible name / type conflicts.</p>
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
interface ExtendedResultHandlerFactory {

    /**
     * Creates a new instance of the {@linkplain ExtendedResultHandler} interface.
     *
     * @return the new new instance of the {@linkplain ExtendedResultHandler} interface.
     */
    ExtendedResultHandler newExtendedResultHandler();
}
