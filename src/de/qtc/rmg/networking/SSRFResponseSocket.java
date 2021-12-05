package de.qtc.rmg.networking;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import de.qtc.rmg.io.DevNullOutputStream;

/**
 * Socket implementation that prevents outputs from being send anywhere and that simulates input
 * by using a byte array that is set when calling the constructor. This socket is used for the
 * --ssrf-response option, where no real network interaction is desired and where the server response
 * is already present in form of a byte array.
 *
 * Due to the nature of ssrf, the SSRFResponseSocket is a single use socket. It is expected to contain
 * the information for one particular remote-method-guesser action. When using e.g. the default enum
 * action with --ssrf-response, the socket is expected to only contain information for the 'list' action.
 * If other actions are desired, they have to be specified explicitly. Multiple actions within a single
 * ssrf operation are not supported.
 *
 * remote-method-guesser attempts to shutdown itself after a single operation was executed with --ssrf-response.
 * As a fallback, the SSRFResponseSocket terminates the program if it is used for multiple actions. This is
 * implemented by a count that tracks how often the OutputStream was used.
 *
 * Notice that the count needs to be implemented on the object level. When using a static count for the class,
 * you get into trouble with the 'lookup' action. During a lookup, a LiveRef is read from the network. This
 * causes the 'read' function of the LiveRef class to trigger, which sends a DGCAck to the obtained remote
 * object. Therefore, performing a lookup causes two SSRFResponseSockets to be created and one InputStream and
 * OutputStream is used on both of them.
 *
 * @author Tobias Neitzel (@qtc_de)
 */
public class SSRFResponseSocket extends Socket {

    private byte[] content;

    private int count = 0;

    public SSRFResponseSocket(byte[] response)
    {
        this.content = response;
    }

    public InputStream getInputStream() throws IOException
    {
        return new ByteArrayInputStream(content);
    }

    public OutputStream getOutputStream()
    {
        if( count != 0 )
            System.exit(0);

        count += 1;
        return new DevNullOutputStream();
    }
}
