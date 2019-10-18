/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.xmlinput;

import org.apache.commons.lang3.Validate;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * An immutable data object which supports XML serialization
 * and represents root element of an XML document.
 *
 * <p>The XML sample of document:</p>
 *
 * <code><pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * &lt;processes&gt;
 *     &lt;process name=&quot;proc1&quot;&gt;
 *         &lt;workdir&gt;/home/jozef/proc1&lt;/workdir&gt;
 *         &lt;command&gt;greeter.sh -c 1&lt;/command&gt;
 *     &lt;/process&gt;
 *     &lt;process name=&quot;proc1&quot;&gt;
 *         &lt;workdir&gt;/home/jozef/proc1&lt;/workdir&gt;
 *         &lt;command&gt;greeter.sh -c 1&lt;/command&gt;
 *     &lt;/process&gt;
 * &lt;/processes&gt;
 * </pre></code>
 *
 * <p>This object of this <code>class</code> represents the root element
 * <code>&lt;processes /&gt;</code> which contains a list of sub-elements
 * <code>&lt;process /&gt; which are represented by the class instances
 * of the class {@linkplain ProcessData}.</code>
 * </p>
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
@XmlRootElement(name = "processes")
@XmlAccessorType(XmlAccessType.FIELD)
public final class ProcessList {

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getProcessesData()}.</p>
     */
    @XmlElement(name = "process")
    private final List<ProcessData> processesData;

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getProcessesView()}.</p>
     */
    @XmlTransient
    private final List<ProcessData> processesView;

    /**
     * A default constructor.
     */
    public ProcessList() {
        this(new ArrayList<>(), false);
    }

    /**
     * A constructor for production use.
     *
     * @param processes the content for the list property {@linkplain #getProcesses()}.
     *
     * @throws NullPointerException is thrown if the parameter <code>processes</code> is the <code>null</code>.
     * @throws IllegalArgumentException is thrown if the list <code>process</code> contains the <code>null</code> element.
     */
    public ProcessList(List<ProcessData> processes) {
        this(new ArrayList<>(processes), false);
    }

    /**
     * A package private constructor for unit test purposes.
     *
     * @param processes the value for the property {@linkplain #getProcesses()}.
     * @param testFlag the auxiliary boolean flag (to distinguish constructors).
     *
     * @throws NullPointerException is thrown if the parameter <code>processes</code> is the <code>null</code>.
     * @throws IllegalArgumentException is thrown if the list <code>process</code> contains the <code>null</code> element.
     */
    ProcessList(List<ProcessData> processes, boolean testFlag) {
        super();

        Objects.requireNonNull(processes);
        Validate.noNullElements(processes);

        this.processesData = Objects.requireNonNull(processes);
        this.processesView = Collections.unmodifiableList(this.processesData);
    }

    /**
     * An unmodifiable list of {@linkplain ProcessData} instances.
     *
     * @return the unmodifiable list of {@linkplain ProcessData} instances.
     */
    public List<ProcessData> getProcesses() {
        return getProcessesView();
    }

    /**
     * An event handler called after this object has been
     * initialized by XML data.
     *
     * <p>Note: We are using this method to add some basic validation
     * to prevent creation of invalid instances.</p>
     *
     * @param unmarshaller the reference to unmarshaller.
     * @param parent the reference to the parent object.
     */
    private void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
        Objects.requireNonNull(getProcessesData());
        Objects.requireNonNull(getProcessesView());
        Validate.noNullElements(getProcessesData());
        Validate.isTrue(getProcessesData().equals(getProcessesView()));
    }

    /**
     * An list of {@linkplain ProcessData} instances.
     *
     * @return the list of {@linkplain ProcessData} instances.
     */
    private List<ProcessData> getProcessesData() {
        return processesData;
    }

    /**
     * An unmodifiable list view of the {@linkplain #getProcessesData()}.
     *
     * <p>Note: This list reference is exposed by the public method
     * {@linkplain #getProcesses()}.</p>
     *
     * @return the unmodifiable list view of the {@linkplain #getProcessesData()}.
     */
    private List<ProcessData> getProcessesView() {
        return processesView;
    }
}
