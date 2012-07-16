package tpyo.mediasurface.support;

import com.mediasurface.client.IItem;
import com.mediasurface.client.IType;
import com.mediasurface.client.Mediasurface;
import com.mediasurface.datatypes.HostKey;
import com.mediasurface.datatypes.SecurityContextHandle;
import com.mediasurface.datatypes.SiteKey;
import com.mediasurface.datatypes.TypeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tpyo.mediasurface.MediasurfaceCallback;
import tpyo.mediasurface.MediasurfaceSession;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Create an {@link IItem}.
 * <p>
 * This only creates an {@code IItem}: populating the created {@code IItem} with state is a separate concern. In
 * particular, the {@link com.mediasurface.client.IItem#getSimpleName()} is <strong>not</strong> set as part of the item
 * creation: use {@link SimpleNameChanger} to set the desired simple name after the content has been created.
 */
public final class CreateItem implements MediasurfaceCallback<IItem>
{
    /**
     * {@inheritDoc}
     * <p>
     * Create the {@link IItem} using the state passed in the {@link #CreateItem(TypeKey, String) constructor}.
     *
     * @return the created {@code IItem}; never {@code null}.
     */
    public IItem execute( MediasurfaceSession session ) throws Exception
    {
        Mediasurface ms = session.getMediasurface();
        SecurityContextHandle credentials = session.getHandle();
        HostKey hostKey = session.getHostKey();
        SiteKey siteKey = session.getSiteKey();

        IType typeOfItemToBeCreated = session.getMediasurface().getType( session.getHandle(), key );

        if ( LOGGER.isDebugEnabled() )
        {
            LOGGER.debug( "Creating new item of type '{}' with fullName '{}' on site '{}'.", new Object[]{
                  typeOfItemToBeCreated.getName(), fullNameOfItemToBeCreated, session.getSite().getName()} );
        }

        return ms.createItem( credentials, key, fullNameOfItemToBeCreated, hostKey, siteKey );
    }

    @Override
    public String toString()
    {
        return toStringHelper( this ).add( "key", key ).addValue( fullNameOfItemToBeCreated ).toString();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger( CreateItem.class );

    private final TypeKey key;
    private final String fullNameOfItemToBeCreated;

    /**
     * Create an instance of the {@link CreateItem} class.
     *
     * @param key      the {@link IType#getKey() key} of the {@link IType} to be created; must not be {@code null}.
     * @param fullName the full name; must not be {@code null}.
     */
    public CreateItem( TypeKey key, String fullName )
    {
        this.key = checkNotNull( key );
        this.fullNameOfItemToBeCreated = checkNotNull( fullName );
    }
}
