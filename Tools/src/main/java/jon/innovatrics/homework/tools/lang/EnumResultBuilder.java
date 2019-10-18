/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.tools.lang;

import java.util.Objects;

/**
 * A mutable builder object which simplify the construction of the instances
 * of the {@linkplain EnumResult} class.
 *
 * @param <E> the generic class parameter of enum type used by this data object..
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
public final class EnumResultBuilder<E extends Enum<E>> {

    /**
     * A private field with getter / setter.
     *
     * <p>For more details see the methods:
     * {@linkplain #getEnumValue()} and  {@linkplain #setEnumValue(Enum)}.</p>
     */
    private E enumValue;

    /**
     * A private field with getter / setter.
     *
     * <p>For more details see the methods:
     * {@linkplain #getTextMessage()} and {@linkplain #setTextMessage(CharSequence)}.</p>
     */
    private String textMessage;


    /**
     * A default public constructor.
     *
     * <ol>
     * <li>{@linkplain #getEnumValue()} is set to the <code>null</code>.</li>
     * <li>{@linkplain #getTextMessage()} is set to the <code>null</code>.</li>
     * </ol>
     */
    public EnumResultBuilder() {
        super();
        setEnumValue(null).setTextMessage(null);
    }

    /**
     * A copy constructor.
     *
     * <p>The builder instance is initialized with the same data as
     * the <code>builder</code>.</p>
     *
     * @param builder the reference to the other builder instance.
     *
     * @throws NullPointerException is thrown if the parameter <code>builder</code> is the <code>null</code>.
     */
    public EnumResultBuilder(EnumResultBuilder<E> builder) {
        super();
        Objects.requireNonNull(builder);
        setEnumValue(builder.getEnumValue());
        setTextMessage(builder.getTextMessage());
    }

    /**
     * A constructor.
     *
     * <p>The builder instance is initialized with the same data as
     * the <code>result</code>.</p>
     *
     * @param result the reference to the result instance.
     *
     * @throws NullPointerException is thrown if the parameter <code>result</code> is the <code>null</code>.
     */
    public EnumResultBuilder(EnumResult<E> result) {
        super();
        Objects.requireNonNull(result);
        setEnumValue(result.getEnumValue());
        setTextMessage(result.getTextMessage());
    }

    /**
     * Creates a new instance of {@linkplain EnumResult} initialized
     * according to the data currently stored in <code>this</code> builder.
     *
     * @return the new instance of {@linkplain EnumResult}
     */
    public EnumResult<E> buildEnumResult() {
        return new EnumResult<>(getEnumValue(), getTextMessage());
    }

    /**
     * A value which will be used to initialize {@linkplain EnumResult#getEnumValue()}.
     *
     * <p>Note: The value could be changed by the method {@linkplain #setEnumValue(Enum)}</p>
     *
     * @return the value which will be used to initialize {@linkplain EnumResult#getEnumValue()}.
     */
    public E getEnumValue() {
        return enumValue;
    }

    /**
     * A value which will be used to initialize {@linkplain EnumResult#getTextMessage()}.
     *
     * <p>Note: The value could be changed by the method {@linkplain #setTextMessage(CharSequence)}</p>
     *
     * @return the value which will be used to initialize {@linkplain EnumResult#getTextMessage()}.
     */
    public String getTextMessage() {
        return textMessage;
    }

    /**
     * A builder style setter which updates the value of the property
     * {@linkplain #getEnumValue()}.
     *
     * <p>Note that this method allows to set given property to the <code>null</code>
     * but also note that in such case the operation {@linkplain #buildEnumResult()}
     * would fail with an runtime exception.</p>
     *
     * @param enumValue the new value.
     *
     * @return the reference to <code>this</code> builder object.
     */
    public EnumResultBuilder<E> setEnumValue(E enumValue) {
        this.enumValue = enumValue;
        return this;
    }

    /**
     * A builder style setter which updates the value of the property
     * {@linkplain #getTextMessage()}.
     *
     * <p>Note that this method allows to set given property to the <code>null</code>
     * but also note that in such case the operation {@linkplain #buildEnumResult()}
     * would fail with an runtime exception.</p>
     *
     * @param textMessage the new value. Note that the type of the value is not
     * the {@linkplain String} but an {@linkplain CharSequence}. If the parameter
     * is not the <code>null</code> the property is initialized by the expression
     * <code>textMessage.toString()</code>.
     *
     * @return the reference to <code>this</code> builder object.
     */
    public EnumResultBuilder<E> setTextMessage(CharSequence textMessage) {
        this.textMessage = textMessage != null ? textMessage.toString() : null;
        return this;
    }
}
