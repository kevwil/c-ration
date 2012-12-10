package core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.*;

public class ConfigMaster
{
    private ConfigMaster(){}
    
    public static ConfigResult getFile( String filename )
    {
        Path path = Paths.get( SharedConfigManager.getRootDir(), filename );
        ConfigResult r = new ConfigResult( path );
        if( ! r.notFound() )
        {
            try
            {
                String content = SubstituteFileReader.resolveFile( filename );
                r.setStream( PathUtil.toStream( content ) );
//                String ct = Files.probeContentType( path );
//                String ct = URLConnection.guessContentTypeFromName( path.getFileName().toString() );
                String ct = getContentType( content );
                if( ct != null && !ct.isEmpty() )
                {
                    r.setContentType( ct );
                }
            }
            catch( Exception e )
            {
                r.setErrorMessage( e.getLocalizedMessage() );
            }
        }
        return r;
    }
    
    private static String getContentType( final String input )
    {
        try
        {
            InputStream in = PathUtil.toStream( input );
            String mime = URLConnection.guessContentTypeFromStream( in );
            in.close();
            return mime;
        }
        catch( IOException e )
        {
            return null;
        }
    }
}
