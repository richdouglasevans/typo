package tpyo.mediasurface;

import com.mediasurface.client.*;
import com.mediasurface.datatypes.HostKey;
import com.mediasurface.datatypes.ItemKey;
import com.mediasurface.datatypes.SecurityContextHandle;
import com.mediasurface.datatypes.SiteKey;

import java.util.List;

/**
 * An initialised and logged-in {@link Mediasurface} session.
 * <p>
 * Exposes state such as the {@link Mediasurface} connection and the attendant {@link SecurityContextHandle credentials},
 * in addition to a number of "sugar" methods to ease using the Mediasurface API.
 *
 * @see MediasurfaceOperations
 */
public interface MediasurfaceSession
{
    IItem getItemAtPath( String path ) throws Exception;

    <T extends IEditable> T edit( T item, Editor<T> editor ) throws Exception;

    IType getNamedType( String typeName ) throws Exception;

    IType getNamedGlobalType( String typeName ) throws Exception;

    List<ILink> getBoundItems( IItem item ) throws Exception;

    IItem getItemByKey( ItemKey key ) throws Exception;

    IStatus getNamedStatus( String statusName ) throws Exception;

    boolean itemExistsAtPath( String path );

    boolean isConnected();

    void deleteItemPermanently( ItemKey keyOfTheItemToBeDeletedPermanently ) throws Exception;

    Iterable<ICollection> getCollections( ISite site ) throws Exception;

    ICollection getNamedCollection( String name ) throws Exception;

    ICollection getNamedCollection( String name, ISite site ) throws Exception;

    Mediasurface getMediasurface();

    SecurityContextHandle getHandle();

    ISite getSite();

    IHost getHost();

    SiteKey getSiteKey();

    HostKey getHostKey();
}
