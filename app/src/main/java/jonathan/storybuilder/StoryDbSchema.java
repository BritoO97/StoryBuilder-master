package jonathan.storybuilder;

/**
 * Created by Joe on 2/29/2016.
 */
public class StoryDbSchema {
    public static final class StoryTable {
        public static final String NAME = "stories";

        public static final class Cols {
            public static final String TITLE = "title";
            public static final String RESPONSE = "response";
            public static final String LINE1 = "line1";
            public static final String LINE2 = "line2";
            public static final String LINE3 = "line3";
            public static final String LINE4 = "line4";
            public static final String LINE5 = "line5";
            public static final String LINE6 = "line6";
            public static final String LINE7 = "line7";
            public static final String LINE8 = "line8";
            public static final String LINE9 = "line9";
            public static final String FINAL_LINE = "final_line";
            public static final String COMPLETE = "complete";
        }
    }

    public static final class PointsTable {
        public static final String NAME = "score";

        public static final class Cols {
            public static final String POINTS = "points";
        }
    }


}
