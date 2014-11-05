package testrunner.android.com.testsrunner;

import android.test.AndroidTestCase;

import testrunner.android.com.testsrunner.DbUtils.DataSource;

/**
 * Created by marjan on 11/1/2014.
 */
public class DataSourceTest extends AndroidTestCase {


    public static final String NAME = "TestName1";
    public static final String TEST_NAME_2 = "TestName2";
    public static final String TEST_NAME_3 = "TestName3";
    private DataSource mDataSource;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mDataSource = new DataSource(getContext());
        mDataSource.open();
        mDataSource.deleteAll();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        mDataSource.deleteAll();
        mDataSource.close();
    }

    public void test_insert_getEmailByName(){
        mDataSource.insert(NAME);
        assertEquals(NAME, mDataSource.getEmailByName(NAME).get(0).getEmailName());
    }

    public void test_delete(){
        mDataSource.insert(NAME);
        mDataSource.delete(NAME);
        assertTrue(mDataSource.getEmailByName(NAME).isEmpty());
    }

    public void test_deleteAll(){
        mDataSource.insert(NAME);
        mDataSource.insert(TEST_NAME_2);
        mDataSource.insert(TEST_NAME_3);
        mDataSource.deleteAll();
        assertTrue(mDataSource.getAllData().isEmpty());
    }

    public void test_getAllData(){
        mDataSource.insert(NAME);
        mDataSource.insert(TEST_NAME_2);
        mDataSource.insert(TEST_NAME_3);
        assertTrue(mDataSource.getAllData().size() == 3);
    }
}
