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
// Generated from file `Channel.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Murmur;

/**
 * A channel.
 **/
public class Channel implements java.lang.Cloneable, java.io.Serializable
{
    /**
     * Channel ID. This is unique per channel, and the root channel is always id 0.
     **/
    public int id;

    /**
     * Name of the channel. There can not be two channels with the same parent that has the same name.
     **/
    public String name;

    /**
     * ID of parent channel, or -1 if this is the root channel.
     **/
    public int parent;

    /**
     * List of id of linked channels.
     **/
    public int[] links;

    /**
     * Description of channel. Shown as tooltip for this channel.
     **/
    public String description;

    /**
     * Channel is temporary, and will be removed when the last user leaves it.
     **/
    public boolean temporary;

    /**
     * Position of the channel which is used in Client for sorting.
     **/
    public int position;

    public Channel()
    {
    }

    public Channel(int id, String name, int parent, int[] links, String description, boolean temporary, int position)
    {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.links = links;
        this.description = description;
        this.temporary = temporary;
        this.position = position;
    }

    public boolean
    equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        Channel _r = null;
        try
        {
            _r = (Channel)rhs;
        }
        catch(ClassCastException ex)
        {
        }

        if(_r != null)
        {
            if(id != _r.id)
            {
                return false;
            }
            if(name != _r.name)
            {
                if(name == null || _r.name == null || !name.equals(_r.name))
                {
                    return false;
                }
            }
            if(parent != _r.parent)
            {
                return false;
            }
            if(!java.util.Arrays.equals(links, _r.links))
            {
                return false;
            }
            if(description != _r.description)
            {
                if(description == null || _r.description == null || !description.equals(_r.description))
                {
                    return false;
                }
            }
            if(temporary != _r.temporary)
            {
                return false;
            }
            if(position != _r.position)
            {
                return false;
            }

            return true;
        }

        return false;
    }

    public int
    hashCode()
    {
        int __h = 0;
        __h = 5 * __h + id;
        if(name != null)
        {
            __h = 5 * __h + name.hashCode();
        }
        __h = 5 * __h + parent;
        if(links != null)
        {
            for(int __i0 = 0; __i0 < links.length; __i0++)
            {
                __h = 5 * __h + links[__i0];
            }
        }
        if(description != null)
        {
            __h = 5 * __h + description.hashCode();
        }
        __h = 5 * __h + (temporary ? 1 : 0);
        __h = 5 * __h + position;
        return __h;
    }

    public java.lang.Object
    clone()
    {
        java.lang.Object o = null;
        try
        {
            o = super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return o;
    }

    public void
    __write(IceInternal.BasicStream __os)
    {
        __os.writeInt(id);
        __os.writeString(name);
        __os.writeInt(parent);
        IntListHelper.write(__os, links);
        __os.writeString(description);
        __os.writeBool(temporary);
        __os.writeInt(position);
    }

    public void
    __read(IceInternal.BasicStream __is)
    {
        id = __is.readInt();
        name = __is.readString();
        parent = __is.readInt();
        links = IntListHelper.read(__is);
        description = __is.readString();
        temporary = __is.readBool();
        position = __is.readInt();
    }
}