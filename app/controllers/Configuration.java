package controllers;

//import com.typesafe.config.ConfigRenderOptions;

import core.PathUtil;
//import core.SharedConfigManager;
import core.SubstituteFileReader;
import play.mvc.*;

public class Configuration extends Controller
{
//    public static Result debug(
//            final String appname,
//            final String environment,
//            final String filename)
//    {
//        System.out.println("request path: "+request().path());
//        StringBuilder sb = new StringBuilder( "you requested:\n" );
//        sb.append( "\n\tappname: " ).append( appname );
//        sb.append( "\n\tenvironment: " ).append( environment );
//        sb.append( "\n\tfilename: " ).append( filename );
//        return ok(sb.toString());
//    }
//    
//    public static Result assembleConfig(
//            final String appname,
//            final String environment,
//            final String filename)
//    {
//        return ok( SharedConfigManager.getSharedConfig().root().render(ConfigRenderOptions.concise().setFormatted( true )) );
//    }
    
    public static Result getFile( final String filename )
    {
        try
        {
            String output = SubstituteFileReader.resolveFile( filename );
            return ok( PathUtil.toStream( output ) );
        }
        catch( Exception e )
        {
            return internalServerError( e.getLocalizedMessage() );
        }
    }
}
