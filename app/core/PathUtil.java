package core;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class PathUtil
{
    public static final Charset FILE_CHARSET = StandardCharsets.UTF_8;
    
    private PathUtil(){}

    public static String tokenizeRelativePath( final Path rootPath, final Path dir )
    {
        Path relative = rootPath.relativize( dir );
        String tokenized = relative.toString().replace( File.separator, "." );
        return tokenized + ".";
    }
    
    public static String readFile( final Path file ) throws IOException
    {
        StringBuffer sb = new StringBuffer();
        for( String line : readFileAsList( file ) )
        {
            sb.append( line ).append( System.getProperty("line.separator") );
        }
        return sb.toString();
    }
    
    public static List<String> readFileAsList( final Path file ) throws IOException
    {
        return Files.readAllLines( file, FILE_CHARSET );
    }
    
    public static InputStream toStream( final String string )
    {
        return new BufferedInputStream( new ByteArrayInputStream( string.getBytes( FILE_CHARSET ) ) );
    }
}
