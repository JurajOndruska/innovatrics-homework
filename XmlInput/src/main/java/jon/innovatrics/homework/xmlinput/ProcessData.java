/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.xmlinput;

import org.apache.commons.lang3.Validate;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

/**
 * An immutable data object which supports XML serialization
 * and represents an inner element of an XML document.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
@XmlType(propOrder = {"command", "directory"})
@XmlAccessorType(XmlAccessType.FIELD)
public final class ProcessData {

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getCommand()}.</p>
     */
    @XmlElement(name = "command")
    private final String command;

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getDirectory()}.</p>
     */
    @XmlElement(name = "workdir")
    private final String directory;

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getDirectory()}.</p>
     */
    @XmlAttribute(name = "name")
    private final String name;

    /**
     * A constructor for production use.
     *
     * @param name the value for the property {@linkplain #getName()}.
     * @param command the value for the property {@linkplain #getCommand()}.
     * @param directory the value for the property {@linkplain #getDirectory()}.
     *
     * @throws NullPointerException is thrown if the parameter <code>name</code> is the <code>null</code>.
     * @throws IllegalArgumentException is thrown if the parameter <code>name</code> is blank.
     * @throws NullPointerException is thrown if the parameter <code>command</code> is the <code>null</code>.
     * @throws IllegalArgumentException is thrown if the parameter <code>command</code> is blank.
     * @throws NullPointerException is thrown if the parameter <code>directory</code> is the <code>null</code>.
     * @throws IllegalArgumentException is thrown if the parameter <code>directory</code> is blank.
     */
    public ProcessData(String name, String command, String directory) {
        super();

        Objects.requireNonNull(name);
        Validate.notBlank(name);
        Objects.requireNonNull(command);
        Validate.notBlank(command);
        Objects.requireNonNull(directory);
        Validate.notBlank(directory);

        this.name = name;
        this.command = command;
        this.directory = directory;
    }

    /**
     * A private constructor (used by JAXB).
     */
    private ProcessData() {
        super();

        this.name = null;
        this.command = null;
        this.directory = null;
    }

    /**
     * A string which contains command which starts external OS process.
     *
     * @return the string which contains command which starts external OS process.
     */
    public String getCommand() {
        return command;
    }

    /**
     * A string which contains the working directory of the external OS process.
     *
     * @return the string which contains the working directory of the external OS process.
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * A name of the external OS process.
     *
     * @return the name of the external OS process.
     */
    public String getName() {
        return name;
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
        Objects.requireNonNull(getName());
        Validate.notBlank(getName());
        Objects.requireNonNull(getCommand());
        Validate.notBlank(getCommand());
        Objects.requireNonNull(getDirectory());
        Validate.notBlank(getDirectory());
    }
}
