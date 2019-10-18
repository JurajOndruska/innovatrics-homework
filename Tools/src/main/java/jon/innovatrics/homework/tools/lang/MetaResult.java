/*
 * Copyright (c) 2019 Juraj Ondruska (juraj.ondr@gmail.com) to Present
 * All rights reserved. Use is subject to license terms.
 */

package jon.innovatrics.homework.tools.lang;

import org.apache.commons.lang3.BooleanUtils;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A simple generic &quot;immutable&quot; data object which allows developers
 * to create operations with self-describing results.
 *
 * <p>Note that this class would be immutable only in case if also the generic
 * type specified by the generic class parameter <code>T</code> is immutable.</p>
 *
 * <p>Note: To simplify creation of the instance of this class there is available
 * builder class {@linkplain MetaResultBuilder}.</p>
 *
 * @param <T> the generic class parameter which specifies the type
 * of the result value of the operation.
 * @param <E> the generic class parameter of enum which serves as meta
 * data descriptor of the result object.
 *
 * @author Juraj Ondruska (juraj.ondr@gmail.com)
 * @since 1.0.0
 */
public final class MetaResult<T, E extends Enum<E>> {

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getMetaId()}.</p>
     */
    private final E metaId;

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getMetaMessage()}.</p>
     */
    private final String metaMessage;

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #getResultData()}.</p>
     */
    private final T resultData;

    /**
     * A private final field with getter.
     *
     * <p>For more details see the method: {@linkplain #isResultPresent()}</p>
     */
    private final boolean resultPresent;

    /**
     * A public constructor.
     *
     * <p>Note: The flag {@linkplain #isResult()} is set to the <code>true</code>.</p>
     *
     * @param result the value for the property {@linkplain #getResult()}.
     * @param metaId the value for the property {@linkplain #getMetaId()}.
     * @param metaMessage the value for the property {@linkplain #getMetaMessage()}.
     *
     * @throws NullPointerException is thrown if the parameter <code>metaId</code> is the <code>null</code>.
     * @throws NullPointerException is thrown if the parameter <code>metaMessage</code> is the <code>null</code>.
     */
    public MetaResult(T result, E metaId, String metaMessage) {
        this(result, true, metaId, metaMessage);
    }

    /**
     * A public constructor.
     *
     * <p>Note: The flag {@linkplain #isResult()} is set to the <code>false</code>.</p>
     *
     * @param metaId the value for the property {@linkplain #getMetaId()}.
     * @param metaMessage the value for the property {@linkplain #getMetaMessage()}.
     *
     * @throws NullPointerException is thrown if the parameter <code>metaId</code> is the <code>null</code>.
     * @throws NullPointerException is thrown if the parameter <code>metaMessage</code> is the <code>null</code>.
     */
    public MetaResult(E metaId, String metaMessage) {
        this(null, false, metaId, metaMessage);
    }

    /**
     * A package private constructor for unit test purposes.
     *
     * @param resultData the value for the property {@linkplain #getResultData()}.
     * @param valueFlag the value for the property {@linkplain #isResultPresent()}.
     * @param metaId the value for the property {@linkplain #getMetaId()}.
     * @param metaMessage the value for the property {@linkplain #getMetaMessage()}.
     *
     * @throws NullPointerException is thrown if the parameter <code>metaId</code> is the <code>null</code>.
     * @throws NullPointerException is thrown if the parameter <code>metaMessage</code> is the <code>null</code>.
     */
    MetaResult(T resultData, boolean valueFlag, E metaId, String metaMessage) {
        super();
        this.resultData = resultData;
        this.resultPresent = valueFlag;
        this.metaId = Objects.requireNonNull(metaId);
        this.metaMessage = Objects.requireNonNull(metaMessage);
    }

    /**
     * An enum constant which serves as meta data descriptor
     * of <code>this</code> result object.
     *
     * <p>The enum constant may indicate for example success or failure.
     * The implementation of this class ensures that it's never the
     * <code>null</code>.</p>
     *
     * @return the enum constant which serves as meta data descriptor
     * of <code>this</code> result object.
     */
    public E getMetaId() {
        return metaId;
    }

    /**
     * A text description which provides more inside information associated
     * with the meta data descriptor {@linkplain #getMetaId()}.
     *
     * <p>For example given message could be the description of the reason why
     * the operation failed. The implementation of this class ensures that it's
     * never the <code>null</code>.</p>
     *
     * @return the text description which provides more inside information associated
     * with the meta data descriptor {@linkplain #getMetaId()}.
     */
    public String getMetaMessage() {
        return metaMessage;
    }

    /**
     * A result of the operation.
     *
     * @return the result of the operation.
     *
     * @throws NoSuchElementException is thrown if <code>this</code> result object does
     * not hold any result value i.e. if <code>this.isResult() == false</code>.
     */
    public T getResult() {
        if (isResultPresent()) {
            return getResultData();
        }
        throw new NoSuchElementException("No result is present!");
    }

    /**
     * A flag which indicates if <code>this</code> result object
     * holds the result value or not.
     *
     * <p>Note that this flag does not also indicate if the result value is the
     * <code>null</code> or not. The result value could be <code>null</code> because
     * it was set to the <code>null</code>.</p>
     *
     * @return the <code>false</code> if <code>this</code> result object
     * holds the result value; the <code>true</code> otherwise.
     */
    public boolean isNotResult() {
        return BooleanUtils.isFalse(isResult());
    }

    /**
     * A flag which indicates if <code>this</code> result object
     * holds the result value or not.
     *
     * <p>Note that this flag does not also indicate if the result value is the
     * <code>null</code> or not. The result value could be <code>null</code> because
     * it was set to the <code>null</code>.</p>
     *
     * @return the <code>true</code> if <code>this</code> result object
     * holds the result value; the <code>false</code> otherwise.
     */
    public boolean isResult() {
        return isResultPresent();
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
}
