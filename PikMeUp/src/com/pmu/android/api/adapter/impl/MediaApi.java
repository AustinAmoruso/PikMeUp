package com.pmu.android.api.adapter.impl;

import android.content.Context;
import android.media.MediaPlayer;

import com.pmu.android.api.adapter.IMediaApi;

public class MediaApi implements IMediaApi {

	private MediaPlayer mp;

	public MediaPlayer getMedia(Context c, int id) {
		if (mp == null) {
			mp = MediaPlayer.create(c, id);
		}
		return mp;
	}

	public MediaPlayer getMedia(Context c, float vol, int id) {
		float log1 = (float) (Math.log(100 - vol) / Math.log(100));
		log1 = 1 - log1;
		if (mp == null) {
			mp = MediaPlayer.create(c, id);
		}
		mp.setVolume(log1, log1);
		return mp;
	}

	public void stopMedia() {
		if (mp != null) {
			mp.release();
			mp = null;
		}
	}

}
