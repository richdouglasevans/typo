package tpyo.mediasurface.support;

import com.google.common.base.Objects;
import com.mediasurface.client.IItem;
import com.mediasurface.client.ILink;
import com.mediasurface.client.IType;
import com.mediasurface.datatypes.ItemFilter;
import com.mediasurface.datatypes.ResultCounter;
import com.mediasurface.general.LinkSortOrder;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterators.transform;
import static com.google.common.collect.Lists.newArrayList;
import static tpyo.mediasurface.support.LinkChildItemExtractor.extractChildItem;

/**
 * A method object encapsulation of the {@link IItem#getBoundItems} method, aiming to make client code easier to use
 * and read.
 * <p>
 * The API of the {@code getBoundItems(..)} method is not great: who the heck can remember what 5 parameters, all of
 * them optional, do? It leads to client code like this...
 *
 * <pre>
 * item.getBoundItems(null, false, null, null, null); // sortField, sortDescending, view, filter, resultcounter
 *
 * ItemFilter facetFilter = new ItemFilter("News Item", null, null);
 * // sortField, sortDescending, view, filter, resultcounter
 * ILink[] folderLinks = item.getBoundItems(null, true, null, facetFilter, null);
 * </pre>
 *
 * <p>Contrast the above with the following:
 *
 * <pre>
 * BoundItemsGetter.Builder.allChildrenWithDefaults().getBoundItems( item );
 *
 * new BoundItemsGetter.Builder()
 *     .filteringByItemType( "News Item" )
 *     .sortDescending()
 *     .build().getBoundItems( item );
 * </pre>
 *
 * @see <a href="http://c2.com/cgi/wiki?MethodObject">Method Object Pattern</a>
 */
public final class BoundItemsGetter
{
    /**
     * Get all of the {@link IItem IItems} bound to {@code item} that satisfy the configured state.
     * <p>
     * <strong>Note:</strong> this will load <strong>all</strong> of the bound item links into memory. If the supplied
     * {@code item} has a lot of content bound to it, then you could potentially blow your heap. You might wish to
     * consider using an {@link com.mediasurface.client.ItemSearch ItemSearch} which supports paging.
     *
     * @param item the source of bound items; must not be {@code null}
     * @return the bound items, as {@link ILink ILinks} ); never {@code null}.
     * @throws Exception if something goes wrong while getting the bound items.
     * @see #getBoundItems
     */
    public List<ILink> getBoundLinks( IItem item ) throws Exception
    {
        return newArrayList( checkNotNull( item )
              .getBoundItems( sortField, sortDescending, viewName, itemFilter, resultCounter ) );
    }

    /**
     * Get all of the {@link IItem IItems} bound to {@code item} that satisfy the configured state.
     * <p>
     * <strong>Note:</strong> this will load <strong>all</strong> of the bound items into memory. If the supplied
     * {@code item} has a lot of content bound to it, then you could potentially blow your heap. You might wish to
     * consider using an {@link com.mediasurface.client.ItemSearch ItemSearch} which supports paging.
     *
     * @param item the source of bound items; must not be {@code null}
     * @return the bound items; never {@code null}.
     * @throws Exception if something goes wrong while getting the bound items.
     * @see #getBoundLinks
     * @see LinkChildItemExtractor
     */
    public List<IItem> getBoundItems( IItem item ) throws Exception
    {
        return newArrayList( transform( getBoundLinks( item ).iterator(), extractChildItem() ) );
    }

    private final String sortField;
    private final boolean sortDescending;
    private final String viewName;
    private final ItemFilter itemFilter;
    private final ResultCounter resultCounter;

    private BoundItemsGetter(
          String sortField, boolean sortDescending,
          String viewName, ItemFilter itemFilter, ResultCounter resultCounter )
    {
        this.sortField = sortField;
        this.sortDescending = sortDescending;
        this.viewName = viewName;
        this.itemFilter = itemFilter;
        this.resultCounter = resultCounter;
    }

    public String getSortField()
    {
        return sortField;
    }

    public boolean isSortDescending()
    {
        return sortDescending;
    }

    public boolean isSortAscending()
    {
        return !isSortDescending();
    }

    public String getViewName()
    {
        return viewName;
    }

    public ItemFilter getItemFilter()
    {
        return itemFilter;
    }

    public ResultCounter getResultCounter()
    {
        return resultCounter;
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper( this )
              .add( "sortField", sortField )
              .add( "sortDescending", sortDescending )
              .add( "viewName", viewName )
              .add( "itemFilter", itemFilter )
              .add( "resultCounter", resultCounter )
              .toString();
    }

    /**
     * Build {@link BoundItemsGetter} instances.
     *
     * @see #allChildrenWithDefaults()
     * @see <a href="http://en.wikipedia.org/wiki/Builder_pattern">Builder Pattern</a>
     */
    public static final class Builder
    {
        private String sortField = LinkSortOrder.LINKSORT_ORDERING;
        private boolean sortDescending = false;
        private String viewName;
        private ItemFilter itemFilter;
        private ResultCounter resultCounter;

        /**
         * Create a {@link BoundItemsGetter} configured with defaults.
         * <p>
         * Those defaults are:
         *
         * <pre>
         * new BoundItemsGetter.Builder()
         *     .sortField( LinkSortOrder.LINKSORT_ORDERING )
         *     .sortAscending()
         *     .filteringBy( null )
         *     .resultCounter( null )
         *     .viewName( null )
         *     .build();
         * </pre>
         *
         * @return a {@code BoundItemsGetter}; never {@code null}.
         * @see LinkSortOrder#LINKSORT_ORDERING
         */
        public static BoundItemsGetter allChildrenWithDefaults()
        {
            return new BoundItemsGetter.Builder().build();
        }

        public BoundItemsGetter build()
        {
            return new BoundItemsGetter( sortField, sortDescending, viewName, itemFilter, resultCounter );
        }

        public Builder sortField( String sortField )
        {
            this.sortField = sortField;
            return this;
        }

        public Builder viewName( String viewName )
        {
            this.viewName = viewName;
            return this;
        }

        public Builder filteringBy( ItemFilter itemFilter )
        {
            this.itemFilter = itemFilter;
            return this;
        }

        public Builder filteringByItemType( String typeName )
        {
            this.itemFilter = new ItemFilter( typeName, null, null );
            return this;
        }

        public Builder filteringByItemType( IType type ) throws Exception
        {
            return filteringByItemType( checkNotNull( type ).getName() );
        }

        public Builder resultCounter( ResultCounter resultCounter )
        {
            this.resultCounter = resultCounter;
            return this;
        }

        public Builder sortDescending()
        {
            this.sortDescending = true;
            return this;
        }

        public Builder sortAscending()
        {
            this.sortDescending = false;
            return this;
        }
    }
}
