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

package com.st.SensNet.demos.features;

import android.support.annotation.Nullable;
import android.util.Log;

import com.st.BlueSTSDK.Feature;
import com.st.BlueSTSDK.Features.Field;
import com.st.BlueSTSDK.Features.remote.RemoteFeatureUtil;
import com.st.BlueSTSDK.Node;
import com.st.BlueSTSDK.Utils.NumberConversion;

import java.util.Date;

public class GenericRemoteFeature extends Feature {

    private static final byte TYPE_ID_PRESSURE=0x01;
    private static final byte TYPE_ID_HUMIDITY=0x02;
    private static final byte TYPE_ID_TEMPERATURE=0x03;
    private static final byte TYPE_ID_LED_STATUS=0x04;
    private static final byte TYPE_ID_PROXIMITY =0x05;
    private static final byte TYPE_ID_MOTION_DETECTION=0x06;
    private static final byte TYPE_ID_MIC_LEVEL=0x07;
    private static final byte TYPE_ID_LUX=0x08;
    private static final byte TYPE_ID_ACCELERATION =0x09;
    private static final byte TYPE_ID_GYROSCOPE =0x0A;
    private static final byte TYPE_ID_MAGNETOMETER =0x0B;
    private static final byte TYPE_ID_STATUS =0x0C;
    private static final byte TYPE_ID_SFUSION =0x0D;

    public static final String FEATURE_NAME = "Generic Remote";

    private static final int REMOTE_NODE_ID_INDEX=0;

    public static final String PRESSURE_UNIT = "mBar";
    public static final String PRESSURE_DATA_NAME = "Pressure";
    public static final float PRESSURE_DATA_MAX = 2000;
    public static final float PRESSURE_DATA_MIN = 0;

    private static final int PRESSURE_INDEX=1;
    private static final Field PRESSURE_FIELD = new Field(PRESSURE_DATA_NAME, PRESSURE_UNIT,
            Field.Type.Float, PRESSURE_DATA_MAX, PRESSURE_DATA_MIN);


    public static final String TEMPERATURE_UNIT = "\u2103"; // celsius degree
    public static final String TEMPERATURE_DATA_NAME = "Temperature";
    public static final float TEMPERATURE_DATA_MAX = 100;
    public static final float TEMPERATURE_DATA_MIN = 0;

    private static final int TEMPERATURE_INDEX=2;
    private static final Field TEMPERATURE_FILED =
            new Field(TEMPERATURE_DATA_NAME, TEMPERATURE_UNIT, Field.Type.Float,
                    TEMPERATURE_DATA_MAX, TEMPERATURE_DATA_MIN);

    private static final int HUMIDITY_INDEX=3;
    public static final String HUMIDITY_UNIT = "%";
    public static final String HUMIDITY_DATA_NAME = "Humidity";
    public static final float HUMIDITY_DATA_MAX = 100;
    public static final float HUMIDITY_DATA_MIN = 0;

    private static final Field HUMIDITY_FILED =
            new Field(HUMIDITY_DATA_NAME, HUMIDITY_UNIT, Field.Type.Float, HUMIDITY_DATA_MAX, HUMIDITY_DATA_MIN);


    private static final int LED_INDEX=4;
    public static final String LED_UNIT = null;
    public static final String LED_DATA_NAME = "Led Status";
    public static final short LED_DATA_MAX = 1;
    public static final short LED_DATA_MIN = 0;

    private static final Field LED_FILED =
            new Field(LED_DATA_NAME, LED_UNIT, Field.Type.UInt8, LED_DATA_MAX, LED_DATA_MIN);


    private static final int PROXIMITY_INDEX=5;
    public static final String PROXIMITY_DATA_NAME = "Proximity";
    public static final String PROXIMITY_UNIT = "mm";
    /**
     * the sensor return this value when the object is out of range
     */
    public static final int PROXIMITY_OUT_OF_RANGE_VALUE = 255;
    /**
     * maximum object distance manage by the sensor
     */
    public static final float PROXIMITY_DATA_MAX = 254;
    /**
     * minimal object distance manage by the sensor
     */
    public static final float PROXIMITY_DATA_MIN = 0;


    private static final Field PROXIMITY_FIELD = new Field(PROXIMITY_DATA_NAME,
            PROXIMITY_UNIT, Field.Type.UInt16, PROXIMITY_DATA_MIN, PROXIMITY_DATA_MAX);

    private static final int MOTION_DETECTION_INDEX=6;
    public static final String MOTION_DETECTION_UNIT = "ms";
    public static final String MOTION_DETECTION_DATA_NAME = "Last Moviment";
    public static final long MOTION_DETECTION_DATA_MAX = Long.MAX_VALUE;
    public static final long MOTION_DETECTION_DATA_MIN = -1;

    private static final Field MOTION_DETECTION_FILED =
            new Field(MOTION_DETECTION_DATA_NAME, MOTION_DETECTION_UNIT, Field.Type.Int64, MOTION_DETECTION_DATA_MAX,
                    MOTION_DETECTION_DATA_MIN);

    private static final int MIC_LEVEL_INDEX=7;
    public static final String MIC_LEVEL_UNIT = "db";
    public static final String MIC_LEVEL_DATA_NAME = "Mic Level";
    public static final short MIC_LEVEL_DATA_MAX = 255;
    public static final short MIC_LEVEL_DATA_MIN = -1;

    private static final Field MIC_LEVEL_FILED =
            new Field(MIC_LEVEL_DATA_NAME, MIC_LEVEL_UNIT, Field.Type.UInt8, MIC_LEVEL_DATA_MAX,
                    MIC_LEVEL_DATA_MIN);

    private static final int LUX_INDEX=8;
    public static final String LUX_UNIT = "Lux";
    public static final String LUX_DATA_NAME = "Luminosity";
    public static final int LUX_DATA_MAX = Short.MAX_VALUE;
    public static final short LUX_DATA_MIN = -1;

    private static final Field LUX_FILED =
            new Field(LUX_DATA_NAME, LUX_UNIT, Field.Type.UInt8, LUX_DATA_MAX,
                    LUX_DATA_MIN);

    private static final int ACCELERATION_X_INDEX=9;
    private static final int ACCELERATION_Y_INDEX=10;
    private static final int ACCELERATION_Z_INDEX=11;
    public static final String[] ACCELERATION_DATA_NAME = {"Xa","Ya","Za"};
    public static final String ACCELERATION_UNIT = "mg";
    public static final float ACCELERATION_DATA_MAX = 2000;
    public static final float ACCELERATION_DATA_MIN = -2000;

    private static final Field ACCELERATION_X_FIELD = new Field(ACCELERATION_DATA_NAME[0], ACCELERATION_UNIT, Field.Type.Int16,
                    ACCELERATION_DATA_MAX, ACCELERATION_DATA_MIN);
    private static final Field ACCELERATION_Y_FIELD = new Field(ACCELERATION_DATA_NAME[1], ACCELERATION_UNIT, Field.Type.Int16,
                    ACCELERATION_DATA_MAX, ACCELERATION_DATA_MIN);
    private static final Field ACCELERATION_Z_FIELD = new Field(ACCELERATION_DATA_NAME[2], ACCELERATION_UNIT, Field.Type.Int16,
                    ACCELERATION_DATA_MAX, ACCELERATION_DATA_MIN);

    private static final int GYROSCOPE_X_INDEX=12;
    private static final int GYROSCOPE_Y_INDEX=13;
    private static final int GYROSCOPE_Z_INDEX=14;
    public static final String[] GYROSCOPE_DATA_NAME = {"Xg","Yg","Zg"};
    public static final String GYROSCOPE_UNIT = "dps";
    public static final float GYROSCOPE_DATA_MAX = ((float)(1 << 15))/10.0f;
    public static final float GYROSCOPE_DATA_MIN = -GYROSCOPE_DATA_MAX;

    private static final Field GYROSCOPE_X_FIELD = new Field(GYROSCOPE_DATA_NAME[0], GYROSCOPE_UNIT, Field.Type.Float,
            GYROSCOPE_DATA_MAX, GYROSCOPE_DATA_MIN);
    private static final Field GYROSCOPE_Y_FIELD = new Field(GYROSCOPE_DATA_NAME[1], GYROSCOPE_UNIT, Field.Type.Float,
            GYROSCOPE_DATA_MAX, GYROSCOPE_DATA_MIN);
    private static final Field GYROSCOPE_Z_FIELD = new Field(GYROSCOPE_DATA_NAME[2], GYROSCOPE_UNIT, Field.Type.Float,
            GYROSCOPE_DATA_MAX, GYROSCOPE_DATA_MIN);

    private static final int MAGNETOMETER_X_INDEX=15;
    private static final int MAGNETOMETER_Y_INDEX=16;
    private static final int MAGNETOMETER_Z_INDEX=17;
    public static final String[] MAGNETOMETER_DATA_NAME = {"Xm","Ym","Zm"};
    public static final String MAGNETOMETER_UNIT = "mGa";
    public static final int MAGNETOMETER_DATA_MAX = 2000;
    public static final int MAGNETOMETER_DATA_MIN = -2000;

    private static final Field MAGNETOMETER_X_FIELD = new Field(MAGNETOMETER_DATA_NAME[0], MAGNETOMETER_UNIT, Field.Type.Int16,
            MAGNETOMETER_DATA_MAX, MAGNETOMETER_DATA_MIN);
    private static final Field MAGNETOMETER_Y_FIELD = new Field(MAGNETOMETER_DATA_NAME[1], MAGNETOMETER_UNIT, Field.Type.Int16,
            MAGNETOMETER_DATA_MAX, MAGNETOMETER_DATA_MIN);
    private static final Field MAGNETOMETER_Z_FIELD = new Field(MAGNETOMETER_DATA_NAME[2], MAGNETOMETER_UNIT, Field.Type.Int16,
            MAGNETOMETER_DATA_MAX, MAGNETOMETER_DATA_MIN);

    private static final int STATUS_INDEX=18;
    private static final Field STATUS_FIELD = new Field("Status", null, Field.Type.Int16, 1, 0);

    private static final int SFUSION_QI_INDEX=19;
    private static final int SFUSION_QJ_INDEX=20;
    private static final int SFUSION_QK_INDEX=21;
    public static final String[] SFUSION_DATA_NAME = {"qi", "qj", "qk", "qs"};
    public static final String SFUSION_UNIT = null;
    public static final float SFUSION_DATA_MAX = 1.0f;;
    public static final float SFUSION_DATA_MIN = -1.0f;;

    private static final Field SFUSION_QI_FIELD = new Field(SFUSION_DATA_NAME[0], SFUSION_UNIT, Field.Type.Float,
            SFUSION_DATA_MAX, SFUSION_DATA_MIN);
    private static final Field SFUSION_QJ_FIELD = new Field(SFUSION_DATA_NAME[1], SFUSION_UNIT, Field.Type.Float,
            SFUSION_DATA_MAX, SFUSION_DATA_MIN);
    private static final Field SFUSION_QK_FIELD = new Field(SFUSION_DATA_NAME[2], SFUSION_UNIT, Field.Type.Float,
            SFUSION_DATA_MAX, SFUSION_DATA_MIN);

    private static final int UNKNOWN_STATUS_INDEX=22;
    public static final String UNKNOWN_STATUS_UNIT = null;
    public static final String UNKNOWN_STATUS_DATA_NAME = "Unknown";
    public static final byte UNKNOWN_STATUS_DATA_MAX = (byte)256;
    public static final byte UNKNOWN_STATUS_DATA_MIN = 0;

    private static final Field UNKNOWN_FILED =
            new Field(UNKNOWN_STATUS_DATA_NAME, UNKNOWN_STATUS_UNIT, Field.Type.ByteArray, UNKNOWN_STATUS_DATA_MAX,
                    UNKNOWN_STATUS_DATA_MIN);

    private static @Nullable Boolean byteToBoolean(byte value){
        if(value<0)
            return null;
        else
            return (value!=0);
    }

    /**
     * return the node id that send the sample
     * @param s sample of data
     * @return node that send the data or -1 if is not a valid sample
     */
    public static int getNodeId(Sample s){
        if(hasValidIndex(s,REMOTE_NODE_ID_INDEX))
            return s.data[REMOTE_NODE_ID_INDEX].intValue();
        return -1;
    }

    /**
     * return the pressure in the sample
     * @param s sample of data
     * @return pressure inside the sample or NaN if not present
     */
    public static float getPressure(Sample s){
        if(hasValidIndex(s,PRESSURE_INDEX))
            return s.data[PRESSURE_INDEX].floatValue();
        //else
        return Float.NaN;
    }

    /**
     * extract the temperature from the sample
     * @param s sample of data
     * @return temperature inside the sample or NaN if not present
     */
    public static float getTemperature(Sample s){
        if(hasValidIndex(s,TEMPERATURE_INDEX))
            return s.data[TEMPERATURE_INDEX].floatValue();
        //else
        return Float.NaN;
    }

    /**
     * extract the humidity data from the sample
     * @param s sample of data
     * @return humidity inside the sample or NaN if not present
     */
    public static float getHumidity(Sample s){
        if(hasValidIndex(s,HUMIDITY_INDEX))
            return s.data[HUMIDITY_INDEX].floatValue();
        //else
        return Float.NaN;
    }

    /**
     * extract the led status from the sample
     * @param s data sample
     * @return !=0 if the led is on, 0 if is off ,<0 if the status in unknown
     */
    public static @Nullable Boolean getLedStatus(Sample s){
        byte value=-1;
        if(hasValidIndex(s,LED_INDEX))
            value = s.data[LED_INDEX].byteValue();
        //else
        return byteToBoolean(value);
    }

    /**
     * extract the proximity from the sample
     * @param s data sample
     * @return <0 if the proximity is not know, otherwise the proximity distance
     */
    public static int getProximity(Sample s){
        if(hasValidIndex(s,PROXIMITY_INDEX))
            return s.data[PROXIMITY_INDEX].intValue();
        //else
        return -1;
    }

    /**
     * extract the luminosity from the sample
     * @param s data sample
     * @return <0 if the luminosity is not know, otherwise the luminosity
     */
    public static int getLuminosity(Sample s){
        if(hasValidIndex(s,LUX_INDEX))
            return s.data[LUX_INDEX].intValue();
        //else
        return -1;
    }

    /**
     * extract the proximity from the sample
     * @param s data sample
     * @return <0 if the proximity is not know, otherwise the proximity distance
     */
    public static short getMicLevel(Sample s){
        if(hasValidIndex(s,MIC_LEVEL_INDEX))
            return s.data[MIC_LEVEL_INDEX].shortValue();
        //else
        return -1;
    }

    /**
     * extract the door status form the sample
     * @param s data sample
     * @return <0 if the status is unknow, 0 if is close , 1 is it open
     */
    public static @Nullable Date getLastMotionDetection(Sample s){
        long value = -1;
        if(hasValidIndex(s,MOTION_DETECTION_INDEX))
            value = s.data[MOTION_DETECTION_INDEX].longValue();
        //else
        if(value>0)
            return new Date(value);
        return null;
    }

    /**
     * extract the Acceleration from the sample
     * @param s data sample
     * @return -1 if the Acceleration is not know, otherwise the Acceleration Value
     */
    public static int getAccelerationX(Sample s){
        if(hasValidIndex(s,ACCELERATION_X_INDEX)){
            return s.data[ACCELERATION_X_INDEX].intValue();
        }
        //else
        return -1;
    }

    public static int getAccelerationY(Sample s){
        if(hasValidIndex(s,ACCELERATION_Y_INDEX)){
            return s.data[ACCELERATION_Y_INDEX].intValue();
        }
        //else
        return -1;
    }

    public static int getAccelerationZ(Sample s){
        if(hasValidIndex(s,ACCELERATION_Z_INDEX)){
            return s.data[ACCELERATION_Z_INDEX].intValue();
        }
        //else
        return -1;
    }

    /**
     * extract the Gyroscope from the sample
     * @param s data sample
     * @return Gyroscope value inside the sample or NaN if not present
     */

    public static float getGyroscopeX(Sample s){
        if(hasValidIndex(s,GYROSCOPE_X_INDEX)){
            return s.data[GYROSCOPE_X_INDEX].floatValue();
        }
        //else
        return Float.NaN;
    }

    public static float getGyroscopeY(Sample s){
        if(hasValidIndex(s,GYROSCOPE_Y_INDEX)){
            return s.data[GYROSCOPE_Y_INDEX].floatValue();
        }
        //else
        return Float.NaN;
    }

    public static float getGyroscopeZ(Sample s){
        if(hasValidIndex(s,GYROSCOPE_Z_INDEX)){
            return s.data[GYROSCOPE_Z_INDEX].floatValue();
        }
        //else
        return Float.NaN;
    }

    /**
     * extract the Magnetometer from the sample
     * @param s data sample
     * @return -1 if the Magnetometer is not know, otherwise the Magnetometer Value
     */
    public static int getMagnetometerX(Sample s){
        if(hasValidIndex(s,MAGNETOMETER_X_INDEX)){
            return s.data[MAGNETOMETER_X_INDEX].intValue();
        }
        //else
        return -1;
    }

    public static int getMagnetometerY(Sample s){
        if(hasValidIndex(s,MAGNETOMETER_Y_INDEX)){
            return s.data[MAGNETOMETER_Y_INDEX].intValue();
        }
        //else
        return -1;
    }

    public static int getMagnetometerZ(Sample s){
        if(hasValidIndex(s,MAGNETOMETER_Z_INDEX)){
            return s.data[MAGNETOMETER_Z_INDEX].intValue();
        }
        //else
        return -1;
    }

    public static int getStatus(Sample s){
        if(hasValidIndex(s,STATUS_INDEX)){
            return s.data[STATUS_INDEX].intValue();
        }
        //else
        return -1;
    }

    /**
     * extract the Mems Sensor Fusion from the sample
     * @param s data sample
     * @return Mems Sensor Fusion value inside the sample or NaN if not present
     */

    public static float getSFusionQI(Sample s){
        if(hasValidIndex(s,SFUSION_QI_INDEX)){
            return s.data[SFUSION_QI_INDEX].floatValue();
        }
        //else
        return Float.NaN;
    }

    public static float getSFusionQJ(Sample s){
        if(hasValidIndex(s,SFUSION_QJ_INDEX)){
            return s.data[SFUSION_QJ_INDEX].floatValue();
        }
        //else
        return Float.NaN;
    }

    public static float getSFusionQK(Sample s){
        if(hasValidIndex(s,SFUSION_QK_INDEX)){
            return s.data[SFUSION_QK_INDEX].floatValue();
        }
        //else
        return Float.NaN;
    }

    public static @Nullable byte[] getUnknownData(Sample s){
        if(!hasValidIndex(s,UNKNOWN_STATUS_INDEX))
            return null;
        //else
        int length = s.data.length-UNKNOWN_STATUS_INDEX;
        byte data[] = new byte[length];
        for(int i=0;i<length;i++){
            data[i]=s.data[UNKNOWN_STATUS_INDEX+i].byteValue();
        }
        return data;
    }

    public GenericRemoteFeature(Node n) {
        super(FEATURE_NAME, n, new Field[]{RemoteFeatureUtil.REMOTE_DEVICE_ID,
                PRESSURE_FIELD,HUMIDITY_FILED,TEMPERATURE_FILED,LED_FILED, PROXIMITY_FIELD,
                MOTION_DETECTION_FILED,MIC_LEVEL_FILED,LUX_FILED,
                ACCELERATION_X_FIELD,ACCELERATION_Y_FIELD,ACCELERATION_Z_FIELD,
                GYROSCOPE_X_FIELD,GYROSCOPE_Y_FIELD,GYROSCOPE_Z_FIELD,
                MAGNETOMETER_X_FIELD,MAGNETOMETER_Y_FIELD,MAGNETOMETER_Z_FIELD,STATUS_FIELD,
                SFUSION_QI_FIELD,SFUSION_QJ_FIELD,SFUSION_QK_FIELD,UNKNOWN_FILED});
    }

    @Override
    protected ExtractResult extractData(long timestamp, byte[] data, int dataOffset) {
        if (data.length - dataOffset < 3) //2 (node Id)+1 (type id)
            throw new IllegalArgumentException("There are no 3 bytes available to read");

        float pressure=Float.NaN;
        float temperature=Float.NaN;
        float humidity=Float.NaN;
        byte ledStatus=-1;
        short micLevel=-1;
        int lux=-1;
        int proximity=-1;
        long motionDetection=-1;
        int accelerationX=-2001;
        int accelerationY=-2001;
        int accelerationZ=-2001;
        float gyroscopeX=Float.NaN;
        float gyroscopeY=Float.NaN;
        float gyroscopeZ=Float.NaN;
        int magnetometerX=-2001;
        int magnetometerY=-2001;
        int magnetometerZ=-2001;
        int status=0;
        float sFusionQI=Float.NaN;
        float sFusionQJ=Float.NaN;
        float sFusionQK=Float.NaN;
        byte unknownData[]=null;

        int readData = dataOffset;

        int nodeID = NumberConversion.LittleEndian.bytesToUInt16(data,readData);
        readData+=2;

        boolean validDataId =true;
        while (validDataId && readData<data.length){
            switch (data[readData]){
                case TYPE_ID_PRESSURE:
                    pressure= NumberConversion.LittleEndian.bytesToInt32(data, readData+1)/100.0f;
                    readData+=4;
                    break;
                case TYPE_ID_TEMPERATURE:
                    temperature = NumberConversion.LittleEndian.bytesToInt16(data, readData+1)/10.0f;
                    readData+=2;
                    break;
                case TYPE_ID_HUMIDITY:
                    humidity = NumberConversion.LittleEndian.bytesToInt16(data, readData+1)/10.0f;
                    readData+=2;
                    break;
                case TYPE_ID_LED_STATUS:
                    ledStatus= data[readData+1];
                    readData++;
                    break;
                case TYPE_ID_PROXIMITY:
                    proximity = NumberConversion.LittleEndian.bytesToUInt16(data, readData+1);
                    readData+=2;
                    break;
                case TYPE_ID_MIC_LEVEL:
                    micLevel = NumberConversion.byteToUInt8(data,readData+1);
                    readData+=1;
                    break;
                case TYPE_ID_LUX:
                    lux = NumberConversion.LittleEndian.bytesToUInt16(data,readData+1);
                    readData+=2;
                    break;
                case TYPE_ID_MOTION_DETECTION:
                    motionDetection= System.currentTimeMillis();
                    break;
                case TYPE_ID_ACCELERATION:
                    accelerationX = NumberConversion.LittleEndian.bytesToInt16(data, readData+1);
                    readData+=2;
                    accelerationY = NumberConversion.LittleEndian.bytesToInt16(data, readData+1);
                    readData+=2;
                    accelerationZ = NumberConversion.LittleEndian.bytesToInt16(data, readData+1);
                    readData+=2;
                    break;
                case TYPE_ID_GYROSCOPE:
                    gyroscopeX = NumberConversion.LittleEndian.bytesToInt16(data, readData+1)/10.0f;
                    readData+=2;
                    gyroscopeY = NumberConversion.LittleEndian.bytesToInt16(data, readData+1)/10.0f;
                    readData+=2;
                    gyroscopeZ = NumberConversion.LittleEndian.bytesToInt16(data, readData+1)/10.0f;
                    readData+=2;
                    break;
                case TYPE_ID_MAGNETOMETER:
                    magnetometerX = NumberConversion.LittleEndian.bytesToInt16(data, readData+1);
                    readData+=2;
                    magnetometerY = NumberConversion.LittleEndian.bytesToInt16(data, readData+1);
                    readData+=2;
                    magnetometerZ = NumberConversion.LittleEndian.bytesToInt16(data, readData+1);
                    readData+=2;
                    break;
                case TYPE_ID_STATUS:
                    status = 1;
                    readData+=1;
                    break;
                case TYPE_ID_SFUSION:
                    Log.e("ERROR","TYPE_ID_SFUSION -- ROW 569");
                    sFusionQI = NumberConversion.LittleEndian.bytesToInt16(data, readData+1)/10000.0f;
                    readData+=2;
                    sFusionQJ = NumberConversion.LittleEndian.bytesToInt16(data, readData+1)/10000.0f;
                    readData+=2;
                    sFusionQK = NumberConversion.LittleEndian.bytesToInt16(data, readData+1)/10000.0f;
                    readData+=2;
                    break;
                default://else
                    unknownData = extractUnknownData(data,readData);
                    validDataId=false;
            }
            readData++; //+1 for the typeid
        }

        Number extractData[] = new Number[]{nodeID,pressure,temperature,
                humidity,ledStatus,proximity,motionDetection,micLevel,lux,
                accelerationX,accelerationY,accelerationZ,
                gyroscopeX,gyroscopeY,gyroscopeZ,
                magnetometerX,magnetometerY,magnetometerZ,status,
                sFusionQI,sFusionQJ,sFusionQK};
        extractData = appendUnknownData(extractData,unknownData);

        return new ExtractResult(new Sample(timestamp,extractData,getFieldsDesc()),
            readData-dataOffset);
    }

    private Number[] appendUnknownData(Number[] prefix, byte[] suffix) {
        if(suffix==null || suffix.length==0)
            return prefix;
        //else
        Number temp[] = new Number[prefix.length+suffix.length];
        System.arraycopy(prefix,0,temp,0,prefix.length);
        for(int i=0;i<suffix.length;i++){
            temp[prefix.length+i]=suffix[i];
        }
        return temp;
    }

    private byte[] extractUnknownData(byte[] data, int offset) {
        int length = data.length-offset;
        byte temp[] = new byte[length];
        System.arraycopy(data,offset,temp,0,length);
        return temp;
    }

    /**
     * send a message for change the led status in a remote node
     * @param remoteId node where change the status
     * @param newStatus new status
     */
    public void changeSwitchStatus(int remoteId, boolean newStatus) {
        sendEnableCommand(TYPE_ID_LED_STATUS,remoteId,newStatus);
    }

    public void enableMicLevel(int remoteId, boolean newStatus) {
        sendEnableCommand(TYPE_ID_MIC_LEVEL,remoteId,newStatus);
    }

    public void enableProximity(int remoteId, boolean newStatus) {
        sendEnableCommand(TYPE_ID_PROXIMITY,remoteId,newStatus);
    }

    public void enableAcceleration(int remoteId, boolean newStatus) {
        sendEnableCommand(TYPE_ID_ACCELERATION,remoteId,newStatus);
    }

    public void enableGyroscope(int remoteId, boolean newStatus) {
        sendEnableCommand(TYPE_ID_GYROSCOPE,remoteId,newStatus);
    }

    public void enableMagnetometer(int remoteId, boolean newStatus) {
        sendEnableCommand(TYPE_ID_MAGNETOMETER,remoteId,newStatus);
    }

    public void enableSFusion(int remoteId, boolean newStatus) {
        sendEnableCommand(TYPE_ID_SFUSION,remoteId,newStatus);
    }

    private void sendEnableCommand(byte commandId, int remoteId, boolean newStatus) {
        byte command[]=new byte[4];
        byte nodeId[] = NumberConversion.BigEndian.uint16ToBytes(remoteId);
        command[0]=nodeId[0];
        command[1]=nodeId[1];
        command[2]=commandId;
        command[3]= newStatus ? (byte) 0x1 : (byte) 0x0;
        writeData(command);
    }

}
