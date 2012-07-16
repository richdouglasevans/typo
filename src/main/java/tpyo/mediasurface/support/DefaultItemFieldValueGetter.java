package tpyo.mediasurface.support;

import com.google.common.base.Objects;
import com.mediasurface.client.IItem;
import tpyo.exception.RuntimeExceptionTranslator;

import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

public final class DefaultItemFieldValueGetter implements ItemFieldValueGetter
{
    public String getStringFieldValue( String fieldName )
    {
        try
        {
            return item.getStringFieldValue( checkNotNull( fieldName ), resolveLinks );
        }
        catch ( Exception ex )
        {
            throw runtimeExceptionTranslator.translate( ex );
        }
    }

    public Date getDateFieldValue( String fieldName )
    {
        try
        {
            return item.getDateFieldValue( fieldName );
        }
        catch ( Exception ex )
        {
            throw runtimeExceptionTranslator.translate( ex );
        }
    }

    /**
     * A convenience method to get a {@link String} field value safely.
     *
     * @param item      the item that field values will be retrieved from; must not be {@code null}.
     * @param fieldName the name of the field value to be retrieved; must not be {@code null}.
     * @return the {@code String} value for the named field; can be {@code null}.
     * @see #DefaultItemFieldValueGetter(IItem)
     * @see #getStringFieldValue(String)
     */
    public static String stringFieldValue( IItem item, String fieldName )
    {
        return new DefaultItemFieldValueGetter( item ).getStringFieldValue( fieldName );
    }

    /**
     * A convenience method to get a {@link Date} field value safely.
     *
     * @param item      the item that field values will be retrieved from; must not be {@code null}.
     * @param fieldName the name of the field value to be retrieved; must not be {@code null}.
     * @return the {@code Date} value for the named field; can be {@code null}.
     * @see #DefaultItemFieldValueGetter(IItem)
     * @see #getDateFieldValue(String)
     */
    public static Date dateFieldValue( IItem item, String fieldName )
    {
        return new DefaultItemFieldValueGetter( item ).getDateFieldValue( fieldName );
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper( this )
              .add( "item", item )
              .add( "resolveLinks", resolveLinks )
              .toString();
    }

    private final IItem item;
    private final boolean resolveLinks;

    /**
     * Create an instance of the {@link DefaultItemFieldValueGetter} class.
     * <p>
     * Link references within markup fields will be resolved when retrieving field values.
     *
     * @param item the item that field values will be retrieved from; must not be {@code null}.
     * @see #DefaultItemFieldValueGetter(IItem, boolean)
     */
    public DefaultItemFieldValueGetter( IItem item )
    {
        this( item, true );
    }

    /**
     * Create an instance of the {@link DefaultItemFieldValueGetter} class.
     *
     * @param item         the item that field values will be retrieved from; must not be {@code null}.
     * @param resolveLinks {@code true} if link references within markup fields should be resolved.
     */
    public DefaultItemFieldValueGetter( IItem item, boolean resolveLinks )
    {
        this.item = checkNotNull( item );
        this.resolveLinks = resolveLinks;
    }

    private RuntimeExceptionTranslator runtimeExceptionTranslator
          = new MediasurfaceExceptionWrappingRuntimeExceptionTranslator();

    public void setRuntimeExceptionTranslator( RuntimeExceptionTranslator runtimeExceptionTranslator )
    {
        this.runtimeExceptionTranslator = checkNotNull( runtimeExceptionTranslator );
    }
}
