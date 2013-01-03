package controllers;

import java.nio.file.*;

import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

import core.PathUtil;

import play.cache.Cached;
//import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller
{
    private static final PegDownProcessor md = new PegDownProcessor(
            Extensions.SMARTYPANTS +
            Extensions.HARDWRAPS +
            Extensions.AUTOLINKS +
            Extensions.FENCED_CODE_BLOCKS);

    @Cached( key = "homePage" )
    public static Result index()
    {
        return ok(index.render("Configuration!"));
    }

    @Cached( key = "readMe" )
    public static Result readme()
    {
        Path mdPath = Paths.get( System.getProperty( "user.dir" ), "README.md" );
        try
        {
            String content = PathUtil.readFile( mdPath );
            String output = md.markdownToHtml( content );
            return ok(readme.render(output));
        }
        catch(Exception e)
        {
            return internalServerError( e.getLocalizedMessage() );
        }
    }

    @Cached( key = "tree", duration = 60 )
    public static Result tree()
    {
        try
        {
            return ok(tree.render());
        }
        catch(Exception e)
        {
            return internalServerError( e.getLocalizedMessage() );
        }
    }
}