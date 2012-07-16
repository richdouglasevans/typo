package tpyo.util

import com.google.common.base.Predicate
import junit.framework.Assert
import org.junit.Test

import static com.google.common.base.Predicates.not
import static junit.framework.Assert.*
import static tpyo.util.FileUtils.slurpLines

final class FileUtilsTest
{
    @Test
    public void slurpLinesWithSingleEmptyLine( )
    {
        def lines = slurpLines( toStream( '' ), discardBlankLines() )

        assertNotNull( 'slurpLines(..) must never return null.', lines )
        Assert.assertTrue( lines.isEmpty() )
    }

    @Test
    public void slurpLinesWithSingleKosherLine( )
    {
        def expected = 'Hello, World!'
        def lines = slurpLines( toStream( expected ), discardBlankLines() )

        assertNotNull( 'slurpLines(..) must never return null.', lines )
        assertFalse( lines.isEmpty() )
        assertEquals( 1, lines.size() )
        assertEquals( expected, lines[0] )
    }

    @Test
    public void slurpLinesWithLotsOfEmptyLines( )
    {
        def lines = slurpLines( toStream( """

\t\t

""" ), discardBlankLines() )

        assertNotNull( 'slurpLines(..) must never return null.', lines )
        assertTrue( lines.isEmpty() )
    }

    @Test
    public void slurpLinesWithKosherLineAmongstLotsOfEmptyLines( )
    {
        def expected = 'Hello, World!'
        def lines = slurpLines( toStream( """

\t\t

\t$expected

""" ), discardBlankLines() )

        assertNotNull( 'slurpLines(..) must never return null.', lines )
        assertFalse( lines.isEmpty() )
        assertEquals( 1, lines.size() )
        assertEquals( expected, lines[0] )
    }

    private Predicate<String> discardBlankLines( )
    {
        not( new FileUtils.BlankStringPredicate() )
    }

    private InputStream toStream( String text )
    {
        new ByteArrayInputStream( text.getBytes() )
    }
}
