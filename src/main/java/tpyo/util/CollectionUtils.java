package tpyo.util;

import com.google.common.base.Predicate;

import java.util.Collection;
import java.util.Iterator;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterators.find;
import static com.google.common.collect.Iterators.forArray;

/** {@link Collection Collection-related} utility methods. */
public final class CollectionUtils
{
    /**
     * Return the first element in {@code iterator} that satisfies the given {@code predicate}.
     *
     * @param predicate the test; must not be {@code null}.
     * @param iterator  the source of values; must not be {@code null}.
     * @return the value, or {@code null} if nothing matched.
     */
    public static <T> T findFirst( Predicate<? super T> predicate, Iterator<T> iterator )
    {
        return find( iterator, predicate, null );
    }

    /**
     * Return the first element in {@code collection} that satisfies the given {@code predicate}.
     *
     * @param predicate  the test; must not be {@code null}.
     * @param collection the source of values; must not be {@code null}.
     * @return the value, or {@code null} if nothing matched.
     */
    public static <T> T findFirst( Predicate<? super T> predicate, Collection<T> collection )
    {
        return find( checkNotNull( collection.iterator() ), predicate, null );
    }

    /**
     * Return the first element in {@code items} that satisfies the given {@code predicate}.
     *
     * @param predicate the test; must not be {@code null}.
     * @param items     the source of values; must not be {@code null}.
     * @return the value, or {@code null} if nothing matched.
     */
    public static <T> T findFirst( Predicate<? super T> predicate, T... items )
    {
        return findFirst( predicate, forArray( items ) );
    }
}
