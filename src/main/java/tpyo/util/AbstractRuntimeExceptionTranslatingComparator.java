package tpyo.util;

import tpyo.exception.RuntimeExceptionTranslator;
import tpyo.exception.SimpleRuntimeExceptionTranslator;

import java.util.Comparator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A convenient {@link Comparator} base class that provides a {@link #doCompare(Object, Object) doCompare(Object,
 * Object)} method that handily {@link Exception throws Exception}.
 * <p>
 * Useful because it allows one to write comparators using APIs that throw checked exceptions in the case where one
 * can't deal meaningfully with any such exception.
 *
 * @param <T> the type to which the comparator will be applied.
 */
public abstract class AbstractRuntimeExceptionTranslatingComparator<T> implements Comparator<T>
{
    /**
     * {@inheritDoc}
     *
     * @see #doCompare(Object, Object)
     */
    public final int compare( T t0, T t1 )
    {
        try
        {
            return doCompare( t0, t1 );
        }
        catch ( Exception ex )
        {
            throw exceptionTranslator.translate( ex );
        }
    }

    /**
     * Returns the result of comparing the two arguments.
     * <p>
     * Any {@link Exception} thrown from this method will be translated according to the injected
     * {@link RuntimeExceptionTranslator}.
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater
     *         than the second.
     * @throws Exception if something goes wrong during the execution of the comparison.
     * @see Comparator#compare(Object, Object)
     */
    protected abstract int doCompare( T o1, T o2 ) throws Exception;

    private RuntimeExceptionTranslator exceptionTranslator = new SimpleRuntimeExceptionTranslator();

    /** Create an instance of the {@link AbstractRuntimeExceptionTranslatingComparator} class. */
    protected AbstractRuntimeExceptionTranslatingComparator()
    {
    }

    /**
     * Create an instance of the {@link AbstractRuntimeExceptionTranslatingComparator} class.
     *
     * @param exceptionTranslator the desired exception translation strategy; must not be {@code null}.
     */
    protected AbstractRuntimeExceptionTranslatingComparator( RuntimeExceptionTranslator exceptionTranslator )
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
