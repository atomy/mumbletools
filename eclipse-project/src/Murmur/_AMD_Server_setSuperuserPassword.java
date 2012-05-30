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
// Generated from file `_AMD_Server_setSuperuserPassword.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Murmur;

final class _AMD_Server_setSuperuserPassword extends IceInternal.IncomingAsync implements AMD_Server_setSuperuserPassword
{
    public
    _AMD_Server_setSuperuserPassword(IceInternal.Incoming in)
    {
        super(in);
    }

    public void
    ice_response()
    {
        if(__validateResponse(true))
        {
            __response(true);
        }
    }

    public void
    ice_exception(java.lang.Exception ex)
    {
        try
        {
            throw ex;
        }
        catch(InvalidSecretException __ex)
        {
            if(__validateResponse(false))
            {
                __os().writeUserException(__ex);
                __response(false);
            }
        }
        catch(java.lang.Exception __ex)
        {
            super.ice_exception(__ex);
        }
    }
}