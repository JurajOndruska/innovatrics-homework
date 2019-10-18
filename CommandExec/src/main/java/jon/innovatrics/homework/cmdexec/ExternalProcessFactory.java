/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.cmdexec;

import jon.innovatrics.homework.tools.lang.ExitType;
import jon.innovatrics.homework.tools.lang.MetaResult;

import java.nio.file.Path;

/**
 * A dedicated public factory object which creates
 * the new instances of {@linkplain ExternalProcess} interface.
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
public interface ExternalProcessFactory {

    /**
     * Creates and starts external OS process.
     *
     * <p>The newly created / started external OS process is represented by
     * the dedicated instance of the interface {@linkplain ExternalProcess}.</p>
     *
     * <p>The success (or failure) of the operation is indicated by the enum constant
     * which could be queried by the method {@linkplain MetaResult#getMetaId()} and
     * which could have following values.</p>
     *
     * <ol>
     * <li>{@linkplain ExitType#SUCCESS} - indicates that the operation was successful.
     * In such case the method {@linkplain MetaResult#getResult()} provides the access
     * to the {@linkplain ExternalProcess} object which represents the newly created /
     * started external OS process.</li>
     * <li>{@linkplain ExitType#FAILURE} - indicates that the operation was not successful.
     * In such case the result value ({@linkplain MetaResult#getResult()}) is not set
     * and the method {@linkplain MetaResult#getMetaMessage()} provides the description
     * what went wrong (invalid command line, non existing working directory, ..).</li>
     * </ol>
     *
     * <p>Note that parameter <code>command</code> can also contain a substitution
     * variables which are substituted before the command will be executed.</p>
     *
     * <ol>
     * <li>Substitution variable <code>${user.dir}</code> will be substituted by the value
     * of the Java system property <code>"user.dir"</code>.</li>
     * <li>Substitution variable <code>${user.home}</code> will be substituted by the value
     * of the Java system property <code>"user.home"</code>.</li>
     * <li>Substitution variable <code>${jon.current.dir}</code> will be substituted by the value
     * name of the current Java process working directory.</li>
     * <li>Substitution variables with prefix <code>jon.cmdexec.subst.*</code> (i.e.
     * ${jon.cmdexec.subst.var1}, ${jon.cmdexec.subst.var2}, ...) will be substituted by
     * the corresponding values of the system properties with given name.</li>
     * </ol>
     *
     * @param command the string which contains the command which starts the new external OS process.
     * @param directory the path to the home directory of the new external OS process.
     *
     * @return the instance {@linkplain MetaResult} which describes the operation result.
     *
     * @throws NullPointerException is thrown if the parameter <code>command</code> is the <code>null</code>.
     * @throws NullPointerException is thrown if the parameter <code>directory</code> is the <code>null</code>.
     */
    MetaResult<ExternalProcess, ExitType> startExternalProcess(String command, Path directory);

    /**
     * Creates and starts external OS process.
     *
     * <p>The newly created / started external OS process is represented by
     * the dedicated instance of the interface {@linkplain ExternalProcess}.</p>
     *
     * <p>The success (or failure) of the operation is indicated by the enum constant
     * which could be queried by the method {@linkplain MetaResult#getMetaId()} and
     * which could have following values.</p>
     *
     * <ol>
     * <li>{@linkplain ExitType#SUCCESS} - indicates that the operation was successful.
     * In such case the method {@linkplain MetaResult#getResult()} provides the access
     * to the {@linkplain ExternalProcess} object which represents the newly created /
     * started external OS process.</li>
     * <li>{@linkplain ExitType#FAILURE} - indicates that the operation was not successful.
     * In such case the result value ({@linkplain MetaResult#getResult()}) is not set
     * and the method {@linkplain MetaResult#getMetaMessage()} provides the description
     * what went wrong (invalid command line, non existing working directory, ..).</li>
     * </ol>
     *
     * <p>Note that parameters <code>command</code> and <code>directory</code> can also
     * contain a substitution variables which are substituted before the command will be
     * executed.</p>
     *
     * <ol>
     * <li>Substitution variable <code>${user.dir}</code> will be substituted by the value
     * of the Java system property <code>"user.dir"</code>.</li>
     * <li>Substitution variable <code>${user.home}</code> will be substituted by the value
     * of the Java system property <code>"user.home"</code>.</li>
     * <li>Substitution variable <code>${jon.current.dir}</code> will be substituted by the value
     * name of the current Java process working directory.</li>
     * <li>Substitution variables with prefix <code>jon.cmdexec.subst.*</code> (i.e.
     * ${jon.cmdexec.subst.var1}, ${jon.cmdexec.subst.var2}, ...) will be substituted by
     * the corresponding values of the system properties with given name.</li>
     * </ol>
     *
     * @param command the string which contains the command which starts the new external OS process.
     * @param directory the path to the home directory of the new external OS process.
     *
     * @return the instance {@linkplain MetaResult} which describes the operation result.
     *
     * @throws NullPointerException is thrown if the parameter <code>command</code> is the <code>null</code>.
     * @throws NullPointerException is thrown if the parameter <code>directory</code> is the <code>null</code>.
     */
    MetaResult<ExternalProcess, ExitType> startExternalProcess(String command, String directory);
}
