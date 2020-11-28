package de.qtc.rmg.internal;

import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.server.RemoteRef;
import java.util.ArrayList;

import de.qtc.rmg.io.Logger;
import de.qtc.rmg.utils.RMGUtils;

public class GuessingWorker implements Runnable {

    private Method method;
    private RemoteRef instance;
    private Remote remote;
    private MethodCandidate candidate;
    private ArrayList<MethodCandidate> existingMethods;

    public GuessingWorker(Method method, Remote remote, RemoteRef instance, ArrayList<MethodCandidate> existingMethods, MethodCandidate candidate) {
        this.method = method;
        this.instance = instance;
        this.existingMethods = existingMethods;
        this.candidate = candidate;
    }

    public void run() {

        try {
            instance.invoke(remote, method, candidate.getConfusedArgument(), candidate.getHash());

        } catch( java.rmi.ServerException e ) {

            Throwable cause = RMGUtils.getCause(e);
            if( cause != null ) {

                if( cause instanceof java.rmi.UnmarshalException) {
                    /*
                     * This server-exception is thrown when the supplied method hash does not match any
                     * remote method. Therefore, we just continue from here.
                     */
                    return;

                } else if( cause instanceof java.rmi.UnknownHostException  ) {
                    Logger.eprintln("Warning! Object tries to connect to unknown host: " + cause.getCause().getMessage());
                    return;

                } else if( cause instanceof java.rmi.ConnectException  ) {
                    Logger.eprintln((cause.getMessage().split(";"))[0]);
                    return;
                }
            }

        } catch (java.rmi.UnmarshalException e) {
            /*
             * This occurs on invocation of methods taking zero arguments. Since the call always succeeds,
             * the remote method returns some value that probably not matches the expected return value of
             * the lookup method.
             */
            return;

        } catch (Exception e) {
            Logger.eprintlnMixedBlue("Caught unexpected exception while guessing:", e.getMessage());
            RMGUtils.stackTrace(e);
        }

        /*
         * Successfully guessed methods cause either an EOFException (object passed instead of primitive
         * or two few arguments) or an OptionalDataException (primitive passed for instead of object). As
         * these exceptions are not caught, we end up here.
         */
        Logger.printlnMixedYellow("HIT! Method with signature", candidate.getSignature(), "exists!");
        existingMethods.add(candidate);
    }
}