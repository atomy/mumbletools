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
// Generated from file `ChannelMapHelper.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Murmur;

public final class ChannelMapHelper
{
    public static void
    write(IceInternal.BasicStream __os, java.util.Map<java.lang.Integer, Channel> __v)
    {
        if(__v == null)
        {
            __os.writeSize(0);
        }
        else
        {
            __os.writeSize(__v.size());
            for(java.util.Map.Entry<java.lang.Integer, Channel> __e : __v.entrySet())
            {
                __os.writeInt(__e.getKey());
                __e.getValue().__write(__os);
            }
        }
    }

    public static java.util.Map<java.lang.Integer, Channel>
    read(IceInternal.BasicStream __is)
    {
        java.util.Map<java.lang.Integer, Channel> __v;
        __v = new java.util.HashMap<java.lang.Integer, Murmur.Channel>();
        int __sz0 = __is.readSize();
        for(int __i0 = 0; __i0 < __sz0; __i0++)
        {
            int __key;
            __key = __is.readInt();
            Channel __value;
            __value = new Channel();
            __value.__read(__is);
            __v.put(__key, __value);
        }
        return __v;
    }
}
