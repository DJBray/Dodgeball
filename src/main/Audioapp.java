/**
 * This class is intended to be used with Greenfoot to allow the programmer
 * to load and play midi files.  This class should be included in a 
 * Greenfoot project as a support (other) class.  Then the programmer can
 * create a MidiPlayer object.  This class was created with help from 
 * the java programming guides at www.sun.com.
 *
 * @author Jeremiah Davis
 * @version 0.1
 */
package main;

import java.io.IOException;
import java.net.URL;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Sequence;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.InvalidMidiDataException;


public class Audioapp {
    
    /** Variable to hold the sequencer. */
    private Sequencer sequencer;

    /** Is a midi file open for playing? */
    private boolean isFileOpen;

    /**
     * A value indicating that looping should continue indefinitely rather 
     * than complete after a specific number of loops.  This is just a "wrapper"
     * so thay the user has access to this variable without importing
     * the midi Sequencer class.
     */
    public static final int CONTINUOUS = Sequencer.LOOP_CONTINUOUSLY;

    /** The currenlty loaded midi file. */
    private String midiFile;

    /** Default constructor.  Just sets isFileOpen and obtains a sequencer.*/
    public Audioapp()
    {
        isFileOpen = false;

        // Open the sequencer.
        open();

        // Set continuous play
        setLoopCount(CONTINUOUS);
    }

    /** Constructor that automatically loads a midi file for playback.
     * It does not start playback, however. */
    public Audioapp(String file)
    {
        // Open the sequencer.
        open();
    
        // Load the file for playback
        load(file);

        // Set continous play
        setLoopCount(CONTINUOUS);
    } // End MidiPlayer
    

    /**
     * getIsFileOpen() - Returns the value of IsFileOpen, just in case you want
     * to know.
     */
    public boolean getIsFileOpen()
    {
        return isFileOpen;
    }

    /**
     * getLoopCount() - Return the number of times a file should play.
     */
    public int getLoopCount()
    {
        return sequencer.getLoopCount();
    }
    
    /**
     * isPlaying() - Returns true if the sequencer is running, false otherwise.
     */
    public boolean isPlaying()
    {
        return sequencer.isRunning();
    }

    /**
     * getMidiFile() - Returns the name of the currently loaded midiFile.
     *  This value will be null if a file has not yet been successfully loaded.
     *  It will also be null if a file has not been loaded since the last time
     *  close() was called, as close will reset MidiFile to null.
     */
    public String getMidiFile()
    {
        return midiFile;
    }

    /** 
     * Does the same as above, except if midiFile is null, then it will 
     * return "Class MidiPlayer.  No midi file loaded."
     */
    public String toString()
    {
        if ( midiFile == null )
            return "Class MidiPlayer.  No midi file loaded.";
        else
            return midiFile;
    }

    /**
     * setLoopCount() - Set the number of times a file should play.  For continuous
     * playing, pass it the special static variable CONTINUOUS.  This is the default.
     *
     * @param count The number of times a file should play.  For continuous play,
     * pass MidiPlayer.CONTINOUS.
     */
    public void setLoopCount(int count) 
    {
        sequencer.setLoopCount(count);
    }

    /**
     * openMidi - Open a midi sequencer to get it ready to play.
     */
    public void open()
    {
        try {
            // Only try if we do not already have a sequencer
            if ( sequencer == null ) {
                sequencer = MidiSystem.getSequencer();
            }
            sequencer.open();
        } catch( MidiUnavailableException e) {
            System.err.println("The midi system is unavailable.");
        }
    } // End openMidi
    
    /**
     * load - load a midi file for playing.
     * 
     * @param midiFile The name of the midi file to load.
     */
    public void load(String file) 
    {
        // when we load a new file, stop playback of other file.
        stop();
        
        // Don't try all this if the sequencer is not open.
        if ( !sequencer.isOpen() ) {
            System.err.println("Cannot load a midiFile. The sequencer is not open.");
            return;
        }
        
        try {
            
            // Open a midi file
            // Get the midi file as a URL
            URL url =  getClass().getClassLoader().getResource("" + file);
           
            // Get a midi sequence from the midi system and url
            Sequence sequence = MidiSystem.getSequence(url);
            
            // Now set this midi file to the sequencer
            sequencer.setSequence(sequence);
        
            // Now set isFileOpen to true, since we successfully opened it.
            isFileOpen = true;
            // Also, set the midiFile variable
            midiFile = file;
        
            // Print a success message to the console
            System.out.println("Loaded " + midiFile + ".");
        
        } catch ( IOException e) {
            System.err.println("IO Exception opening midi file: " + midiFile);
            System.err.println(e);
            isFileOpen = false;
        } catch ( InvalidMidiDataException e) {
            System.err.println("Invalid Midi Data");
            System.err.println(e);
            isFileOpen = false;
        }
    } // End load()

    /**
     * play() - Play a loaded midi file.  The file will play the number of times
     * specified with setLoopCount().
     */
    public void play() 
    {
        // Play the midi file if it is open, and if the squencer is not already
        // playing.
        if ( !isFileOpen ) {
            System.err.println("Cannot play.  Midi file not loaded.");
            return;
        }
        if ( sequencer.isRunning() ){
            System.err.println("Cannot play.  Already playing.");
            return;
        }
        
        // Start the sequencer to start playing.
        sequencer.start();
        
    } // End play()
    
    /**
     * midiStop - Stop midi playback.
     */
    public void stop()
    {
        // If the sequencer is running, stop it.
        if ( sequencer.isRunning() ) {
            sequencer.stop();
        }
    } // End midiStop()

    /** 
     * close - Close the sequencer if it is open.
     */
    public void close() 
    {
        // Stop play back.
        stop();

        // Now close the sequencer
        if ( sequencer.isOpen() ) 
            sequencer.close();
        
        // Now set isFileOpen to false, because it is closed at this point.
        isFileOpen = false;
        midiFile = null;
    } // End close()

} // End class MidiPlayer
