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
// Generated from file `Callback_Server_setChannelState.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Murmur;

/**
 * Set state of a single channel. You can use this to move or relink channels.
 **/

public abstract class Callback_Server_setChannelState extends Ice.TwowayCallback
{
    public abstract void response();
    public abstract void exception(Ice.UserException __ex);

    public final void __completed(Ice.AsyncResult __result)
    {
        ServerPrx __proxy = (ServerPrx)__result.getProxy();
        try
        {
            __proxy.end_setChannelState(__result);
        }
        catch(Ice.UserException __ex)
        {
            exception(__ex);
            return;
        }
        catch(Ice.LocalException __ex)
        {
            exception(__ex);
            return;
        }
        response();
    }
}