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
// Generated from file `ACLListHelper.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Murmur;

public final class ACLListHelper
{
    public static void
    write(IceInternal.BasicStream __os, ACL[] __v)
    {
        if(__v == null)
        {
            __os.writeSize(0);
        }
        else
        {
            __os.writeSize(__v.length);
            for(int __i0 = 0; __i0 < __v.length; __i0++)
            {
                __v[__i0].__write(__os);
            }
        }
    }

    public static ACL[]
    read(IceInternal.BasicStream __is)
    {
        ACL[] __v;
        final int __len0 = __is.readAndCheckSeqSize(16);
        __v = new ACL[__len0];
        for(int __i0 = 0; __i0 < __len0; __i0++)
        {
            __v[__i0] = new ACL();
            __v[__i0].__read(__is);
        }
        return __v;
    }
}
