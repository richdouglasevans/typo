package tpyo.mediasurface;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.mediasurface.client.*;
import com.mediasurface.datatypes.HostKey;
import com.mediasurface.datatypes.ItemKey;
import com.mediasurface.datatypes.SecurityContextHandle;
import com.mediasurface.datatypes.SiteKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tpyo.mediasurface.support.BoundItemsGetter;
import tpyo.mediasurface.support.StatusNameMatches;
import tpyo.util.AbstractRuntimeExceptionTranslatingPredicate;
import tpyo.util.CollectionUtils;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static tpyo.util.CollectionUtils.findFirst;

public final class DefaultMediasurfaceSession implements MediasurfaceSession
{
    public IItem getItemAtPath( String path ) throws Exception
    {
        return host.getItem( checkNotNull( path ) );
    }

    public <T extends IEditable> T edit( T item, Editor<T> editor ) throws Exception
    {
        checkNotNull( item );

        try
        {
            item.edit();
            editor.edit( this, item );
            item.saveChanges();
            return item;
        }
        catch ( Exception ex )
        {
            try
            {
                item.abandonChanges();
            }
            catch ( Exception another )
            {
                // we swallow this subsequent exception because we don't want to mask the original exception
                LOGGER.warn( "Failed to abandon changes to item {} because of {}.", item, another );
            }
            throw ex;
        }
    }

    public IType getNamedType( String typeName ) throws Exception
    {
        return getNamedType( typeName, site.getKey() );
    }

    public IType getNamedGlobalType( String typeName ) throws Exception
    {
        return getNamedType( typeName, null );
    }

    private IType getNamedType( String typeName, SiteKey siteKey ) throws Exception
    {
        return mediasurface.getType( handle, checkNotNull( typeName ), siteKey );
    }

    public List<ILink> getBoundItems( IItem item ) throws Exception
    {
        return BoundItemsGetter.Builder.allChildrenWithDefaults().getBoundLinks( item );
    }

    public IItem getItemByKey( ItemKey key ) throws Exception
    {
        return mediasurface.getItem( handle, checkNotNull( key ) );
    }

    public IStatus getNamedStatus( String statusName ) throws Exception
    {
        return findFirst( new StatusNameMatches( statusName ), site.getStatuses() );
    }

    public boolean itemExistsAtPath( String path )
    {
        boolean exists = true;
        try
        {
            getItemAtPath( checkNotNull( path ) );
        }
        catch ( Exception ex )
        {
            exists = false;
        }
        return exists;
    }


    public boolean isConnected()
    {
        return mediasurface.isConnected();
    }

    public void deleteItemPermanently( ItemKey keyOfTheItemToBeDeletedPermanently ) throws Exception
    {
        IItem item = getItemByKey( keyOfTheItemToBeDeletedPermanently );
        if ( LOGGER.isDebugEnabled() )
        {
            LOGGER.debug( "Permanently deleting '{}' located at '{}'", item.getFullName(), item.getPath() );
        }
        item.deleteAllVersions( true );
    }

    public Iterable<ICollection> getCollections( ISite site ) throws Exception
    {
        checkNotNull( site );

        return ImmutableList.copyOf( site.getCollections() );
    }

    public ICollection getNamedCollection( String name ) throws Exception
    {
        return getNamedCollection( name, this.site );
    }

    public ICollection getNamedCollection( final String name, ISite site ) throws Exception
    {
        checkNotNull( name );
        checkNotNull( site );

        return CollectionUtils.findFirst( new AbstractRuntimeExceptionTranslatingPredicate<ICollection>()
        {
            protected boolean doApply( ICollection collection ) throws Exception
            {
                return name.equals( collection.getName() );
            }
        }, getCollections( site ).iterator() );
    }

    private static final Logger LOGGER = LoggerFactory.getLogger( DefaultMediasurfaceSession.class );

    private final Mediasurface mediasurface;
    private final SecurityContextHandle handle;
    private final ISite site;
    private final IHost host;
    private final MediasurfaceConfiguration configuration;

    public DefaultMediasurfaceSession(
          Mediasurface mediasurface, SecurityContextHandle handle, ISite site, IHost host,
          MediasurfaceConfiguration configuration )
    {
        this.mediasurface = checkNotNull( mediasurface );
        this.handle = checkNotNull( handle );
        this.site = checkNotNull( site );
        this.host = checkNotNull( host );
        this.configuration = checkNotNull( configuration );
    }

    public String toString()
    {
        return Objects.toStringHelper( this )
              .add( "configuration", configuration ).toString();
    }

    public Mediasurface getMediasurface()
    {
        return mediasurface;
    }

    public SecurityContextHandle getHandle()
    {
        return handle;
    }

    public ISite getSite()
    {
        return site;
    }

    public IHost getHost()
    {
        return host;
    }

    public SiteKey getSiteKey()
    {
        return site.getKey();
    }

    public HostKey getHostKey()
    {
        return host.getKey();
    }
}
