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
// Generated from file `AMD_Server_getRegistration.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Murmur;

/**
 * Fetch registration for a single user.
 **/

public interface AMD_Server_getRegistration extends Ice.AMDCallback
{
    /**
     * ice_response indicates that
     * the operation completed successfully.
     * @param __ret (return value) Registration record.
     **/
    void ice_response(java.util.Map<UserInfo, java.lang.String> __ret);
}