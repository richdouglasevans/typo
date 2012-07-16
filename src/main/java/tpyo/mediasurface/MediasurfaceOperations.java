package tpyo.mediasurface;

import com.mediasurface.client.*;
import com.mediasurface.datatypes.ItemFilter;
import com.mediasurface.datatypes.ItemKey;
import com.mediasurface.datatypes.ResultCounter;
import com.mediasurface.general.ResourceException;

import java.util.List;

/**
 * Common Mediasurface operations encapsulated in more developer-friendly API.
 * <p>
 * This interface exists independently of the {@link MediasurfaceTemplate} implementation to aid in testing.
 */
public interface MediasurfaceOperations
{
    /**
     * Execute some logic in the context of a {@link MediasurfaceSession}.
     *
     * @param callback the logic to be run; must not be {@code null}.
     * @return any value returned from the execution of {@code callback}; can be {@code null}.
     * @throws MediasurfaceException if an error occurs.
     */
    <T> T execute( MediasurfaceCallback<T> callback );

    /**
     * Edit the supplied item.
     *
     * @param item the item to be edited; must not be {@code null}.
     * @param edit the editing logic; must not be {@code null}.
     * @return the edited item.
     * @throws MediasurfaceException if an error occurs during editing.
     */
    <T extends IEditable> T edit( T item, Editor<T> edit );

    /**
     * Edit the item fetched by {@code fetch}.
     * <p>
     * In a slight refinement of the {@link MediasurfaceCallback} contract, {@code fetch} <strong>must</strong> return a
     * non-{@code null} value, because the expectation is that it's fetching the item to be edited. If the {@code fetch}
     * does return {@code null}, then a {@link NullPointerException} with an informative error message will be thrown.
     *
     * @param fetch fetches the item to be edited; must not be {@code null}.
     * @param edit  the editing logic; must not be {@code null}.
     * @return the edited item.
     * @throws MediasurfaceException if an error occurs during editing.
     */
    <T extends IEditable> T edit( MediasurfaceCallback<T> fetch, Editor<T> edit );

    /**
     * Get the {@link IType} matching the supplied {@code typeName}.
     *
     * @param typeName the discriminating name; must not be {@code null}.
     * @return the named {@code IType}; never {@code null}
     * @throws MediasurfaceException if no such named {@code IType} can be found.
     */
    IType getNamedType( String typeName );

    /**
     * Get the <strong>global</strong> {@link IType} matching the supplied {@code typeName}.
     *
     * @param typeName the discriminating name; must not be {@code null}.
     * @return the named <strong>global</strong> {@code IType}; never {@code null}
     * @throws MediasurfaceException if no such named {@code IType} can be found.
     */
    IType getNamedGlobalType( String typeName );

    /**
     * Get the {@link IStatus} matching the supplied {@code typeName}.
     *
     * @param statusName the discriminating name; must not be {@code null}.
     * @return the named {@code IStatus}, or {@code null} if no such named {@code IStatus} can be found.
     */
    IStatus getNamedStatus( String statusName );

    /**
     * Get the {@link IItem item} at the supplied {@code path}.
     * <p>
     * If there isn't an item at the supplied path then a (wrapped in a {@link RuntimeException})
     * {@link ResourceException} will be thrown.
     *
     * @param path the path, forex {@code '/children/news'}; must not be {@code null}.
     * @return the item; never {@code null}.
     * @see #itemExistsAtPath(String)
     */
    IItem getItemAtPath( String path );

    /**
     * Get the root {@link IItem item} for the underlying {@link ISite host}.
     * <p>
     * If you want to get the root item for another host, write your own {@link MediasurfaceCallback} and pass it to the
     * {@link #execute(MediasurfaceCallback)} method.
     *
     * @return the root item; never {@code null}.
     */
    IItem getRootItem();

    /**
     * Get the items bound to the supplied {@code item}.
     * <p>
     * This is purely a convenience method that returns <emphasis>all</emphasis> bound items in no explicit order. If
     * you want more control over the bound items that are returned, write your own {@link MediasurfaceCallback} and
     * pass it to the {@link #execute(MediasurfaceCallback)} method.
     *
     * @param item the item to be interrogated for its bound items; must not be {@code null}.
     * @return links to the bound items; never {@code null}, but might be empty.
     * @see IItem#getBoundItems(String, boolean, String, ItemFilter, ResultCounter)
     * @see tpyo.mediasurface.support.LinkChildItemExtractor
     * @see tpyo.mediasurface.support.BoundItemsGetter
     */
    List<ILink> getBoundItems( IItem item );

    /**
     * Delete the {@link IItem item} referred to by the supplied {@link ItemKey item}, <strong>permanently</strong>.
     * <p>
     * This includes <strong>all</strong> versions, and also any entry in the (Recycle) Bin.
     * <p>
     * Use with care, because this does exactly what it says on the tin.
     *
     * @param key the key of the item to be deleted; must not be {@code null}.
     */
    void deleteItemPermanently( ItemKey key );

    /**
     * Does an {@link IItem} exist at the supplied {@code path}?
     *
     * @param path the path to be checked; must not be {@code null}.
     * @return true if an {@code IItem} exists at the supplied {@code path}.
     * @see #getItemAtPath(String)
     */
    boolean itemExistsAtPath( String path );
}
