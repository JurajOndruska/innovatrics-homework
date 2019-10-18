/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.xmlinput.implementation;

import jon.innovatrics.homework.tools.lang.ExitType;
import jon.innovatrics.homework.tools.lang.MetaResult;
import jon.innovatrics.homework.tools.lang.MetaResultBuilder;
import jon.innovatrics.homework.xmlinput.ProcessList;
import jon.innovatrics.homework.xmlinput.XmlInputService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.nio.file.Path;
import java.util.Objects;

/**
 * A default implementation of the interface {@linkplain XmlInputService}.
 *
 * <p>Note that this class is marked as spring component which is by default
 * a bean with singleton scope.</p>
 *
 * <p>Implementation note: This implementation is using a dirty solution
 * for creation of JAXB context associated with {@linkplain ProcessList}
 * which may be problematic in case the application would use custom class
 * loaders but in this case it's sufficient.</p>
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
@Component("jon.innovatrics.homework.xmlinput.implementation.DefaultXmlInputService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
final class DefaultXmlInputService implements XmlInputService {

    /**
     * A local instance of the {@linkplain Logger} object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultXmlInputService.class);

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getJaxbContext()}.</p>
     */
    private final JAXBContext jaxbContext;

    /**
     * A public default constructor.
     *
     * <p>Note that this constructor is used to create the bean
     * singleton instance during spring's classpath scan.</p>
     */
    @Autowired
    public DefaultXmlInputService() {
        this(newJAXBContext());
    }

    /**
     * A package local constructor for unit test purposes.
     *
     * @param jaxbContext the reference to the JAXB context.
     *
     * @throws NullPointerException is thrown if the parameter <code>jaxbContext</code> is the <code>null</code>.
     */
    DefaultXmlInputService(JAXBContext jaxbContext) {
        super();
        this.jaxbContext = Objects.requireNonNull(jaxbContext);
    }

    /**
     * {@inheritDoc}
     *
     * @param path {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public MetaResult<ProcessList, ExitType> loadXmlInput(Path path) {

        /* Local variables */
        MetaResultBuilder<ProcessList, ExitType> builder;
        Unmarshaller unmarshaller;

        /* Check contract */
        Objects.requireNonNull(path);

        /* Create & Initialize result builder */
        builder = new MetaResultBuilder<>();
        builder.setMetaId(ExitType.SUCCESS).setMetaMessage("");

        /* Perform operation */
        try {
            unmarshaller = getJaxbContext().createUnmarshaller();
            builder.setResult((ProcessList) unmarshaller.unmarshal(path.toFile()));
        } catch (Exception e) {
            LOGGER.error("Failed to load XML file " + path + "!", e);
            builder.clearResult()
                .setMetaId(ExitType.FAILURE)
                .setMetaMessage(String.valueOf(e.getMessage()));
        }

        /* Build and return result */
        return builder.buildMetaResult();
    }

    /**
     * A local static utility method which creates the {@linkplain JAXBContext}
     * instance associated with the class {@linkplain ProcessList}.
     *
     * @return the new {@linkplain JAXBContext} instance associated with the
     * class {@linkplain ProcessList}.
     *
     * @throws RuntimeException is thrown if the operation fails for any reason.
     */
    private static JAXBContext newJAXBContext() {
        try {
            return JAXBContext.newInstance(ProcessList.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A local reference to the {@linkplain JAXBContext}
     * associated with the class {@linkplain ProcessList}.
     *
     * @return the local reference to the {@linkplain JAXBContext}
     * associated with the class {@linkplain ProcessList}.
     */
    private JAXBContext getJaxbContext() {
        return jaxbContext;
    }
}
