package tpyo.util;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

import java.io.*;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Predicates.not;

/** File-related utility methods. */
public final class FileUtils
{
    public static List<String> slurpLines( File file ) throws Exception
    {
        return slurpLines( file, Predicates.<String>alwaysTrue() );
    }

    public static List<String> slurpLinesIgnoringBlanks( File file ) throws Exception
    {
        return slurpLines( file, not( new BlankStringPredicate() ) );
    }

    public static List<String> slurpLines( File file, Predicate<String> include ) throws IOException
    {
        checkNotNull( file );

        FileInputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream( file );
            return slurpLines( inputStream, include );
        }
        finally
        {
            if ( inputStream != null )
            {
                inputStream.close();
            }
        }
    }

    public static List<String> slurpLines( InputStream inputStream, Predicate<String> include ) throws IOException
    {
        checkNotNull( inputStream );
        checkNotNull( include );

        List<String> lines = Lists.newArrayList();
        String line;
        BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
        while ( ( line = reader.readLine() ) != null )
        {
            line = line.trim();
            if ( include.apply( line ) )
            {
                lines.add( line );
            }
        }
        return lines;
    }

    public static final class BlankStringPredicate implements Predicate<String>
    {
        public boolean apply( String input )
        {
            return input.trim().length() == 0;
        }
    }
}
