package com.pmu.android.api.storage.impl;

import java.io.IOException;
import java.io.OutputStreamWriter;

import android.content.Context;

public class FileApi {

	public static void saveFile(Context context, String name, String content) {
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					context.openFileOutput(name, Context.MODE_PRIVATE));
			outputStreamWriter.write(content);
			outputStreamWriter.close();
		} catch (IOException e) {
		}
	}

	public static String getPath(Context context, String name) {
		return "file://" + name;
	}
}
