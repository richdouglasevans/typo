package tpyo.mediasurface.support;

import java.util.Date;

/** Get field values from an {@link com.mediasurface.client.IItem IItem}. */
public interface ItemFieldValueGetter
{
    /**
     * Get a {@link String} field value.
     *
     * @param fieldName the name of the field value to be retrieved; must not be {@code null}.
     * @return the {@code String} value for the named field; can be {@code null}.
     */
    String getStringFieldValue( String fieldName );

    /**
     * Get a {@link Date} field value.
     *
     * @param fieldName the name of the field value to be retrieved; must not be {@code null}.
     * @return the {@code Date} value for the named field; can be {@code null}.
     */
    Date getDateFieldValue( String fieldName );
}
