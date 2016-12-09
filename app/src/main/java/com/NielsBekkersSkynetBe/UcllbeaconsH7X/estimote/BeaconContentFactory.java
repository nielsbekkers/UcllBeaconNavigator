package com.NielsBekkersSkynetBe.UcllbeaconsH7X.estimote;

public interface BeaconContentFactory {

    void getContent(BeaconID beaconID, Callback callback);

    interface Callback {
        void onContentReady(Object content);
    }
}
