package org.nathan;

import org.lwjgl.openal.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class AudioListener {
    private ArrayList<Integer> buffers = new ArrayList<>();
    private long device;
    private long context;
    public AudioListener() {
        device = ALC10.alcOpenDevice((ByteBuffer) null);
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        context = ALC10.alcCreateContext(device, (IntBuffer) null);
        ALC10.alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);
        setPosition(0, 0, 0);
        setVelocity(0, 0, 0);
    }

    public int getBuffer(String fileName) {
        int buffer = AL10.alGenBuffers();
        buffers.add(buffer);
        FileInputStream fin = null;
        BufferedInputStream bin = null;
        try {
            fin = new FileInputStream(fileName);
            bin = new BufferedInputStream(fin);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        WaveData waveFile = WaveData.create(bin);
        AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        return buffer;
    }

    public void setPosition(float x, float y, float z) {
        AL10.alListener3f(AL10.AL_POSITION, x, y, z);
    }

    public void setPosition(float x, float y) {
        AL10.alListener3f(AL10.AL_POSITION, x, y, 0);
    }

    public void setVelocity(float x, float y, float z) {
        AL10.alListener3f(AL10.AL_VELOCITY, x, y, z);
    }

    public void cleanUp() {
        for (int buffer : buffers) {
            AL10.alDeleteBuffers(buffer);
        }
        ALC10.alcSuspendContext(context);
        ALC10.alcCloseDevice(device);
    }
}