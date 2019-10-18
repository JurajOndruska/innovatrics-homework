/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.tools.lang;

import org.apache.commons.lang3.BooleanUtils;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A mutable builder object which simplify the construction of the instances
 * of the {@linkplain MetaResult} class.
 *
 * @param <T> the generic class parameter which specifies the type
 * of the result value of the operation.
 * @param <E> the generic class parameter of enum which serves as meta
 * data descriptor of the result object.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
public final class MetaResultBuilder<T, E extends Enum<E>> {

    /**
     * A private field with getter / setter.
     *
     * <p>For more details see the methods:
     * {@linkplain #getMetaId()} and  {@linkplain #setMetaId(Enum)}.</p>
     */
    private E metaId;

    /**
     * A private field with getter / setter.
     *
     * <p>For more details see the methods:
     * {@linkplain #getMetaMessage()} and {@linkplain #setMetaMessage(CharSequence)}.</p>
     */
    private String metaMessage;

    /**
     * A private field with getter / setter.
     *
     * <p>For more details see the methods:
     * {@linkplain #getResultData()} and {@linkplain #setResultData(Object)}.</p>
     */
    private T resultData;

    /**
     * A private field with getter / setter.
     *
     * <p>For more details see the methods:
     * {@linkplain #isResultPresent()} and {@linkplain #setResultPresent(boolean)}.</p>
     */
    private boolean resultPresent;

    /**
     * A default public constructor.
     *
     * <ol>
     * <li>{@linkplain #isResult()} is set to the <code>false</code>.</li>
     * <li>{@linkplain #getMetaId()} is set to the <code>null</code>.</li>
     * <li>{@linkplain #getMetaMessage()} is set to the <code>null</code>.</li>
     * </ol>
     */
    public MetaResultBuilder() {
        super();
        clearResult().setMetaId(null).setMetaMessage(null);
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
    public MetaResultBuilder(MetaResultBuilder<T, E> builder) {
        super();
        Objects.requireNonNull(builder);
        setResultData(builder.getResultData());
        setResultPresent(builder.isResultPresent());
        setMetaId(builder.getMetaId());
        setMetaMessage(builder.getMetaMessage());
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
    public MetaResultBuilder(MetaResult<T, E> result) {
        super();
        Objects.requireNonNull(result);
        setResultData(result.isResult() ? result.getResult() : null);
        setResultPresent(result.isResult());
        setMetaId(result.getMetaId());
        setMetaMessage(result.getMetaMessage());
    }

    /**
     * Creates a new instance of {@linkplain MetaResult} initialized
     * according to the data currently stored in <code>this</code> builder.
     *
     * @return the new instance of {@linkplain MetaResult}
     */
    public MetaResult<T, E> buildMetaResult() {
        if (isResult()) {
            return new MetaResult<>(getResultData(), getMetaId(), getMetaMessage());
        }
        return new MetaResult<>(getMetaId(), getMetaMessage());
    }

    /**
     * A builder style method which clears the value of the property
     * {@linkplain #getResult()} and sets the the flag {@linkplain #isResult()}
     * to the <code>false</code>.</p>
     *
     * @return the reference to <code>this</code> builder object.
     */
    public MetaResultBuilder<T, E> clearResult() {
        setResultData(null);
        setResultPresent(false);
        return this;
    }

    /**
     * A value which will be used to initialize {@linkplain MetaResult#getMetaId()}.
     *
     * <p>Note: The value could be changed by the method {@linkplain #setMetaId(Enum)}</p>
     *
     * @return the value which will be used to initialize {@linkplain MetaResult#getMetaId()}.
     */
    public E getMetaId() {
        return metaId;
    }

    /**
     * A value which will be used to initialize {@linkplain MetaResult#getMetaMessage()}.
     *
     * <p>Note: The value could be changed by the method {@linkplain #setMetaMessage(CharSequence)}</p>
     *
     * @return the value which will be used to initialize {@linkplain MetaResult#getMetaMessage()}.
     */
    public String getMetaMessage() {
        return metaMessage;
    }

    /**
     * A value which will be used to initialize {@linkplain MetaResult#getResult()}.
     *
     * <p>Note: The value could be changed by the methods {@linkplain #setResult(Object)}
     * and {@linkplain #clearResult()}.</p>
     *
     * @return value which will be used to initialize {@linkplain MetaResult#getResult()}.
     *
     * @throws NoSuchElementException is thrown if the result value is not currently set
     * i.e. <code>this.isResult() == false</code>.
     */
    public T getResult() {
        if (isResult()) {
            return getResultData();
        }
        throw new NoSuchElementException("No result is present!");
    }

    /**
     * A flag which indicates if the result value ({@linkplain #getResult()}) is
     * currently set or not.
     *
     * <p>Note that this flag does not also indicate if the result value is the
     * <code>null</code> or not. The result value could be <code>null</code> because
     * it was set to the <code>null</code>.</p>
     *
     * @return the <code>false</code> if the result value is set; the <code>true</code> otherwise.
     */
    public boolean isNotResult() {
        return BooleanUtils.isFalse(isResult());
    }

    /**
     * A flag which indicates if the result value ({@linkplain #getResult()}) is
     * currently set or not.
     *
     * <p>Note that this flag does not also indicate if the result value is the
     * <code>null</code> or not. The result value could be <code>null</code> because
     * it was set to the <code>null</code>.</p>
     *
     * @return the <code>true</code> if the result value is set; the <code>false</code> otherwise.
     */
    public boolean isResult() {
        return isResultPresent();
    }

    /**
     * A builder style setter which updates the value of the property
     * {@linkplain #getMetaId()}.
     *
     * <p>Note that this method allows to set given property to the <code>null</code>
     * but also note that in such case the operation {@linkplain #buildMetaResult()}
     * would fail with an runtime exception.</p>
     *
     * @param metaId the new value.
     *
     * @return the reference to <code>this</code> builder object.
     */
    public MetaResultBuilder<T, E> setMetaId(E metaId) {
        this.metaId = metaId;
        return this;
    }

    /**
     * A builder style setter which updates the value of the property
     * {@linkplain #getMetaMessage()}.
     *
     * <p>Note that this method allows to set given property to the <code>null</code>
     * but also note that in such case the operation {@linkplain #buildMetaResult()}
     * would fail with an runtime exception.</p>
     *
     * @param metaMessage the new value. Note that the type of the value is not
     * the {@linkplain String} but an {@linkplain CharSequence}. If the parameter
     * is not the <code>null</code> the property is initialized by the expression
     * <code>metaMessage.toString()</code>.
     *
     * @return the reference to <code>this</code> builder object.
     */
    public MetaResultBuilder<T, E> setMetaMessage(CharSequence metaMessage) {
        this.metaMessage = metaMessage != null ? metaMessage.toString() : null;
        return this;
    }

    /**
     * A builder style setter which updates the value of the property
     * {@linkplain #getResult()}.
     *
     * <p>Note that this method also sets the flag {@linkplain #isResult()}
     * to the <code>true</code>.</p>
     *
     * @param result the new value.
     *
     * @return the reference to <code>this</code> builder object.
     */
    public MetaResultBuilder<T, E> setResult(T result) {
        setResultData(result);
        setResultPresent(true);
        return this;
    }

    /**
     * A private property which holds the actual value of
     * the {@linkplain #getResult()}.
     *
     * @return the private property which holds the actual value of
     * the {@linkplain #getResult()}.
     */
    private T getResultData() {
        return resultData;
    }

    /**
     * A private property which holds the actual value of
     * the flag {@linkplain #isResult()}.
     *
     * @return the private property which holds the actual value of
     * the flag {@linkplain #isResult()}.
     */
    private boolean isResultPresent() {
        return resultPresent;
    }

    /**
     * A private setter associated with the property {@linkplain #getResultData()}.
     *
     * @param resultData the new value.
     */
    private void setResultData(T resultData) {
        this.resultData = resultData;
    }

    /**
     * A private setter associated with the property {@linkplain #isResultPresent()}.
     *
     * @param resultPresent the new value.
     */
    private void setResultPresent(boolean resultPresent) {
        this.resultPresent = resultPresent;
    }
}
