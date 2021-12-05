package de.qtc.rmg.operations;

import java.lang.reflect.Method;

import de.qtc.rmg.internal.ExceptionHandler;
import de.qtc.rmg.internal.RMGOption;
import net.sourceforge.argparse4j.inf.Subparser;
import net.sourceforge.argparse4j.inf.Subparsers;

/**
 * The Operation enum class contains one item for each possible rmg action. An enum item consists out of
 * the corresponding method name, the expected positional parameters and the helpstring that should be
 * displayed for the method. This allows to keep all this information structured in one place without having
 * to maintain it elsewhere. During the construction, the constructor of the Operation class looks up the specified
 * method within the Dispatcher class and saves a reference to it. Methods are then invoked by using the
 * Operation.invoke function.
 *
 * To create a new rmg action, a new enum item has to be created and the corresponding method has to be added to
 * the Dispatcher class.
 *
 * @author Tobias Neitzel (@qtc_de)
 */
public enum Operation {

    BIND("dispatchBind", "[object] <listener>", "Binds an object to the registry thats points to listener", new RMGOption[] {
            RMGOption.TARGET_HOST,
            RMGOption.TARGET_PORT,
            RMGOption.GLOBAL_CONFIG,
            RMGOption.GLOBAL_NO_COLOR,
            RMGOption.GLOBAL_PLUGIN,
            RMGOption.GLOBAL_STACK_TRACE,
            RMGOption.GLOBAL_VERBOSE,
            RMGOption.CONN_SSL,
            RMGOption.SSRF,
            RMGOption.SSRF_GOPHER,
            RMGOption.SSRFRESPONSE,
            RMGOption.SSRF_ENCODE,
            RMGOption.SSRF_RAW,
            RMGOption.BIND_BOUND_NAME,
            RMGOption.BIND_BYPASS,
            RMGOption.BIND_OBJID,
            RMGOption.BIND_ADDRESS,
    }),

    CALL("dispatchCall", "<arguments>", "Regulary calls a method with the specified arguments", new RMGOption[] {
            RMGOption.TARGET_HOST,
            RMGOption.TARGET_PORT,
            RMGOption.TARGET_BOUND_NAME,
            RMGOption.TARGET_OBJID,
            RMGOption.TARGET_SIGNATURE,
            RMGOption.TARGET_COMPONENT,
            RMGOption.GLOBAL_CONFIG,
            RMGOption.GLOBAL_NO_COLOR,
            RMGOption.GLOBAL_PLUGIN,
            RMGOption.GLOBAL_STACK_TRACE,
            RMGOption.GLOBAL_VERBOSE,
            RMGOption.CONN_SSL,
            RMGOption.CONN_FOLLOW,
            RMGOption.SSRF,
            RMGOption.SSRF_GOPHER,
            RMGOption.SSRFRESPONSE,
            RMGOption.SSRF_ENCODE,
            RMGOption.SSRF_RAW,
            RMGOption.CALL_ARGUMENTS,
    }),

    CODEBASE("dispatchCodebase", "<classname> <url>", "Perform remote class loading attacks", new RMGOption[] {
            RMGOption.TARGET_HOST,
            RMGOption.TARGET_PORT,
            RMGOption.TARGET_BOUND_NAME,
            RMGOption.TARGET_OBJID,
            RMGOption.TARGET_SIGNATURE,
            RMGOption.TARGET_COMPONENT,
            RMGOption.GLOBAL_CONFIG,
            RMGOption.GLOBAL_NO_COLOR,
            RMGOption.GLOBAL_STACK_TRACE,
            RMGOption.GLOBAL_VERBOSE,
            RMGOption.CONN_SSL,
            RMGOption.CONN_FOLLOW,
            RMGOption.SSRF,
            RMGOption.SSRF_GOPHER,
            RMGOption.SSRFRESPONSE,
            RMGOption.SSRF_ENCODE,
            RMGOption.SSRF_RAW,
            RMGOption.CODEBASE_URL,
            RMGOption.CODEBASS_CLASS,
            RMGOption.ARGUMENT_POS,
    }),

    ENUM("dispatchEnum", "[scan-action ...]", "Enumerate common vulnerabilities on Java RMI endpoints", new RMGOption[] {
            RMGOption.TARGET_HOST,
            RMGOption.TARGET_PORT,
            RMGOption.TARGET_BOUND_NAME,
            RMGOption.GLOBAL_CONFIG,
            RMGOption.GLOBAL_NO_COLOR,
            RMGOption.GLOBAL_STACK_TRACE,
            RMGOption.GLOBAL_VERBOSE,
            RMGOption.ENUM_ACTION,
            RMGOption.ENUM_BYPASS,
            RMGOption.CONN_SSL,
            RMGOption.CONN_FOLLOW,
            RMGOption.SSRF,
            RMGOption.SSRF_GOPHER,
            RMGOption.SSRFRESPONSE,
            RMGOption.SSRF_ENCODE,
            RMGOption.SSRF_RAW,
            RMGOption.DGC_METHOD,
            RMGOption.REG_METHOD,
    }),

    GUESS("dispatchGuess", "", "Guess methods on bound names", new RMGOption[] {
            RMGOption.TARGET_HOST,
            RMGOption.TARGET_PORT,
            RMGOption.TARGET_SIGNATURE,
            RMGOption.TARGET_BOUND_NAME,
            RMGOption.TARGET_OBJID,
            RMGOption.TARGET_COMPONENT,
            RMGOption.GLOBAL_CONFIG,
            RMGOption.GLOBAL_NO_COLOR,
            RMGOption.GLOBAL_STACK_TRACE,
            RMGOption.GLOBAL_VERBOSE,
            RMGOption.CONN_SSL,
            RMGOption.CONN_FOLLOW,
            RMGOption.GUESS_WORDLIST_FILE,
            RMGOption.GUESS_WORDLIST_FOLDER,
            RMGOption.GUESS_CREATE_SAMPLES,
            RMGOption.GUESS_SAMPLE_FOLDER,
            RMGOption.GUESS_TEMPLATE_FOLDER,
            RMGOption.GUESS_TRUSTED,
            RMGOption.GUESS_FORCE_GUESSING,
            RMGOption.GUESS_DUPLICATES,
            RMGOption.GUESS_UPDATE,
            RMGOption.GUESS_ZERO_ARG,
            RMGOption.THREADS,
    }),

    KNOWN("dispatchKnown", "<className>", "Display details of known remote objects", new RMGOption[] {
            RMGOption.GLOBAL_NO_COLOR,
            RMGOption.GLOBAL_STACK_TRACE,
            RMGOption.KNOWN_CLASS,
    }),

    LISTEN("dispatchListen", "<gadget> <command>", "Open ysoserials JRMP listener", new RMGOption[] {
            RMGOption.GLOBAL_CONFIG,
            RMGOption.GLOBAL_NO_COLOR,
            RMGOption.GLOBAL_STACK_TRACE,
            RMGOption.GLOBAL_VERBOSE,
            RMGOption.GLOBAL_PLUGIN,
            RMGOption.LISTEN_IP,
            RMGOption.LISTEN_PORT,
            RMGOption.GADGET_NAME,
            RMGOption.GADGET_CMD,
    }),

    OBJID("dispatchObjID", "<objid>", "Print information contained within an ObjID", new RMGOption[] {
            RMGOption.GLOBAL_CONFIG,
            RMGOption.GLOBAL_NO_COLOR,
            RMGOption.GLOBAL_STACK_TRACE,
            RMGOption.GLOBAL_VERBOSE,
            RMGOption.OBJID_OBJID,
    }),

    REBIND("dispatchRebind", "[object] <listener>", "Rebinds boundname as object that points to listener", new RMGOption[] {
            RMGOption.TARGET_HOST,
            RMGOption.TARGET_PORT,
            RMGOption.GLOBAL_CONFIG,
            RMGOption.GLOBAL_NO_COLOR,
            RMGOption.GLOBAL_PLUGIN,
            RMGOption.GLOBAL_STACK_TRACE,
            RMGOption.GLOBAL_VERBOSE,
            RMGOption.CONN_SSL,
            RMGOption.SSRF,
            RMGOption.SSRF_GOPHER,
            RMGOption.SSRFRESPONSE,
            RMGOption.SSRF_ENCODE,
            RMGOption.SSRF_RAW,
            RMGOption.BIND_BOUND_NAME,
            RMGOption.BIND_BYPASS,
            RMGOption.BIND_OBJID,
            RMGOption.BIND_ADDRESS,
    }),

    ROGUEJMX("dispatchRogueJMX", "[forward-host]", "Creates a rogue JMX listener (collect credentials)", new RMGOption[] {
            RMGOption.GLOBAL_CONFIG,
            RMGOption.GLOBAL_NO_COLOR,
            RMGOption.GLOBAL_STACK_TRACE,
            RMGOption.GLOBAL_VERBOSE,
            RMGOption.CONN_SSL,
            RMGOption.CONN_FOLLOW,
            RMGOption.ROGUEJMX_OBJID,
            RMGOption.ROGUEJMX_FORWARD_HOST,
            RMGOption.ROGUEJMX_FORWARD_PORT,
            RMGOption.ROGUEJMX_FORWARD_BOUND_NAME,
            RMGOption.ROGUEJMX_FORWARD_OBJID,
            RMGOption.LISTEN_IP,
            RMGOption.LISTEN_PORT
    }),

    SCAN("dispatchPortScan", "[<port> [<port>] ...]", "Perform an RMI service scan on common RMI ports", new RMGOption[] {
            RMGOption.GLOBAL_CONFIG,
            RMGOption.GLOBAL_NO_COLOR,
            RMGOption.GLOBAL_STACK_TRACE,
            RMGOption.GLOBAL_VERBOSE,
            RMGOption.SCAN_HOST,
            RMGOption.SCAN_PORTS,
            RMGOption.SCAN_TIMEOUT_CONNECT,
            RMGOption.SCAN_TIMEOUT_READ,
            RMGOption.THREADS,
    }),

    SERIAL("dispatchSerial", "<gadget> <command>", "Perform deserialization attacks against default RMI components", new RMGOption[] {
            RMGOption.TARGET_HOST,
            RMGOption.TARGET_PORT,
            RMGOption.TARGET_BOUND_NAME,
            RMGOption.TARGET_OBJID,
            RMGOption.TARGET_SIGNATURE,
            RMGOption.TARGET_COMPONENT,
            RMGOption.GLOBAL_CONFIG,
            RMGOption.GLOBAL_NO_COLOR,
            RMGOption.GLOBAL_PLUGIN,
            RMGOption.GLOBAL_STACK_TRACE,
            RMGOption.GLOBAL_VERBOSE,
            RMGOption.CONN_SSL,
            RMGOption.CONN_FOLLOW,
            RMGOption.SSRF,
            RMGOption.SSRF_GOPHER,
            RMGOption.SSRFRESPONSE,
            RMGOption.SSRF_ENCODE,
            RMGOption.SSRF_RAW,
            RMGOption.ARGUMENT_POS,
            RMGOption.GADGET_NAME,
            RMGOption.GADGET_CMD,
    }),

    UNBIND("dispatchUnbind", "", "Removes the specified bound name from the registry", new RMGOption[] {
            RMGOption.TARGET_HOST,
            RMGOption.TARGET_PORT,
            RMGOption.GLOBAL_CONFIG,
            RMGOption.GLOBAL_NO_COLOR,
            RMGOption.GLOBAL_STACK_TRACE,
            RMGOption.GLOBAL_VERBOSE,
            RMGOption.CONN_SSL,
            RMGOption.SSRF,
            RMGOption.SSRF_GOPHER,
            RMGOption.SSRFRESPONSE,
            RMGOption.SSRF_ENCODE,
            RMGOption.SSRF_RAW,
            RMGOption.BIND_BOUND_NAME,
            RMGOption.BIND_BYPASS,
    });

    private Method method;
    private String arguments;
    private String description;
    private RMGOption[] options;

    /**
     * Creates a new Operation item. The first argument (methodName) is used for a reflective method
     * accesses within the Dispatcher class. The corresponding method is saved within the corresponding
     * enum item.
     *
     * @param methodName name of the method that belongs to the operation
     * @param arguments expected positional arguments
     * @param description description of the method to show in the help menu
     */
    Operation(String methodName, String arguments, String description, RMGOption[] options)
    {
        try {
            this.method = Dispatcher.class.getDeclaredMethod(methodName, new Class<?>[] {});
        } catch(Exception e) {
            ExceptionHandler.internalException(e, "Operation constructor", true);
        }

        this.arguments = arguments;
        this.description = description;
        this.options = options;
    }

    public Method getMethod()
    {
        return this.method;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getArgs()
    {
        return this.arguments;
    }

    /**
     * Invokes the method that was saved within the Operation.
     *
     * @param dispatcherObject object to invoke the method on
     */
    public void invoke(Dispatcher dispatcherObject)
    {
        try {
            this.method.invoke(dispatcherObject);
        } catch(Exception e) {
            ExceptionHandler.internalException(e, "Operation.invoke(...)", true);
        }
    }

    public boolean containsOption(RMGOption option)
    {
        for( RMGOption o : this.options )
            if( o == option )
                return true;

        return false;
    }
    /**
     * Iterates over the Operation enumeration and returns the operation that equals the specified
     * operation name.
     *
     * @param name desired Operation to return
     * @return requested Operation object or null if not found
     */
    public static Operation getByName(String name)
    {
        Operation returnItem = null;

        for(Operation item : Operation.values()) {
            if(item.toString().equalsIgnoreCase(name)) {
                returnItem = item;
                break;
            }
        }

        return returnItem;
    }

    public static void addSubparsers(Subparsers argumentParser)
    {
        for( Operation operation : Operation.values() ) {

            Subparser parser = argumentParser.addParser(operation.name().toLowerCase()).help(operation.description);
            RMGOption.addOptions(operation, parser);
        }
    }
}
