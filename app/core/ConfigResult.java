package core;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigResult
{
    private boolean _notFound;
    private String _contentType;
    private String _error;
    private InputStream _stream;
    private boolean _hasContentType;
    private boolean _hasError;
    private Path _path;
    
    public ConfigResult( Path path )
    {
        _path = path;
        _notFound = ! Files.exists( _path );
    }
    
    public boolean notFound()
    {
        return _notFound;
    }

    public boolean hasError()
    {
        return _hasError;
    }

    public String getErrorMessage()
    {
        return _error;
    }

    public boolean hasContentType()
    {
        return _hasContentType;
    }

    public String getContentType()
    {
        return _contentType;
    }

    public InputStream getStream()
    {
        return _stream;
    }
    
    public void setStream( InputStream stream )
    {
        _stream = stream;
    }

    public void setContentType( final String contentType )
    {
        _contentType = contentType;
        _hasContentType = true;
    }
    
    public void setErrorMessage( final String error )
    {
        _error = error;
        _hasError = true;
    }
}
