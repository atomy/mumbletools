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
// Generated from file `ACL.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Murmur;

/**
 * Access Control List for a channel. ACLs are defined per channel, and can be inherited from parent channels.
 **/
public class ACL implements java.lang.Cloneable, java.io.Serializable
{
    /**
     * Does the ACL apply to this channel?
     **/
    public boolean applyHere;

    /**
     * Does the ACL apply to subchannels?
     **/
    public boolean applySubs;

    /**
     * Is this ACL inherited from a parent channel? Read-only.
     **/
    public boolean inherited;

    /**
     * ID of user this ACL applies to. -1 if using a group name.
     **/
    public int userid;

    /**
     * Group this ACL applies to. Blank if using userid.
     **/
    public String group;

    /**
     * Binary mask of privileges to allow.
     **/
    public int allow;

    /**
     * Binary mask of privileges to deny.
     **/
    public int deny;

    public ACL()
    {
    }

    public ACL(boolean applyHere, boolean applySubs, boolean inherited, int userid, String group, int allow, int deny)
    {
        this.applyHere = applyHere;
        this.applySubs = applySubs;
        this.inherited = inherited;
        this.userid = userid;
        this.group = group;
        this.allow = allow;
        this.deny = deny;
    }

    public boolean
    equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        ACL _r = null;
        try
        {
            _r = (ACL)rhs;
        }
        catch(ClassCastException ex)
        {
        }

        if(_r != null)
        {
            if(applyHere != _r.applyHere)
            {
                return false;
            }
            if(applySubs != _r.applySubs)
            {
                return false;
            }
            if(inherited != _r.inherited)
            {
                return false;
            }
            if(userid != _r.userid)
            {
                return false;
            }
            if(group != _r.group)
            {
                if(group == null || _r.group == null || !group.equals(_r.group))
                {
                    return false;
                }
            }
            if(allow != _r.allow)
            {
                return false;
            }
            if(deny != _r.deny)
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
        __h = 5 * __h + (applyHere ? 1 : 0);
        __h = 5 * __h + (applySubs ? 1 : 0);
        __h = 5 * __h + (inherited ? 1 : 0);
        __h = 5 * __h + userid;
        if(group != null)
        {
            __h = 5 * __h + group.hashCode();
        }
        __h = 5 * __h + allow;
        __h = 5 * __h + deny;
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
        __os.writeBool(applyHere);
        __os.writeBool(applySubs);
        __os.writeBool(inherited);
        __os.writeInt(userid);
        __os.writeString(group);
        __os.writeInt(allow);
        __os.writeInt(deny);
    }

    public void
    __read(IceInternal.BasicStream __is)
    {
        applyHere = __is.readBool();
        applySubs = __is.readBool();
        inherited = __is.readBool();
        userid = __is.readInt();
        group = __is.readString();
        allow = __is.readInt();
        deny = __is.readInt();
    }
}
