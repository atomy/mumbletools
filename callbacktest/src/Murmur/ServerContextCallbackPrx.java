// **********************************************************************
//
// Copyright (c) 2003-2011 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.4.2
//
// <auto-generated>
//
// Generated from file `ServerContextCallbackPrx.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Murmur;

/**
 * Callback interface for context actions. You need to supply one of these for {@link Server.addContext}. 
 * If an added callback ever throws an exception or goes away, it will be automatically removed.
 * Please note that all callbacks are done asynchronously; murmur does not wait for the callback to
 * complete before continuing processing.
 **/
public interface ServerContextCallbackPrx extends Ice.ObjectPrx
{
    /**
     * Called when a context action is performed.
     * @param action Action to be performed.
     * @param usr User which initiated the action.
     * @param session If nonzero, session of target user.
     * @param channelid If nonzero, session of target channel.
     **/
    public void contextAction(String action, User usr, int session, int channelid);

    /**
     * Called when a context action is performed.
     * @param action Action to be performed.
     * @param usr User which initiated the action.
     * @param session If nonzero, session of target user.
     * @param channelid If nonzero, session of target channel.
     * @param __ctx The Context map to send with the invocation.
     **/
    public void contextAction(String action, User usr, int session, int channelid, java.util.Map<String, String> __ctx);

    /**
     * Called when a context action is performed.
     * @param action Action to be performed.
     * @param session If nonzero, session of target user.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_contextAction(String action, User usr, int session, int channelid);

    /**
     * Called when a context action is performed.
     * @param action Action to be performed.
     * @param session If nonzero, session of target user.
     * @param __ctx The Context map to send with the invocation.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_contextAction(String action, User usr, int session, int channelid, java.util.Map<String, String> __ctx);

    /**
     * Called when a context action is performed.
     * @param action Action to be performed.
     * @param session If nonzero, session of target user.
     * @param __cb The asynchronous callback object.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_contextAction(String action, User usr, int session, int channelid, Ice.Callback __cb);

    /**
     * Called when a context action is performed.
     * @param action Action to be performed.
     * @param session If nonzero, session of target user.
     * @param __ctx The Context map to send with the invocation.
     * @param __cb The asynchronous callback object.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_contextAction(String action, User usr, int session, int channelid, java.util.Map<String, String> __ctx, Ice.Callback __cb);

    /**
     * Called when a context action is performed.
     * @param action Action to be performed.
     * @param session If nonzero, session of target user.
     * @param __cb The asynchronous callback object.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_contextAction(String action, User usr, int session, int channelid, Callback_ServerContextCallback_contextAction __cb);

    /**
     * Called when a context action is performed.
     * @param action Action to be performed.
     * @param session If nonzero, session of target user.
     * @param __ctx The Context map to send with the invocation.
     * @param __cb The asynchronous callback object.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_contextAction(String action, User usr, int session, int channelid, java.util.Map<String, String> __ctx, Callback_ServerContextCallback_contextAction __cb);

    /**
     * Called when a context action is performed.
     * @param __result The asynchronous result object.
     **/
    public void end_contextAction(Ice.AsyncResult __result);
}
