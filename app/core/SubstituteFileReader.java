package core;

import java.io.IOException;
import java.nio.file.*;

public abstract class SubstituteFileReader
{
    private SubstituteFileReader(){}
    
    public static String resolveFile( final String path ) throws IOException
    {
        Path pathToFile = Paths.get( SharedConfigManager.getRootDir(), path );
        String contents = PathUtil.readFile( pathToFile );
        return Substitutionizer.substituteSharedValues( SharedConfigManager.getSharedConfig(), contents );
    }
}
