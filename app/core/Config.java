package core;

import java.net.URLConnection;
import java.nio.file.*;

public class Config
{

    public static ConfigResult getFile( String filename )
    {
        Path path = Paths.get( SharedConfigManager.getRootDir(), filename );
        ConfigResult r = new ConfigResult( path );
        if( ! r.notFound() )
        {
            try
            {
                String output = SubstituteFileReader.resolveFile( filename );
                r.setStream( PathUtil.toStream( output ) );
//                String ct = Files.probeContentType( path );
                String ct = URLConnection.guessContentTypeFromName( path.getFileName().toString() );
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
}
