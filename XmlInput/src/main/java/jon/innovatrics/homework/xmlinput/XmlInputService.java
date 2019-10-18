/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.xmlinput;

import jon.innovatrics.homework.tools.lang.ExitType;
import jon.innovatrics.homework.tools.lang.MetaResult;

import java.nio.file.Path;

/**
 * A simple service which provides basic operations related
 * to the processing of the XML input file consumed by the
 * command line application.
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
public interface XmlInputService {

    /**
     * Loads data from the XML file which location is specified by the parameter
     * <code>path</code> into an instance of the {@linkplain ProcessList} class.
     *
     * <p>The success (or failure) of the operation is indicated by the enum constant
     * which could be queried by the method {@linkplain MetaResult#getMetaId()} and
     * which could have following values.</p>
     *
     * <ol>
     * <li>{@linkplain ExitType#SUCCESS} - indicates that the operation was successful.
     * In such case the method {@linkplain MetaResult#getResult()} provides the access
     * to the {@linkplain ProcessList} data object with the data loaded from the XML file.</li>
     * <li>{@linkplain ExitType#FAILURE} - indicates that the operation was not successful.
     * In such case the result value ({@linkplain MetaResult#getResult()}) is not set
     * and the method {@linkplain MetaResult#getMetaMessage()} provides the description
     * what went wrong (file didn't exists, was not valid XML file, ... etc.).</li>
     * </ol>
     *
     * @param path the location of the XML file.
     *
     * @return the instance {@linkplain MetaResult} which describes the operation result.
     *
     * @throws NullPointerException is thrown if the parameter <code>path</code> is the <code>null</code>.
     */
    MetaResult<ProcessList, ExitType> loadXmlInput(Path path);
}
