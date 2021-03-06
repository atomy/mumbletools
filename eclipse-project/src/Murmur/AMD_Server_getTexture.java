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
// Generated from file `AMD_Server_getTexture.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Murmur;

/**
 * Fetch user texture. Textures are stored as zlib compress()ed 600x60 32-bit BGRA data.
 **/

public interface AMD_Server_getTexture extends Ice.AMDCallback
{
    /**
     * ice_response indicates that
     * the operation completed successfully.
     * @param __ret (return value) Custom texture associated with user or an empty texture.
     **/
    void ice_response(byte[] __ret);
}
