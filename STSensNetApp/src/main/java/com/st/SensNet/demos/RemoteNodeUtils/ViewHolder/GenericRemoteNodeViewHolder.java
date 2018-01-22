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

package com.st.SensNet.demos.RemoteNodeUtils.ViewHolder;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.st.BlueSTSDK.gui.util.RepeatAnimator;
import com.st.SensNet.R;
import com.st.SensNet.demos.RemoteNodeUtils.data.GenericRemoteNode;
import com.st.SensNet.demos.features.GenericRemoteFeature;
import com.st.SensNet.demos.util.GLCubeRender;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GenericRemoteNodeViewHolder extends EnvironmentalViewHolder {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss",Locale.getDefault());
    private static final String PROXIMITY_FORMAT ="%1$4d %2$s";
    private static final String SFUSION_FORMAT ="%s = %.3f";
    private static final String MICLEVEL_FORMAT ="%1$4d %2$s";

    static public void onBindViewHolder(final GenericRemoteNodeViewHolder holder, GenericRemoteNode item) {
        EnvironmentalViewHolder.onBindViewHolder(holder,item);
        holder.updateNode(item);
        holder.setMotionDetection(item.getLastDetectMovement());
        holder.setProximity(item.getProximity());
        holder.setMicLevel(item.getMicLevel());
        holder.setAcceleration(item.getAccelerationX(),item.getAccelerationY(),item.getAccelerationZ());
        holder.setGyroscope(item.getGyroscopeX(),item.getGyroscopeY(),item.getGyroscopeZ());
        holder.setMagnetometer(item.getMagnetometerX(),item.getMagnetometerY(),item.getMagnetometerZ());
        holder.setSFusion(item.getSFusionQI(),item.getSFusionQJ(),item.getSFusionQK());
        holder.setNodeStatus(item.getStatus());
    }

    public interface GenericRemoteNodeViewCallback extends  EnvironmentalNodeViewCallback{

        void onProximitySwitchChange(GenericRemoteNode node, boolean newStatus);
        void onMicLevelSwitchChange(GenericRemoteNode node, boolean newStatus);
        void onAccelerationSwitchChange(GenericRemoteNode node, boolean newStatus);
        void onGyroscopeSwitchChange(GenericRemoteNode node, boolean newStatus);
        void onMagnetometerSwitchChange(GenericRemoteNode node, boolean newStatus);
        void onSFusionSwitchChange(GenericRemoteNode node, boolean newStatus);

    }

    private final RelativeLayout mNodeLayout;
    private final ViewGroup mMotionLayout;
    private final TextView mMotionDetection;
    private Date mLastDisplayDate;
    private final RepeatAnimator mShakeMotionDetection;

    private final ViewGroup mProximityLayout;
    private final TextView mProximity;
    private final ProgressBar mProximityBar;
    private final CompoundButton mProximitySwitch;
    private final CompoundButton.OnCheckedChangeListener mProximityChangeListener;

    private final ViewGroup mPlotLayout;
    private final GraphView mGraph;
    private LineGraphSeries<DataPoint> mSeries1;
    private LineGraphSeries<DataPoint> mSeries2;
    private LineGraphSeries<DataPoint> mSeries3;
    private double mGraphLastXValue = 0d;

    private final ViewGroup mMemsLayout;
    private final TextView mNodeState;
    private final TextView mAcceleration;
    private final CompoundButton mAccelerationSwitch;
    private final CompoundButton.OnCheckedChangeListener mAccelerationChangeListener;
    private boolean mStatusAcc=false;
    private final TextView mGyroscope;
    private final CompoundButton mGyroscopeSwitch;
    private final CompoundButton.OnCheckedChangeListener mGyroscopeChangeListener;
    private boolean mStatusGyr=false;
    private final TextView mMagnetometer;
    private final CompoundButton mMagnetometerSwitch;
    private final CompoundButton.OnCheckedChangeListener mMagnetometerChangeListener;
    private boolean mStatusMag=false;

    private final ImageView mSFusionIcon;
    private final TableLayout mSFusionTable;
    private final TextView mSFusionQI;
    private final TextView mSFusionQJ;
    private final TextView mSFusionQK;
    private final TextView mSFusionQS;
    private final CompoundButton mSFusionSwitch;
    private final CompoundButton.OnCheckedChangeListener mSFusionChangeListener;
    private final ViewGroup mCubeLayout;
    private final TableLayout mGlSurfaceLayout;
    private GLSurfaceView mGlSurface;
    private GLCubeRender mGlRenderer;
    private final Button mResetButton;
    private boolean mFirstQuat = true;
    private final TextView mNotSupported;
    private int error = 0;
    private boolean mStatusMSF=false;

    private final ViewGroup mMicLevelLayout;
    private final TextView mMicLevel;
    private final ProgressBar mMicLevelBar;

    private final CompoundButton mMicSwitch;
    private final CompoundButton.OnCheckedChangeListener mMicSwitchChangeListener;

    private final CompoundButton mLedSwitch;

    private GenericRemoteNode mNode;
    private final GenericRemoteNodeViewCallback mCallback;

    public static GenericRemoteNodeViewHolder build(ViewGroup parent, GenericRemoteNodeViewCallback callback){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.generic_node_item, parent, false);
        return new GenericRemoteNodeViewHolder(view,callback);
    }

    private static RepeatAnimator initShakeAnimation(View objectToShake){
        AnimatorSet shakeImage = (AnimatorSet) AnimatorInflater.loadAnimator(objectToShake.getContext(),
                R.animator.shake);
        shakeImage.setTarget(objectToShake);

        return new RepeatAnimator(shakeImage, 5);
    }

    private GenericRemoteNodeViewHolder(View view,GenericRemoteNodeViewCallback callback) {
        super(view,
                (TextView) view.findViewById(R.id.remoteIdLabel),
                (TextView) view.findViewById(R.id.temperatureValLabel),
                (TextView) view.findViewById(R.id.pressureValLabel),
                (TextView) view.findViewById(R.id.humidityValLabel),
                (ImageView) view.findViewById(R.id.ledIcon),
                (CompoundButton) view.findViewById(R.id.ledSwitch),
                (TextView) view.findViewById(R.id.ledStatusLabel),
                callback);

        mCallback=callback;
        mNodeLayout = (RelativeLayout) view.findViewById(R.id.nodeRelativeLayout);
        mNodeState = (TextView) view.findViewById(R.id.nodeStatusValue);

        mMotionDetection = (TextView) view.findViewById(R.id.motionDetectionStatusLabel);
        mMotionLayout = (ViewGroup) view.findViewById(R.id.motionDetectionLayout);
        mShakeMotionDetection = initShakeAnimation(mMotionLayout);
        mLastDisplayDate=null;

        mProximityLayout = (ViewGroup) view.findViewById(R.id.proximityLayout);
        mProximity = (TextView) view.findViewById(R.id.proximityValue);
        mProximityBar = (ProgressBar) view.findViewById(R.id.proximityBar);
        mProximityBar.setMax((int) GenericRemoteFeature.PROXIMITY_DATA_MAX);

        mProximitySwitch= (CompoundButton) view.findViewById(R.id.switchEnableDistance);
        mProximityChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean newStatus) {
                mCallback.onProximitySwitchChange(mNode, newStatus);
            }
        };
        mProximitySwitch.setOnCheckedChangeListener(mProximityChangeListener);

        mPlotLayout = (ViewGroup) view.findViewById(R.id.plotLayout);
        mPlotLayout.setVisibility(View.GONE);
        mGraph = (GraphView) view.findViewById(R.id.graph);
        mSeries1 = new LineGraphSeries<>(new DataPoint[] {new DataPoint(0,0)});
        mSeries1.setColor(Color.RED);
        mGraph.addSeries(mSeries1);
        mSeries2 = new LineGraphSeries<>(new DataPoint[] {new DataPoint(0,0)});
        mSeries2.setColor(Color.BLUE);
        mGraph.addSeries(mSeries2);
        mSeries3 = new LineGraphSeries<>(new DataPoint[] {new DataPoint(0,0)});
        mSeries3.setColor(Color.GREEN);
        mGraph.addSeries(mSeries3);
        mGraph.getLegendRenderer().setVisible(true);
        mGraph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        mGraph.getViewport().setXAxisBoundsManual(true);
        mGraph.getViewport().setMinX(0);
        mGraph.getViewport().setMaxX(40);

        mMemsLayout = (ViewGroup) view.findViewById(R.id.memsLayout);
        mAcceleration = (TextView) view.findViewById(R.id.accelerationValue);
        mAccelerationSwitch= (CompoundButton) view.findViewById(R.id.switchEnableAcc);
        mAccelerationChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean newStatus)
            {
                mCallback.onAccelerationSwitchChange(mNode, newStatus);
                resetStatus();
                mStatusAcc=setStatus(newStatus,mStatusAcc,"ACCELERATION ENABLE");
            }
        };
        mAccelerationSwitch.setOnCheckedChangeListener(mAccelerationChangeListener);

        mGyroscope = (TextView) view.findViewById(R.id.gyroscopeValue);
        mGyroscopeSwitch= (CompoundButton) view.findViewById(R.id.switchEnableGyro);
        mGyroscopeChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean newStatus)
            {
                mCallback.onGyroscopeSwitchChange(mNode, newStatus);
                resetStatus();
                mStatusGyr=setStatus(newStatus,mStatusAcc,"GYROSCOPE ENABLE");
            }
        };
        mGyroscopeSwitch.setOnCheckedChangeListener(mGyroscopeChangeListener);

        mMagnetometer = (TextView) view.findViewById(R.id.magnetometerValue);
        mMagnetometerSwitch= (CompoundButton) view.findViewById(R.id.switchEnableMag);
        mMagnetometerChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean newStatus)
            {
                mCallback.onMagnetometerSwitchChange(mNode, newStatus);
                resetStatus();
                mStatusMag=setStatus(newStatus,mStatusAcc,"MAGNETOMETER ENABLE");
            }
        };
        mMagnetometerSwitch.setOnCheckedChangeListener(mMagnetometerChangeListener);

        mCubeLayout = (ViewGroup) view.findViewById(R.id.cubeLayout);
        mCubeLayout.setVisibility(View.GONE);
        mSFusionIcon = (ImageView) view.findViewById(R.id.sFusionIcon);
        mSFusionIcon.setImageResource(R.mipmap.demo_sensors_fusion);
        mSFusionTable = (TableLayout) view.findViewById(R.id.sFusionTable);
        mSFusionQI = (TextView) view.findViewById(R.id.sFusionQIValue);
        mSFusionQJ = (TextView) view.findViewById(R.id.sFusionQJValue);
        mSFusionQK = (TextView) view.findViewById(R.id.sFusionQKValue);
        mSFusionQS = (TextView) view.findViewById(R.id.sFusionQSValue);
        mGlSurfaceLayout = (TableLayout) view.findViewById(R.id.glSurfaceLayout);
        mGlSurface = (GLSurfaceView) view.findViewById(R.id.glSurface);
        mGlSurface.setEGLContextClientVersion(2);
        mGlRenderer = new GLCubeRender(view.getContext().getApplicationContext(),Color.WHITE);
        mGlRenderer.setScaleCube(1.0f);
        mGlSurface.setRenderer(mGlRenderer);
        mResetButton = (Button) view.findViewById(R.id.resetButton);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGlRenderer.resetCube();
            }//onClick
        });
        mNotSupported = (TextView) view.findViewById(R.id.notSupported);
        mNotSupported.setText("MEMS SENSOR FUSION NOT SUPPORTED!");
        mNotSupported.setTextColor(Color.RED);
        mNotSupported.setVisibility(View.GONE);

        mSFusionSwitch= (CompoundButton) view.findViewById(R.id.switchEnableSFusion);
        mSFusionChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean newStatus)
            {
                mCallback.onSFusionSwitchChange(mNode, newStatus);
                resetStatus();
                if(newStatus){
                    mStatusMSF=true;
                    mFirstQuat=true;
                    error=0;
                    mPlotLayout.setVisibility(View.GONE);
                    mNotSupported.setVisibility(View.GONE);
                    mGlSurfaceLayout.setVisibility(View.VISIBLE);
                    mCubeLayout.setVisibility(View.VISIBLE);
                    mSFusionTable.setVisibility(View.VISIBLE);
                }
                else{
                    mStatusMSF=false;
                    mCubeLayout.setVisibility(View.GONE);
                    mSFusionTable.setVisibility(View.GONE);
                }
            }
        };
        mSFusionSwitch.setOnCheckedChangeListener(mSFusionChangeListener);

        mMicLevelLayout = (ViewGroup) view.findViewById(R.id.micLevelLayout);
        mMicLevel = (TextView) view.findViewById(R.id.micLevelValue);
        mMicLevelBar = (ProgressBar) view.findViewById(R.id.micLevelBar);
        mMicLevelBar.setMax((int) GenericRemoteFeature.MIC_LEVEL_DATA_MAX);
        mMicSwitch = (CompoundButton) view.findViewById(R.id.switchEnableMic);
        mMicSwitchChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean newStatus) {
                mCallback.onMicLevelSwitchChange(mNode,newStatus);
            }
        };
        mMicSwitch.setOnCheckedChangeListener(mMicSwitchChangeListener);

        mLedSwitch = (CompoundButton) view.findViewById(R.id.ledSwitch);
    }

    private void setMotionDetection(@Nullable Date lastMovement){
        if(lastMovement==null){
            mMotionLayout.setVisibility(View.GONE);
            return;
        }//else

        //if we have already a display a date and is different from the one displayed
        if(mMotionLayout.getVisibility()==View.VISIBLE &&
                mLastDisplayDate!=null &&
                lastMovement!=mLastDisplayDate){
            if(!mShakeMotionDetection.isRunning())
                mShakeMotionDetection.start();
        }else {
            mMotionLayout.setVisibility(View.VISIBLE);
        }
        mLastDisplayDate=lastMovement;
        mMotionDetection.setText(mMotionDetection.getContext().getString(R.string.lastMotion,
                DATE_FORMAT.format(lastMovement)));
    }

    public void setProximity(int proximity) {
        if(proximity<0) {
            mProximityLayout.setVisibility(View.GONE);
            return;
        }//else
        mNotSupported.setVisibility(View.GONE);
        mProximityLayout.setVisibility(View.VISIBLE);

        mProximitySwitch.setOnCheckedChangeListener(null);
        mProximitySwitch.setChecked(mNode.proximityIsEnabled());
        mProximitySwitch.setOnCheckedChangeListener(mProximityChangeListener);

        if(proximity != GenericRemoteFeature.PROXIMITY_OUT_OF_RANGE_VALUE) {
            mProximity.setText(
                    String.format(
                            Locale.getDefault(),
                            PROXIMITY_FORMAT, proximity,
                            GenericRemoteFeature.PROXIMITY_UNIT));
            mProximityBar.setProgress(proximity);
            mProximityBar.setVisibility(View.VISIBLE);
        }else {
            mProximity.setText(R.string.proximity_out_of_range);
            mProximityBar.setVisibility(View.INVISIBLE);
        }//if-else
    }

    public boolean setStatus(boolean status, boolean mStatus, String title){
        if(status) {
            mGraph.setTitle(title);
            mGraph.setTitleColor(Color.RED);
            mStatus=true;
            mCubeLayout.setVisibility(View.GONE);
            mSFusionTable.setVisibility(View.GONE);
            mNotSupported.setVisibility(View.GONE);
            mPlotLayout.setVisibility(View.VISIBLE);
        }
        else{
            mStatus=false;
            mPlotLayout.setVisibility(View.GONE);
        }
        resetPlot();
        return mStatus;
    }

    public void resetStatus(){
        mStatusAcc=false;
        mStatusGyr=false;
        mStatusMag=false;
        mStatusMSF=false;
    }

    public void setPlot(boolean status, int X, int Y, int Z){
        if(status){
            mGraphLastXValue += 1d;
            mSeries1.appendData(new DataPoint(mGraphLastXValue, X), true, 40);
            mSeries2.appendData(new DataPoint(mGraphLastXValue, Y), true, 40);
            mSeries3.appendData(new DataPoint(mGraphLastXValue, Z), true, 40);
            mSeries1.setTitle("X = " + String.valueOf(X));
            mSeries2.setTitle("Y = " + String.valueOf(Y));
            mSeries3.setTitle("Z = " + String.valueOf(Z));
        }
    }

    public void resetPlot(){
        mSeries1.resetData(new DataPoint[] {new DataPoint(0,0)});
        mSeries2.resetData(new DataPoint[] {new DataPoint(0,0)});
        mSeries3.resetData(new DataPoint[] {new DataPoint(0,0)});
        mGraphLastXValue=0d;
    }

    public void setNodeStatus(int nodeStatus) {
        if (nodeStatus == 1) {
            mLedSwitch.setEnabled(false);
            mMicSwitch.setEnabled(false);
            mProximitySwitch.setEnabled(false);
            mAccelerationSwitch.setEnabled(false);
            mGyroscopeSwitch.setEnabled(false);
            mMagnetometerSwitch.setEnabled(false);
            mSFusionSwitch.setEnabled(false);
            mNodeLayout.setBackgroundColor(Color.GRAY);
            mNodeState.setText(String.format(Locale.getDefault(),"%s","DISCONNECTED"));
            mNodeState.setVisibility(View.VISIBLE);
        }
        else{
            mLedSwitch.setEnabled(true);
            mMicSwitch.setEnabled(true);
            mProximitySwitch.setEnabled(true);
            mAccelerationSwitch.setEnabled(true);
            mGyroscopeSwitch.setEnabled(true);
            mMagnetometerSwitch.setEnabled(true);
            mSFusionSwitch.setEnabled(true);
            mNodeState.setVisibility(View.GONE);
            mNodeLayout.setBackgroundColor(Color.WHITE);
        }
    }

    public void setAcceleration(int accelerationX, int accelerationY, int accelerationZ) {
        if(!mStatusAcc) {
            mAcceleration.setText("Acc OFF");
            mAccelerationSwitch.setOnCheckedChangeListener(null);
            mAccelerationSwitch.setChecked(mNode.accelerationIsEnabled());
            mAccelerationSwitch.setOnCheckedChangeListener(mAccelerationChangeListener);
            return;
        }
        mMemsLayout.setVisibility(View.VISIBLE);

        mAccelerationSwitch.setOnCheckedChangeListener(null);
        mAccelerationSwitch.setChecked(mNode.accelerationIsEnabled());
        mAccelerationSwitch.setOnCheckedChangeListener(mAccelerationChangeListener);
        mAcceleration.setText("Acc ON");
        setPlot(mStatusAcc,accelerationX,accelerationY,accelerationZ);
    }

    public void setGyroscope(float gyroscopeX, float gyroscopeY, float gyroscopeZ) {
        if(!mStatusGyr){
            mGyroscope.setText("Gyro OFF");
            mGyroscopeSwitch.setOnCheckedChangeListener(null);
            mGyroscopeSwitch.setChecked(mNode.gyroscopeIsEnabled());
            mGyroscopeSwitch.setOnCheckedChangeListener(mGyroscopeChangeListener);
            return;
        }
        mMemsLayout.setVisibility(View.VISIBLE);

        mGyroscopeSwitch.setOnCheckedChangeListener(null);
        mGyroscopeSwitch.setChecked(mNode.gyroscopeIsEnabled());
        mGyroscopeSwitch.setOnCheckedChangeListener(mGyroscopeChangeListener);

        if(mStatusGyr){
            mGraphLastXValue += 1d;
            mSeries1.appendData(new DataPoint(mGraphLastXValue, gyroscopeX), true, 40);
            mSeries2.appendData(new DataPoint(mGraphLastXValue, gyroscopeY), true, 40);
            mSeries3.appendData(new DataPoint(mGraphLastXValue, gyroscopeZ), true, 40);
            mSeries1.setTitle("X = " + String.valueOf(gyroscopeX));
            mSeries2.setTitle("Y = " + String.valueOf(gyroscopeY));
            mSeries3.setTitle("Z = " + String.valueOf(gyroscopeZ));
        }
        mGyroscope.setText("Gyro ON");
    }

    public void setMagnetometer(int magnetometerX, int magnetometerY, int magnetometerZ) {
        if(!mStatusMag){
            mMagnetometer.setText("Mag OFF");
            mMagnetometerSwitch.setOnCheckedChangeListener(null);
            mMagnetometerSwitch.setChecked(mNode.magnetometerIsEnabled());
            mMagnetometerSwitch.setOnCheckedChangeListener(mMagnetometerChangeListener);
            return;
        }
        mMemsLayout.setVisibility(View.VISIBLE);

        mMagnetometerSwitch.setOnCheckedChangeListener(null);
        mMagnetometerSwitch.setChecked(mNode.magnetometerIsEnabled());
        mMagnetometerSwitch.setOnCheckedChangeListener(mMagnetometerChangeListener);

        mMagnetometer.setText("Mag ON");
        setPlot(mStatusMag,magnetometerX,magnetometerY,magnetometerZ);
    }

    public void setSFusion(float sFusionQI, float sFusionQJ, float sFusionQK) {
        if(!mStatusMSF){
            mSFusionSwitch.setOnCheckedChangeListener(null);
            mSFusionSwitch.setChecked(mNode.sFusionIsEnabled());
            mSFusionSwitch.setOnCheckedChangeListener(mSFusionChangeListener);
            return;
        }
        float sFusionQS = (float) Math.sqrt(1 - (sFusionQI * sFusionQI + sFusionQJ * sFusionQJ + sFusionQK * sFusionQK));

        mSFusionSwitch.setOnCheckedChangeListener(null);
        mSFusionSwitch.setChecked(mNode.sFusionIsEnabled());
        mSFusionSwitch.setOnCheckedChangeListener(mSFusionChangeListener);

        mSFusionQI.setText(String.format(Locale.getDefault(),SFUSION_FORMAT, "QI", sFusionQI));
        mSFusionQJ.setText(String.format(Locale.getDefault(),SFUSION_FORMAT, "QJ", sFusionQJ));
        mSFusionQK.setText(String.format(Locale.getDefault(),SFUSION_FORMAT, "QK", sFusionQK));
        mSFusionQS.setText(String.format(Locale.getDefault(),SFUSION_FORMAT, "QS", sFusionQS));
        mGlRenderer.setRotation(sFusionQI,sFusionQJ,sFusionQK,sFusionQS);
        if (sFusionQI<=1 && mFirstQuat==true){
            mGlRenderer.resetCube();
            mFirstQuat=false;
            error=0;
        }
        else if(Float.isNaN(sFusionQI) && mFirstQuat==true){
            error++;
            if (error == 2){
                mGlSurfaceLayout.setVisibility(View.GONE);
                mSFusionTable.setVisibility(View.GONE);
                mNotSupported.setVisibility(View.VISIBLE);

                mNode.setSFusionEnabled(false);
                mSFusionSwitch.setEnabled(false);
                mSFusionSwitch.setClickable(false);
                mSFusionSwitch.setOnCheckedChangeListener(null);
                mSFusionSwitch.setChecked(mNode.sFusionIsEnabled());
                mSFusionSwitch.setOnCheckedChangeListener(mSFusionChangeListener);
                mSFusionIcon.setImageResource(R.mipmap.demo_sensors_fusion_off);
            }
        }
    }

    public void setMicLevel(short micLevel) {
        if(micLevel<0) {
            mMicLevelLayout.setVisibility(View.GONE);
            return;
        }//else

        mNotSupported.setVisibility(View.GONE);
        mMicSwitch.setOnCheckedChangeListener(null);
        mMicSwitch.setChecked(mNode.micIsEnabled());
        mMicSwitch.setOnCheckedChangeListener(mMicSwitchChangeListener);

        mMicLevelLayout.setVisibility(View.VISIBLE);
        mMicLevel.setText(
                String.format(
                        Locale.getDefault(),
                        MICLEVEL_FORMAT, micLevel,
                        GenericRemoteFeature.MIC_LEVEL_UNIT));
        mMicLevelBar.setProgress(micLevel);
    }

    private void updateNode(GenericRemoteNode item) {
        if(mNode==null || mNode.getId()!=item.getId()) {
            mNode = item;
            mMicSwitch.setChecked(false);
            mProximitySwitch.setChecked(false);
            mAccelerationSwitch.setChecked(false);
            mGyroscopeSwitch.setChecked(false);
            mMagnetometerSwitch.setChecked(false);
            mSFusionSwitch.setChecked(false);
        }
    }
}
