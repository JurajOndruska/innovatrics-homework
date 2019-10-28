/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.runner;

import jon.innovatrics.homework.taskmgr.TaskActionType;
import jon.innovatrics.homework.taskmgr.TaskDetail;
import jon.innovatrics.homework.taskmgr.TaskDetailBuilder;
import jon.innovatrics.homework.taskmgr.TaskManager;
import jon.innovatrics.homework.taskmgr.TaskManagerEventObserver;
import jon.innovatrics.homework.taskmgr.TaskManagerFactory;
import jon.innovatrics.homework.taskmgr.TaskResult;
import jon.innovatrics.homework.taskmgr.TaskType;
import jon.innovatrics.homework.tools.lang.EnumResult;
import jon.innovatrics.homework.tools.lang.ExitType;
import jon.innovatrics.homework.tools.lang.MetaResult;
import jon.innovatrics.homework.xmlinput.ProcessData;
import jon.innovatrics.homework.xmlinput.ProcessList;
import jon.innovatrics.homework.xmlinput.XmlInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

/**
 * A simple spring boot application application.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {RunnerApplication.class, XmlInputService.class, TaskManager.class})
public class RunnerApplication implements CommandLineRunner {

    private final InputStream standardInputStream;

    private final PrintStream standardOutputStream;

    @Autowired
    private final TaskManager taskManager;

    private final boolean windows;

    private final XmlInputService xmlInputService;

    @Autowired
    public RunnerApplication(@Qualifier("standardInputStream") InputStream standardInputStream,
                             @Qualifier("standardOutputStream") PrintStream standardOutputStream,
                             @Qualifier("TheTaskManager") TaskManager taskManager,
                             @Qualifier("IsOsWindows") boolean windows,
                             XmlInputService xmlInputService) {
        this.standardInputStream = Objects.requireNonNull(standardInputStream);
        this.standardOutputStream = Objects.requireNonNull(standardOutputStream);
        this.xmlInputService = Objects.requireNonNull(xmlInputService);
        this.taskManager = Objects.requireNonNull(taskManager);
        this.windows = windows;
    }

    @Bean("TheTaskManager")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public static TaskManager createTheTaskManager(TaskManagerFactory taskManagerFactory) {
        return taskManagerFactory.newTaskManager(new TaskManagerEventObserver() {
            @Override
            public void onComplete(String taskId, TaskDetail taskDetail, TaskResult taskResult) throws Exception {
                StringBuilder sb;

                sb = new StringBuilder();
                sb.append("Detected stopped task (taskId:  ").append(taskId)
                    .append("; name: ").append(taskDetail.getName())
                    .append("; exit code: ").append(taskResult.getExitCode())
                    .append("; exit message: ")
                    .append(taskResult.getExitMessage()).append(")");
                System.out.println(sb.toString());
            }

            @Override
            public void onRestart(String taskId, TaskDetail taskDetail) throws Exception {
                StringBuilder sb;

                sb = new StringBuilder();
                sb.append("Restarting stopped task (taskId:  ").append(taskId)
                    .append("; name: ").append(taskDetail.getName())
                    .append(")");
                System.out.println(sb.toString());
            }
        });
    }

    @Bean
    @Qualifier("IsOsWindows")
    public static boolean detectWindows() {
        return System.getProperty("os.name").toLowerCase().startsWith("windows");
    }

    public static void main(String[] args) {
        SpringApplication.run(RunnerApplication.class, args).close();
    }

    @Bean
    public static InputStream standardInputStream() {
        return System.in;
    }

    @Bean
    public static PrintStream standardOutputStream() {
        return System.out;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner;
        ProcessList xml;
        MetaResult<ProcessList, ExitType> xmlResult;
        EnumResult<TaskActionType> cancelResult;
        StringBuilder sb;

        sb = new StringBuilder();

        if (!isWindows()) {
            getStandardOutputStream().println("Unsupported OS environment - requires windows to run!");
            return;
        }

        /* Check argument */
        if (args.length != 1 || Objects.isNull(args[0])) {
            getStandardOutputStream().println("Application accepts only one parameter which is an XML file (EXIT)!");
            return;
        }

        /* Parse XML file and start tasks */
        xmlResult = getXmlInputService().loadXmlInput(Paths.get(args[0]));
        if (!ExitType.SUCCESS.equals(xmlResult.getMetaId())) {
            sb.setLength(0);
            sb.append("Failed to process XML file ").append(args[0]).append("! Error: ").append(xmlResult.getMetaMessage());
            getStandardOutputStream().println(sb.toString());
            return;
        }
        xml = xmlResult.getResult();
        for (ProcessData processData : xml.getProcesses()) {
            startTask(processData);
        }

        /* Now wait for user input to stop application  */
        getStandardOutputStream().print("Press ENTER to stop the application ...");
        scanner = new Scanner(getStandardInputStream());
        scanner.nextLine();

        /* Perform cleanup before close */
        sb = new StringBuilder();
        getStandardOutputStream().println("Cleanup Start ...");
        for (String taskId : getTaskManager().getAllTaskIds()) {
            cancelResult = getTaskManager().cancelTask(taskId);
            sb.setLength(0);
            sb.append("Cleanup (taskId: ").append(taskId)
                .append("; result: ").append(cancelResult.getEnumValue().toString())
                .append("; message: ").append(cancelResult.getTextMessage()).append(")");
            getStandardOutputStream().println(sb.toString());
        }
        getStandardOutputStream().println("Cleanup Done!");
    }

    private InputStream getStandardInputStream() {
        return standardInputStream;
    }

    private PrintStream getStandardOutputStream() {
        return standardOutputStream;
    }

    private TaskManager getTaskManager() {
        return taskManager;
    }

    private XmlInputService getXmlInputService() {
        return xmlInputService;
    }

    private boolean isWindows() {
        return windows;
    }

    private void startTask(ProcessData pd) throws InterruptedException {
        TaskDetailBuilder tdb;
        MetaResult<String, TaskActionType> sr;

        tdb = new TaskDetailBuilder();
        tdb.setName(pd.getName())
            .setCommand(pd.getCommand())
            .setDirectory(pd.getDirectory())
            .setTaskType(TaskType.REPEATER);

        System.out.println("Starting: " + tdb.getName());
        sr = getTaskManager().loudSubmitTask(tdb.buildTaskDetail(), Integer.MAX_VALUE);
    }
}
