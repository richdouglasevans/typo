package tpyo.mediasurface.support;

import com.mediasurface.client.IItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Get field values from an {@link IItem} "safely", that is, without throwing an {@link Exception}.
 * <p>
 * Useful for those times when you want to get a field and you don't know, or care, if the field exists in the target
 * {@code IItem}.
 * <p>
 * All {@code Exceptions} are swallowed, so use with care. Any swallowed {@code Exceptions} are logged, but that's it.
 */
public final class SafeItemFieldValueGetter implements ItemFieldValueGetter
{
    public String getStringFieldValue( String fieldName )
    {
        return getStringFieldValue( fieldName, null );
    }

    public String getStringFieldValue( String fieldName, String defaultValue )
    {
        checkNotNull( fieldName );

        String fieldValue = defaultValue;
        try
        {
            fieldValue = getter.getStringFieldValue( fieldName );
        }
        catch ( Exception swallowed )
        {
            logFailureToGet( fieldName );
        }
        return fieldValue;
    }

    public Date getDateFieldValue( String fieldName )
    {
        return getDateFieldValue( fieldName, null );
    }

    public Date getDateFieldValue( String fieldName, Date defaultValue )
    {
        checkNotNull( fieldName );

        Date fieldValue = defaultValue;
        try
        {
            fieldValue = getter.getDateFieldValue( fieldName );
        }
        catch ( Exception swallowed )
        {
            logFailureToGet( fieldName );
        }
        return fieldValue;
    }

    private void logFailureToGet( String fieldName )
    {
        if ( LOGGER.isWarnEnabled() )
        {
            try
            {
                LOGGER.warn( "Failed to get field named '{}' using '{}'.", fieldName, getter );
            }
            catch ( Exception ignored )
            {
            }
        }
    }

    /**
     * A convenience method to get a {@link String} field value safely.
     *
     * @param item      the item that field values will be retrieved from; must not be {@code null}.
     * @param fieldName the name of the field value to be retrieved; must not be {@code null}.
     * @return the {@code String} value for the named field; can be {@code null}.
     * @see #SafeItemFieldValueGetter(IItem)
     * @see #getStringFieldValue(String)
     */
    public static String safeStringFieldValue( IItem item, String fieldName )
    {
        return new SafeItemFieldValueGetter( item ).getStringFieldValue( fieldName );
    }

    /**
     * A convenience method to get a {@link Date} field value safely.
     *
     * @param item      the item that field values will be retrieved from; must not be {@code null}.
     * @param fieldName the name of the field value to be retrieved; must not be {@code null}.
     * @return the {@code Date} value for the named field; can be {@code null}.
     * @see #SafeItemFieldValueGetter(IItem)
     * @see #getDateFieldValue(String)
     */
    public static Date safeDateFieldValue( IItem item, String fieldName )
    {
        return new SafeItemFieldValueGetter( item ).getDateFieldValue( fieldName );
    }

    private static final Logger LOGGER = LoggerFactory.getLogger( SafeItemFieldValueGetter.class );

    private final ItemFieldValueGetter getter;

    /**
     * Create an instance of the {@link SafeItemFieldValueGetter} class.
     * <p>
     * Link references within markup fields will be resolved when retrieving field values.
     *
     * @param item the item that field values will be retrieved from; must not be {@code null}.
     * @see #SafeItemFieldValueGetter(IItem, boolean)
     */
    public SafeItemFieldValueGetter( IItem item )
    {
        this( item, true );
    }

    /**
     * Create an instance of the {@link SafeItemFieldValueGetter} class.
     *
     * @param item         the item that field values will be retrieved from; must not be {@code null}.
     * @param resolveLinks {@code true} if link references within markup fields should be resolved.
     */
    public SafeItemFieldValueGetter( IItem item, boolean resolveLinks )
    {
        this( new DefaultItemFieldValueGetter( item, resolveLinks ) );
    }

    /**
     * Create an instance of the {@link SafeItemFieldValueGetter} class.
     *
     * @param getter the getting strategy; must not be {@code null}.
     */
    public SafeItemFieldValueGetter( ItemFieldValueGetter getter )
    {
        this.getter = checkNotNull( getter );
    }
}
