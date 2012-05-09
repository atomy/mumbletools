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
// Generated from file `Callback_ServerAuthenticator_getInfo.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Murmur;

/**
 * Fetch information about a user. This is used to retrieve information like email address, keyhash etc. If you
 * want murmur to take care of this information itself, simply return false to fall through.
 **/

public abstract class Callback_ServerAuthenticator_getInfo extends Ice.TwowayCallback
{
    public abstract void response(boolean __ret, java.util.Map<UserInfo, java.lang.String> info);

    public final void __completed(Ice.AsyncResult __result)
    {
        ServerAuthenticatorPrx __proxy = (ServerAuthenticatorPrx)__result.getProxy();
        boolean __ret = false;
        UserInfoMapHolder info = new UserInfoMapHolder();
        try
        {
            __ret = __proxy.end_getInfo(info, __result);
        }
        catch(Ice.LocalException __ex)
        {
            exception(__ex);
            return;
        }
        response(__ret, info.value);
    }
}
