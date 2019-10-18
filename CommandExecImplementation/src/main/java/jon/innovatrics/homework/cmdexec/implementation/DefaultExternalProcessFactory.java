/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.cmdexec.implementation;

import jon.innovatrics.homework.cmdexec.ExternalProcess;
import jon.innovatrics.homework.cmdexec.ExternalProcessFactory;
import jon.innovatrics.homework.tools.lang.ExitType;
import jon.innovatrics.homework.tools.lang.MetaResult;
import jon.innovatrics.homework.tools.lang.MetaResultBuilder;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import static org.apache.commons.exec.util.StringUtils.stringSubstitution;

/**
 * A default implementation of the interface {@linkplain ExternalProcessFactory}.
 *
 * <p>Note that this class is marked as spring component which is by default
 * a bean with singleton scope.</p>
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
@Component("jon.innovatrics.homework.manager.implementation.DefaultExternalProcessFactory")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
final class DefaultExternalProcessFactory implements ExternalProcessFactory {

    /**
     * A local string constant which contains the name of the substitution
     * property which contains the current working directory.
     */
    private static final String CURRENT_DIR_PROPERTY = "jon.current.dir";

    /**
     * A static reference to the logger object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExternalProcessFactory.class);

    /**
     * A local string constant which contains the prefix of the Java system
     * properties which are used for substitution.
     */
    private static final String SUBSTITUTION_PREFIX = "jon.cmdexec.subst.";

    /**
     * A local string constant which contains the name of the Java system
     * property which specifies the user's working directory.
     */
    private static final String USER_DIR_PROPERTY = "user.dir";

    /**
     * A local string constant which contains the name of the Java system
     * property which specifies the user's home directory.
     */
    private static final String USER_HOME_PROPERTY = "user.home";

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getProcessDestroyer()}.</p>
     */
    public final LocalProcessDestroyer processDestroyer;

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getExecutorFactory()}.</p>
     */
    private final ExecutorFactory executorFactory;

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getWatchdogFactory()}.</p>
     */
    @Value("#{systemProperties}")
    private final Properties properties;

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getResultHandlerFactory()}.</p>
     */
    private final ExtendedResultHandlerFactory resultHandlerFactory;

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getWatchdogFactory()}.</p>
     */
    private final ExecuteWatchdogFactory watchdogFactory;

    /**
     * A public constructor for production use.
     *
     * @param executorFactory the value for the property {@linkplain #getExecutorFactory()}.
     * @param watchdogFactory the value for the property {@linkplain #getWatchdogFactory()}.
     * @param resultHandlerFactory the value for the property {@linkplain #getResultHandlerFactory()}.
     * @param processDestroyer the value for the property {@linkplain #getProcessDestroyer()}.
     * @param properties the value for the property {@linkplain #getProperties()}.
     *
     * @throws NullPointerException is thrown if the parameter <code>executorFactory</code> is the <code>null</code>.
     */
    @Autowired
    public DefaultExternalProcessFactory(ExecutorFactory executorFactory,
                                         ExecuteWatchdogFactory watchdogFactory,
                                         ExtendedResultHandlerFactory resultHandlerFactory,
                                         LocalProcessDestroyer processDestroyer,
                                         @Value("#{systemProperties}") Properties properties) {
        super();
        this.executorFactory = Objects.requireNonNull(executorFactory);
        this.watchdogFactory = Objects.requireNonNull(watchdogFactory);
        this.resultHandlerFactory = Objects.requireNonNull(resultHandlerFactory);
        this.processDestroyer = Objects.requireNonNull(processDestroyer);
        this.properties = Objects.requireNonNull(properties);
    }

    /**
     * {@inheritDoc}
     *
     * @param command {@inheritDoc}
     * @param directory {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public MetaResult<ExternalProcess, ExitType> startExternalProcess(String command, String directory) {
        StringBuffer buffer;

        Objects.requireNonNull(command);
        Objects.requireNonNull(directory);

        buffer = stringSubstitution(directory, buildSubstitutionMap(), true);
        return startExternalProcess(command, Paths.get(buffer.toString()).normalize());
    }

    /**
     * {@inheritDoc}
     *
     * @param command {@inheritDoc}
     * @param directory {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public MetaResult<ExternalProcess, ExitType> startExternalProcess(String command, Path directory) {
        MetaResultBuilder<ExternalProcess, ExitType> builder;
        CommandLine commandLine;
        Executor executor;
        ExecuteWatchdog watchdog;
        ExtendedResultHandler resultHandler;
        ExternalProcess process;

        Objects.requireNonNull(command);
        Objects.requireNonNull(directory);

        builder = new MetaResultBuilder<>();

        try {
            commandLine = CommandLine.parse(command);
            commandLine.setSubstitutionMap(buildSubstitutionMap());
            watchdog = getWatchdogFactory().newExecuteWatchdog();
            resultHandler = getResultHandlerFactory().newExtendedResultHandler();

            executor = getExecutorFactory().newExecutor();
            executor.setWatchdog(watchdog);
            executor.setWorkingDirectory(directory.toFile());
            // Not daemon (not necessary to register)
            // executor.setProcessDestroyer(getProcessDestroyer());

            process = new DefaultExternalProcess(watchdog, resultHandler);
            executor.execute(commandLine, resultHandler);

            builder.setResult(process)
                .setMetaId(ExitType.SUCCESS)
                .setMetaMessage(StringUtils.EMPTY);
        } catch (Exception e) {
            LOGGER.error("Failed to start the external process!", e);
            builder.clearResult()
                .setMetaId(ExitType.FAILURE)
                .setMetaMessage(String.valueOf(e.getMessage()));
        }
        return builder.buildMetaResult();
    }

    /**
     * A convenience method which creates unmodifiable map which entries are supported
     * substitution pairs where key is the name of the substitution variable and the value
     * is the substituted value.
     *
     * @return the convenience method which creates unmodifiable map which entries are supported
     * substitution pairs where key is the name of the substitution variable and the value
     * is the substituted value.
     */
    private Map<String, String> buildSubstitutionMap() {

        /* Local variables */
        Map<String, String> resultMap;

        /* Create map instance */
        resultMap = new ConcurrentHashMap<>();

        /* Add properties with matching name prefix */
        getProperties().entrySet().stream()
            .filter(e -> Objects.nonNull(e.getKey()) && Objects.nonNull(e.getValue()))
            .filter(e -> e.getKey() instanceof String)
            .filter(e -> e.getValue() instanceof String)
            .filter(e -> String.valueOf(e.getKey()).startsWith(SUBSTITUTION_PREFIX))
            .forEach(e -> resultMap.put(String.valueOf(e.getKey()), String.valueOf(e.getValue())));

        /* Puts selected system properties */
        if (Objects.nonNull(getProperties().getProperty(USER_HOME_PROPERTY))) {
            resultMap.put(USER_HOME_PROPERTY, getProperties().getProperty(USER_HOME_PROPERTY));
        }
        if (Objects.nonNull(getProperties().getProperty(USER_DIR_PROPERTY))) {
            resultMap.put(USER_DIR_PROPERTY, getProperties().getProperty(USER_DIR_PROPERTY));
        }

        /* Calculate CURRENT_DIR_PROPERTY */
        resultMap.put(CURRENT_DIR_PROPERTY, Paths.get(".").toAbsolutePath().normalize().toString());

        /* Returns as un modifiable map */
        return Collections.unmodifiableMap(resultMap);
    }

    /**
     * A reference to the factory object which supplies the
     * new instances of the {@linkplain Executor} interface.
     *
     * @return the reference to the factory object which supplies the
     * new instances of the {@linkplain Executor} interface.
     */
    private ExecutorFactory getExecutorFactory() {
        return executorFactory;
    }

    private LocalProcessDestroyer getProcessDestroyer() {
        return processDestroyer;
    }

    /**
     * A local reference to the system properties.
     *
     * @return the local reference to the system properties.
     */
    private Properties getProperties() {
        return properties;
    }

    /**
     * A reference to the factory object which supplies the new
     * instances of the {@linkplain ExtendedResultHandler} interface.
     *
     * @return the reference to the factory object which supplies the
     * new instances of the {@linkplain ExtendedResultHandler} interface.
     */
    private ExtendedResultHandlerFactory getResultHandlerFactory() {
        return resultHandlerFactory;
    }

    /**
     * A reference to the factory object which supplies the new
     * instances of the {@linkplain ExecuteWatchdog} class.
     *
     * @return the reference to the factory object which supplies the
     * new instances of the {@linkplain ExecuteWatchdog} class.
     */
    private ExecuteWatchdogFactory getWatchdogFactory() {
        return watchdogFactory;
    }
}
