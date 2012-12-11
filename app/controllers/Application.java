package controllers;

import java.nio.file.*;

import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

import core.PathUtil;

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

    public static Result index()
    {
        return ok(index.render("Configuration!"));
    }

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

}