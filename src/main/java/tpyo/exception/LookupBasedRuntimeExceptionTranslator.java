package tpyo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Map.Entry;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newHashMap;

/** Translate {@link Exception Exceptions} using a {@link Map lookup table} . */
public final class LookupBasedRuntimeExceptionTranslator implements RuntimeExceptionTranslator
{
    @SuppressWarnings({"rawtypes", "ThrowableResultOfMethodCallIgnored"})
    public RuntimeException translate( Exception ex )
    {
        Class current = checkNotNull( ex ).getClass();
        RuntimeException translation = null;

        while ( translation == null && current != null && !(current.equals( Object.class )) )
        {
            if ( checkedToRuntime.containsKey( current ) )
            {
                Constructor<? extends RuntimeException> ctor = checkedToRuntime.get( current );
                try
                {
                    translation = ctor.newInstance( ex );
                }
                catch ( Exception bah )
                {
                    LOGGER.warn( "Translating {} failed: {}", current, bah );
                }
            }
            current = current.getSuperclass();
        }
        return (translation != null) ? translation : new RuntimeException( ex );
    }

    private static final Logger LOGGER = LoggerFactory.getLogger( LookupBasedRuntimeExceptionTranslator.class );

    private final Map<Class<? extends Exception>, Constructor<? extends RuntimeException>> checkedToRuntime = newHashMap();

    /**
     * Create an instance of the {@link LookupBasedRuntimeExceptionTranslator} class.
     * <p>
     * See the test fixture for an example of typical usage.
     *
     * @param mapping the mapping of {@link Exception} name to the {@link RuntimeException} name to be translated to.
     * @throws NoSuchMethodException if any of the mapped {@link Class} values do not have single argument constructors
     *                               accepting a {@link Throwable}.
     */
    public LookupBasedRuntimeExceptionTranslator(
          Map<Class<? extends Exception>, Class<? extends RuntimeException>> mapping ) throws NoSuchMethodException
    {
        checkNotNull( mapping );

        for ( Entry<Class<? extends Exception>, Class<? extends RuntimeException>> entry : mapping.entrySet() )
        {
            Constructor<? extends RuntimeException> ctor = entry.getValue().getConstructor( Throwable.class );
            checkedToRuntime.put( entry.getKey(), ctor );
        }
    }
}
