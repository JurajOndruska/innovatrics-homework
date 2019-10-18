/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.taskmgr.implementation;

import jon.innovatrics.homework.cmdexec.ExternalProcess;
import jon.innovatrics.homework.cmdexec.ExternalProcessFactory;
import jon.innovatrics.homework.taskmgr.TaskDetail;
import jon.innovatrics.homework.taskmgr.TaskManager;
import jon.innovatrics.homework.tools.lang.EnumResult;
import jon.innovatrics.homework.tools.lang.EnumResultBuilder;
import jon.innovatrics.homework.tools.lang.ExitType;
import jon.innovatrics.homework.tools.lang.MetaResult;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * A default implementation of the internal object which represents
 * a task managed by the {@linkplain TaskManager}
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
final class DefaultTaskProcess implements ExternalProcess, TaskProcess {

    private final ExternalProcessFactory processFactory;

    private final TaskDetail taskDetail;

    private volatile ExternalProcess process;

    public DefaultTaskProcess(ExternalProcessFactory processFactory,
                              ExternalProcess process,
                              TaskDetail taskDetail) {
        this.processFactory = Objects.requireNonNull(processFactory);
        this.process = process;
        this.taskDetail = Objects.requireNonNull(taskDetail);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        getProcess().destroy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getExitCode() {
        return getProcess().getExitCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExitMessage() {
        return getProcess().getExitMessage();
    }

    public TaskDetail getTaskDetail() {
        return taskDetail;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFinished() {
        return getProcess().isFinished();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunning() {
        return getProcess().isRunning();
    }

    /**
     * {@inheritDoc}
     *
     * @param timeout {@inheritDoc}
     *
     * @throws InterruptedException {@inheritDoc}
     */
    @Override
    public void loudWaitFor(long timeout) throws InterruptedException {
        getProcess().loudWaitFor(timeout);
    }

    /**
     * {@inheritDoc}
     *
     * @throws InterruptedException {@inheritDoc}
     */
    @Override
    public void loudWaitFor() throws InterruptedException {
        getProcess().loudWaitFor();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     *
     * @throws InterruptedException {@inheritDoc}
     */
    @Override
    public EnumResult<ExitType> restart() throws InterruptedException {
        MetaResult<ExternalProcess, ExitType> process;
        EnumResultBuilder<ExitType> rb;

        rb = new EnumResultBuilder<>();
        if (Objects.nonNull(getProcess())) {
            destroy();
            loudWaitFor();
            if (isRunning()) {
                return rb.setEnumValue(ExitType.FAILURE)
                    .setTextMessage("Failed to stop previous process ")
                    .buildEnumResult();
            }
        }
        process = startExternalProcess();
        if (ExitType.SUCCESS.equals(process.getMetaId())) {
            setProcess(process.getResult());
            return rb.setEnumValue(ExitType.SUCCESS)
                .setTextMessage(StringUtils.EMPTY)
                .buildEnumResult();
        }
        return rb.setEnumValue(ExitType.FAILURE)
            .setTextMessage(process.getMetaMessage())
            .buildEnumResult();
    }

    private ExternalProcess getProcess() {
        return process;
    }

    private ExternalProcessFactory getProcessFactory() {
        return processFactory;
    }

    private void setProcess(ExternalProcess process) {
        this.process = process;
    }

    private MetaResult<ExternalProcess, ExitType> startExternalProcess(String command, String directory) {
        return getProcessFactory().startExternalProcess(getTaskDetail().getCommand(), getTaskDetail().getDirectory());
    }

    private MetaResult<ExternalProcess, ExitType> startExternalProcess() {
        return startExternalProcess(getTaskDetail().getCommand(), getTaskDetail().getDirectory());
    }
}
