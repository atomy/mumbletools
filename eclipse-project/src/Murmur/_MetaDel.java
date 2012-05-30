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
// Generated from file `_MetaDel.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Murmur;

public interface _MetaDel extends Ice._ObjectDel
{
    ServerPrx getServer(int id, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException;

    ServerPrx newServer(java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException;

    ServerPrx[] getBootedServers(java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException;

    ServerPrx[] getAllServers(java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException;

    java.util.Map<java.lang.String, java.lang.String> getDefaultConf(java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException;

    void getVersion(Ice.IntHolder major, Ice.IntHolder minor, Ice.IntHolder patch, Ice.StringHolder text, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper;

    void addCallback(MetaCallbackPrx cb, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidCallbackException,
               InvalidSecretException;

    void removeCallback(MetaCallbackPrx cb, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidCallbackException,
               InvalidSecretException;

    int getUptime(java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper;

    String getSlice(java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper;

    java.util.Map<java.lang.String, java.lang.String> getSliceChecksums(java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper;
}
