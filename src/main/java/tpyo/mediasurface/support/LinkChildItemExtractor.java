package tpyo.mediasurface.support;

import com.google.common.collect.ImmutableList;
import com.mediasurface.client.IItem;
import com.mediasurface.client.ILink;
import tpyo.mediasurface.MediasurfaceException;
import tpyo.util.AbstractRuntimeExceptionTranslatingFunction;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterators.forArray;
import static com.google.common.collect.Iterators.transform;

/**
 * Extract the {@link ILink#getChildItem() child item} value from an {@link ILink}.
 * <p>
 * Any checked Mediasurface-related {@link Exception} will be translated into a runtime {@link MediasurfaceException}.
 *
 * @see ILink#getChildItem()
 */
public final class LinkChildItemExtractor extends AbstractRuntimeExceptionTranslatingFunction<ILink, IItem>
{
    /**
     * {@inheritDoc}
     *
     * @see ILink#getChildItem()
     */
    @Override
    protected IItem doApply( ILink link ) throws Exception
    {
        return link.getChildItem();
    }

    /**
     * Create an instance of the {@link LinkChildItemExtractor} class.
     *
     * @return a {@link LinkChildItemExtractor} instance; never {@code null}.
     */
    public static LinkChildItemExtractor extractChildItem()
    {
        return new LinkChildItemExtractor();
    }

    /**
     * A convenience function to extract the {@link ILink#getChildItem() child item} from the supplied {@code links}
     * into an {@link Iterable}.
     *
     * @param links the links from which the child {@link IItem items} are to be extracted; must not be {@code null}.
     * @return the child items; never {@code null}.
     */
    public static Iterable<IItem> extractChildItemsFrom( ILink[] links )
    {
        return ImmutableList.copyOf( transform( forArray( checkNotNull( links ) ), extractChildItem() ) );
    }

    /** Create an instance of the {@link LinkChildItemExtractor} class. */
    public LinkChildItemExtractor()
    {
        super( new MediasurfaceExceptionWrappingRuntimeExceptionTranslator() );
    }
}
