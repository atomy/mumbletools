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
// Generated from file `InvalidChannelException.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Murmur;

/**
 * This is thrown when you specify an invalid channel id. This may happen if the channel was removed by another provess. It can also be thrown if you try to add an invalid channel.
 **/
public class InvalidChannelException extends MurmurException
{
    public String
    ice_name()
    {
        return "Murmur::InvalidChannelException";
    }

    public void
    __write(IceInternal.BasicStream __os)
    {
        __os.writeString("::Murmur::InvalidChannelException");
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
        ex.reason = "exception Murmur::InvalidChannelException was not generated with stream support";
        throw ex;
    }

    public void
    __read(Ice.InputStream __inS, boolean __rid)
    {
        Ice.MarshalException ex = new Ice.MarshalException();
        ex.reason = "exception Murmur::InvalidChannelException was not generated with stream support";
        throw ex;
    }
}