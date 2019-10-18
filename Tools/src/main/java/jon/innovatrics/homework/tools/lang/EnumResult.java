/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.tools.lang;

import java.util.Objects;

/**
 * A simple generic immutable data object which allows developers
 * to create operations which returns enum constants with accompanying
 * optional text message.
 *
 * <p>Note: To simplify creation of the instance of this class there is available
 * builder class {@linkplain EnumResultBuilder}.</p>
 *
 * @param <E> the generic class parameter of enum type used by this data object..
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
public final class EnumResult<E extends Enum<E>> {

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getEnumValue()}.</p>
     */
    private final E enumValue;

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getTextMessage()}.</p>
     */
    private final String textMessage;

    /**
     * A public constructor.
     *
     * @param enumValue the value for the property {@linkplain #getEnumValue()}.
     * @param textMessage the value for the property {@linkplain #getTextMessage()}.
     *
     * @throws NullPointerException is thrown if the parameter <code>enumValue</code> is the <code>null</code>.
     * @throws NullPointerException is thrown if the parameter <code>textMessage</code> is the <code>null</code>.
     */
    public EnumResult(E enumValue, String textMessage) {
        this(enumValue, textMessage, false);
    }


    /**
     * A package private constructor for unit test purposes.
     *
     * @param enumValue the value for the property {@linkplain #getEnumValue()}.
     * @param textMessage the value for the property {@linkplain #getTextMessage()}.
     * @param testFlag the additional parameter to distinguish signature of this unit test constructor.
     *
     * @throws NullPointerException is thrown if the parameter <code>enumValue</code> is the <code>null</code>.
     * @throws NullPointerException is thrown if the parameter <code>textMessage</code> is the <code>null</code>.
     */
    EnumResult(E enumValue, String textMessage, boolean testFlag) {
        super();
        this.enumValue = Objects.requireNonNull(enumValue);
        this.textMessage = Objects.requireNonNull(textMessage);
    }

    /**
     * An enum constant wrapped by <code>this</code> result object.
     *
     * <p>The enum constant may indicate for example success or failure.
     * The implementation of this class ensures that it's never the
     * <code>null</code>.</p>
     *
     * @return the enum constant wrapped by <code>this</code> result object.
     */
    public E getEnumValue() {
        return enumValue;
    }

    /**
     * An accompanying text description which provides more information
     * concerning {@linkplain #getEnumValue()}.
     *
     * <p>For example given message could be the description of the reason why
     * the operation failed. The implementation of this class ensures that it's
     * never the <code>null</code>.</p>
     *
     * @return accompanying text description which provides more information
     * concerning {@linkplain #getEnumValue()}.
     */
    public String getTextMessage() {
        return textMessage;
    }
}
