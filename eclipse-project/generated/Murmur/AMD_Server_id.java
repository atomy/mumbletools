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
// Generated from file `AMD_Server_id.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Murmur;

/**
 * Fetch the server id.
 * 
 **/

public interface AMD_Server_id extends Ice.AMDCallback
{
    /**
     * ice_response indicates that
     * the operation completed successfully.
     * @param __ret (return value) Unique server id.
     **/
    void ice_response(int __ret);
}