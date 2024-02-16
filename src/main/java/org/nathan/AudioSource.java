package org.nathan;

import org.lwjgl.openal.AL10;

/**
 * Contains functionality for playing audio from a certain location.
 * @author (Nathan Tao, largely influenced by https://youtu.be/BR8KjNkYURk)
 * @version (2/16/2024)
 */
public class AudioSource {
    private int sourceId;

    /**
     * Creates a new AudioSource instance.
     */
    public AudioSource() {
        sourceId = AL10.alGenSources();
        AL10.alSourcef(sourceId, AL10.AL_GAIN, 1);
        AL10.alSourcef(sourceId, AL10.AL_PITCH, 1);
        AL10.alSource3f(sourceId, AL10.AL_POSITION, 0, 0, 0);
    }

    /**
     * Plays the audio associated with a given buffer.
     * @param buffer an int representing the name of the buffer to play.
     */
    public void play(int buffer) {
        stop();
        AL10.alSourcei(sourceId, AL10.AL_BUFFER, buffer);
        AL10.alSourcePlay(sourceId);
    }

    /**
     * Sets the volume of this AudioSource.
     * @param volume a double >= 0 that will become the new volume of this AudioSource relative to this AudioSource's original audio from this AudioSource's .wav.
     */
    public void setVolume(float volume) {
        AL10.alSourcef(sourceId, AL10.AL_GAIN, volume);
    }

    /**
     * Sets the pitch of this AudioSource.
     * @param pitch a double representing the new pitch of this AudioSource relative to this AudioSource's original audio from this AudioSource's .wav.
     */
    public void setPitch(float pitch) {
        AL10.alSourcef(sourceId, AL10.AL_PITCH, pitch);
    }

    /**
     * Sets the position of this AudioSource instance, therefore changing the directional audio.
     * @param x a float representing the new x position of this AudioSource
     * @param y a float representing the new y position of this AudioSource
     * @param z a float representing the new z position of this AudioSource
     */
    public void setPosition(float x, float y, float z) {
        AL10.alSource3f(sourceId, AL10.AL_POSITION, x, y, z);
    }

    /**
     * Sets the position of this AudioSource instance, therefore changing the directional audio.
     * @param x a float representing the new x position of this AudioSource
     * @param y a float representing the new y position of this AudioSource
     */
    public void setPosition(float x, float y) {
        setPosition(x, y, 2);  // 2 is intentional for more gradual sound changes
    }

    /**
     * Sets the velocity of this AudioSource, therefore changing the directional audio.
     * @param x a float representing the new x velocity of this AudioSource
     * @param y a float representing the new y velocity of this AudioSource
     * @param z a float representing the new z velocity of this AudioSource
     */
    public void setVelocity(float x, float y, float z) {
        AL10.alSource3f(sourceId, AL10.AL_VELOCITY, x, y, z);
    }

    /**
     * Sets if this AudioSource should loop (play on repeat) audio.
     * @param loop whether this AudioSource should loop audio.
     */
    public void setLooping(boolean loop) {
        AL10.alSourcei(sourceId, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
    }

    /**
     * Stops this AudioSource from playing sound.
     */
    public void stop() {
        AL10.alSourceStop(sourceId);
    }

    /**
     * Deletes the source buffer for this AudioSource. This should be the last method ever called for this AudioSource instance.
     */
    public void delete() {
        stop();
        AL10.alDeleteSources(sourceId);
    }
}
