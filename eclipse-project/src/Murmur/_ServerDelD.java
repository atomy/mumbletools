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
// Generated from file `_ServerDelD.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Murmur;

public final class _ServerDelD extends Ice._ObjectDelD implements _ServerDel
{
    public void
    addCallback(final ServerCallbackPrx cb, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidCallbackException,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public int
    addChannel(final String name, final int parent, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidChannelException,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    addContextCallback(final int session, final String action, final String text, final ServerContextCallbackPrx cb, final int ctx, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidCallbackException,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    addUserToGroup(final int channelid, final int session, final String group, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidChannelException,
               InvalidSecretException,
               InvalidSessionException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    delete(java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    getACL(final int channelid, final ACLListHolder acls, final GroupListHolder groups, final Ice.BooleanHolder inherit, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidChannelException,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public java.util.Map<java.lang.String, java.lang.String>
    getAllConf(java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public Ban[]
    getBans(java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public byte[][]
    getCertificateList(final int session, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               InvalidSessionException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public Channel
    getChannelState(final int channelid, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidChannelException,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public java.util.Map<java.lang.Integer, Channel>
    getChannels(java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public String
    getConf(final String key, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public LogEntry[]
    getLog(final int first, final int last, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public int
    getLogLen(java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public java.util.Map<java.lang.Integer, java.lang.String>
    getRegisteredUsers(final String filter, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public java.util.Map<UserInfo, java.lang.String>
    getRegistration(final int userid, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               InvalidUserException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public User
    getState(final int session, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               InvalidSessionException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public byte[]
    getTexture(final int userid, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               InvalidUserException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public Tree
    getTree(java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public int
    getUptime(java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public java.util.Map<java.lang.String, java.lang.Integer>
    getUserIds(final String[] names, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public java.util.Map<java.lang.Integer, java.lang.String>
    getUserNames(final int[] ids, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public java.util.Map<java.lang.Integer, User>
    getUsers(java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public boolean
    hasPermission(final int session, final int channelid, final int perm, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidChannelException,
               InvalidSecretException,
               InvalidSessionException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public int
    id(java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public boolean
    isRunning(java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    kickUser(final int session, final String reason, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               InvalidSessionException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    redirectWhisperGroup(final int session, final String source, final String target, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               InvalidSessionException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public int
    registerUser(final java.util.Map<UserInfo, java.lang.String> info, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               InvalidUserException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    removeCallback(final ServerCallbackPrx cb, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidCallbackException,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    removeChannel(final int channelid, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidChannelException,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    removeContextCallback(final ServerContextCallbackPrx cb, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidCallbackException,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    removeUserFromGroup(final int channelid, final int session, final String group, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidChannelException,
               InvalidSecretException,
               InvalidSessionException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    sendMessage(final int session, final String text, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               InvalidSessionException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    sendMessageChannel(final int channelid, final boolean tree, final String text, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidChannelException,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    setACL(final int channelid, final ACL[] acls, final Group[] groups, final boolean inherit, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidChannelException,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    setAuthenticator(final ServerAuthenticatorPrx auth, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidCallbackException,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    setBans(final Ban[] bans, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    setChannelState(final Channel state, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidChannelException,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    setConf(final String key, final String value, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    setState(final User state, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidChannelException,
               InvalidSecretException,
               InvalidSessionException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    setSuperuserPassword(final String pw, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    setTexture(final int userid, final byte[] tex, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               InvalidTextureException,
               InvalidUserException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    start(java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               ServerBootedException,
               ServerFailureException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    stop(java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    unregisterUser(final int userid, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               InvalidUserException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public void
    updateRegistration(final int userid, final java.util.Map<UserInfo, java.lang.String> info, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               InvalidUserException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }

    public int
    verifyPassword(final String name, final String pw, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper,
               InvalidSecretException,
               ServerBootedException
    {
        throw new Ice.CollocationOptimizationException();
    }
}
