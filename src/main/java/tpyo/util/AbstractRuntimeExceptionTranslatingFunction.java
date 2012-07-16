package tpyo.util;

import com.google.common.base.Function;
import tpyo.exception.RuntimeExceptionTranslator;
import tpyo.exception.SimpleRuntimeExceptionTranslator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A convenient {@link Function} base class that provides a {@link #doApply(Object) doApply(Object)} method that handily
 * {@link Exception throws Exception}.
 * <p>
 * Useful because it allows one to write functions using APIs that throw checked exceptions in the case where one can't
 * deal meaningfully with any such exception.
 *
 * @param <F> the type to which the function will be applied.
 * @param <T> the type that the function will {@link #doApply(Object) return}.
 */
public abstract class AbstractRuntimeExceptionTranslatingFunction<F, T> implements Function<F, T>
{

    /**
     * {@inheritDoc}
     *
     * @see #doApply(Object)
     */
    public final T apply( F input )
    {
        try
        {
            return doApply( input );
        }
        catch ( Exception ex )
        {
            throw exceptionTranslator.translate( ex );
        }
    }

    /**
     * Returns the result of applying this function to {@code input}.
     * <p>
     * Any {@link Exception} thrown from this method will be translated according to the injected
     * {@link RuntimeExceptionTranslator}.
     *
     * @param input the input to be transformed; can be {@code null}.
     * @return the result; might be {@code null}.
     * @throws Exception if something goes wrong during the execution of the function.
     * @see Function#apply(Object)
     */
    protected abstract T doApply( F input ) throws Exception;

    private RuntimeExceptionTranslator exceptionTranslator = new SimpleRuntimeExceptionTranslator();

    /** Create an instance of the {@link AbstractRuntimeExceptionTranslatingFunction} class. */
    protected AbstractRuntimeExceptionTranslatingFunction()
    {
    }

    /**
     * Create an instance of the {@link AbstractRuntimeExceptionTranslatingFunction} class.
     *
     * @param exceptionTranslator the desired exception translation strategy; must not be {@code null}.
     */
    protected AbstractRuntimeExceptionTranslatingFunction( RuntimeExceptionTranslator exceptionTranslator )
    {
        setRuntimeExceptionTranslator( exceptionTranslator );
    }

    /**
     * Set the desired exception translation strategy.
     *
     * @param exceptionTranslator the strategy; must not be {@code null}.
     */
    public final void setRuntimeExceptionTranslator( RuntimeExceptionTranslator exceptionTranslator )
    {
        this.exceptionTranslator = checkNotNull( exceptionTranslator );
    }
}
