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
// Generated from file `_MetaOperationsNC.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Murmur;

/**
 * This is the meta interface. It is primarily used for retrieving the {@link Server} interfaces for each individual server.
 **/
public interface _MetaOperationsNC
{
    /**
     * Fetch interface to specific server.
     * @param __cb The callback object for the operation.
     * @param id Server ID. See {@link Server.getId}.
     **/
    void getServer_async(AMD_Meta_getServer __cb, int id)
        throws InvalidSecretException;

    /**
     * Create a new server. Call {@link Server.getId} on the returned interface to find it's ID.
     * @param __cb The callback object for the operation.
     **/
    void newServer_async(AMD_Meta_newServer __cb)
        throws InvalidSecretException;

    /**
     * Fetch list of all currently running servers.
     * @param __cb The callback object for the operation.
     **/
    void getBootedServers_async(AMD_Meta_getBootedServers __cb)
        throws InvalidSecretException;

    /**
     * Fetch list of all defined servers.
     * @param __cb The callback object for the operation.
     **/
    void getAllServers_async(AMD_Meta_getAllServers __cb)
        throws InvalidSecretException;

    /**
     * Fetch default configuraion. This returns the configuration items that were set in the configuration file, or
     * the built-in default. The individual servers will use these values unless they have been overridden in the
     * server specific configuration. The only special case is the port, which defaults to the value defined here +
     * the servers ID - 1 (so that virtual server #1 uses the defined port, server #2 uses port+1 etc).
     * @param __cb The callback object for the operation.
     **/
    void getDefaultConf_async(AMD_Meta_getDefaultConf __cb)
        throws InvalidSecretException;

    /**
     * Fetch version of Murmur. 
     * @param __cb The callback object for the operation.
     **/
    void getVersion_async(AMD_Meta_getVersion __cb);

    /**
     * Add a callback. The callback will receive notifications when servers are started or stopped.
     * 
     * @param __cb The callback object for the operation.
     * @param cb Callback interface which will receive notifications.
     **/
    void addCallback_async(AMD_Meta_addCallback __cb, MetaCallbackPrx cb)
        throws InvalidCallbackException,
               InvalidSecretException;

    /**
     * Remove a callback.
     * 
     * @param __cb The callback object for the operation.
     * @param cb Callback interface to be removed.
     **/
    void removeCallback_async(AMD_Meta_removeCallback __cb, MetaCallbackPrx cb)
        throws InvalidCallbackException,
               InvalidSecretException;

    /**
     * Get murmur uptime.
     * @param __cb The callback object for the operation.
     **/
    void getUptime_async(AMD_Meta_getUptime __cb);

    /**
     * Get slice file.
     * @param __cb The callback object for the operation.
     **/
    void getSlice_async(AMD_Meta_getSlice __cb);

    /**
     * Returns a checksum dict for the slice file.
     * @param __cb The callback object for the operation.
     **/
    void getSliceChecksums_async(AMD_Meta_getSliceChecksums __cb);
}
