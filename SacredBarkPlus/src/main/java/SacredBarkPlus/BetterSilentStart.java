package SacredBarkPlus;

import basemod.BaseMod;
import basemod.interfaces.*;


import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

@SpireInitializer
public class BetterSilentStart implements
        PostInitializeSubscriber,
        AddAudioSubscriber{

    public static final String ELITE = "NodeSpeaker:elite";
    public static final String ENEMY = "NodeSpeaker:enemy";
    public static final String MERCHANT = "NodeSpeaker:merchant";
    public static final String REST = "NodeSpeaker:rest";
    public static final String TREASURE = "NodeSpeaker:treasure";
    public static final String UNKNOWN = "NodeSpeaker:unknown";




    public static void initialize() {
        BaseMod.subscribe(new BetterSilentStart());
    }

    @Override
    public void receivePostInitialize() {

    }


    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio(ELITE, "nodeSpeakerResources/audio/elite.mp3");
        BaseMod.addAudio(ENEMY, "nodeSpeakerResources/audio/enemy.mp3");
        BaseMod.addAudio(MERCHANT, "nodeSpeakerResources/audio/merchant.mp3");
        BaseMod.addAudio(REST, "nodeSpeakerResources/audio/rest.mp3");
        BaseMod.addAudio(TREASURE, "nodeSpeakerResources/audio/treasure.mp3");
        BaseMod.addAudio(UNKNOWN, "nodeSpeakerResources/audio/unknown.mp3");
    }
}