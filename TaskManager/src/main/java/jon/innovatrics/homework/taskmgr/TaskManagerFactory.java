/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr;

/**
 * A dedicated public factory object which creates
 * the new instances of {@linkplain TaskManager} interface.
 *
 * <p>Note that this interface is meant to be a subject of spring's
 * classpath scan and it's backed by a dedicated implementation class.
 * Because of this it's not recommended to create any additional implementation
 * of this classes (and mark them for spring's component scan) because it
 * may produce name / type conflicts in spring's application context.</p>
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
public interface TaskManagerFactory {

    /**
     * Creates a new instance of the {@linkplain TaskManager}.
     *
     * @return the new instance of the {@linkplain TaskManager}.
     */
    TaskManager newTaskManager();

    /**
     * Creates a new instance of the {@linkplain TaskManager}.
     *
     * @param observer the observer object.
     *
     * @return the new instance of the {@linkplain TaskManager}.
     *
     * @throws NullPointerException is thrown if the parameter <code>observer</code> is the <code>null</code>.
     */
    TaskManager newTaskManager(TaskManagerEventObserver observer);
}
