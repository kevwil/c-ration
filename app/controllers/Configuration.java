package controllers;

import java.nio.file.Path;

import org.codehaus.jackson.node.ObjectNode;

import core.ConfigMaster;
import core.ConfigResult;
import core.DynaTreeJsonBuilder;
import core.SharedConfigManager;
import play.mvc.*;

public class Configuration extends Controller
{
    public static Result getFile( final String filename )
    {
        ConfigResult r = ConfigMaster.getFile( filename );
        if( r.notFound() )
        {
            return notFound();
        }
        if( r.hasError() )
        {
            return internalServerError( r.getErrorMessage() );
        }
        if( r.hasContentType() )
        {
            return ok( r.getStream() ).as( r.getContentType() );
        }
        else
        {
            return ok( r.getStream() );
        }
    }
    
    public static Result tree()
    {
        try
        {
            Path root = SharedConfigManager.getRootPath();
            ObjectNode json = DynaTreeJsonBuilder.walkAsJson( root.toAbsolutePath() );
            return ok(json).as( "application/json; charset=utf-8" );
        }
        catch( Exception e )
        {
            return internalServerError( e.getLocalizedMessage() );
        }
    }
}
