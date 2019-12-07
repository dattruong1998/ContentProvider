package Module;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestPattern {
    private static final String PATTERN_MA="[\\d]+";
    private static final String PATTERN_SOCHI="[\\d]+";
    public boolean TestMa(String ma)
    {
        Pattern pattern=Pattern.compile(PATTERN_MA);
        Matcher matcher=pattern.matcher(ma);
        if(matcher.find())
        {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean TestSoChi(String ma)
    {
        Pattern pattern=Pattern.compile(PATTERN_SOCHI);
        Matcher matcher=pattern.matcher(ma);
        if(matcher.find())
        {
            return true;
        }
        else {
            return false;
        }
    }

}
