package HQ.Planner.utilities;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import HQ.Planner.model.Event;
import HQ.Planner.model.Movie;
import HQ.Planner.model.Planner;

public class MFileHandler {

    private static Planner planner = Planner.getShared();

    public static void loadFromFile(Context context) {
        if (planner.getAllEvents().size() == 0) {
            initMovieList(context);
            initEventList(context);
        }
    }

    private static void initEventList(Context context) {

        String EVENT_FILE_NAME = "events.txt";
        String string = readTextFileString(EVENT_FILE_NAME, context);
        String[] temp = string.split("\n");
        List<Event> eventList = new ArrayList<>();

        for (String line : temp) {
            if (line.startsWith("//")) {
                continue;
            }
            String[] splitLine = line.split(",");
            if (splitLine.length == 7) {
                System.out.println();
                eventList.add(new Event(

                                splitLine[1],         //title
                                splitLine[4],         //venue
                                (splitLine[5] + "," + splitLine[6]),//location
                                MDate.toDate(splitLine[2]),// start date
                                MDate.toDate(splitLine[3]) // end date

                        )
                );
            } else {
                System.out.println("Error when Add file data to EVENTS.");
            }
        }
        planner.setAllEvent(eventList);
        MToast.say(context,"Load Event List From File:"+EVENT_FILE_NAME);

    }

    private static void initMovieList(Context context) {
        String MOVIE_FILE_NAME = "movies.txt";
        String string = readTextFileString(MOVIE_FILE_NAME, context);
        String[] temp = string.split("\n");
        List<Movie> movieList = new ArrayList<>();

        for (String line : temp) {
            if (line.startsWith("// ")) {
                continue;
            }
            String[] splitLine = line.split(",");
            if (splitLine.length == 4) {
                movieList.add(
                        new Movie(
                                splitLine[1],
                                splitLine[2],
                                splitLine[3]
                        )
                );
            } else {
                System.out.println("Error when Add file data to MOVIES.");
            }
        }
        planner.setAllMovies(movieList);
        MToast.say(context,"Load Movie List From File:"+MOVIE_FILE_NAME);
    }


    private static List<String> readTextFile(String fileName, Context context) {
        String line;
        List<String> lines = new ArrayList<String>();
        try {
            InputStream fileInputStream = context.getAssets().open(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null) {
                line.replace("\"", "");
                lines.add(line + System.getProperty("line.separator"));
            }
            fileInputStream.close();
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            String TAG = "MainActivity";
            Log.d(TAG, ex.getMessage());
        } catch (IOException ex) {
            String TAG = "MainActivity";
            Log.d(TAG, ex.getMessage());
        }
        return lines;
    }

    private static String readTextFileString(String fileName, Context context) {
        String s = "";
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            s = new String(buffer);
        } catch (IOException e) {
            System.out.println("Read file error.");
        }
        return s.replace("\"", "");
    }


}
