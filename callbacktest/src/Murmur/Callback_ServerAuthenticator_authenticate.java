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
// Generated from file `Callback_ServerAuthenticator_authenticate.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Murmur;

/**
 * Called to authenticate a user. If you do not know the username in question, always return -2 from this
 * method to fall through to normal database authentication.
 * Note that if authentication succeeds, murmur will create a record of the user in it's database, reserving
 * the username and id so it cannot be used for normal database authentication.
 * The data in the certificate (name, email addresses etc), as well as the list of signing certificates,
 * should only be trusted if certstrong is true.
 * 
 **/

public abstract class Callback_ServerAuthenticator_authenticate extends Ice.TwowayCallback
{
    public abstract void response(int __ret, String newname, String[] groups);

    public final void __completed(Ice.AsyncResult __result)
    {
        ServerAuthenticatorPrx __proxy = (ServerAuthenticatorPrx)__result.getProxy();
        int __ret = 0;
        Ice.StringHolder newname = new Ice.StringHolder();
        GroupNameListHolder groups = new GroupNameListHolder();
        try
        {
            __ret = __proxy.end_authenticate(newname, groups, __result);
        }
        catch(Ice.LocalException __ex)
        {
            exception(__ex);
            return;
        }
        response(__ret, newname.value, groups.value);
    }
}
