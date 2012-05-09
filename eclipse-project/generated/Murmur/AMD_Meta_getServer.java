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
// Generated from file `AMD_Meta_getServer.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Murmur;

/**
 * Fetch interface to specific server.
 **/

public interface AMD_Meta_getServer extends Ice.AMDCallback
{
    /**
     * ice_response indicates that
     * the operation completed successfully.
     * @param __ret (return value) Interface for specified server, or a null proxy if id is invalid.
     **/
    void ice_response(ServerPrx __ret);
}
