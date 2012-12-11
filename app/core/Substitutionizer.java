package core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.typesafe.config.Config;

public abstract class Substitutionizer
{
    private Substitutionizer(){}
    
    public static String substituteSharedValues( final Config config, final String contents )
    {
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
