package tpyo.util;

import com.google.common.base.Predicate;
import tpyo.exception.RuntimeExceptionTranslator;
import tpyo.exception.SimpleRuntimeExceptionTranslator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A convenient {@link Predicate} base class that provides a {@link #doApply(Object) doApply(Object)} method that
 * handily {@link Exception throws Exception}.
 * <p>
 * Useful because it allows one to write predicates using APIs that throw checked exceptions in the cases where one
 * can't deal meaningfully with such exceptions.
 *
 * @param <T> the type that the predicate will {@link #doApply(Object) test against}.
 */
public abstract class AbstractRuntimeExceptionTranslatingPredicate<T> implements Predicate<T>
{

    /**
     * {@inheritDoc}
     *
     * @see #doApply(Object)
     */
    public final boolean apply( T input )
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
     * Returns the result of applying this predicate to {@code input}.
     * <p>
     * Any {@link Exception} thrown from this method will be translated according to the injected
     * {@link RuntimeExceptionTranslator}.
     *
     * @param input the input to be tested; can be {@code null}.
     * @return {@code true} if the test passes.
     * @throws Exception if something goes wrong during the test.
     * @see Predicate#apply(Object)
     */
    protected abstract boolean doApply( T input ) throws Exception;

    private RuntimeExceptionTranslator exceptionTranslator = new SimpleRuntimeExceptionTranslator();

    /** Create an instance of the {@link AbstractRuntimeExceptionTranslatingPredicate} class. */
    protected AbstractRuntimeExceptionTranslatingPredicate()
    {
    }

    /**
     * Create an instance of the {@link AbstractRuntimeExceptionTranslatingPredicate} class.
     *
     * @param exceptionTranslator the desired exception translation strategy; must not be {@code null}.
     */
    protected AbstractRuntimeExceptionTranslatingPredicate( RuntimeExceptionTranslator exceptionTranslator )
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

    protected final RuntimeExceptionTranslator getRuntimeExceptionTranslator()
    {
        return exceptionTranslator;
    }
}
