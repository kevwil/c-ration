package controllers;

import core.ConfigMaster;
import core.ConfigResult;
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
}
