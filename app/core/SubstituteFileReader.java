package core;

import java.io.IOException;
import java.nio.file.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.typesafe.config.Config;

public abstract class SubstituteFileReader
{
    private SubstituteFileReader(){}
    
    public static String resolveFile( final String path ) throws IOException
    {
        Path pathToFile = Paths.get( SharedConfigManager.getRootDir(), path );
        String contents = PathUtil.readFile( pathToFile );
        return substituteSharedValues( contents );
    }

    private static String substituteSharedValues( String contents )
    {
        Config config = SharedConfigManager.getSharedConfig();
        Pattern p = Pattern.compile( "(\\$\\{(.*?)\\})" );
        Matcher m = p.matcher( contents );
        StringBuffer output = new StringBuffer(contents.length());
        while( m.find() )
        {
            // 0 is whole file, 1 is substitution with ${}, 2 is substitution value
            String path = m.group( 2 );
            String replacement = config.getString( path );
            m.appendReplacement( output, Matcher.quoteReplacement( replacement ) );
        }
        m.appendTail( output );
        return output.toString();
    }
}
