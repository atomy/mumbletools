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
// Generated from file `ServerBootedException.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Murmur;

/**
 * This happens if you try to fetch user or channel state on a stopped server, if you try to stop an already stopped server or start an already started server.
 **/
public class ServerBootedException extends MurmurException
{
    public String
    ice_name()
    {
        return "Murmur::ServerBootedException";
    }

    public void
    __write(IceInternal.BasicStream __os)
    {
        __os.writeString("::Murmur::ServerBootedException");
        __os.startWriteSlice();
        __os.endWriteSlice();
        super.__write(__os);
    }

    public void
    __read(IceInternal.BasicStream __is, boolean __rid)
    {
        if(__rid)
        {
            __is.readString();
        }
        __is.startReadSlice();
        __is.endReadSlice();
        super.__read(__is, true);
    }

    public void
    __write(Ice.OutputStream __outS)
    {
        Ice.MarshalException ex = new Ice.MarshalException();
        ex.reason = "exception Murmur::ServerBootedException was not generated with stream support";
        throw ex;
    }

    public void
    __read(Ice.InputStream __inS, boolean __rid)
    {
        Ice.MarshalException ex = new Ice.MarshalException();
        ex.reason = "exception Murmur::ServerBootedException was not generated with stream support";
        throw ex;
    }
}
