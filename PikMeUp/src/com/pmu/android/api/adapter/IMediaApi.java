package com.pmu.android.api.adapter;

import android.content.Context;
import android.media.MediaPlayer;

public interface IMediaApi {

	MediaPlayer getMedia(Context c, int id);

	void stopMedia();

	MediaPlayer getMedia(Context c, float vol, int id);

}
