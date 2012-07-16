package tpyo.mediasurface;

import com.mediasurface.client.*;
import com.mediasurface.datatypes.ItemKey;
import tpyo.exception.RuntimeExceptionTranslator;
import tpyo.mediasurface.support.DeleteItemPermanently;
import tpyo.mediasurface.support.IdentityMediasurfaceCallback;
import tpyo.mediasurface.support.MediasurfaceExceptionWrappingRuntimeExceptionTranslator;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/** A helper class encapsulating common Mediasurface operations in more developer-friendly API. */
public final class MediasurfaceTemplate implements MediasurfaceOperations
{
    public <T> T execute( MediasurfaceCallback<T> callback )
    {
        checkNotNull( callback );

        try
        {
            MediasurfaceSession session = mediasurfaceSessionFactory.newSession();
            return callback.execute( session );
        }
        catch ( Exception ex )
        {
            throw exceptionTranslator.translate( ex );
        }
    }

    public <T extends IEditable> T edit( final MediasurfaceCallback<T> fetch, final Editor<T> edit )
    {
        checkNotNull( fetch );
        checkNotNull( edit );

        return execute( new MediasurfaceCallback<T>()
        {
            public T execute( MediasurfaceSession session ) throws Exception
            {
                T item = fetch.execute( session );
                if ( item == null )
                {
                    throw new NullPointerException( "Can't edit null item returned from fetch callback." );
                }
                return session.edit( item, edit );
            }
        } );
    }

    public <T extends IEditable> T edit( T item, Editor<T> edit )
    {
        return edit( IdentityMediasurfaceCallback.identity( item ), edit );
    }

    public IType getNamedType( final String typeName )
    {
        return execute( new MediasurfaceCallback<IType>()
        {
            public IType execute( MediasurfaceSession session ) throws Exception
            {
                return session.getNamedType( typeName );
            }
        } );
    }

    public IType getNamedGlobalType( final String typeName )
    {
        return execute( new MediasurfaceCallback<IType>()
        {
            public IType execute( MediasurfaceSession session ) throws Exception
            {
                return session.getNamedGlobalType( typeName );
            }
        } );
    }

    public List<ILink> getBoundItems( final IItem item )
    {
        return execute( new MediasurfaceCallback<List<ILink>>()
        {
            public List<ILink> execute( MediasurfaceSession session ) throws Exception
            {
                return session.getBoundItems( item );
            }
        } );
    }

    public IItem getItemAtPath( final String path )
    {
        return execute( new MediasurfaceCallback<IItem>()
        {
            public IItem execute( MediasurfaceSession session ) throws Exception
            {
                return session.getItemAtPath( path );
            }
        } );
    }

    public IItem getRootItem()
    {
        return execute( new MediasurfaceCallback<IItem>()
        {
            public IItem execute( MediasurfaceSession session ) throws Exception
            {
                return session.getHost().getRootItem();
            }
        } );
    }

    public IStatus getNamedStatus( final String statusName )
    {
        return execute( new MediasurfaceCallback<IStatus>()
        {
            public IStatus execute( MediasurfaceSession session ) throws Exception
            {
                return session.getNamedStatus( statusName );
            }
        } );
    }

    public void deleteItemPermanently( ItemKey keyOfTheItemToBeDeletedPermanently )
    {
        execute( new DeleteItemPermanently( keyOfTheItemToBeDeletedPermanently ) );
    }

    public boolean itemExistsAtPath( final String path )
    {
        return execute( new MediasurfaceCallback<Boolean>()
        {
            public Boolean execute( MediasurfaceSession session ) throws Exception
            {
                return session.itemExistsAtPath( path );
            }
        } );
    }

    private final MediasurfaceSessionFactory mediasurfaceSessionFactory;

    /**
     * Create an instance of the {@link MediasurfaceTemplate} class.
     *
     * @param mediasurfaceSessionFactory used to source {@link MediasurfaceSession MediasurfaceSessions}; must not be
     *                                   {@code null}.
     */
    public MediasurfaceTemplate( MediasurfaceSessionFactory mediasurfaceSessionFactory )
    {
        this.mediasurfaceSessionFactory = checkNotNull( mediasurfaceSessionFactory );
    }

    private RuntimeExceptionTranslator exceptionTranslator = new MediasurfaceExceptionWrappingRuntimeExceptionTranslator();

    public final void setMediasurfaceExceptionTranslator( RuntimeExceptionTranslator exceptionTranslator )
    {
        this.exceptionTranslator = checkNotNull( exceptionTranslator );
    }
}
