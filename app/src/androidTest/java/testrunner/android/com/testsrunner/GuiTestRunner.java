package testrunner.android.com.testsrunner;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by marjan on 11/1/2014.
 */
public class GuiTestRunner extends ActivityInstrumentationTestCase2<MyActivity> {

    public static final String EMAIL_1_TEST = "email1";
    private MyActivity myActivity;

    public GuiTestRunner() {
        super(MyActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        myActivity = getActivity();

        myActivity.deleteAllFromDB();
    }

    @Override
    protected void tearDown() throws Exception {
        myActivity.deleteAllFromDB();
        super.tearDown();
    }

    public void test_PressSearchWithOutSearchWord() {
        Solo solo = new Solo(getInstrumentation(), myActivity);
        getInstrumentation().waitForIdleSync();

        Button searchButton = (Button) myActivity.findViewById(R.id.search_button);

        TouchUtils.clickView(this, searchButton);

        assertTrue(solo.searchText(myActivity.getString(R.string.error_message)));
    }


    public void test_InsertNewEmail() throws InterruptedException {
        Solo solo = new Solo(getInstrumentation(), myActivity);
        getInstrumentation().waitForIdleSync();

        final EditText editText = (EditText) myActivity.findViewById(R.id.search_edit_text);

        myActivity.runOnUiThread(new Runnable() {
            public void run() {
                editText.setText(EMAIL_1_TEST);
            }
        });

        Button adButton = (Button) myActivity.findViewById(R.id.add_button);

        Button searchButton = (Button) myActivity.findViewById(R.id.search_button);

        synchronized (this) {
            TouchUtils.clickView(this, adButton);

            wait(5000L);

            TouchUtils.clickView(this, searchButton);

            wait(5000L);
        }

        assertTrue(solo.searchText(EMAIL_1_TEST));
    }
}
