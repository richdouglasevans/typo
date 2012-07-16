package tpyo.mediasurface.support

import com.mediasurface.client.IItem
import com.mediasurface.general.SelfLoggingException
import org.junit.Test
import tpyo.mediasurface.MediasurfaceException

import static org.junit.Assert.assertEquals
import static org.junit.Assert.fail
import static org.mockito.Mockito.*

@SuppressWarnings("GroovyUnusedCatchParameter")
final class DefaultItemFieldValueGetterTest
{
    @Test
    public void getStringFieldValue_WithFieldThatExists( )
    {
        IItem item = mock( IItem )
        when( item.getStringFieldValue( 'title', true ) ).thenReturn( 'Sitemap' )
        ItemFieldValueGetter sitemapFieldValues = new DefaultItemFieldValueGetter( item )

        assertEquals 'Sitemap', sitemapFieldValues.getStringFieldValue( 'title' )

        verify( item ).getStringFieldValue( 'title', true )
    }

    @Test
    public void getStringFieldValue_WithFieldThatDoesNotExist( )
    {
        IItem item = mock( IItem )
        when( item.getStringFieldValue( 'foshiz', true ) ).thenThrow( SelfLoggingException )
        ItemFieldValueGetter sitemapFieldValues = new DefaultItemFieldValueGetter( item )

        try {
            sitemapFieldValues.getStringFieldValue( 'foshiz' )
            fail()
        } catch (MediasurfaceException expected) {}

        verify( item ).getStringFieldValue( 'foshiz', true )
    }

    @Test
    public void getDateFieldValue_WithFieldThatExists( )
    {
        IItem item = mock( IItem )
        def date = new Date()
        when( item.getDateFieldValue( 'some_date' ) ).thenReturn( date )
        ItemFieldValueGetter sitemapFieldValues = new DefaultItemFieldValueGetter( item )

        assertEquals date, sitemapFieldValues.getDateFieldValue( 'some_date' )

        verify( item ).getDateFieldValue( 'some_date' )
    }

    @Test
    public void getDateFieldValue_WithFieldThatDoesNotExist( )
    {
        IItem item = mock( IItem )
        when( item.getDateFieldValue( 'some_date' ) ).thenThrow( SelfLoggingException )
        ItemFieldValueGetter sitemapFieldValues = new DefaultItemFieldValueGetter( item )

        try {
            sitemapFieldValues.getDateFieldValue( 'some_date' )
            fail()
        } catch (MediasurfaceException expected) {}

        verify( item ).getDateFieldValue( 'some_date' )
    }

    @Test
    public void safeStringFieldValue_WithFieldThatExists( )
    {
        IItem item = mock( IItem )
        when( item.getStringFieldValue( 'title', true ) ).thenReturn( 'Sitemap' )

        assertEquals 'Sitemap', DefaultItemFieldValueGetter.stringFieldValue( item, 'title' )

        verify( item ).getStringFieldValue( 'title', true )
    }

    @Test
    public void safeStringFieldValue_WithFieldThatDoesNotExist( )
    {
        IItem item = mock( IItem )
        when( item.getStringFieldValue( 'foshiz', true ) ).thenThrow( SelfLoggingException )

        try {
            DefaultItemFieldValueGetter.stringFieldValue( item, 'foshiz' )
            fail()
        } catch (MediasurfaceException expected) {}

        verify( item ).getStringFieldValue( 'foshiz', true )
    }

    @Test
    public void safeDateFieldValue_WithFieldThatExists( )
    {
        IItem item = mock( IItem )
        def date = new Date()
        when( item.getDateFieldValue( 'some_date' ) ).thenReturn( date )

        assertEquals date, DefaultItemFieldValueGetter.dateFieldValue( item, 'some_date' )

        verify( item ).getDateFieldValue( 'some_date' )
    }

    @Test
    public void safeDateFieldValue_WithFieldThatDoesNotExist( )
    {
        IItem item = mock( IItem )
        when( item.getDateFieldValue( 'some_date' ) ).thenThrow( SelfLoggingException )

        try {
            DefaultItemFieldValueGetter.dateFieldValue( item, 'some_date' )
            fail()
        } catch (MediasurfaceException expected) {}

        verify( item ).getDateFieldValue( 'some_date' )
    }
}
