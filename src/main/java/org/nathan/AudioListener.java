package org.nathan;

import org.lwjgl.openal.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

/**
 * Contains functionality for a listener to audio sources.
 * @author (Nathan Tao, largely influenced by https://youtu.be/BR8KjNkYURk)
 * @version (2/16/2024)
 */
public class AudioListener {
    private ArrayList<Integer> buffers = new ArrayList<>();
    private long device;
    private long context;

    /**
     * Creates a new AudioListener instance.
     */
    public AudioListener() {
        device = ALC10.alcOpenDevice((ByteBuffer) null);
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        context = ALC10.alcCreateContext(device, (IntBuffer) null);
        ALC10.alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);
        setPosition(0, 0, 0);
        setVelocity(0, 0, 0);
    }

    /**
     * Creates and returns a name corresponding a buffer corresponding to a file.
     * @param fileName a String representing the file path of the .wav audio file to create a buffer of.
     * @returns an int representing the name of the generated buffer.
     */
    public int getBuffer(String fileName) {
        int buffer = AL10.alGenBuffers();
        buffers.add(buffer);
        FileInputStream fin;
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

    /**
     * Sets the position of this AudioListener, therefore changing the directional audio.
     * @param x a float representing the new x position of this AudioListener
     * @param y a float representing the new y position of this AudioListener
     * @param z a float representing the new z position of this AudioListener
     */
    public void setPosition(float x, float y, float z) {
        AL10.alListener3f(AL10.AL_POSITION, x, y, z);
    }

    /**
     * Sets the position of this AudioListener, therefore changing the directional audio.
     * @param x a float representing the new x position of this AudioListener
     * @param y a float representing the new y position of this AudioListener
     */
    public void setPosition(float x, float y) {
        AL10.alListener3f(AL10.AL_POSITION, x, y, 0);
    }

    /**
     * Sets the velocity of this AudioListener, therefore changing the directional audio.
     * @param x a float representing the new x velocity of this AudioListener
     * @param y a float representing the new y velocity of this AudioListener
     * @param z a float representing the new z velocity of this AudioListener
     */
    public void setVelocity(float x, float y, float z) {
        // NOTE: I did not end up needing to use this method in my project because I did not choose to implement the
        //       feature I originally intended to implement that would have used this method.
        AL10.alListener3f(AL10.AL_VELOCITY, x, y, z);
    }

    /**
     * Deletes all buffers. This should be the last method ever called for this AudioListener object.
     */
    public void cleanUp() {
        for (int buffer : buffers) {
            AL10.alDeleteBuffers(buffer);
        }
    }
}