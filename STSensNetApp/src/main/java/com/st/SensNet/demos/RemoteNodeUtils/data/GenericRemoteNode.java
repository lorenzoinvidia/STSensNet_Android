/*
 * Copyright (c) 2017  STMicroelectronics – All rights reserved
 * The STMicroelectronics corporate logo is a trademark of STMicroelectronics
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this list of conditions
 *   and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice, this list of
 *   conditions and the following disclaimer in the documentation and/or other materials provided
 *   with the distribution.
 *
 * - Neither the name nor trademarks of STMicroelectronics International N.V. nor any other
 *   STMicroelectronics company nor the names of its contributors may be used to endorse or
 *   promote products derived from this software without specific prior written permission.
 *
 * - All of the icons, pictures, logos and other images that are provided with the source code
 *   in a directory whose title begins with st_images may only be used for internal purposes and
 *   shall not be redistributed to any third party or modified in any way.
 *
 * - Any redistributions in binary form shall not include the capability to display any of the
 *   icons, pictures, logos and other images that are provided with the source code in a directory
 *   whose title begins with st_images.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 */

package com.st.SensNet.demos.RemoteNodeUtils.data;

import android.os.Parcel;
import android.support.annotation.Nullable;

import java.util.Date;

/**
 * Remote node that contains the data exported by a GenericRemoteNode
 */
public class GenericRemoteNode extends EnvironmentalRemoteNode {

    /**
     * proximity value in the remote node, <0 if unknown
     */
    private int mProximity;
    private boolean mProximityEnabled;
    private short mMicLevel;
    private boolean mMicLevelEnabled;
    private @Nullable Date mDetectMovement;
    private int mAccelerationX;
    private int mAccelerationY;
    private int mAccelerationZ;
    private boolean mAccelerationEnabled;
    private float mGyroscopeX;
    private float mGyroscopeY;
    private float mGyroscopeZ;
    private boolean mGyroscopeEnabled;
    private int mMagnetometerX;
    private int mMagnetometerY;
    private int mMagnetometerZ;
    private boolean mMagnetometerEnabled;
    private int mStatus;
    private float mSFusionQI;
    private float mSFusionQJ;
    private float mSFusionQK;
    private boolean mSFusionEnabled;
    private byte[] mUnknownData;

    public GenericRemoteNode(int id){
        super(id);
        mProximity=-1;
        mMicLevel=-1;
        mDetectMovement=null;
        mAccelerationX=-2001;
        mAccelerationY=-2001;
        mAccelerationZ=-2001;
        mGyroscopeX=Float.NaN;
        mGyroscopeY=Float.NaN;
        mGyroscopeZ=Float.NaN;
        mMagnetometerX=-2001;
        mMagnetometerY=-2001;
        mMagnetometerZ=-2001;
        mStatus=0;
        mSFusionQI=Float.NaN;
        mSFusionQJ=Float.NaN;
        mSFusionQK=Float.NaN;
        mUnknownData = null;
    }


    public boolean proximityIsEnabled(){return mProximityEnabled;}
    public boolean micIsEnabled(){return mMicLevelEnabled;}
    public boolean accelerationIsEnabled(){return mAccelerationEnabled;}
    public boolean gyroscopeIsEnabled(){return mGyroscopeEnabled;}
    public boolean magnetometerIsEnabled(){return mMagnetometerEnabled;}
    public boolean sFusionIsEnabled(){return mSFusionEnabled;}

    public void setProximityEnabled(boolean enabled){mProximityEnabled = enabled;}
    public void setMicEnabled(boolean enabled){mMicLevelEnabled = enabled;}
    public void setAccelerationEnabled(boolean enabled){mAccelerationEnabled = enabled;}
    public void setGyroscopeEnabled(boolean enabled){mGyroscopeEnabled = enabled;}
    public void setMagnetometerEnabled(boolean enabled){mMagnetometerEnabled = enabled;}
    public void setSFusionEnabled(boolean enabled){mSFusionEnabled = enabled;}

    public boolean hasProximity() {
        return  mProximity>=0;
    }

    public int getProximity() {
        return mProximity;
    }

    public void setProximity(int proximity) {
        if(proximity>=0)
            mProximity = proximity;
    }

    public boolean hasMicLevel() {
        return  mMicLevel>=0;
    }

    public short getMicLevel() {
        return mMicLevel;
    }

    public void setMicLevel(short micLevel) {
        if(micLevel>=0) {
            mMicLevel = micLevel;
        }
    }

    public boolean hasAcceleration() {
        return  mAccelerationX>=-2000;
    }

    public int getAccelerationX() {
        return mAccelerationX;
    }

    public void setAccelerationX(int accelerationX) {
        if(accelerationX>=-2000 && accelerationX<=2000)
            mAccelerationX = accelerationX;
    }

    public int getAccelerationY() {
        return mAccelerationY;
    }

    public void setAccelerationY(int accelerationY) {
        if(accelerationY>=-2000 && accelerationY<=2000)
            mAccelerationY = accelerationY;
    }

    public int getAccelerationZ() {
        return mAccelerationZ;
    }

    public void setAccelerationZ(int accelerationZ) {
        if(accelerationZ>=-2000 && accelerationZ<=2000)
            mAccelerationZ = accelerationZ;
    }

    public float getGyroscopeX() {
        return mGyroscopeX;
    }

    public void setGyroscopeX(float gyroscopeX) {
        if(!Float.isNaN(gyroscopeX))
            mGyroscopeX = gyroscopeX;
    }

    public float getGyroscopeY() {
        return mGyroscopeY;
    }

    public void setGyroscopeY(float gyroscopeY) {
        if(!Float.isNaN(gyroscopeY))
            mGyroscopeY = gyroscopeY;
    }

    public float getGyroscopeZ() {
        return mGyroscopeZ;
    }

    public void setGyroscopeZ(float gyroscopeZ) {
        if(!Float.isNaN(gyroscopeZ))
            mGyroscopeZ = gyroscopeZ;
    }

    public int getMagnetometerX() {
        return mMagnetometerX;
    }

    public void setMagnetometerX(int magnetometerX) {
        if(magnetometerX>=-2000 && magnetometerX<=2000)
            mMagnetometerX = magnetometerX;
    }

    public int getMagnetometerY() {
        return mMagnetometerY;
    }

    public void setMagnetometerY(int magnetometerY) {
        if(magnetometerY>=-2000 && magnetometerY<=2000)
            mMagnetometerY = magnetometerY;
    }

    public int getMagnetometerZ() {
        return mMagnetometerZ;
    }

    public void setMagnetometerZ(int magnetometerZ) {
        if(magnetometerZ>=-2000 && magnetometerZ<=2000)
            mMagnetometerZ = magnetometerZ;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        if(status>=0 && status<=1)
            mStatus = status;
    }

    public float getSFusionQI() {
        return mSFusionQI;
    }

    public void setSFusionQI(float sFusionQI) {
        if(!Float.isNaN(sFusionQI))
            mSFusionQI = sFusionQI;
    }

    public float getSFusionQJ() {
        return mSFusionQJ;
    }

    public void setSFusionQJ(float sFusionQJ) {
        if(!Float.isNaN(sFusionQJ))
            mSFusionQJ = sFusionQJ;
    }

    public float getSFusionQK() {
        return mSFusionQK;
    }

    public void setSFusionQK(float sFusionQK) {
        if(!Float.isNaN(sFusionQK))
            mSFusionQK = sFusionQK;
    }

    public byte[] getUnknownData() {
        return mUnknownData;
    }

    public void setUnknownData(byte[] unknownData) {
        if(unknownData!=null && unknownData.length!=0)
            this.mUnknownData = unknownData;
    }

    public boolean hasUnknownData() {
        return mUnknownData !=null && mUnknownData.length!=0;
    }

    public @Nullable
    Date getLastDetectMovement() {
        return mDetectMovement;
    }

    public void setDetectMovement(@Nullable Date detectMovement) {
        if(detectMovement==null)
            return;
        mDetectMovement = detectMovement;
    }

    //////////////////////PARCELABLE IMPLEMENTATION//////////////////////////////////

    public static final Creator<GenericRemoteNode> CREATOR = new Creator<GenericRemoteNode>() {
        @Override
        public GenericRemoteNode createFromParcel(Parcel in) {
            return new GenericRemoteNode(in);
        }

        @Override
        public GenericRemoteNode[] newArray(int size) {
            return new GenericRemoteNode[size];
        }
    };

    private GenericRemoteNode(Parcel in) {
        super(in);

        mProximity = in.readInt();
        mProximityEnabled = in.readByte() ==0;

        mMicLevel  =(short)in.readInt();
        mMicLevelEnabled = in.readByte() ==0;

        long timeValue = in.readLong();
        if(timeValue<0)
            mDetectMovement=null;
        else
            mDetectMovement = new Date(timeValue);

        mAccelerationX = in.readInt();
        mAccelerationY = in.readInt();
        mAccelerationZ = in.readInt();
        mAccelerationEnabled = in.readByte() ==0;
        mGyroscopeX = in.readFloat();
        mGyroscopeY = in.readFloat();
        mGyroscopeZ = in.readFloat();
        mGyroscopeEnabled = in.readByte() ==0;
        mMagnetometerX = in.readInt();
        mMagnetometerY = in.readInt();
        mMagnetometerZ = in.readInt();
        mMagnetometerEnabled = in.readByte() ==0;
        mStatus = in.readInt();
        mSFusionQI = in.readFloat();
        mSFusionQJ = in.readFloat();
        mSFusionQK = in.readFloat();
        mSFusionEnabled = in.readByte() ==0;

        int unknownDataLength = in.readInt();
        if(unknownDataLength!=0) {
            mUnknownData = new byte[in.readInt()];
            in.readByteArray(mUnknownData);
        }else{
            mUnknownData =null;
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel,i);

        parcel.writeInt(mProximity);
        parcel.writeByte(mProximityEnabled?(byte)0:(byte)1);

        if(mDetectMovement==null)
            parcel.writeLong(-1);
        else
            parcel.writeLong(mDetectMovement.getTime());

        parcel.writeInt(mMicLevel);
        parcel.writeByte(mMicLevelEnabled?(byte)0:(byte)1);

        parcel.writeInt(mAccelerationX);
        parcel.writeInt(mAccelerationY);
        parcel.writeInt(mAccelerationZ);
        parcel.writeByte(mAccelerationEnabled?(byte)0:(byte)1);
        parcel.writeFloat(mGyroscopeX);
        parcel.writeFloat(mGyroscopeY);
        parcel.writeFloat(mGyroscopeZ);
        parcel.writeByte(mGyroscopeEnabled?(byte)0:(byte)1);
        parcel.writeInt(mMagnetometerX);
        parcel.writeInt(mMagnetometerY);
        parcel.writeInt(mMagnetometerZ);
        parcel.writeByte(mMagnetometerEnabled?(byte)0:(byte)1);
        parcel.writeInt(mStatus);
        parcel.writeFloat(mSFusionQI);
        parcel.writeFloat(mSFusionQJ);
        parcel.writeFloat(mSFusionQK);
        parcel.writeByte(mSFusionEnabled?(byte)0:(byte)1);

        if(mUnknownData !=null) {
            parcel.writeInt(mUnknownData.length);
            parcel.writeByteArray(mUnknownData);
        }else{
            parcel.writeInt(0);
        }
    }
}
